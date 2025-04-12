package com.tyrcl.gameplay.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tyrcl.common.log.LogEntryBuffer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.tyrcl.mapeditor.model.Country;

/**
 * Unit test class for testing the {@link DeployOrder} class.
 * 
 * This class contains various tests that check the functionality of the
 * {@link DeployOrder} class, including creating deploy orders, executing
 * orders,
 * and validating conditions such as reinforcement pool and country existence.
 */
public class DeployOrderTest {

    private Player mockPlayer;
    private Country mockCountry;
    private LogEntryBuffer mockLogEntryBuffer;
    private DeployOrder deployOrder;

    @BeforeEach
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockCountry = mock(Country.class);
        mockLogEntryBuffer = mock(LogEntryBuffer.class);

        when(mockCountry.getCountryID()).thenReturn(1);
        when(mockPlayer.ownsCountry(1)).thenReturn(true);
        when(mockPlayer.getd_reinforcementPool()).thenReturn(10);

        deployOrder = new DeployOrder(mockPlayer, mockCountry, 5, mockLogEntryBuffer);
    }



    @Test
    public void testValidate_Failure_InvalidCountry() {
        deployOrder = new DeployOrder(mockPlayer, null, 5, mockLogEntryBuffer);
        assertFalse(deployOrder.validate());
    }



    @Test
    public void testValidate_Failure_InvalidNumberOfArmies() {
        deployOrder = new DeployOrder(mockPlayer, mockCountry, -1, mockLogEntryBuffer);
        assertFalse(deployOrder.validate());
    }



    @Test
    public void testPreValidate_Failure_InvalidCountry() {
        deployOrder = new DeployOrder(mockPlayer, null, 5, mockLogEntryBuffer);
        assertFalse(deployOrder.preValidate());
    }



    @Test
    public void testPreValidate_Failure_InvalidNumberOfArmies() {
        deployOrder = new DeployOrder(mockPlayer, mockCountry, -1, mockLogEntryBuffer);
        assertFalse(deployOrder.preValidate());
    }



}