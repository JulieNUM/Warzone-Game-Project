package com.tyrcl.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.tyrcl.common.model.MapType;
import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

public class DominationMapFileManager {

    /**
     * Saves the map data to a file.
     *
     * @param p_map      The map object containing continents, countries, and
     *                   borders.
     * @param p_filename The name of the file to save the map data.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void saveMap(Map p_map, String p_filename) throws IOException {
      
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_filename))) {
            // Write continents
            l_writer.write("[continents]\n");
            for (Continent l_continent : p_map.getContinents().values()) {
                l_writer.write(l_continent.getContinentID() + " " + l_continent.getContinentName() + " "
                        + l_continent.getArmyValue() + "\n");
            }

            // Write countries (without x, y)
            l_writer.write("\n[countries]\n");
            for (Country l_country : p_map.getCountries().values()) {
                l_writer.write(l_country.getCountryID() + " " + l_country.getCountryName() + " "
                        + l_country.getContinentID() + "\n");
            }

            // Write adjacency list
            l_writer.write("\n[borders]\n");
            for (Entry<Integer, List<Integer>> l_entry : p_map.getAdjacencyList().entrySet()) {
                l_writer.write(l_entry.getKey() + "");
                for (Integer l_neighbor : l_entry.getValue()) {
                    l_writer.write(" " + l_neighbor);
                }
                l_writer.write("\n");
            }
        }

        // Added successful save confirmation output
        System.out.println("Map successfully saved to " + p_filename);
    }

    /**
     * Loads map data from a file and populates the map object.
     *
     * @param p_map      The map object to populate with the loaded data.
     * @param p_filename The name of the file to read the map data from.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void loadMap(Map p_map, String p_filename) throws IOException {
        // Clear the map to prevent old data from remaining.
        p_map.cleanup(); // Implement the cleanup method in the Map class.
        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filename))) {
            String l_line;
            while ((l_line = l_reader.readLine()) != null) {
                // Convert the read line to lowercase for comparison
                String l_lowerLine = l_line.toLowerCase();

                if (l_lowerLine.startsWith("[continents]")) {
                    // Parse continents
                    while ((l_line = l_reader.readLine()) != null && !l_line.toLowerCase().startsWith("[countries]")) {
                        if (l_line.trim().isEmpty())
                            continue;
                        String[] l_parts = l_line.split(" ");
                        int l_continentID = Integer.parseInt(l_parts[0]);
                        String l_continentName = l_parts[1];
                        int l_armyValue = Integer.parseInt(l_parts[2]);
                        p_map.addContinent(l_continentID, l_continentName, l_armyValue);
                    }
                }

                if (l_line != null && l_line.toLowerCase().startsWith("[countries]")) {
                    // Parse countries (without x, y)
                    while ((l_line = l_reader.readLine()) != null && !l_line.toLowerCase().startsWith("[borders]")) {
                        if (l_line.trim().isEmpty())
                            continue;
                        String[] l_parts = l_line.split(" ");
                        int l_countryID = Integer.parseInt(l_parts[0]);
                        String l_countryName = l_parts[1];
                        int l_continentID = Integer.parseInt(l_parts[2]);
                        p_map.addCountry(l_countryID, l_countryName, l_continentID);
                    }
                }

                if (l_line != null && l_line.toLowerCase().startsWith("[borders]")) {
                    // Parse adjacency list
                    while ((l_line = l_reader.readLine()) != null) {
                        if (l_line.trim().isEmpty())
                            continue;
                        String[] l_parts = l_line.split(" ");
                        int l_countryID = Integer.parseInt(l_parts[0]);
                        for (int i = 1; i < l_parts.length; i++) {
                            int l_neighborID = Integer.parseInt(l_parts[i]);
                            p_map.addNeighbor(l_countryID, l_neighborID);
                        }
                    }
                }
            }
            p_map.setMapType(MapType.DOMINATION);
        }
    }

}
