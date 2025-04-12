package com.tyrcl.mapeditor.model;

import java.util.*;

import com.tyrcl.common.model.MapType;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.utils.Helper;
import com.tyrcl.utils.InvalidMapException;

/**
 * The Map class is used to manage continents, countires and adjacencies.
 */
public class Map {
    public java.util.Map<Integer, Continent> d_continents = new java.util.HashMap<>();
    public java.util.Map<Integer, Country> d_countries = new java.util.HashMap<>();
    public java.util.Map<Integer, List<Integer>> d_adjacencyList = new java.util.HashMap<>();
    public MapType d_mapType;

    /**
     * Initiate map
     */
    public Map()
    {
        this.d_mapType = MapType.DOMINATION;
    }

    /**
     * Get all continenets
     * 
     * @return a list of continents
     */
    public java.util.Map<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * Get all countries
     * 
     * @return a list of countries
     */
    public java.util.Map<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Get all countries and their neighbors
     * 
     * @return a list of countries and their neighbors
     */
    public java.util.Map<Integer, List<Integer>> getAdjacencyList() {
        return d_adjacencyList;
    }

    /**
     * Add a continent with specified information, it is used for the map file
     * handler
     * 
     * @param p_continentID   the ID of the continent (int)
     * @param p_continentName the Name of the continent
     * @param p_armyValue     a bonus value that determines the number of armies
     *                        per turn that is given
     *                        to a player that controls all of it
     */
    public void addContinent(int p_continentID, String p_continentName, int p_armyValue) {
        if (!d_continents.containsKey(p_continentID)) {
            d_continents.put(p_continentID, new Continent(p_continentID, p_continentName, p_armyValue));
            System.out.println(
                    "Added Continent: id=" + p_continentID + ", name='" + p_continentName + "', armyValue="
                            + p_armyValue);
        } else {
            System.out.println("Continent with ID " + p_continentID + " already exists.");
        }
    }

    /**
     * Remove a continent from the map, it is used for the map file handler
     * 
     * @param p_continentID the ID of the continent
     */
    public void removeContinent(int p_continentID) {
        if (d_continents.containsKey(p_continentID)) {
            // Remove related countries
            d_countries.values().removeIf(l_country -> l_country.getContinentID() == p_continentID);
            d_continents.remove(p_continentID);
            System.out.println("Continent " + p_continentID + " and its associated countries removed.");
        } else {
            System.out.println("Continent with ID " + p_continentID + " not found.");
        }
    }

    /**
     * Add a country with specified information, it is used for the map file handler
     * 
     * @param p_countryID   the ID of the country
     * @param p_countryName the name of the country
     * @param p_continentID the ID of the continent that the country belongs to
     */
    public void addCountry(int p_countryID, String p_countryName, int p_continentID) {
        if (d_continents.containsKey(p_continentID)) {
            if (!d_countries.containsKey(p_countryID)) {
                d_countries.put(p_countryID, new Country(p_countryID, p_countryName, p_continentID));
                d_adjacencyList.putIfAbsent(p_countryID, new ArrayList<>());
                System.out.println(
                        "Added Country: id=" + p_countryID + ", name='" + p_countryName + "', continentID="
                                + p_continentID);
            } else {
                System.out.println("Country with ID " + p_countryID + " already exists.");
            }
        } else {
            System.out.println("Continent with ID " + p_continentID + " not found.");
        }
    }

    /**
     * Remove a country with specified ID, it is used for the map file handler
     * 
     * @param p_countryID the ID of the country
     */
    public void removeCountry(int p_countryID) {
        if (d_countries.containsKey(p_countryID)) {
            d_countries.remove(p_countryID);
            d_adjacencyList.remove(p_countryID);
            d_adjacencyList.values().forEach(l_neighbors -> l_neighbors.remove(Integer.valueOf(p_countryID)));
            System.out.println("Removed Country: id=" + p_countryID);
        } else {
            System.out.println("Country with ID " + p_countryID + " not found.");
        }
    }

    /**
     * Add a neighbor to a country with specified information, it is used for the
     * map file handler
     * 
     * @param p_countryID  the ID of the country
     * @param p_neighborID the ID if the neighbor contry
     */
    public void addNeighbor(int p_countryID, int p_neighborID) {
        if (d_countries.containsKey(p_countryID) && d_countries.containsKey(p_neighborID)) {
            if (!d_adjacencyList.get(p_countryID).contains(p_neighborID)) {
                d_adjacencyList.get(p_countryID).add(p_neighborID);
                d_adjacencyList.get(p_neighborID).add(p_countryID); // Bidirectional
                // update 'Added neighbor' output
                System.out.println("Added neighbor: " + d_countries.get(p_countryID).getCountryName() +
                        " <-> " + d_countries.get(p_neighborID).getCountryName());

                // delete "Neighbor relationship already exists..." extra output

            }
        } else {
            System.out.println("Invalid country IDs.");
        }
    }

    /**
     * Remove the neighbor relationship between two countries, it is used for the
     * map file handler
     * 
     * @param p_countryID  the ID of the country
     * @param p_neighborID the ID of the neighbor country
     */
    public void removeNeighbor(int p_countryID, int p_neighborID) {
        if (d_countries.containsKey(p_countryID) && d_countries.containsKey(p_neighborID)) {
            d_adjacencyList.get(p_countryID).remove(Integer.valueOf(p_neighborID));
            d_adjacencyList.get(p_neighborID).remove(Integer.valueOf(p_countryID)); // Bidirectional
            System.out.println("Removed neighbor: " + p_countryID + " <-> " + p_neighborID);
        } else {
            System.out.println("Invalid country IDs.");
        }
    }

    /**
     * Show the map details including continents, countries and adjacencies.
     */
    public void showMap() {

        System.out.println("Map Information:");

        // Calculate maximum widths for each column
        int l_idWidth = "Country ID".length();
        int l_nameWidth = "Country Name".length();
        int l_continentWidth = "Continent".length();
        int l_playerWidth = "Player".length();
        int l_armyWidth = "Army".length();
        int l_neighboursWidth = "Neighbours".length();

        for (Country l_country : d_countries.values()) {
            l_nameWidth = Math.max(l_nameWidth, l_country.getCountryName().length());
            l_continentWidth = Math.max(l_continentWidth,
                    d_continents.get(l_country.getContinentID()).getContinentName().length());
            l_playerWidth = Math.max(l_playerWidth, l_country.getPlayerName().length());
            l_armyWidth = Math.max(l_armyWidth, String.valueOf(l_country.getNumOfArmies()).length());
            l_neighboursWidth = l_nameWidth + String.valueOf(d_countries.size()).length() + 3;
        }

        // Print header
        String l_format = "‖ %-" + l_idWidth + "s | %-" + l_nameWidth + "s | %-" + l_continentWidth + "s | %-"
                + l_playerWidth + "s | %-" + l_armyWidth + "s | %-" + l_neighboursWidth + "s ‖%n";
        String l_inner = "‖-" + "-".repeat(l_idWidth) + "-+-" + "-".repeat(l_nameWidth) + "-+-"
                + "-".repeat(l_continentWidth) + "-+-" + "-".repeat(l_playerWidth) + "-+-" + "-".repeat(l_armyWidth)
                + "-+-" + "-".repeat(l_neighboursWidth) + "-‖";
        String l_outer = "==" + "=".repeat(l_idWidth) + "===" + "=".repeat(l_nameWidth) + "==="
                + "=".repeat(l_continentWidth) + "===" + "=".repeat(l_playerWidth) + "===" + "=".repeat(l_armyWidth)
                + "===" + "=".repeat(l_neighboursWidth) + "==";

        System.out.println(l_outer);
        System.out.printf(l_format, "Country ID", "Country Name", "Continent", "Player", "Army", "Neighbours");
        System.out.println(l_outer);

        // Print each country's information
        for (Country l_country : d_countries.values()) {
            String l_continentName = d_continents.get(l_country.getContinentID()).getContinentName();
            List<String> l_neighbors = outputNeighborsForMap(l_country);
            System.out.printf(l_format, l_country.getCountryID(), l_country.getCountryName(),
                    l_continentName,
                    l_country.getPlayerName(), l_country.getNumOfArmies(), l_neighbors.get(0).toString());
            for (int i = 1; i < l_neighbors.size(); i++) {
                System.out.printf(l_format, "", "", "", "", "", l_neighbors.get(i).toString());
            }
            if (l_country.getCountryID() == d_countries.size()) {
                System.out.println(l_outer);
            } else {
                System.out.println(l_inner);
            }
        }

    }

    /**
     * Clears all data in the map before loading a new one.
     */
    public void cleanup() {
        d_continents.clear();
        d_countries.clear();
        d_adjacencyList.clear();
        System.out.println(" Map data has been cleared before loading a new map.");
    }

    /**
     * validate the map object, print errors if any
     */
    public boolean validateMap() {
        Boolean l_isValid = true;
        try {
            if (d_continents.isEmpty() || d_countries.isEmpty()) {
                throw new InvalidMapException("Map must contain at least one continent and one country.");
            }
            // Check for duplicate IDs
            Set<Integer> l_continentIDs = new HashSet<>();
            Set<Integer> l_countryIDs = new HashSet<>();
            for (Continent l_continent : d_continents.values()) {
                if (!l_continentIDs.add(l_continent.getContinentID())) {
                    throw new InvalidMapException("Duplicate continent ID found: " + l_continent.getContinentID());
                }
            }
            for (Country l_country : d_countries.values()) {
                if (!l_countryIDs.add(l_country.getCountryID())) {
                    throw new InvalidMapException("Duplicate country ID found: " + l_country.getCountryID());
                }
            }
            // Check if all countries belong to valid continents
            for (Country l_country : d_countries.values()) {
                if (!d_continents.containsKey(l_country.getContinentID())) {
                    throw new InvalidMapException(
                            "Country " + l_country.getCountryName() + " belongs to an invalid continent.");
                }
            }
            // Check if the map is fully connected
            Set<Integer> l_visitedCountries = new HashSet<>();
            if (!d_countries.isEmpty()) {
                markCountryVisited(d_countries.keySet().iterator().next(), l_visitedCountries);
            }
            if (l_visitedCountries.size() != d_countries.size()) {
                throw new InvalidMapException("Map is not fully connected.");
            }
        } catch (InvalidMapException e) {
            l_isValid = false;
            System.out.println("The map validation process has completed. Errors were detected and require attention: "
                    + e.getMessage());
        }
        if (l_isValid) {
            System.out.println("The map has been successfully validated. No errors were found.");
        }
        return l_isValid;
    }

    /**
     * Find all countries that are reachable from a given country
     * 
     * @param p_countryID        the current country being visited
     * @param p_visitedCountries all countries that have been visited
     */
    private void markCountryVisited(int p_countryID, Set<Integer> p_visitedCountries) {
        p_visitedCountries.add(p_countryID);
        for (Integer neighborID : d_adjacencyList.getOrDefault(p_countryID, Collections.emptyList())) {
            if (!p_visitedCountries.contains(neighborID)) {
                markCountryVisited(neighborID, p_visitedCountries);
            }
        }
    }

    /**
     * Randomly assign countries to each player
     * 
     * @param p_players            the list of players
     * @param p_countriesPerPlayer the number of countries to be assigned to each
     *                             player
     * @return list of players and their countries
     */
    public java.util.Map<Player, List<Country>> assignAdjacentCountriesToPlayers(
            List<Player> p_players, int p_countriesPerPlayer) {
        java.util.Map<Player, List<Country>> l_assignments = new HashMap<>();
        List<Integer> l_availableCountries = new ArrayList<>(d_adjacencyList.keySet());
        Collections.shuffle(l_availableCountries); // Shuffle the list of countries to randomize the assignment
        boolean l_valid = true;
        for (Player l_player : p_players) {
            List<Integer> l_assignedCountries = new ArrayList<>();
            Integer l_startingCountry = findStartingCountry(l_availableCountries);
            if (l_startingCountry != null) {
                l_assignedCountries.add(l_startingCountry);
                l_availableCountries.remove(l_startingCountry);

                // Assign adjacent countries
                while (l_assignedCountries.size() < p_countriesPerPlayer && !l_availableCountries.isEmpty()) {
                    Integer l_lastAssigned = l_assignedCountries.get(l_assignedCountries.size() - 1);
                    List<Integer> l_adjacentCountries = d_adjacencyList.get(l_lastAssigned);
                    Collections.shuffle(l_adjacentCountries);

                    boolean l_foundAdjacent = false;
                    for (Integer l_adjacent : l_adjacentCountries) {
                        if (l_availableCountries.contains(l_adjacent)) {
                            l_assignedCountries.add(l_adjacent);
                            l_availableCountries.remove(l_adjacent);
                            l_foundAdjacent = true;
                            break;
                        }
                    }
                    if (!l_foundAdjacent) {
                        break; // No more adjacent countries available
                    }
                }
            }
            List<Country> l_assignedCountryList = new ArrayList<>();
            int l_countryCounter = 0;
            for (Integer l_country : l_assignedCountries) {
                Country l_assignedCountry = d_countries.get(l_country);
                l_assignedCountry.setPlayer(l_player);
                l_assignedCountryList.add(l_assignedCountry);
                l_countryCounter++;
            }
            if (l_countryCounter < p_countriesPerPlayer) {
                l_valid = false;
            }
            l_player.setd_ownedCountries(l_assignedCountryList);
            l_player.setMap(this);
            l_assignments.put(l_player, l_assignedCountryList);
        }

        this.printPlayerCountryList(l_assignments, l_valid);
        return l_assignments;
    }

    /**
     * Locate the starting country in the available country list
     * 
     * @param p_availableCountries list of available countries
     * @return the ID of the first country found in the list
     */
    private Integer findStartingCountry(List<Integer> p_availableCountries) {
        for (Integer l_country : p_availableCountries) {
            if (!d_adjacencyList.get(l_country).isEmpty()) {
                return l_country;
            }
        }
        return null; // No valid starting country found
    }

    /**
     * Print out the information that shows all players and the countries that have
     * been assigned.
     * 
     * @param p_playerAssignments A list of players and their assigned countries
     */
    private void printPlayerCountryList(
            java.util.Map<Player, List<Country>> p_playerAssignments, Boolean p_isValid) {
        if (!p_isValid) {
            System.out.println(
                    "!!-Please note that there may not be enough countries available for all players. Kindly verify the assignments below and make adjustments as necessary.-!!");
        }
        for (Player l_key : p_playerAssignments.keySet()) {
            Player l_player = l_key;
            List<Country> l_assignedCountries = p_playerAssignments.get(l_key);
            for (Country l_country : l_assignedCountries) {
                System.out.println(l_player.getd_name() + ": " + l_country.getCountryName() + "("
                        + l_country.getCountryID() + ")");
            }
        }

    }

    /**
     * Retrieves and formats the list of neighboring countries for a given country.
     * This method looks up the neighboring countries based on the adjacency list and 
     * returns a list of strings, each representing a neighboring country in the format 
     * {[countryID] countryName}.
     * 
     * The method uses the country's ID to find its neighbors in the adjacency list and then 
     * retrieves the country details (ID and name) from the {d_countries} map.
     * 
     * @param p_country The country for which the neighbors are to be retrieved.
     *                  The country must not be {null}.
     * 
     * @return A list of strings where each string represents a neighboring country in the format 
     *         {[countryID] countryName}. Returns an empty list if the country has no neighbors.
     * 
     * @throws NullPointerException if the country or adjacency list is {null}.
     */
    public List<String> outputNeighborsForOrder(Country p_country) {
        List<String> l_neighborArray = new ArrayList<String>();
        List<Integer> l_neighbors = d_adjacencyList.get(p_country.getCountryID());
        for (Integer l_neighborID : l_neighbors) {
            Country l_neighborCountry = d_countries.get(l_neighborID);
            String l_newNeighbor = String.format(
                    "[ID: %d, Player: %s, Armies: %d, Name: %s]",
                    l_neighborCountry.getCountryID(),l_neighborCountry.getPlayerName(), l_neighborCountry.getNumOfArmies(),
                    l_neighborCountry.getCountryName()   
            );
            l_neighborArray.add(l_newNeighbor);
        }
        return l_neighborArray;
    }

        /**
     * Retrieves and formats the list of neighboring countries for a given country.
     * This method looks up the neighboring countries based on the adjacency list and 
     * returns a list of strings, each representing a neighboring country in the format 
     * {[countryID] countryName}.
     * 
     * The method uses the country's ID to find its neighbors in the adjacency list and then 
     * retrieves the country details (ID and name) from the {d_countries} map.
     * 
     * @param p_country The country for which the neighbors are to be retrieved.
     *                  The country must not be {null}.
     * 
     * @return A list of strings where each string represents a neighboring country in the format 
     *         {[countryID] countryName}. Returns an empty list if the country has no neighbors.
     * 
     * @throws NullPointerException if the country or adjacency list is {null}.
     */
    public List<String> outputNeighborsForMap(Country p_country) {
        List<String> l_neighborArray = new ArrayList<String>();
        List<Integer> l_neighbors = d_adjacencyList.get(p_country.getCountryID());
        for (Integer l_neighborID : l_neighbors) {
            String l_newNeighbor = "[" + d_countries.get(l_neighborID).getCountryID() + "] "
                    + d_countries.get(l_neighborID).getCountryName();
            l_neighborArray.add(l_newNeighbor);
        }
        return l_neighborArray;
    }

    /**
     * Checks if two countries are adjacent.
     *
     * @param p_country1 The first country to check.
     * @param p_country2 The second country to check.
     * @return true if the two countries are adjacent, false otherwise.
     */
    public boolean areAdjacent(Country p_country1, Country p_country2) {
        return d_adjacencyList.get(p_country1.getCountryID()).contains(p_country2.getCountryID());
    }

    /**
     * Checks if the map contains a specific country.
     *
     * @param p_country the country to check
     * @return true if the map contains the country, false otherwise
     */
    public boolean hasCountry(Country p_country) {
        for (Country l_country : this.getCountries().values()) {
            if (l_country.equals(p_country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the map reinforcement.
     */
    public void initMapReiforcement()
    {
        for (Integer l_key : d_countries.keySet()) {
            Country l_country = d_countries.get(l_key);
            if(l_country.isNeutral())
            {
                l_country.setNumOfArmies(Helper.INITIAL_ARMY);
            }
        }
    }

    /**
     * Get the list of country IDs by continent ID.
     *
     * @param p_continentID the ID of the continent
     * @return a list of country IDs in the specified continent
     */
    public List<Integer> getCountriesByContinent(int p_continentID) {
        List<Integer> l_countryIDs = new ArrayList<>();
        for (Country l_country : d_countries.values()) {
            if (l_country.getContinentID() == p_continentID) {
                l_countryIDs.add(l_country.getCountryID());
            }
        }
        return l_countryIDs;
    }

    /**
     * Return list of neighbors 
     * @param p_country the country looking for neighbors
     * @return list of its neighbors
     */
    public List<Country> getListOfNeiborCountries(Country p_country)
    {
        List<Country> l_countries = new ArrayList<Country>();
        List<Integer> l_countryIDs = getAdjacencyList().get(p_country.getCountryID());
        for (Integer l_countryID : l_countryIDs) {
            Country l_neighborCountry = getCountries().get(l_countryID);
            l_countries.add(l_neighborCountry);
        }
        return l_countries;
    }

    /**
     * Set the type of the map by passing a param.
     * @param p_mapType the type of the map that's set to
     */
    public void setMapType(MapType p_mapType)
    {
        this.d_mapType = p_mapType;
    }

    /**
     * Get the type of the current map
     * @return the type the map
     */
    public MapType getMapType()
    {
        return this.d_mapType;
    }

}
