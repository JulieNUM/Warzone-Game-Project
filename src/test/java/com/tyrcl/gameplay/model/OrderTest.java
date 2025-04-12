package com.tyrcl.gameplay.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test; // Use JUnit 5's Test annotation

import com.tyrcl.gameplay.engine.GameEngine;

/**
 * Unit test class for testing the {@link Order} class.
 * 
 * This class contains tests to verify the behavior of the {@link Order} class
 * and
 * its concrete subclass, {@link MockOrder}. It checks the execution and
 * validation
 * of the order.
 */
public class OrderTest {

    /**
     * A mock subclass of {@link Order} used for testing purposes.
     * This class provides concrete implementations for the abstract methods
     * {@link Order#execute()} and {@link Order#validate()}.
     */
    private class MockOrder implements Order {

        /**
         * Constructor to create a mock order for a specified player.
         * 
         * @param d_player The player to associate with this order.
         */
        public MockOrder(Player d_player) {

        }

        /**
         * Mock implementation of the {@link Order#execute()} method.
         * This method simply prints a message indicating that the mock order has been
         * executed.
         */
        @Override
        public void execute() {
            System.out.println("MockOrder executed");
        }

        /**
         * Mock implementation of the {@link Order#validate()} method.
         * This method always returns {@code true}, indicating the order is valid.
         * 
         * @return {@code true}, meaning the mock order is always considered valid.
         */
        @Override
        public boolean validate() {
            return true;
        }

        @Override
        public boolean preValidate() {
            return true;
        }

        @Override
        public void print() {

        }

        @Override
        public String getName() {
            return "";
        }
    }

    /**
     * Test that verifies the functionality of the {@link Order} class.
     * It checks that the order can be executed and validated correctly.
     */
    @Test
    public void testOrder() {
        Player l_player = new Player("Player1", null);
        MockOrder l_mockOrder = new MockOrder(l_player);

        // Execute the order (mock implementation)
        l_mockOrder.execute();

        // Verify that the order is valid
        assertTrue(l_mockOrder.validate());
    }
}
