package com.tyrcl.gameplay.model;


/**
 * Inferface for all orders.
 * Ensures that every order is associated with a player and defines an execution method.
 */

public interface Order {

    /**
     * Executes the order. Must be implemented by subclasses.
     */
    public abstract void execute();

    /**
     * Validates whether the order can be executed.
     *
     * @return true if the order is valid and can be executed, otherwise false.
     */
    public abstract boolean validate();

    /**
     * Validates whether the order can be created.
     * 
     * @return true if the order is valid and can be created, otherwise false.
     */
    public abstract boolean preValidate();

    /**
     * Prints a representation of the order.
     */
    public void print();

    /**
     * Get name of the order
     */
    public String getName();
}

