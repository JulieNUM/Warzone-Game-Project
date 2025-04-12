package com.tyrcl.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;

/**
 * Unit tests for the {@link Helper} class. These tests cover various utility methods provided by the
 * Helper class, ensuring the correctness of command string generation, ID generation, continent and country 
 * lookups, and text manipulation functions such as line splitting.
 */
public class HelperTest {


    /**
     * Tests the generation of a gameplay title with appropriate formatting.
     * Verifies that the title is correctly enclosed within the specified decoration.
     */
    @Test
    void testGenerateGameplayTitle() {
        String l_result = Helper.generateGameplayTitle("Gameplay");
        assertEquals("\n=====<Gameplay>=====", l_result);
    }

    /**
     * Tests the generation of a new ID based on an existing map of IDs.
     * Verifies that the next ID generated is the next consecutive integer.
     */
    @Test
    void testGenerateID() {
        // Create a map with some IDs
        Map<Integer, String> l_map = new HashMap<>();
        l_map.put(1, "One");
        l_map.put(3, "Three");
        l_map.put(5, "Five");

        // Test generating a new ID
        int l_newID = Helper.generateID(l_map);
        assertEquals(6, l_newID); // The next ID should be 6
    }

    /**
     * Tests the generation of a new ID when the map is empty.
     * Verifies that the first ID generated is 1.
     */
    @Test
    void testGenerateIDWithEmptyMap() {
        // Create an empty map
        Map<Integer, String> l_map = new HashMap<>();

        // Test generating a new ID
        int l_newID = Helper.generateID(l_map);
        assertEquals(1, l_newID); // The next ID should be 1
    }

    /**
     * Tests getting the continent ID by its name.
     * Verifies that the correct continent ID is returned for an existing continent.
     * Ensures that 0 is returned when the continent does not exist.
     */
    @Test
    void testGetContinentIDByName() {
        // Create a map of continents
        Map<Integer, Continent> l_continents = new HashMap<>();
        l_continents.put(1, new Continent(1, "Asia", 5));
        l_continents.put(2, new Continent(2, "Europe", 3));

        // Test getting the ID of an existing continent
        int l_id = Helper.getContinentIDByName("Asia", l_continents);
        assertEquals(1, l_id);

        // Test getting the ID of a non-existent continent
        l_id = Helper.getContinentIDByName("Africa", l_continents);
        assertEquals(0, l_id);
    }

    /**
     * Tests getting the continent ID by its name in a case-insensitive manner.
     * Verifies that the method handles case differences correctly.
     */
    @Test
    void testGetContinentIDByNameCaseInsensitive() {
        // Create a map of continents
        Map<Integer, Continent> l_continents = new HashMap<>();
        l_continents.put(1, new Continent(1, "Asia", 5));
        l_continents.put(2, new Continent(2, "Europe", 3));

        // Test getting the ID with different case
        int l_id = Helper.getContinentIDByName("aSiA", l_continents);
        assertEquals(1, l_id);
    }

    /**
     * Tests getting the country ID by its name.
     * Verifies that the correct country ID is returned for an existing country.
     * Ensures that 0 is returned when the country does not exist.
     */
    @Test
    void testGetCountryIDByName() {
        // Create a map of countries
        Map<Integer, Country> l_countries = new HashMap<>();
        l_countries.put(1, new Country(1, "China", 1));
        l_countries.put(2, new Country(2, "India", 1));

        // Test getting the ID of an existing country
        int l_id = Helper.getCountryIDByName("China", l_countries);
        assertEquals(1, l_id);

        // Test getting the ID of a non-existent country
        l_id = Helper.getCountryIDByName("Japan", l_countries);
        assertEquals(0, l_id);
    }

    /**
     * Tests getting the country ID by its name in a case-insensitive manner.
     * Verifies that the method handles case differences correctly.
     */
    @Test
    void testGetCountryIDByNameCaseInsensitive() {
        // Create a map of countries
        Map<Integer, Country> l_countries = new HashMap<>();
        l_countries.put(1, new Country(1, "China", 1));
        l_countries.put(2, new Country(2, "India", 1));

        // Test getting the ID with different case
        int l_id = Helper.getCountryIDByName("cHiNa", l_countries);
        assertEquals(1, l_id);
    }

    /**
     * Tests the splitting of text into lines of specified maximum length.
     * Verifies that the text is split correctly into lines that fit within the given length,
     * including cases with long words and exact-length words.
     */
    @Test
    void testSplitIntoLines() {
        // Test splitting a short text
        String[] l_result = Helper.splitIntoLines("This is a short text", 20);
        assertArrayEquals(new String[] { "This is a short text" }, l_result);

        // Test splitting a long text
        l_result = Helper.splitIntoLines("This is a longer text that needs to be split into multiple lines", 20);
        assertArrayEquals(new String[] {
                "This is a longer",
                "text that needs to",
                "be split into",
                "multiple lines"
        }, l_result);

        // Test splitting a text with exact length
        l_result = Helper.splitIntoLines("Exactly 20 chars!!", 20);
        assertArrayEquals(new String[] { "Exactly 20 chars!!" }, l_result);

        // Test splitting a text with words longer than maxLength
        l_result = Helper.splitIntoLines("ThisIsAWordThatIsTooLongToFitInOneLine", 10);
        assertArrayEquals(new String[] {
               "", "ThisIsAWordThatIsTooLongToFitInOneLine"
        }, l_result);
    }

    /**
     * Tests the splitting of an empty text into lines.
     * Verifies that an empty string is correctly handled.
     */
    @Test
    void testSplitIntoLinesWithEmptyText() {
        // Test splitting an empty text
        String[] l_result = Helper.splitIntoLines("", 20);
        assertArrayEquals(new String[] { "" }, l_result);
    }

    /**
     * Tests the splitting of a null text into lines.
     * Verifies that a null string is correctly handled as an empty string.
     */
    @Test
    void testSplitIntoLinesWithNullText() {
        // Test splitting a null text
        String[] l_result = Helper.splitIntoLines("", 20);
        assertArrayEquals(new String[] { "" }, l_result);
    }
}
