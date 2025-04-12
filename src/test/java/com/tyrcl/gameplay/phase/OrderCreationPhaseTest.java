package com.tyrcl.gameplay.phase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

public class OrderCreationPhaseTest {

    @Mock
    private GameEngine mockGameEngine;
    
    @Mock
    private Player mockPlayer1, mockPlayer2;
    
    @Mock
    private Map mockMap;
    
    @Mock
    private Country mockCountry1, mockCountry2;
    
    private OrderCreation orderCreation;
    private List<Player> players;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        players = new ArrayList<>(Arrays.asList(mockPlayer1, mockPlayer2));
        
        when(mockGameEngine.getPlayers()).thenReturn(players);
        when(mockGameEngine.getGameMap()).thenReturn(mockMap);
        
        orderCreation = new OrderCreation(mockGameEngine);
    }


    @Test
    public void testAllPlayersHavePlacedAllOrders_AllTrue() {
        when(mockPlayer1.hasPlacedAllOrders()).thenReturn(true);
        when(mockPlayer2.hasPlacedAllOrders()).thenReturn(true);
        
        assertTrue(orderCreation.allPlayersHavePlacedAllOrders());
    }

    @Test
    public void testAllPlayersHavePlacedAllOrders_OneFalse() {
        when(mockPlayer1.hasPlacedAllOrders()).thenReturn(true);
        when(mockPlayer2.hasPlacedAllOrders()).thenReturn(false);
        
        assertFalse(orderCreation.allPlayersHavePlacedAllOrders());
    }

    @Test
    public void testResetPlayersForNewTurn() {
        orderCreation.resetPlayersForNewTurn();
        
        verify(mockPlayer1).resetForNewTurn();
        verify(mockPlayer2).resetForNewTurn();
    }

    @Test
    public void testAssgiReinforcements() {
        when(mockPlayer1.getd_ownedCountries()).thenReturn(Arrays.asList(mockCountry1, mockCountry2));
        when(mockPlayer2.getd_ownedCountries()).thenReturn(Arrays.asList(mockCountry1));
        when(mockPlayer1.calculateContinentBonuses()).thenReturn(2);
        when(mockPlayer2.calculateContinentBonuses()).thenReturn(1);
        
        orderCreation.assgiReinforcements();
        
        // Verify reinforcements were set correctly
        verify(mockPlayer1).setd_reinforcementPool(3 + 2); // 3 (base) + 2 (bonus)
        verify(mockPlayer2).setd_reinforcementPool(3 + 1); // 3 (base) + 1 (bonus)
    }

    @Test
    public void testReinforce() {
        when(mockPlayer1.hasPlacedAllOrders()).thenReturn(false, true); // First false, then true
        when(mockPlayer2.hasPlacedAllOrders()).thenReturn(false, true); // First false, then true
        
        // Simulate players issuing orders
        doAnswer(invocation -> {
            when(mockPlayer1.hasPlacedAllOrders()).thenReturn(true);
            return null;
        }).when(mockPlayer1).issue_order();
        
        doAnswer(invocation -> {
            when(mockPlayer2.hasPlacedAllOrders()).thenReturn(true);
            return null;
        }).when(mockPlayer2).issue_order();
        
        orderCreation.reinforce();
        
        // Verify players were reset
        verify(mockPlayer1).resetForNewTurn();
        verify(mockPlayer2).resetForNewTurn();
        
        // Verify reinforcements were assigned
        verify(mockPlayer1, atLeastOnce()).getd_ownedCountries();
        verify(mockPlayer2, atLeastOnce()).getd_ownedCountries();
        
        // Verify phase was changed to OrderExecution
        verify(mockGameEngine).setPhase(any(OrderExecution.class));
    }

    @Test
    public void testNext() {
        orderCreation.next();
        verify(mockGameEngine).setPhase(any(OrderExecution.class));
    }
}