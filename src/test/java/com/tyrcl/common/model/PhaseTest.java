package com.tyrcl.common.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.tyrcl.gameplay.engine.GameEngine;

/**
 * Unit test class for testing the {@link Phase} class and its methods.
 * 
 * This test class contains tests to ensure the correct initialization of the Phase
 * class, the handling of abstract method implementations in a mock subclass,
 * and the behavior of specific methods like printing invalid command messages.
 */
public class PhaseTest {

    /**
     * A mock subclass of {@link Phase} used to test the behavior of abstract methods.
     * It provides empty implementations of all abstract methods of {@link Phase}.
     */
    class MockPhase extends Phase {
        public MockPhase(GameEngine p_ge) {
            super(p_ge);
        }

        @Override
        public void loadMap() {}

        @Override
        public void showMap() {}

        @Override
        public void editMap() {}

        @Override
        public void startUp() {}

        @Override
        public void reinforce() {}

        @Override
        public void endGame() {}

        @Override
        public void next() {}

        @Override
        public void execution() {
         
        }

        @Override
        public void tournamentGame() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'tournamentGame'");
        }

        @Override
        public void gameData() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'gameData'");
        }
    }

    /**
     * Test the initialization of the {@link Phase} class and its dependencies.
     * Ensures that the GameEngine reference is properly initialized and not null.
     */
    @Test
    void testPhaseInitialization() {
        // Arrange
        GameEngine l_gameEngine = new GameEngine();

        // Act
        MockPhase l_mockPhase = new MockPhase(l_gameEngine);

        // Assert
        assertNotNull(l_mockPhase.d_ge, "GameEngine reference should not be null after initialization.");
    }

    /**
     * Test the {@link Phase#printInvalidCommandMessage()} method to ensure that
     * it does not throw an exception when called.
     */
    @Test
    void testPrintInvalidCommandMessage() {
        // Arrange
        GameEngine l_gameEngine = new GameEngine();
        MockPhase l_mockPhase = new MockPhase(l_gameEngine);

        // Act & Assert
        assertDoesNotThrow(() -> l_mockPhase.printInvalidCommandMessage(), 
            "printInvalidCommandMessage should not throw an exception.");
    }

    /**
     * Test the abstract methods of the {@link Phase} class to ensure that they do not
     * throw any exceptions when invoked on a {@link MockPhase} instance. This ensures
     * that the methods are implemented without error in the mock subclass.
     */
    @Test
    void testAbstractMethods() {
        // Arrange
        GameEngine l_gameEngine = new GameEngine();
        MockPhase l_mockPhase = new MockPhase(l_gameEngine);

        // Act & Assert
        assertDoesNotThrow(() -> l_mockPhase.loadMap(), "loadMap should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.showMap(), "showMap should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.editMap(), "editMap should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.startUp(), "startUp should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.reinforce(), "reinforce should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.endGame(), "endGame should not throw an exception.");
        assertDoesNotThrow(() -> l_mockPhase.next(), "next should not throw an exception.");
    }
}
