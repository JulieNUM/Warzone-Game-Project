package com.tyrcl.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

/**
 * Unit test class for {@link MapFileHandler}. This class tests the functionality of saving and loading
 * maps to and from files, ensuring that data is correctly written and read from files.
 */
public class MapFileHandlerTest {
    private Map d_map;
    private String TEST_FILE_NAME = "test_map_1.txt";
    private String TEST_EMPTY_FILE_NAME = "test_empty_map.txt";

    /**
     * Sets up the test data by creating a map with continents, countries, and borders.
     * This method is called before each test to ensure that the map is initialized with sample data.
     */
    private void setUp() {
        // Initialize a map with sample data
        d_map = new Map();
        d_map.addContinent(1, "Asia", 5);
        d_map.addContinent(2, "Europe", 3);

        d_map.addCountry(1, "China", 1);
        d_map.addCountry(2, "India", 1);
        d_map.addCountry(3, "France", 2);

        d_map.addNeighbor(1, 2); // China is connected to India
        d_map.addNeighbor(2, 3); // India is connected to France
    }

    /**
     * Tests the {@link MapFileHandler#saveMap(Map, String)} method by saving a map to a file
     * and verifying the contents of the file.
     * 
     * @throws IOException If an I/O error occurs during file operations.
     */
    @Test
    public void testSaveMap() throws IOException {
        setUp();
        // Save the map to a file
        MapFileHandler.saveMap(d_map, TEST_FILE_NAME);

        // Verify the file content by reading it back
        try (BufferedReader l_reader = new BufferedReader(new FileReader(TEST_FILE_NAME))) {
            String l_line;
            StringBuilder l_content = new StringBuilder();
            while ((l_line = l_reader.readLine()) != null) {
                l_content.append(l_line).append("\n");
            }

            String l_expectedContent =
                "[continents]\n" +
                "1 Asia 5\n" +
                "2 Europe 3\n" +
                "\n" +
                "[countries]\n" +
                "1 China 1\n" +
                "2 India 1\n" +
                "3 France 2\n" +
                "\n" +
                "[borders]\n" +
                "1 2\n" +
                "2 1 3\n" +
                "3 2\n";

            assertEquals(l_expectedContent, l_content.toString());
        }
    }

    /**
     * Tests the {@link MapFileHandler#loadMap(Map, String)} method by saving a map to a file
     * and then loading it back into a new map, verifying the loaded data.
     * 
     * @throws IOException If an I/O error occurs during file operations.
     */
    @Test
    public void testLoadMap() throws IOException {
        setUp();
        // First, save the map to a file
        MapFileHandler.saveMap(d_map, TEST_FILE_NAME);

        // Create a new map and load the data from the file
        Map l_loadedMap = new Map();
        MapFileHandler.loadMap(l_loadedMap, TEST_FILE_NAME);

        // Verify the loaded data
        // Check continents
        List<Continent> l_continents = new ArrayList<>(l_loadedMap.getContinents().values());
        assertEquals(2, l_continents.size());
        assertEquals("Asia", l_continents.get(0).getContinentName());
        assertEquals(5, l_continents.get(0).getArmyValue());
        assertEquals("Europe", l_continents.get(1).getContinentName());
        assertEquals(3, l_continents.get(1).getArmyValue());

        // Check countries
        List<Country> l_countries = new ArrayList<>(l_loadedMap.getCountries().values());
        assertEquals(3, l_countries.size());
        assertEquals("China", l_countries.get(0).getCountryName());
        assertEquals(1, l_countries.get(0).getContinentID());
        assertEquals("India", l_countries.get(1).getCountryName());
        assertEquals(1, l_countries.get(1).getContinentID());
        assertEquals("France", l_countries.get(2).getCountryName());
        assertEquals(2, l_countries.get(2).getContinentID());

        // Check borders
        java.util.Map<Integer, List<Integer>> l_adjacencyList = l_loadedMap.getAdjacencyList();
        assertEquals(3, l_adjacencyList.size());
        assertTrue(l_adjacencyList.get(1).contains(2)); // China -> India
        assertTrue(l_adjacencyList.get(2).contains(3)); // India -> France
    }

    /**
     * Tests the {@link MapFileHandler#loadMap(Map, String)} method with a non-existent file
     * to ensure that an {@link IOException} is thrown.
     * 
     * @throws IOException If an I/O error occurs during file operations.
     */
    @Test(expected = IOException.class)
    public void testLoadMapInvalidFile() throws IOException {
        // Attempt to load a non-existent file
        Map l_loadedMap = new Map();
        MapFileHandler.loadMap(l_loadedMap, "non_existent_file.txt");
    }
}
