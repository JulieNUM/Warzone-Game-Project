package com.tyrcl.utils;

import java.io.*;
import java.util.*;

import com.tyrcl.common.model.MapType;
import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

public class ConquestMapFileManager {

    /**
     * Saves the map data to a file in Conquest format.
     *
     * @param p_map      The map object containing continents, countries, and borders.
     * @param p_filename The name of the file to save the map data.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void saveMap(Map p_map, String p_filename) throws IOException {
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_filename))) {
            // Write continents
            l_writer.write("[Continents]\n");
            for (Continent l_continent : p_map.getContinents().values()) {
                l_writer.write(l_continent.getContinentID() + " " + l_continent.getContinentName() + "="
                        + l_continent.getArmyValue() + "\n");
            }

            // Write countries (without x, y)
            l_writer.write("\n[Territories]\n");
            for (Country l_country : p_map.getCountries().values()) {
                StringBuilder l_sb = new StringBuilder();
                l_sb.append(l_country.getCountryID())
                     .append(",").append(l_country.getCountryName())
                     .append(",").append(getContinentNameByID(p_map, l_country.getContinentID())); // Use method to get continent name

                List<Integer> l_neighbors = p_map.getAdjacencyList().get(l_country.getCountryID());
                if (l_neighbors != null) {
                    for (Integer l_neighbor : l_neighbors) {
                        l_sb.append(",").append(l_neighbor);
                    }
                }
                l_writer.write(l_sb.toString() + "\n");
            }
        }

        System.out.println("Conquest map successfully saved to " + p_filename);
    }

    /**
     * Loads the Conquest map data from a file and populates the map object.
     *
     * @param p_map      The map object to populate with the loaded data.
     * @param p_filename The name of the file to read the map data from.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void loadMap(Map p_map, String p_filename) throws IOException {
        p_map.cleanup(); // Clear the map to prevent old data from remaining.

        List<String> l_territoryLines = new ArrayList<>();
        java.util.Map<String, Integer> l_continentNameToID = new java.util.HashMap<>();
        boolean l_readingContinents = false;
        boolean l_readingTerritories = false;

        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filename))) {
            String l_line;
            while ((l_line = l_reader.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty()) continue;

                if (l_line.equalsIgnoreCase("[Continents]")) {
                    l_readingContinents = true;
                    l_readingTerritories = false;
                    continue;
                } else if (l_line.equalsIgnoreCase("[Territories]")) {
                    l_readingContinents = false;
                    l_readingTerritories = true;
                    continue;
                }

                if (l_readingContinents) {
                    String[] l_parts = l_line.split(" ", 2);
                    if (l_parts.length < 2 || !l_parts[1].contains("=")) {
                        System.out.println("Invalid continent format: " + l_line);
                        continue;
                    }
                    int l_continentID = Integer.parseInt(l_parts[0]);
                    String[] l_nameAndValue = l_parts[1].split("=");
                    String l_continentName = l_nameAndValue[0];
                    int l_armyValue = Integer.parseInt(l_nameAndValue[1]);
                    p_map.addContinent(l_continentID, l_continentName, l_armyValue);
                    l_continentNameToID.put(l_continentName, l_continentID);
                } else if (l_readingTerritories) {
                    l_territoryLines.add(l_line);
                }
            }
        }

        // First pass: add countries
        for (String l_line : l_territoryLines) {
            String[] l_parts = l_line.split(",");
            if (l_parts.length < 3) continue;
            int l_countryID = Integer.parseInt(l_parts[0]);
            String l_countryName = l_parts[1];
            String l_continentName = l_parts[2];
            int l_continentID = l_continentNameToID.getOrDefault(l_continentName, -1);
            if (l_continentID == -1) {
                throw new IOException("Unknown continent name: " + l_continentName);
            }
            p_map.addCountry(l_countryID, l_countryName, l_continentID);
        }

        // Second pass: add neighbors
        for (String l_line : l_territoryLines) {
            String[] l_parts = l_line.split(",");
            if (l_parts.length < 4) continue;
            int l_countryID = Integer.parseInt(l_parts[0]);
            for (int i = 3; i < l_parts.length; i++) {
                try {
                    int l_neighborID = Integer.parseInt(l_parts[i]);
                    p_map.addNeighbor(l_countryID, l_neighborID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid neighbor ID: " + l_parts[i]);
                }
            }
        }

        p_map.setMapType(MapType.CONQUEST);
    }

    /**
     * Helper method to get the continent name by continent ID.
     *
     * @param p_map         The map object.
     * @param p_continentID The ID of the continent.
     * @return The continent name.
     */
    private String getContinentNameByID(Map p_map, int p_continentID) {
        Continent l_continent = p_map.getContinents().get(p_continentID);
        return l_continent != null ? l_continent.getContinentName() : "Unknown";
    }
}
