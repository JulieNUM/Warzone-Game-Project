package com.tyrcl.gameplay.model.cards;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;

/**
 * The BlockadeOrder class represents a blockade command.
 * When executed, it triples the armies in a territory and makes it neutral.
 */
public class BlockadeOrder implements Order {

    /** The player issuing the blockade order. */
    private Player d_player;
    
    /** The target country to be blockaded. */
    private Country d_targetCountry;
    
    /** Buffer for logging order execution details. */
    private LogEntryBuffer d_logEntryBuffer;
    
    /** The new number of armies after tripling. */
    private int d_newArmyCount;
    
    /**
     * Constructs a BlockadeOrder with the specified player, target country, and log entry buffer.
     *
     * @param p_player The player issuing the order.
     * @param p_targetCountry The target country.
     * @param p_logEntryBuffer The logging buffer.
     */
    public BlockadeOrder(Player p_player, Country p_targetCountry, LogEntryBuffer p_logEntryBuffer) {
        this.d_player = p_player;
        this.d_targetCountry = p_targetCountry;
        this.d_logEntryBuffer = p_logEntryBuffer;
    }
    
    /**
     * Executes the blockade order if valid.
     * Triples the number of armies on the target country and sets the territory to neutral.
     */
    @Override
    public void execute() {
        if (validate()) {
            d_newArmyCount = d_targetCountry.getNumOfArmies() * 3;
            d_targetCountry.setNumOfArmies(d_newArmyCount);
             // Set to neutral: Use setPlayer(null) to indicate a neutral state.
            d_targetCountry.setPlayer(null);
            d_player.removeBlockadeCard();
        }
    }
    
    /**
     * Validates whether the blockade order can be executed.
     * Validation criteria:
     * 1. The target country must be owned by the player.
     * 2. The player must have a Blockade Card.
     * 3. The target country must not already be neutral.
     *
     * @return {@code true} if the order is valid; otherwise, {@code false}.
     */
    @Override
    public boolean validate() {
        if (!d_player.ownsCountry(d_targetCountry.getCountryID())) {
            System.out.println("Invalid BlockadeOrder: Target country is not owned by the player.");
            return false;
        }
        if (!d_player.hasBlockadeCard()) {
            System.out.println("Invalid BlockadeOrder: Player does not have a Blockade Card.");
            return false;
        }
          // Check neutral state: Use getPlayerName() to verify. If it returns "Neutral," 
         //it indicates that the state is already neutral.
        if (d_targetCountry.getPlayerName().equals("Neutral")) {
            System.out.println("Invalid BlockadeOrder: Target country is already neutral.");
            return false;
        }
        return true;
    }
    
    /**
     * Logs the details of the blockade order execution.
     */
    @Override
    public void print() {
        String orderMsg = "Blockade order executed by player " + d_player.getd_name() +
                          ": " + d_targetCountry.getCountryName() + " now has " +
                          d_newArmyCount + " armies and is neutral.";
        System.out.println(orderMsg);
        d_logEntryBuffer.setLogEntry(orderMsg);
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
       return "blockade";
    }

}
