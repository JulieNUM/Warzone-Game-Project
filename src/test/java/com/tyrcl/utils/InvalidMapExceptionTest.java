package com.tyrcl.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Unit test for the {@link InvalidMapException} class. This test ensures that the exception is thrown correctly 
 * when invalid map data is encountered and that the expected error message is returned.
 */
public class InvalidMapExceptionTest {

    /**
     * Tests the handling of {@link InvalidMapException}.
     * Ensures that an {@link InvalidMapException} is thrown with the correct error message.
     */
    @Test
    public void testInvalidMapException() {
        // Assert that the InvalidMapException is thrown with the correct message
        Exception l_exception = assertThrows(InvalidMapException.class, () -> {
            throw new InvalidMapException("Invalid map data");
        });

        // Verify that the exception message is as expected
        assertEquals("Invalid map data", l_exception.getMessage());
    }
}

