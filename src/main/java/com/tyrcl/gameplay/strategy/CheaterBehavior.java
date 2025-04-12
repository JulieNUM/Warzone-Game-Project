package com.tyrcl.gameplay.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;

/**
 * This class represents the cheater behavior strategy for a player.
 * The player will conquer all neighboring countries that are not owned by them
 * and double the number of armies in their own countries.
 */
public class CheaterBehavior extends PlayerBehaviorBase {

    /**
     * Gets the name of the behavior.
     *
     * @return the name of the behavior.
     */
    @Override
    public String getBehaviorName() {
        return "Cheater";
    }

    /**
     * Creates a deploy order for the player.
     * The cheater behavior does not create deploy orders.
     *
     * @param p_player the player for whom the deploy order is created.
     * @return null as no deploy order is created.
     */
    @Override
    public Order createDeployOrder(Player p_player) {
        return null;
    }

    /**
     * Creates an attack order for the player.
     * The cheater behavior does not create attack orders.
     *
     * @param p_player the player for whom the attack order is created.
     * @return null as no attack order is created.
     */
    @Override
    public Order createAttackOrder(Player p_player) {
        return null;
    }

    /**
     * Creates a move order for the player.
     * The cheater behavior does not create move orders.
     *
     * @param p_player the player for whom the move order is created.
     * @return null as no move order is created.
     */
    @Override
    public Order createMoveOrder(Player p_player) {
        return null;
    }

    /**
     * Issues the cheating behavior order for the player.
     * The player will conquer all neighboring countries that are not owned by them
     * and double the number of armies in their own countries.
     *
     * @param p_player the player executing the cheating behavior.
     */
    public void issueOrder(Player p_player) {
        System.out.println("Cheater Executing cheating behavior...");

        List<Country> l_ownedCountries = new ArrayList<>(p_player.getd_ownedCountries());

        for (Country country : l_ownedCountries) {
            List<Country> neighbors = p_player.getMap().getListOfNeiborCountries(country);
            boolean l_hasEnemyNeighbor = false;

            for (Country neighbor : neighbors) {
                if (!neighbor.getPlayerName().equals(p_player.getd_name())) {
                    System.out.println(" → Conquered: " + neighbor.getCountryName());
                    neighbor.setPlayer(p_player);
                    p_player.getd_ownedCountries().add(neighbor);
                    l_hasEnemyNeighbor = true;
                }
            }

            if (l_hasEnemyNeighbor) {
                int l_old = country.getNumOfArmies();
                country.setNumOfArmies(l_old * 2);
                System.out.println(" → Doubled armies at " + country.getCountryName() + ": " + l_old + " → " + country.getNumOfArmies());
            }
        }

    }
}
