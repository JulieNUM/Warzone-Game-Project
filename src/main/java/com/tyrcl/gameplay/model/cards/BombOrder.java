package com.tyrcl.gameplay.model.cards;

import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;

import java.util.List;

/**
 * The BombOrder class represents a bombing action in a game where a player
 * can target an opponent's country (which is adjacent to one of the player's
 * countries)
 * and reduce its number of armies by half.
 * This class implements the {@code Order} interface.
 */
public class BombOrder implements Order {

    /** Buffer for logging order execution details. */
    private LogEntryBuffer d_logEntryBuffer;

    /** The player issuing the bomb order. */
    private Player d_player;

    /** The target country to be bombed. */
    private Country d_targetCountry;

    /** The remaining armies in the target country after execution. */
    private int d_remainingArmies;

    /** The Map object containing the adjacency list information. */
    private Map d_map;

    /**
     * Constructs a BombOrder with the specified player, target country, log entry
     * buffer, and map.
     *
     * @param p_player         The player issuing the bomb order.
     * @param p_country        The country to be bombed.
     * @param p_logEntryBuffer The logging buffer for recording order execution.
     */
    public BombOrder(Player p_player, Country p_country, LogEntryBuffer p_logEntryBuffer) {
        this.d_player = p_player;
        this.d_targetCountry = p_country;
        this.d_logEntryBuffer = p_logEntryBuffer;
        this.d_map = d_player.getMap();
    }

    /**
     * Executes the bomb order if valid. The number of armies in the target country
     * is reduced by half if the order is successfully validated.
     */
    @Override
    public void execute() {
        if (validate()) {
            d_remainingArmies = d_targetCountry.getNumOfArmies() / 2;
            d_targetCountry.setNumOfArmies(d_remainingArmies);
            d_player.removeBoomCard();
        }
    }

    /**
     * Validates whether the bomb order can be executed.
     * Validation criteria:
     * 1. The target country must have at least one army.
     * 2. The target country must be owned by an opponent
     * 3. The target country must be adjacent to at least one of the issuing
     * player's countries,
     * as determined by the map's adjacency list.
     *
     * @return {@code true} if the order is valid; otherwise, {@code false}.
     */
    @Override
    public boolean validate() {
        boolean l_preValidated = preValidate();
        if (l_preValidated) {
            if (d_player.verifyDiplomacyPartner(d_targetCountry.getPlayer())) {
                System.out.println("The diplomacy partner country cannot be boomed. ");
                return false;
            } else {
                return true;
            }
        }
        return l_preValidated;
    }

    /**
     * Prints and logs the details of the bomb order execution.
     */
    @Override
    public void print() {
        String orderMsg = "Bomb order issued by player " + d_player.getd_name() + ": Bomb " +
                d_targetCountry.getCountryName() + " Remaining Armies " + d_remainingArmies;
        System.out.println(orderMsg);
        d_logEntryBuffer.setLogEntry(orderMsg);
    }

    /**
     * Pre-validation method.
     * In this implementation, pre-validation uses the same logic as validation.
     *
     * @return {@code true} if pre-validation is successful; otherwise,
     *         {@code false}.
     */
    @Override
    public boolean preValidate() {

        // 1. Check if the target country has an army.
        if (d_targetCountry.getNumOfArmies() <= 0) {
            System.out.println("Invalid BombOrder: Target country has no armies.");
            return false;
        }

        // 2. Check if the target country belongs to an opponent: it must not be neutral
        // and must not belong to the current player.
        String targetOwner = d_targetCountry.getPlayerName();
        if (targetOwner.equals(d_player.getd_name())) {
            System.out.println("Invalid BombOrder: The target country " + d_targetCountry.getCountryName() +
                    " does not belong to an opponent.");

            return false;
        }

        // 3. Check if the target country is adjacent to one of the current player's
        // countries.
        boolean l_adjacent = false;
        for (Country ownedCountry : d_player.getd_ownedCountries()) {
            // Get the list of neighboring country IDs for the current player's country from
            // the map's adjacency list.
            List<Integer> neighborIDs = d_map.getAdjacencyList().get(ownedCountry.getCountryID());
            if (neighborIDs != null && neighborIDs.contains(d_targetCountry.getCountryID())) {
                l_adjacent = true;
                break;
            }
        }
        if (!l_adjacent) {
            System.out.println("Invalid BombOrder: Target country is not adjacent to any of your territories.");
            return false;
        }

        return true;

    }

    @Override
    public String getName() {
       return "bomb";
    }
}
