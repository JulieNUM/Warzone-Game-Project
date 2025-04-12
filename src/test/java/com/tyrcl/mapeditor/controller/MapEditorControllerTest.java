package com.tyrcl.mapeditor.controller;

import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*; 
import com.tyrcl.mapeditor.model.Map; 
import com.tyrcl.mapeditor.controller.MapEditorController; 


/**
 * Unit test class for testing the {@link MapEditorController} class.
 * This class contains tests for the functionality of adding/removing continents, countries,
 * setting up neighbors, and saving/loading maps.
 * 
 * The test cases focus on ensuring that the map's internal state is correctly modified
 * after each operation and that appropriate exceptions are not thrown for valid operations.
 */
public class MapEditorControllerTest {

    /**
     * Test that the {@link MapEditorController#addContinent(String, String)} method
     * adds a continent to the map successfully.
     */
    @Test
    public void testAddContinent() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);

        // Act
        l_controller.addContinent("Asia", "5");

        // Assert
        assertNotNull(l_map.d_continents.get(1), "Continent 'Asia' should be added to the map.");
    }

    /**
     * Test that the {@link MapEditorController#addContinent(String, String)} method
     * does not add a continent with an invalid value (non-numeric value for the continent's bonus).
     */
    @Test
    public void testAddContinentWithInvalidValue() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);

        // Act
        l_controller.addContinent("Europe", "invalid");

        // Assert
        assertTrue(l_map.d_continents.isEmpty(), "Continent should not be added with invalid value.");
    }

    /**
     * Test that the {@link MapEditorController#removeContinent(String)} method
     * removes a continent from the map successfully.
     */
    @Test
    public void testRemoveContinent() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Africa", "3");

        // Act
        l_controller.removeContinent("Africa");

        // Assert
        assertTrue(l_map.d_continents.isEmpty(), "Continent 'Africa' should be removed from the map.");
    }

    /**
     * Test that the {@link MapEditorController#addCountry(String, String)} method
     * adds a country to the map successfully.
     */
    @Test
    public void testAddCountry() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Asia", "5");

        // Act
        l_controller.addCountry("India", "Asia");

        // Assert
        assertNotNull(l_map.d_countries.get(1), "Country 'India' should be added to the map.");
    }

    /**
     * Test that the {@link MapEditorController#addCountry(String, String)} method
     * does not add a country to the map with an invalid continent.
     */
    @Test
    public void testAddCountryWithInvalidContinent() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);

        // Act
        l_controller.addCountry("Germany", "Europe");

        // Assert
        assertTrue(l_map.d_countries.isEmpty(), "Country should not be added with invalid continent.");
    }

    /**
     * Test that the {@link MapEditorController#removeCountry(String)} method
     * removes a country from the map successfully.
     */
    @Test
    public void testRemoveCountry() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Asia", "5");
        l_controller.addCountry("India", "Asia");

        // Act
        l_controller.removeCountry("India");

        // Assert
        assertTrue(l_map.d_countries.isEmpty(), "Country 'India' should be removed from the map.");
    }

    /**
     * Test that the {@link MapEditorController#addNeighbor(String, String)} method
     * adds a neighbor relationship between two countries successfully.
     */
    @Test
    public void testAddNeighbor() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Asia", "5");
        l_controller.addCountry("India", "Asia");
        l_controller.addCountry("China", "Asia");

        // Act
        l_controller.addNeighbor("India", "China");

        // Assert
        assertTrue(l_map.d_adjacencyList.get(1).contains(2), "China should be a neighbor of India.");
    }

    /**
     * Test that the {@link MapEditorController#removeNeighbor(String, String)} method
     * removes a neighbor relationship between two countries successfully.
     */
    @Test
    public void testRemoveNeighbor() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Asia", "5");
        l_controller.addCountry("India", "Asia");
        l_controller.addCountry("China", "Asia");
        l_controller.addNeighbor("India", "China");

        // Act
        l_controller.removeNeighbor("India", "China");

        // Assert
        assertFalse(l_map.d_adjacencyList.get(1).contains(2), "China should no longer be a neighbor of India.");
    }

    /**
     * Test that the {@link MapEditorController#saveMap(String)} method saves the map without exceptions.
     */
    @Test
    public void testSaveMap() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);
        l_controller.addContinent("Asia", "5");
        l_controller.addCountry("India", "Asia");

        // Act & Assert
        assertDoesNotThrow(() -> l_controller.saveMap(l_map.getMapType(), "testMap.txt"), "Saving the map should not throw an exception.");
    }

    /**
     * Test that the {@link MapEditorController#loadMap(String)} method loads a map without exceptions.
     */
    @Test
    public void testLoadMap() {
        // Arrange
        Map l_map = new Map();
        MapEditorController l_controller = new MapEditorController(l_map);

        // Act & Assert
        assertDoesNotThrow(() -> l_controller.loadMap(l_map.getMapType(),"test_map.txt"), "Loading the map should not throw an exception.");
    }
}
