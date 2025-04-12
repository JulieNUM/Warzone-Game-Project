package com.tyrcl.gameplay.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.mapeditor.model.Country;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class AdvanceOrderTest {

    private Player player1;
    private Player player2;
    private Country sourceCountry;
    private Country targetCountry;
    private LogEntryBuffer logEntryBuffer;
    private AdvanceOrder advanceOrder;

    @BeforeEach
    void setUp() {
        logEntryBuffer = mock(LogEntryBuffer.class);
        player1 = new Player("P1", logEntryBuffer);
        player2 = new Player("P2", logEntryBuffer);

        sourceCountry = new Country(001, "Alinor", 1);
        targetCountry = new Country(002, "Sunhold", 1);

        sourceCountry.setPlayer(player1);
        targetCountry.setPlayer(player2);
        sourceCountry.setNumOfArmies(20);
        targetCountry.setNumOfArmies(10);
    }

    @Test
    void testValidateSuccess() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 5, logEntryBuffer);
        assertTrue(!advanceOrder.validate());
    }

    @Test
    void testValidateFailureDueToSourceOwnership() {
        sourceCountry.setPlayer(player2);
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 5, logEntryBuffer);
        assertFalse(advanceOrder.validate());
    }

    @Test
    void testValidateFailureDueToInsufficientArmies() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 25, logEntryBuffer);
        assertFalse(advanceOrder.validate());
    }


    @Test
    void testExecuteAttack() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 5, logEntryBuffer);
        advanceOrder.execute();
        assertTrue(targetCountry.getNumOfArmies() <= 10); // Defending armies should be reduced
    }

    @Test
    void testPreValidateSuccess() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 5, logEntryBuffer);
        assertTrue(!advanceOrder.preValidate());
    }



    @Test
    void testPreValidateFailureDueToInvalidArmyCount() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, 0, logEntryBuffer);
        assertFalse(advanceOrder.preValidate());

        advanceOrder = new AdvanceOrder(player1, sourceCountry, targetCountry, -5, logEntryBuffer);
        assertFalse(advanceOrder.preValidate());
    }

    @Test
    void testPreValidateFailureDueToSameSourceAndTarget() {
        advanceOrder = new AdvanceOrder(player1, sourceCountry, sourceCountry, 5, logEntryBuffer);
        assertFalse(advanceOrder.preValidate());
    }
}
