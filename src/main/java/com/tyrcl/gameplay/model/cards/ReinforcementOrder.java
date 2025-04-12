package com.tyrcl.gameplay.model.cards;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;

/**
 * The ReinforcementOrder class represents a reinforcement command.
 * When executed, it grants the player 5 reinforcement army units.
 */
public class ReinforcementOrder implements Order {

    /** The player issuing the reinforcement order. */
    private Player d_player;
    
    /** Buffer for logging order execution details. */
    private LogEntryBuffer d_logEntryBuffer;
    
    /** Constant reinforcement amount granted by this order. */
    private final int REINFORCEMENT_AMOUNT = 5;

    /**
     * Constructs a ReinforcementOrder with the specified player and log entry buffer.
     *
     * @param p_player The player issuing the order.
     * @param p_logEntryBuffer The logging buffer.
     */
    public ReinforcementOrder(Player p_player, LogEntryBuffer p_logEntryBuffer) {
        this.d_player = p_player;
        this.d_logEntryBuffer = p_logEntryBuffer;
    }

    /**
     * Executes the reinforcement order if valid.
     * Grants the player 5 reinforcement armies and removes the Reinforcement card.
     */
    @Override
    public void execute() {
        if (validate()) {
            int l_currentPool = d_player.getd_reinforcementPool();
            d_player.setd_reinforcementPool(l_currentPool + REINFORCEMENT_AMOUNT);
            d_player.removeReinforcementCard();
            
        }
    }

    /**
     * Validates whether the reinforcement order can be executed.
     * Checks if the player possesses a Reinforcement Card.
     *
     * @return {@code true} if the order is valid; otherwise, {@code false}.
     */
    @Override
    public boolean validate() {
        if (!d_player.hasReinforcementCard()) {
            System.out.println("Invalid ReinforcementOrder: Player does not have a Reinforcement Card.");
            return false;
        }
        return true;
    }

    /**
     * Logs the details of the reinforcement order execution.
     */
    @Override
    public void print() {
        String l_orderMsg = "Reinforcement order executed: " + d_player.getd_name() +
                            " received " + REINFORCEMENT_AMOUNT + " reinforcement armies. " +
                            "New total reinforcement pool: " + d_player.getd_reinforcementPool() + ".";
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
       return "reinforcement";
    }
}
