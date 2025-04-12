package com.tyrcl.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import com.tyrcl.gameplay.engine.GameEngine;

/**
 * Unit test class for testing the {@link End} class.
 * 
 * This class contains tests for the various methods in the {@link End} class that
 * should throw an {@link UnsupportedOperationException}. Each test checks if the
 * corresponding method correctly throws the exception with the expected message.
 */
public class EndTest {

    private End d_endPhase;
    private GameEngine d_mockGameEngine;

    /**
     * Sets up the necessary test objects before each test method.
     * Initializes the mock GameEngine and End phase objects.
     */
    @BeforeEach
    public void setUp() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
    }

    /**
     * Test that the {@link End#loadMap()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'loadMap'".
     */
    @Test
    public void testLoadMap_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.loadMap());
        assertEquals("Unimplemented method 'loadMap'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#showMap()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'showMap'".
     */
    @Test
    public void testShowMap_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.showMap());
        assertEquals("Unimplemented method 'showMap'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#editMap()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'editMap'".
     */
    @Test
    public void testEditMap_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.editMap());
        assertEquals("Unimplemented method 'editMap'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#reinforce()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'reinforce'".
     */
    @Test
    public void testReinforce_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.reinforce());
        assertEquals("Unimplemented method 'reinforce'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#endGame()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'endGame'".
     */
    @Test
    public void testEndGame_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.endGame());
        assertEquals("Unimplemented method 'endGame'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#next()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'next'".
     */
    @Test
    public void testNext_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.next());
        assertEquals("Unimplemented method 'next'", l_exception.getMessage());
    }

    /**
     * Test that the {@link End#startUp()} method throws an
     * {@link UnsupportedOperationException}.
     * The method should throw the exception with the message "Unimplemented method
     * 'startUp'".
     */
    @Test
    public void testStartUp_ShouldThrowException() {
        d_mockGameEngine = new GameEngine(); // Assuming a default constructor exists
        d_endPhase = new End(d_mockGameEngine);
        Exception l_exception = assertThrows(UnsupportedOperationException.class, () -> d_endPhase.startUp());
        assertEquals("Unimplemented method 'startUp'", l_exception.getMessage());
    }

}
