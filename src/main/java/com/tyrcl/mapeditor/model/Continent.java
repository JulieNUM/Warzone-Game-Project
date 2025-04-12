package com.tyrcl.mapeditor.model;


/**
 * Represents a continent in the game. Each continent has an ID, a name, and an army value.
 */
public class Continent {
    private int d_continentID;
    private String d_continentName;
    private int d_armyValue;

    /**
     * Constructs a new Continent object.
     *
     * @param p_continentID  The unique identifier for the continent.
     * @param p_continentName The name of the continent.
     * @param p_armyValue The army value associated with controlling this continent.
     */
    public Continent(int p_continentID, String p_continentName, int p_armyValue) {
        this.d_continentID = p_continentID;
        this.d_continentName = p_continentName;
        this.d_armyValue = p_armyValue;
    }

    /**
     * Gets the continent's ID.
     *
     * @return The unique identifier of the continent.
     */
    public int getContinentID() {
        return d_continentID;
    }

    /**
     * Gets the name of the continent.
     *
     * @return The name of the continent.
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Gets the army value of the continent.
     *
     * @return The army value associated with the continent.
     */
    public int getArmyValue() {
        return d_armyValue;
    }

    /**
     * Returns a string representation of the continent.
     *
     * @return A string containing the continent's ID, name, and army value.
     */
    @Override
    public String toString() {
        return "Continent{id=" + d_continentID + ", name='" + d_continentName + "', armyValue=" + d_armyValue + "}";
    }
}
