package com.tyrcl.gameplay.model.cards;

import org.junit.Before;
import org.junit.Test;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BlockadeOrderTest {

    private Player mockPlayer;
    private Country mockCountry;
    private LogEntryBuffer mockLogEntryBuffer;
    private BlockadeOrder blockadeOrder;

    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockCountry = mock(Country.class);
        mockLogEntryBuffer = mock(LogEntryBuffer.class);
        blockadeOrder = new BlockadeOrder(mockPlayer, mockCountry, mockLogEntryBuffer);
    }

    @Test
    public void testValidate_ValidOrder() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(true);
        when(mockPlayer.hasBlockadeCard()).thenReturn(true);
        when(mockCountry.getPlayerName()).thenReturn("Player1");

        assertTrue(blockadeOrder.validate());
    }

    @Test
    public void testValidate_InvalidOrder_NotOwnedByPlayer() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(false);
        when(mockPlayer.hasBlockadeCard()).thenReturn(true);
        when(mockCountry.getPlayerName()).thenReturn("Player1");

        assertFalse(blockadeOrder.validate());
    }

    @Test
    public void testValidate_InvalidOrder_NoBlockadeCard() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(true);
        when(mockPlayer.hasBlockadeCard()).thenReturn(false);
        when(mockCountry.getPlayerName()).thenReturn("Player1");

        assertFalse(blockadeOrder.validate());
    }

    @Test
    public void testValidate_InvalidOrder_AlreadyNeutral() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(true);
        when(mockPlayer.hasBlockadeCard()).thenReturn(true);
        when(mockCountry.getPlayerName()).thenReturn("Neutral");

        assertFalse(blockadeOrder.validate());
    }

    @Test
    public void testExecute_ValidOrder() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(true);
        when(mockPlayer.hasBlockadeCard()).thenReturn(true);
        when(mockCountry.getPlayerName()).thenReturn("Player1");
        when(mockCountry.getNumOfArmies()).thenReturn(5);

        blockadeOrder.execute();

        verify(mockCountry).setNumOfArmies(15); // 5 * 3 = 15
        verify(mockCountry).setPlayer(null);
        verify(mockPlayer).removeBlockadeCard();
    }

    @Test
    public void testExecute_InvalidOrder() {
        when(mockPlayer.ownsCountry(anyInt())).thenReturn(false);
        when(mockPlayer.hasBlockadeCard()).thenReturn(true);
        when(mockCountry.getPlayerName()).thenReturn("Player1");

        blockadeOrder.execute();

        verify(mockCountry, never()).setNumOfArmies(anyInt());
        verify(mockCountry, never()).setPlayer(null);
        verify(mockPlayer, never()).removeBlockadeCard();
    }
}