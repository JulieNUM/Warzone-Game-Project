package com.tyrcl.gameplay.model.data;

/**
 * Represents data related to a country, including its ID, associated continent ID, 
 * name, army count, and the player's name who controls it.
 */
public class CountryData {

    /**
     * The unique identifier for the country.
     */
    public int d_ID;

    /**
     * The ID of the continent to which the country belongs.
     */
    public int d_cID;

    /**
     * The name of the country.
     */
    public String d_name;

    /**
     * The number of armies stationed in the country.
     */
    public int d_army;

    /**
     * The name of the player controlling the country.
     */
    public String d_playerName;

    /**
     * Default constructor initializing an empty CountryData object.
     */
    public CountryData() {}

    /**
     * Constructs a CountryData object with the specified details.
     *
     * @param p_ID         The unique identifier for the country.
     * @param p_cID        The ID of the continent to which the country belongs.
     * @param p_name       The name of the country.
     * @param p_army       The number of armies stationed in the country.
     * @param p_playerName The name of the player controlling the country.
     */
    public CountryData(int p_ID, int p_cID, String p_name, int p_army, String p_playerName) {
        this.d_ID = p_ID;
        this.d_name = p_name;
        this.d_cID = p_cID;
        this.d_army = p_army;
        this.d_playerName = p_playerName;
    }
}
