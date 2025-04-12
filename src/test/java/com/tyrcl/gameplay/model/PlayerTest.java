package com.tyrcl.gameplay.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // Use JUnit 5's Test annotation

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

/**
 * Unit test class for testing the {@link Player} class.
 * 
 * This class contains tests to verify the functionality of methods in the {@link Player}
 * class, including order handling, issuing commands, and country ownership.
 */
public class PlayerTest {

    private Player player;
    private LogEntryBuffer logEntryBuffer;

    @BeforeEach
    public void setUp() {
        logEntryBuffer = new LogEntryBuffer(); // Assuming LogEntryBuffer has a default constructor
        player = new Player("TestPlayer", logEntryBuffer);
    }

    @Test
    public void testObtainCard() {
        String result = player.obtainCard();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertFalse(player.getCards().isEmpty());
    }

    @Test
    public void testHasAirliftCard() {
        assertFalse(player.hasAirliftCard());
        player.getCards().add(Card.AIRLIFT);
        assertTrue(player.hasAirliftCard());
    }

    @Test
    public void testRemoveAirliftCard() {
        player.getCards().add(Card.AIRLIFT);
        player.removeAirliftCard();
        assertFalse(player.hasAirliftCard());
    }

    @Test
    public void testHasBlockadeCard() {
        assertFalse(player.hasBlockadeCard());
        player.getCards().add(Card.BLOCKADE);
        assertTrue(player.hasBlockadeCard());
    }

    @Test
    public void testRemoveBlockadeCard() {
        player.getCards().add(Card.BLOCKADE);
        player.removeBlockadeCard();
        assertFalse(player.hasBlockadeCard());
    }

    @Test
    public void testHasDiplomacyCard() {
        assertFalse(player.hasDiplomacyCard());
        player.getCards().add(Card.DIPLOMACY);
        assertTrue(player.hasDiplomacyCard());
    }

    @Test
    public void testRemoveDiplomacyCard() {
        player.getCards().add(Card.DIPLOMACY);
        player.removeDiplomacyCard();
        assertFalse(player.hasDiplomacyCard());
    }

    @Test
    public void testHasBoomCard() {
        assertFalse(player.hasBoomCard());
        player.getCards().add(Card.BOMB);
        assertTrue(player.hasBoomCard());
    }

    @Test
    public void testRemoveBoomCard() {
        player.getCards().add(Card.BOMB);
        player.removeBoomCard();
        assertFalse(player.hasBoomCard());
    }

    @Test
    public void testAddDiplomacyPartner() {
        Player partner = new Player("PartnerPlayer", logEntryBuffer);
        player.addDiplomacyPartner(partner);
        assertTrue(player.verifyDiplomacyPartner(partner));
    }

    @Test
    public  void testVerifyDiplomacyPartner() {
        Player partner = new Player("PartnerPlayer", logEntryBuffer);
        player.addDiplomacyPartner(partner);
        assertTrue(player.verifyDiplomacyPartner(partner));
        Player nonPartner = new Player("NonPartnerPlayer", logEntryBuffer);
        assertFalse(player.verifyDiplomacyPartner(nonPartner));
    }


    @Test
    public  void testToString() {
        player.setd_reinforcementPool(10);
        String result = player.toString();
        assertTrue(result.contains("TestPlayer"));
        assertTrue(result.contains("10"));
    }

    @Test
    public void testNextOrder() {
        assertNull(player.next_order());
        player.getd_orders().add(new DeployOrder(player, new Country(1, "TestCountry",1), 5, logEntryBuffer));
        assertNotNull(player.next_order());
    }


    @Test
    public void testSetd_ID() {
        player.setd_ID(10);
        assertEquals(10, player.getd_ID());
        assertThrows(IllegalArgumentException.class, () -> player.setd_ID(-1));
    }

    @Test
    void testSetd_name() {
        player.setd_name("NewName");
        assertEquals("NewName", player.getd_name());
        assertThrows(IllegalArgumentException.class, () -> player.setd_name(""));
    }


    @Test
    public void testSetd_orders() {
        Queue<Order> orders = new LinkedList<>();
        
        orders.add(new DeployOrder(player, new Country(1, "TestCountry",1), 5, logEntryBuffer));
        player.setd_orders(orders);
        assertEquals(orders, player.getd_orders());
        assertThrows(IllegalArgumentException.class, () -> player.setd_orders(null));
    }

    @Test
    public void testSetd_reinforcementPool() {
        player.setd_reinforcementPool(10);
        assertEquals(10, player.getd_reinforcementPool());
        assertThrows(IllegalArgumentException.class, () -> player.setd_reinforcementPool(-1));
    }


}
