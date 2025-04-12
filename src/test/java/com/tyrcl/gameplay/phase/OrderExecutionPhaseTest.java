package com.tyrcl.gameplay.phase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Map;


@ExtendWith(MockitoExtension.class)
public class OrderExecutionPhaseTest {

    @Mock
    private GameEngine mockGameEngine;
    
    @Mock
    private Player mockPlayer1, mockPlayer2;
    
    @Mock
    private Order mockOrder1, mockOrder2, mockQuitOrder;
    
    @Mock
    private Map mockMap;
    
    private OrderExecution orderExecution;
    private List<Player> players;

    @BeforeEach
    public void setUp() {
        players = new ArrayList<>(Arrays.asList(mockPlayer1, mockPlayer2));
        
        // Properly mock the GameEngine behavior
        when(mockGameEngine.getPlayers()).thenReturn(players);
        when(mockGameEngine.getGameMap()).thenReturn(mockMap);
        
        // Initialize the class under test
        orderExecution = new OrderExecution(mockGameEngine);
    }


}