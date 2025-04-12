package com.tyrcl.gameplay.model.data;

/**
 * Represents data related to a continent, including its ID, name, and army count.
 */
public class ContinentData {

    /**
     * The unique identifier for the continent.
     */
    public int d_ID;

    /**
     * The name of the continent.
     */
    public String d_name;

    /**
     * The number of armies associated with the continent.
     */
    public int d_army;

    /**
     * Default constructor initializing an empty ContinentData object.
     */
    public ContinentData() {}

    /**
     * Constructs a ContinentData object with the specified ID, name, and army count.
     *
     * @param p_ID   The unique identifier for the continent.
     * @param p_name The name of the continent.
     * @param p_army The number of armies associated with the continent.
     */
    public ContinentData(int p_ID, String p_name, int p_army) {
        this.d_ID = p_ID;
        this.d_name = p_name;
        this.d_army = p_army;
    }
}


