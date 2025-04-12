package com.tyrcl.mapeditor.view;
import static org.junit.Assert.assertNotNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.tyrcl.gameplay.engine.GameEngine; 

/**
 * Unit tests for the {@link MapEditor} class. These tests ensure the functionality of map operations such as 
 * loading, showing, and editing a map within the context of the game.
 */
public class MapEditorTest {

    /**
     * Tests the loading of a map into the {@link GameEngine}.
     * This test verifies that a valid map can be loaded correctly from the input and that the 
     * game engine's map is not null after loading.
     */
    @Test
    void testLoadMap() {
        // Arrange: Create a new GameEngine and set up the input stream to simulate a user loading a map
        GameEngine l_gameEngine = new GameEngine();
        Scanner l_scanner = new Scanner("loadmap testMap.txt\n");
        String l_input = "loadmap test_map.txt";
        InputStream l_inputStream = new ByteArrayInputStream(l_input.getBytes());
        System.setIn(l_inputStream); // Redirect System.in to the custom input stream

        MapEditor l_mapEditor = new MapEditor(l_gameEngine, l_scanner);

        // Act: Call the loadMap method to simulate loading a map
        l_mapEditor.loadMap();

        // Assert: Ensure the game map has been loaded and is not null
        assertNotNull("Map should be loaded into the GameEngine.", l_gameEngine.getGameMap());
    }

    /**
     * Tests the showing of the map through the {@link MapEditor}.
     * This test ensures that the showMap method executes without throwing an exception and that
     * the map is not null when the operation is performed.
     */
    @Test
    void testShowMap() {
        // Arrange: Create a new GameEngine and set up the input stream to simulate the 'showmap' command
        GameEngine l_gameEngine = new GameEngine();
        Scanner l_scanner = new Scanner("showmap\n");
        String l_input = "showmap";
        InputStream l_inputStream = new ByteArrayInputStream(l_input.getBytes());
        System.setIn(l_inputStream); // Redirect System.in to the custom input stream

        MapEditor l_mapEditor = new MapEditor(l_gameEngine, l_scanner);

        // Act: Call the showMap method
        l_mapEditor.showMap();

        // Assert: Verify that the game map is not null (since showMap prints, we check for no exceptions)
        assertNotNull("showMap should not be null", l_gameEngine.getGameMap());
    }

    /**
     * Tests the editing of a map through the {@link MapEditor}.
     * This test verifies that adding a continent to the map using the editMap command works correctly
     * and that the continent is added to the game map.
     */
    @Test
    void testEditMap() {
        // Arrange: Create a new GameEngine and set up the input stream to simulate the 'editcontinent' command
        GameEngine l_gameEngine = new GameEngine();
        Scanner l_scanner = new Scanner("editcontinent -add Asia 5\n");
        String l_input = "editcontinent -add Asia 5";
        InputStream l_inputStream = new ByteArrayInputStream(l_input.getBytes());
        System.setIn(l_inputStream); // Redirect System.in to the custom input stream

        MapEditor l_mapEditor = new MapEditor(l_gameEngine, l_scanner);

        // Act: Call the editMap method to simulate adding a continent
        l_mapEditor.editMap();

        // Assert: Verify that the continent 'Asia' was added to the map
        assertNotNull("Continent 'Asia' should be added to the map.", l_gameEngine.getGameMap().d_continents.get(1));
    }
}
