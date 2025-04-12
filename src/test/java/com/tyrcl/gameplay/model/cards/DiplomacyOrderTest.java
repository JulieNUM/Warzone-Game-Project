package com.tyrcl.gameplay.model.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Player;

import static org.mockito.Mockito.*;

class DiplomacyOrderTest {

    private Player mockPlayer;
    private Player mockTargetPlayer;
    private LogEntryBuffer mockLogEntryBuffer;
    private DiplomacyOrder diplomacyOrder;

    @BeforeEach
    void setUp() {
        mockPlayer = Mockito.mock(Player.class);
        mockTargetPlayer = Mockito.mock(Player.class);
        mockLogEntryBuffer = Mockito.mock(LogEntryBuffer.class);
        diplomacyOrder = new DiplomacyOrder(mockPlayer, mockTargetPlayer, mockLogEntryBuffer);
    }



    @Test
    void testPrint() {
        when(mockPlayer.getd_name()).thenReturn("Player1");
        when(mockTargetPlayer.getd_name()).thenReturn("Player2");

        diplomacyOrder.print();

        String expectedLog = "Diplomacy order executed: Player1 and Player2 have entered a diplomatic agreement. No attacks between them until the end of the turn.";
        verify(mockLogEntryBuffer).setLogEntry(expectedLog);
    }


}
