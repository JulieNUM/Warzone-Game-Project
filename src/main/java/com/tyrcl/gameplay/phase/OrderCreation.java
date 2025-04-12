package com.tyrcl.gameplay.phase;

import java.util.List;

import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.MainPlay;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.utils.Helper;

/**
 * Represents the order creation phase of the game.
 */
public class OrderCreation extends MainPlay {

    private List<Player> d_players;
    private int d_currentPlayerIndex;
    private int d_turnCounter = 0;
    private Map d_map;

    /**
     * Constructs an OrderCreation phase with the specified game engine.
     *
     * @param p_ge The game engine to associate with this phase.
     */
    public OrderCreation(GameEngine p_ge) {
        super(p_ge);
        d_players = d_ge.getPlayers();
        d_map = p_ge.getGameMap();
        d_currentPlayerIndex = 0;
    }

    /**
     * Reinforces the game state by assigning reinforcements to players and allowing
     * them to issue orders.
     */
    @Override
    public void reinforce() {
        d_turnCounter++;
        resetPlayersForNewTurn();
        assgiReinforcements();

        while (!allPlayersHavePlacedAllOrders()) {
            try {
                Player l_currentPlayer = d_players.get(d_currentPlayerIndex);
                if (!l_currentPlayer.hasPlacedAllOrders()) {
                    System.out.println("============================================================");
                    System.out.println("Current Player:" + l_currentPlayer.getd_name() + ", reinforcements:"
                            + l_currentPlayer.getd_reinforcementPool());
                    //l_currentPlayer.printCards();
                    System.out.println("============================================================");
                    showNeighboringCountries(l_currentPlayer);
                    l_currentPlayer.issue_order();
                }
                d_currentPlayerIndex = (d_currentPlayerIndex + 1) % d_players.size();
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("All players have placed all orders.");
        d_ge.setPhase(new OrderExecution(d_ge));
    }

    /**
     * Displays the neighboring countries for the specified player.
     *
     * @param p_player The player whose neighboring countries are to be displayed.
     */
    private void showNeighboringCountries(Player p_player) {
        System.out.println("Player " + p_player.getd_name() + "'s neighbours:");
        for (Country l_country : p_player.getd_ownedCountries()) {
            System.out.println("Country: " + l_country.getCountryName() + ", ID: " + l_country.getCountryID()
                    + ", Current Armies: " + l_country.getNumOfArmies());
            List<String> l_neighbors = d_map.outputNeighborsForOrder(l_country);
            for (String l_neighbor : l_neighbors) {
                System.out.println("  Neighbor: " + l_neighbor);
            }
        }
    }

    /**
     * Checks if all players have placed all their orders.
     *
     * @return true if all players have placed all orders, false otherwise.
     */
    public boolean allPlayersHavePlacedAllOrders() {
        for (Player l_player : d_players) {
            if (!l_player.hasPlacedAllOrders()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Assigns reinforcements to each player based on the number of countries they
     * own and continent bonuses.
     */
    public void assgiReinforcements() {
        System.out.println(Helper.generateGameplayTitle("Reinforcements"));
        for (Player l_player : d_players) {

            int l_baseReinforce = Math.max(3, l_player.getd_ownedCountries().size() / 3);
            int l_continentBonus = l_player.calculateContinentBonuses();
            l_player.setd_reinforcementPool(l_baseReinforce + l_continentBonus);
            System.out.println(l_player.getd_name() + " receives " + l_player.getd_reinforcementPool()
                    + " reinforcements in this turn!");
        }
    }

    /**
     * Resets the state of all players for a new turn.
     */
    public void resetPlayersForNewTurn() {
        for (Player l_player : d_players) {
            l_player.resetForNewTurn();
        }
    }

    /**
     * Proceeds to the next phase of the game.
     */
    @Override
    public void next() {

        d_ge.setPhase(new OrderExecution(d_ge));
    }
}
