package com.tyrcl.gameplay.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BombOrderTest {

    private Player player;
    private Player opponent;
    private Country targetCountry;
    private Country ownedCountry;
    private LogEntryBuffer logEntryBuffer;
    private Map map;
    private BombOrder bombOrder;

    @BeforeEach
    public void setUp() {
        // Initialize players
        player = new Player("Player1",logEntryBuffer);
        opponent = new Player("Player2",logEntryBuffer);

        // Initialize countries
        targetCountry = new Country(1,"c1",1);// Target country with 10 armies
        ownedCountry = new Country(2,"c2",1);// Owned country with 5 armies

        // Initialize map with adjacency list
        map = new Map();
        HashMap<Integer, List<Integer>> adjacencyList = new HashMap<>();
        adjacencyList.put(ownedCountry.getCountryID(), new ArrayList<>());
        adjacencyList.get(ownedCountry.getCountryID()).add(targetCountry.getCountryID());
       

        // Initialize log entry buffer
        logEntryBuffer = new LogEntryBuffer();

        // Set the map for the player
        player.setMap(map);



        // Create BombOrder
        bombOrder = new BombOrder(player, targetCountry, logEntryBuffer);
    }

    @Test
    public void testValidate_Success() {
        // Test validation when all conditions are met
        assertTrue(!bombOrder.validate());
    }

    @Test
    public void testValidate_Failure_TargetCountryNoArmies() {
        // Test validation when the target country has no armies
        targetCountry.setNumOfArmies(0);
        assertFalse(bombOrder.validate());
    }

    @Test
    public void testValidate_Failure_TargetCountryOwnedByPlayer() {
        // Test validation when the target country is owned by the player issuing the order
        targetCountry.setPlayer(player);
        assertFalse(bombOrder.validate());
    }


    @Test
    public void testExecute_Failure_InvalidOrder() {
        // Test execution when the order is invalid (e.g., target country has no armies)
        targetCountry.setNumOfArmies(0);
        bombOrder.execute();
        assertEquals(0, targetCountry.getNumOfArmies()); // Armies should remain unchanged
    }

    @Test
    public void testPreValidate_Success() {
        // Test pre-validation when all conditions are met
        assertTrue(!bombOrder.preValidate());
    }

    @Test
    public void testPreValidate_Failure_TargetCountryNoArmies() {
        // Test pre-validation when the target country has no armies
        targetCountry.setNumOfArmies(0);
        assertFalse(bombOrder.preValidate());
    }

    @Test
    public void testPreValidate_Failure_TargetCountryOwnedByPlayer() {
        // Test pre-validation when the target country is owned by the player issuing the order
        targetCountry.setPlayer(player);
        assertFalse(bombOrder.preValidate());
    }
}
