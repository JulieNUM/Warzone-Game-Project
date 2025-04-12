package com.tyrcl.mapeditor.controller;

import java.io.IOException;

import com.tyrcl.common.model.MapType;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.utils.Helper;
import com.tyrcl.utils.MapFileHandler;

/**
 * Manages user input and updates the model and view.
 */
public class MapEditorController {
    private Map l_map;

    /**
     * Constructs the controller with a map object
     * 
     * @param p_map the map object
     */
    public MapEditorController(Map p_map) {
        this.l_map = p_map;
    }

    /**
     * Add continent with specified information, it is used for the command line
     * 
     * @param p_continentID    the name of the continent
     * @param p_continentValue a bonus value that determines the number of armies
     *                         per turn that is given
     *                         to a player that controls all of it
     */
    public void addContinent(String p_continentID, String p_continentValue) {
        try {
            int l_continentValue = Integer.parseInt(p_continentValue);
            int l_continentID = Helper.getContinentIDByName(p_continentID, l_map.d_continents);
            if (l_continentID == 0) {
                l_continentID = Helper.generateID(l_map.d_continents);
                l_map.addContinent(l_continentID, p_continentID, l_continentValue);
            } else {
                System.out.println("Continent '" + p_continentID + "' already exists.");
            }
        } catch (NumberFormatException e) {
            System.out.println("continentValue requires a number format. Please provide a numeric value");
        }
    }

    /**
     * Remove a continent from the map, it is used for the command line
     * 
     * @param p_continentID the name of the continent
     */
    public void removeContinent(String p_continentID) {
        int l_continentID = Helper.getContinentIDByName(p_continentID, l_map.d_continents);
        if (l_continentID != 0) {
            l_map.removeContinent(l_continentID);
        } else {
            System.out.println("Continent '" + p_continentID + "' not found.");
        }
    }

    /**
     * Add a country with specified information, it is used for the command line
     * 
     * @param p_countryID   the name of the country
     * @param p_continentID the name of the continent that the country belongs to
     */
    public void addCountry(String p_countryID, String p_continentID) {
        int l_countryID = Helper.getCountryIDByName(p_countryID, l_map.d_countries);
        int l_continentID = Helper.getContinentIDByName(p_continentID, l_map.d_continents);
        if (l_countryID == 0) {
            if (l_continentID != 0) {
                l_countryID = Helper.generateID(l_map.d_countries);
                l_map.addCountry(l_countryID, p_countryID, l_continentID);
            } else {
                System.out.println("Continent '" + p_continentID + "' not found.");
            }
        } else {
            System.out.println("Country '" + p_countryID + " already exists.");
        }
    }

    /**
     * Remove a country with a specified ID, it is used for the command line
     * 
     * @param p_countryID the name of the country
     */
    public void removeCountry(String p_countryID) {
        int l_countryID = Helper.getCountryIDByName(p_countryID, l_map.d_countries);
        if (l_countryID == 0) {
            System.out.println("Country '" + l_countryID + "' not found.");
        } else {
            l_map.removeCountry(l_countryID);
        }
    }

    /**
     * Add a neighbor to a country with specified information, it is used for the
     * command line
     * 
     * @param p_countryID  the ID of the country
     * @param p_neighborID the ID of the neighbor country
     */
    public void addNeighbor(String p_countryID, String p_neighborID) {
        int l_countryID = Helper.getCountryIDByName(p_countryID, l_map.d_countries);
        int l_neighborID = Helper.getCountryIDByName(p_neighborID, l_map.d_countries);
        if (l_countryID != 0) {
            if (l_neighborID != 0) {
                l_map.addNeighbor(l_countryID, l_neighborID);
                // l_map.addNeighbor(l_neighborID, l_countryID);
            } else {
                System.out.println("Country '" + l_countryID + "' not found.");
            }
        } else {
            System.out.println("Neighbor country '" + p_neighborID + "' not found.");
        }
    }

    /**
     * Remove the neighbor relationship between two countries, it is used for the
     * command line
     * 
     * @param p_countryID  the name of the country
     * @param p_neighborID the name of the neighbor country
     */
    public void removeNeighbor(String p_countryID, String p_neighborID) {
        int l_countryID = Helper.getCountryIDByName(p_countryID, l_map.d_countries);
        int l_neighborID = Helper.getCountryIDByName(p_neighborID, l_map.d_countries);
        if (l_countryID != 0) {
            if (l_neighborID != 0) {
                l_map.removeNeighbor(l_countryID, l_neighborID);
            } else {
                System.out.println("Country '" + l_countryID + "' not found.");
            }
        } else {
            System.out.println("Neighbor country '" + p_neighborID + "' not found.");
        }
    }

    /**
     * display map data in the console.
     */
    public void showMap() {
        boolean l_isValid = l_map.validateMap();
        if (l_isValid) {
            l_map.showMap();
        }
    }

    /**
     * Perform map validation
     */
    public void validateMap() {
        l_map.validateMap();
    }

    /**
     * Save the current map data to a txt file
     * 
     * @param p_fileName the name of the txt file
     */
    public void saveMap(MapType p_mapType, String p_fileName) {
        try {
            boolean l_valid = l_map.validateMap();
            if (l_valid) {
                MapFileHandler.saveMap(l_map, p_fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a txt file into a map object
     * 
     * @param p_fileName the name of the file
     * @throws IOException error occurs when the map doesn't exist.
     */
    public Map loadMap(MapType p_mapType, String p_fileName) throws IOException {

        MapFileHandler.loadMap(l_map, p_fileName);
        l_map.validateMap();

        return l_map;
    }

}
