package com.tyrcl.mapeditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Unit test class for testing the initialization of a {@link Continent} object.
 * This class contains a test for ensuring that the properties of the Continent, such as 
 * ID, name, and army value, are correctly set during initialization.
 */
public class ContinentTest {

    /**
     * Tests the initialization of a Continent object.
     * Ensures that the continent ID, name, and army value are correctly set.
     * 
     * <p>
     * This test checks that when a new {@link Continent} is created with specific parameters,
     * the properties of the continent (ID, name, and army value) match the values passed to the constructor.
     * </p>
     */
    @Test
    public void TestContinentInitialization() {
        // Arrange: Create a new Continent object with ID 1, name "North America", and army value 5
        Continent l_continent = new Continent(1, "North America", 5);

        // Act & Assert: Verify that the continent's ID, name, and army value match the expected values
        assertEquals(1, l_continent.getContinentID(), "Continent ID should be correctly set.");
        assertEquals("North America", l_continent.getContinentName(), "Continent name should be correctly set.");
        assertEquals(5, l_continent.getArmyValue(), "Continent army value should be correctly set.");
    }
}
