package com.tyrcl.gameplay.model.cards;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;

/**
 * The DiplomacyOrder class represents a diplomacy command.
 * When executed, it prevents attacks between the issuing player and the target player until the end of the turn.
 */
public class DiplomacyOrder implements Order {

    /** The player issuing the diplomacy order. */
    private Player d_player;
    
    /** The target player with whom diplomacy is negotiated. */
    private Player d_targetPlayer;
    
    /** Buffer for logging order execution details. */
    private LogEntryBuffer d_logEntryBuffer;
    
    /**
     * Constructs a DiplomacyOrder with the specified player, target player, and log entry buffer.
     *
     * @param p_player The player issuing the order.
     * @param p_targetPlayer The target player.
     * @param p_logEntryBuffer The logging buffer.
     */
    public DiplomacyOrder(Player p_player, Player p_targetPlayer, LogEntryBuffer p_logEntryBuffer) {
        this.d_player = p_player;
        this.d_targetPlayer = p_targetPlayer;
        this.d_logEntryBuffer = p_logEntryBuffer;
    }
    
    /**
     * Executes the diplomacy order if valid.
     * Establishes a diplomatic relationship between the issuing player and the target player.
     */
    @Override
    public void execute() {
        if (validate()) {
            d_player.addDiplomacyPartner(d_targetPlayer);
            d_targetPlayer.addDiplomacyPartner(d_player);
            d_player.removeDiplomacyCard();
        }
    }
    
    /**
     * Validates whether the diplomacy order can be executed.
     * Validation criteria:
     * 1. The target player must not be the same as the issuing player.
     * 2. The issuing player must have a Diplomacy Card.
     *
     * @return {@code true} if the order is valid; otherwise, {@code false}.
     */
    @Override
    public boolean validate() {
        Player l_player = d_player;
        Player l_targetPlayer = d_targetPlayer;

        if (l_player.equals(l_targetPlayer)) {
            System.out.println("Invalid DiplomacyOrder: A player cannot form a diplomatic agreement with themselves.");
            return false;
        }
        if (!l_player.hasDiplomacyCard()) {
            System.out.println("Invalid DiplomacyOrder: The player does not have a Diplomacy Card.");
            return false;
        }
        return true;
    }
    
    /**
     * Logs the details of the diplomacy order execution.
     */
    @Override
    public void print() {
        String l_orderMsg = "Diplomacy order executed: " + d_player.getd_name() +
                            " and " + d_targetPlayer.getd_name() +
                            " have entered a diplomatic agreement. No attacks between them until the end of the turn.";
        System.out.println(l_orderMsg);
        d_logEntryBuffer.setLogEntry(l_orderMsg);
    }
    
    /**
     * Pre-validation method. Uses the same logic as validate().
     *
     * @return {@code true} if pre-validation is successful; otherwise, {@code false}.
     */
    @Override
    public boolean preValidate() {
        return validate();
    }


    @Override
    public String getName() {
       return "diplomacy";
    }
}
