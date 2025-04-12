package com.tyrcl.gameplay.engine;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.tyrcl.mapeditor.model.Country;

/**
 * Unit test class for testing the {@link GameEngine} class.
 * 
 * This class contains various tests that check the functionality of the
 * {@link GameEngine} class, including adding/removing players, loading maps,
 * and retrieving countries by their IDs.
 */
public class GameEngineTest {

    private GameEngine d_gameEngine;

    /**
     * Sets up the necessary test objects before each test method.
     * Initializes a new {@link GameEngine} instance for each test.
     */
    @Before
    public void setUp() {
        d_gameEngine = new GameEngine();
    }

    /**
     * Test the {@link GameEngine#addPlayer(String)} method to ensure that
     * a player is correctly added to the game engine.
     * 
     * Verifies that the player's name is correctly set and that the player count
     * in the game engine increases accordingly.
     */
    @Test
    public void testAddPlayer() {
        d_gameEngine.addPlayer("Player1");
        assertEquals(1, d_gameEngine.getPlayers().size()); // Ensure player count is 1
        assertEquals("Player1", d_gameEngine.getPlayers().get(0).getd_name()); // Ensure the name is correctly set
    }

    /**
     * Test the {@link GameEngine#removePlayer(String)} method to ensure that
     * a player can be successfully removed from the game engine.
     * 
     * Verifies that the player is removed and the player count decreases accordingly.
     */
    @Test
    public void testRemovePlayer() {
        d_gameEngine.addPlayer("Player1");
        assertTrue(d_gameEngine.removePlayer("Player1")); // Ensure removal is successful
        assertEquals(0, d_gameEngine.getPlayers().size()); // Ensure player list is empty after removal
    }


    /**
     * Test the {@link GameEngine#loadMap(String)} method to ensure that it throws
     * an {@link IOException} when attempting to load a map from an invalid file.
     * 
     * Verifies that an exception is thrown when attempting to load a non-existent file.
     * 
     * @throws IOException if loading the map fails
     */
    @Test(expected = IOException.class)
    public void testLoadMapInvalidFile() throws IOException {
        d_gameEngine.loadMap("invalid_map.txt"); // Non-existent file
    }

    /**
     * Test the {@link GameEngine#getCountryByID(int)} method to ensure that
     * it correctly retrieves a country by its ID.
     * 
     * Verifies that the correct country is returned for an existing ID and
     * that {@code null} is returned for a non-existent country.
     */
    @Test
    public void testGetCountryByID() {
        d_gameEngine.getGameMap().d_countries.put(1, new Country(1, "Country1", 1));
        assertNotNull(d_gameEngine.getCountryByID(1)); // Ensure the country with ID 1 exists
        assertNull(d_gameEngine.getCountryByID(2)); // Ensure non-existent country returns null
    }
}
