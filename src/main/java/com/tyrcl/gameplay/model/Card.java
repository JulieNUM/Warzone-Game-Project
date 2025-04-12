package com.tyrcl.gameplay.model;

/**
 * The {@code Card} enum represents different types of cards that can be used in the game.
 * Each card has a specific type associated with it, which determines its effect.
 */
public enum Card {
    
    /** Card that allows a player to bomb an enemy country. */
    BOMB("bomb"),
    
    /** Card that allows a player to place a blockade on a country. */
    BLOCKADE("blockade"),
    
    /** Card that allows a player to airlift armies between countries. */
    AIRLIFT("airlift"),
    
    /** Card that allows a player to establish diplomacy with another player. */
    DIPLOMACY("diplomacy");

    /** The type of the card represented as a string. */
    private final String type;

    /**
     * Constructs a {@code Card} with the specified type.
     * 
     * @param type the string representation of the card type
     */
    Card(String type) {
        this.type = type;
    }

    /**
     * Returns the type of the card.
     * 
     * @return a string representing the card type
     */
    public String getType() {
        return type;
    }
}
