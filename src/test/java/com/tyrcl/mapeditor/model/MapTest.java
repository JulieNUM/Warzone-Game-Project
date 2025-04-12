package com.tyrcl.mapeditor.model;
import com.tyrcl.utils.InvalidMapException;
import com.tyrcl.utils.MapFileHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.Test;

/**
 * JUnit test cases for the Map, Continent, and Country classes.
 * This class tests the initialization of Country and Continent objects,
 * validates exception handling for invalid maps, and verifies basic map operations
 * such as adding continents and saving maps.
 */

public class MapTest {
    
    /**
     * Tests the initialization of a Country object.
     * Ensures that the country ID, name, and continent ID are correctly set.
     */

     @Test
    public void TestCountryInitialization() {
        Country l_country = new Country(1, "Canada", 10);
        assertEquals(1, l_country.getCountryID());
        assertEquals("Canada", l_country.getCountryName());
        assertEquals(10, l_country.getContinentID());
    }

    /**
     * Tests the initialization of a Continent object.
     * Ensures that the continent ID, name, and army value are correctly set.
     */
    
    @Test
    public void TestContinentInitialization() {
        Continent l_continent = new Continent(1, "North America", 5);
        assertEquals(1, l_continent.getContinentID());
        assertEquals("North America", l_continent.getContinentName());
        assertEquals(5, l_continent.getArmyValue());
    }

     /**
     * Tests the handling of InvalidMapException.
     * Ensures that an exception is correctly thrown and the expected message is returned.
     */
    
    @Test
    public void TestInvalidMapException() {
        Exception l_exception = assertThrows(InvalidMapException.class, () -> {
            throw new InvalidMapException("Invalid map data");
        });
        assertEquals("Invalid map data", l_exception.getMessage());
    }

     /**
     * Tests basic map operations by adding a continent to the map.
     * Ensures that the added continent is not null.
     */
    
    @Test
    public void TestMapOperations() {
        Map l_gameMap = new Map();
        l_gameMap.addContinent(1, "North America", 5);
        assertNotNull(l_gameMap.getContinents().get(1));
    }
    
}



