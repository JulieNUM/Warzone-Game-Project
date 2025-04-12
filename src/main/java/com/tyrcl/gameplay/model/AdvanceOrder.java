package com.tyrcl.gameplay.model;

import com.tyrcl.mapeditor.model.Map;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.mapeditor.model.Country;

/**
 * This order is to advance armies from one country to another, which involves
 * moving armies, attacking another country, or conquering it.
 */
public class AdvanceOrder implements Order {

    /** Buffer for logging order execution details. */
    protected LogEntryBuffer d_logEntryBuffer;

    protected Player d_player;
    protected Map d_currentMap;

    protected Country d_sourceCountry;
    protected Country d_targetCountry;
    protected int d_numArmies;

    protected enum AdvanceType {
        MOVE,
        ATTACK,
        CONQUER
    }

    protected AdvanceType d_advanceType;
    protected String l_orderMsg;
    protected int d_initSourceCountryArmy;

    /**
     * Constructs an AdvanceOrder with the specified player, source country, target
     * country, number of armies, and log entry buffer.
     * 
     * @param p_player         The player issuing the order.
     * @param p_sourceCountry  The country .....
     * @param p_targetCountry  The country .....
     * @param p_numArmies      The number of advancing armies.
     * @param p_LogEntryBuffer The logging buffer for recording order execution.
     */
    public AdvanceOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry,
            int p_numArmies, LogEntryBuffer p_LogEntryBuffer) {
        d_logEntryBuffer = p_LogEntryBuffer;

        d_player = p_player;
        d_currentMap = p_player.getMap();

        d_sourceCountry = p_sourceCountry;
        d_targetCountry = p_targetCountry;
        d_numArmies = p_numArmies;

        d_advanceType = AdvanceType.MOVE;
        l_orderMsg = null;

        d_initSourceCountryArmy = d_sourceCountry.getNumOfArmies();
    }

    /**
     * Executes the Advance order.
     */
    @Override
    public void execute() {
        if (validate()) {
            if (d_advanceType == AdvanceType.ATTACK) {
                simulateBattle();
            } else if (d_advanceType == AdvanceType.CONQUER) {
                d_targetCountry.setNumOfArmies(d_targetCountry.getNumOfArmies() + d_numArmies);
                d_targetCountry.setPlayer(d_player);
                String l_cardMsg = d_player.obtainCard();
                if (!l_cardMsg.equals("")) {
                    l_orderMsg += " \n" + l_cardMsg + " \n" + "-----------------------------------------------------";  
                }
            } else {
                d_targetCountry.setNumOfArmies(d_targetCountry.getNumOfArmies() + d_numArmies);
            }
        }
    }

    /**
     * Validates the Advance order before execution.
     *
     * @return True if the player has enough armies in the source country, the
     *         target
     *         country is not a diplomacy partner, and the source country is owned
     *         by
     *         the player, false otherwise.
     */
    @Override
    public boolean validate() {

        if (d_player.verifyDiplomacyPartner(d_targetCountry.getPlayer())) {
            System.out.println("Order aborts! Diplomacy partner's country cannot be attacked");
            return false;
        }
        if (!d_player.ownsCountry(d_sourceCountry.getCountryID())) {
            System.out.println("Order aborts! Owner of source country is not the player"); // maybe warn elsewhere
            return false;
        }
        if (d_initSourceCountryArmy < d_numArmies) {
            System.out.println("Order aborts! Not enough armies in source country");
            return false;
        }
        if (d_player.ownsCountry(d_targetCountry.getCountryID())) {
            d_advanceType = AdvanceType.MOVE;
        } else if (d_targetCountry.getNumOfArmies() == 0) {
            d_advanceType = AdvanceType.CONQUER;
        } else {
            d_advanceType = AdvanceType.ATTACK;
        }
        return true;
    }

    /**
     * Prints the details of the order.
     */
    @Override
    public void print() {
        if (d_advanceType == AdvanceType.ATTACK) {
            String l_newMsg = d_player.getd_name() + " attacks " + d_targetCountry.getCountryName() + " with "
                    + d_numArmies + " armies";
            l_orderMsg = l_newMsg + "\n" + l_orderMsg;
        } else if (d_advanceType == AdvanceType.CONQUER) {
            String l_newMsg = d_player.getd_name() + " conquers " + d_targetCountry.getCountryName() + " with "
                    + d_numArmies + " armies";
            l_orderMsg = l_newMsg;
        } else {
            String l_newMsg = d_player.getd_name() + " moves " + d_numArmies + " armies from "
                    + d_sourceCountry.getCountryName() + " to " + d_targetCountry.getCountryName();
            l_orderMsg = l_newMsg;
        }
        System.out.println(l_orderMsg);
        d_logEntryBuffer.setLogEntry(l_orderMsg);
    }

    /**
     * Validates the order before creating by player. This method checks if the player 
     * owns the source country, if the two countries are adjacent, if the source country
     * has enough armies to advance, and if the parameters are valid.
     * 
     * @return true if this order is valid, false otherwise.
     */
    @Override
    public boolean preValidate() {
        if (d_sourceCountry == null || d_targetCountry == null) {
            return false;
        }
        if (!d_player.ownsCountry(d_sourceCountry.getCountryID())) {
            return false;
        }
        if (!d_currentMap.areAdjacent(d_sourceCountry, d_targetCountry)) {
            return false;
        }
        if (d_numArmies <= 0) {
            return false;
        }
        if (d_sourceCountry.getNumOfArmies() < d_numArmies) {
            return false;
        }
        if (d_sourceCountry.equals(d_targetCountry)) {
            return false;
        }
        d_sourceCountry.setNumOfArmies(d_sourceCountry.getNumOfArmies() - d_numArmies); // deduct armies while creating
                                                                                        // order TODO: maybe add it back
                                                                                        // in execute
        return true;
    }

    /**
     * Simulates a battle between the attacking and defending armies in rounds.
     */
    public void simulateBattle() {
        int l_attackingArmies = d_numArmies;
        int l_defendingArmies = d_targetCountry.getNumOfArmies();

        String l_cardMsg = "";
        String l_battleTitle = "Battle of " + d_targetCountry.getCountryName() + ": " + d_sourceCountry.getPlayerName()
                + " (" + l_attackingArmies
                + ") vs " + d_targetCountry.getPlayerName() + " (" + l_defendingArmies + ")";
        String l_line = new String(new char[l_battleTitle.length()]).replace('\0', '-');
        l_orderMsg = l_line + "\n" + l_battleTitle;
        int l_numRounds = 1;

        // Battle in rounds until one side has no armies left
        while (l_attackingArmies > 0 && l_defendingArmies > 0) {
            if (Math.random() < 0.6) {
                l_defendingArmies--;
            }
            if (Math.random() < 0.7) {
                l_attackingArmies--;
            }
            String l_battleLog = "Round " + l_numRounds + ": Attacking Armies (" + l_attackingArmies
                    + ") Defending Armies (" + l_defendingArmies + ")";
            l_orderMsg = l_orderMsg + "\n" + l_battleLog;
            l_numRounds++;
        }

        String l_battleResult = "Battle Result: ";
        if (l_defendingArmies == 0 && l_attackingArmies >= 0) {
            // Attacker wins
            d_targetCountry.setPlayer(d_player);
            d_targetCountry.setNumOfArmies(l_attackingArmies);
            l_battleResult += "Attacker wins";

            // Try to add card if player wins
            l_cardMsg = d_player.obtainCard();
        } else {
            // Defender wins
            d_targetCountry.setNumOfArmies(l_defendingArmies);
            l_battleResult += "Defender wins";
        }

        l_orderMsg = l_orderMsg + "\n" + l_line + "\n" + l_battleResult + "\n" + l_line;
        if (!l_cardMsg.equals("")) {
            l_orderMsg += " \n" + l_cardMsg+ " \n" + "-----------------------------------------------------";  
        }
    }

    @Override
    public String getName() {
       return "advance";
    }
}
