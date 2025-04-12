package com.tyrcl.gameplay.phase;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test; // Use JUnit 5's Test annotation

import com.tyrcl.gameplay.engine.GameEngine;


/**
 * Unit test class for testing the {@link StartupPhase} class.
 * 
 * This class tests the behavior of the {@link StartupPhase} class, specifically ensuring
 * the correct instantiation of a {@link StartupPhase} object and its basic functionality.
 */
public class StartupPhaseTest {

    private GameEngine d_gameEngine;
    private Startup d_startupPhase;

    /**
     * Sets up the necessary objects before each test method.
     * Initializes a {@link GameEngine} instance, loads the map, and sets up a {@link StartupPhase} instance.
     *
     * @throws IOException If there is an issue loading the map.
     */
    @Before
    public void setUp() throws IOException {
        d_gameEngine = new GameEngine();
        d_gameEngine.loadMap("test_map.txt"); // Ensure the test map is available in the project
        d_startupPhase = new Startup(d_gameEngine);
    }

    /**
     * Test the instantiation of the {@link StartupPhase} class.
     * This test ensures that the {@link StartupPhase} object is correctly created and is an instance of {@link StartupPhase}.
     *
     * @throws IOException If there is an issue loading the map.
     */
    @Test
    public void testInstance() throws IOException {
        d_startupPhase = new Startup(d_gameEngine);
        assertTrue(d_startupPhase instanceof Startup); // Verifies that the object is of type StartupPhase
    }
}
