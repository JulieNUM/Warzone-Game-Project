package com.tyrcl.gameplay.model;

/**
 * The {@code QuitOrder} class represents an order to quit the game.
 * This order ensures that when executed, the game will be terminated or the player will exit.
 */
public class QuitOrder implements Order {

    /**
     * Executes the quit order. This method should contain logic to terminate the game session.
     * Currently, it does nothing and should be implemented as needed.
     */
    @Override
    public void execute() {
        // Implementation for quitting the game can be added here
    }

    /**
     * Validates the quit order. Since quitting is always a valid action, this method always returns {@code true}.
     *
     * @return {@code true}, as quitting the game is always valid.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Performs a pre-validation check before executing the order.
     * This method always returns {@code true} since quitting does not require any conditions.
     *
     * @return {@code true}, as quitting does not require pre-validation.
     */
    @Override
    public boolean preValidate() {
        return true;
    }

    /**
     * Prints the quit message to the console.
     */
    @Override
    public void print() {
        System.out.println("Quit the game");
    }

    /**
     * Returns the name of this order.
     *
     * @return a string representing the order name, which is "quit".
     */
    @Override
    public String getName() {
        return "quit";
    }
}
