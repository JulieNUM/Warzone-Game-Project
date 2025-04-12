package com.tyrcl.gameplay.model.cards;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.AdvanceOrder;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.gameplay.model.Player;

/**
 * This order is to advance armies from one country to another by airlifting.
 * It extends the AdvanceOrder class and adds specific logic for airlifting
 * armies.
 */
public class AirliftOrder extends AdvanceOrder {

    /**
     * Constructor for AirliftOrder.
     * 
     * @param p_player         The player issuing the order.
     * @param p_sourceCountry  The source country from which armies are airlifted.
     * @param p_targetCountry  The target country to which armies are airlifted.
     * @param p_numArmies      The number of armies to be airlifted.
     * @param p_LogEntryBuffer The log entry buffer for logging actions.
     */
    public AirliftOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry,
            int p_numArmies, LogEntryBuffer p_LogEntryBuffer) {
        super(p_player, p_sourceCountry, p_targetCountry, p_numArmies, p_LogEntryBuffer);
    }

    /**
     * Executes the Airlift order then removes the airlift card from the player's
     * card stack.
     */
    @Override
    public void execute() {
        super.execute();
        d_player.removeAirliftCard();
    }

    /**
     * Validates the airlift order, which is the same as the advance order.
     * 
     * @return true if the order is valid, false otherwise.
     */
    @Override
    public boolean validate() {
        return (super.validate());
    }

    /**
     * Prints the details of the order.
     */
    @Override
    public void print() {
        if (d_advanceType == AdvanceType.ATTACK) {
            String l_newMsg = d_player.getd_name() + " attacks " + d_targetCountry.getCountryName() + " with "
                    + d_numArmies + " airlifted armies";
            l_orderMsg = l_newMsg + "\n" + l_orderMsg;
        } else if (d_advanceType == AdvanceType.CONQUER) {
            String l_newMsg = d_player.getd_name() + " conquers " + d_targetCountry.getCountryName() + " with "
                    + d_numArmies + " airlifted armies";
            l_orderMsg = l_newMsg;
        } else {
            String l_newMsg = d_player.getd_name() + " moves " + d_numArmies + " airlifted armies from "
                    + d_sourceCountry.getCountryName() + " to " + d_targetCountry.getCountryName();
            l_orderMsg = l_newMsg;
        }
        System.out.println(l_orderMsg);
        d_logEntryBuffer.setLogEntry(l_orderMsg);
    }

    /**
     * Validates the order before creating by player. This method checks if the
     * player has an
     * airlift card, owns the source country, and has enough armies to airlift.
     * 
     * @return true if the order is pre-validated, false otherwise.
     */
    @Override
    public boolean preValidate() {
        if (!d_player.hasAirliftCard()) {
            return false;
        }
        if (!d_player.ownsCountry(d_sourceCountry.getCountryID())) {
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
        d_sourceCountry.setNumOfArmies(d_sourceCountry.getNumOfArmies() - d_numArmies);
        return true;
    }

    @Override
    public String getName() {
       return "airlift";
    }
}