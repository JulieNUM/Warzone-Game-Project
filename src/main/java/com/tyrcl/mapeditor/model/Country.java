package com.tyrcl.mapeditor.model;

import com.tyrcl.gameplay.model.Player;

/**
 * Represents a country in the game, which belongs to a specific continent.
 */
public class Country {
    private int d_countryID;
    private String d_countryName;
    private int d_continentID;
    private Player d_player;
    private int d_numOfArmies;

    /**
     * Constructs a Country object.
     *
     * @param p_countryID   The unique identifier for the country.
     * @param p_countryName The name of the country.
     * @param p_continentID The ID of the continent to which the country belongs.
     */
    public Country(int p_countryID, String p_countryName, int p_continentID) {
        this.d_countryID = p_countryID;
        this.d_countryName = p_countryName;
        this.d_continentID = p_continentID;
    }

    /**
     * Add player to the country
     * 
     * @param p_player the player
     */
    public void setPlayer(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * set number of arimes in the country
     * 
     * @param p_numOfArmies number of armies
     */
    public void setNumOfArmies(int p_numOfArmies) {
        this.d_numOfArmies = p_numOfArmies;
    }

    /**
     * Gets the unique identifier of the country.
     *
     * @return The country ID.
     */
    public int getCountryID() {
        return d_countryID;
    }

    /**
     * Gets the name of the country.
     *
     * @return The country name.
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * Gets the ID of the continent that this country belongs to.
     *
     * @return The continent ID.
     */
    public int getContinentID() {
        return d_continentID;
    }

    /**
     * Gets the name of the player who accupied the country
     * 
     * @return the name of the player
     */
    public String getPlayerName() {
        if (this.d_player != null) {
            return this.d_player.getd_name();
        }
        return "Neutral";
    }

    /**
     * Retrieves the player who owns this country.
     * 
     * @return the {@code Player} object representing the owner of this country.
     */
    public Player getPlayer() {
        return this.d_player;
    }

    /**
     * Checks if the country is neutral (i.e., not owned by any player).
     * 
     * @return {@code true} if the country is neutral, otherwise {@code false}.
     */
    public boolean isNeutral() {
        return d_player == null;
    }

    /**
     * Gets number of Armies in the country
     * 
     * @return the numbrer of armies
     */
    public int getNumOfArmies() {
        return d_numOfArmies;
    }

    /**
     * Returns a string representation of the Country object.
     *
     * @return A string containing the country's details.
     */
    @Override
    public String toString() {
        String l_playerName = "Neutral";
        if (d_player != null) {
            l_playerName = d_player.getd_name();
        }
        return "Country{id=" + d_countryID + ", name='" + d_countryName + "',  continentID=" + d_continentID +
                ",       player='" + l_playerName + "', numOfArmies=" + d_numOfArmies + "}";
    }
}
