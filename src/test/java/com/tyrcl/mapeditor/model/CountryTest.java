package com.tyrcl.mapeditor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Unit test class for testing the initialization of a {@link Country} object.
 * This class ensures that the country ID, name, and continent ID are correctly set during initialization.
 */
public class CountryTest {

    /**
     * Tests the initialization of a {@link Country} object.
     * Ensures that the country ID, country name, and continent ID are correctly set.
     *
     * <p>
     * This test verifies that when a new {@link Country} is created with specific parameters,
     * the properties of the country (ID, name, and continent ID) are properly set as expected.
     * </p>
     */
    @Test
    public void TestCountryInitialization() {
        // Arrange: Create a new Country object with ID 1, name "Canada", and continent ID 10
        Country l_country = new Country(1, "Canada", 10);

        // Act & Assert: Verify that the country ID, name, and continent ID match the expected values
        assertEquals(1, l_country.getCountryID(), "Country ID should be correctly set.");
        assertEquals("Canada", l_country.getCountryName(), "Country name should be correctly set.");
        assertEquals(10, l_country.getContinentID(), "Continent ID should be correctly set.");
    }
}
