package com.tyrcl.gameplay.strategy;

import java.util.Collections;
import java.util.List;

import com.tyrcl.gameplay.model.AdvanceOrder;

import com.tyrcl.gameplay.model.DeployOrder;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;

import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.utils.Helper;
import com.tyrcl.utils.RandomItemFromList;

/**
 * The {@code RandomBehavior} class represents a player behavior that makes random decisions
 * for deploying, attacking, and moving armies. This class extends {@code PlayerBehaviorBase}
 * and provides implementations for randomly generating orders.
 */
public class RandomBehavior extends PlayerBehaviorBase {

    /**
     * Returns the name of this behavior.
     *
     * @return the string "Random"
     */
    @Override
    public String getBehaviorName() {
        return "Random";
    }

    /**
     * Creates a random deploy order by selecting a random owned country and deploying all available reinforcements.
     *
     * @param p_player the player executing the deploy order
     * @return a {@code DeployOrder} object if a valid country is found, otherwise {@code null}
     */
    @Override
    public Order createDeployOrder(Player p_player) {
        int l_numOfArmies = p_player.getd_reinforcementPool();
        Country l_country = RandomItemFromList.getRandomItem(p_player.getd_ownedCountries());
        if (l_country != null) {
            System.out.println("Created Deploy order");
            return new DeployOrder(p_player, l_country, l_numOfArmies, p_player.getLogBuffer());
        } else {
            return null;
        }
    }

    /**
     * Creates a random attack order by selecting a random owned country and attacking a neighboring enemy country.
     *
     * @param p_player the player executing the attack order
     * @return an {@code AdvanceOrder} object if a valid attack move is found, otherwise {@code null}
     */
    @Override
    public Order createAttackOrder(Player p_player) {
        List<Country> l_countries = p_player.getd_ownedCountries();
        Collections.shuffle(l_countries);
        for (Country l_country : l_countries) {
            List<Country> l_neighbors = p_player.getMap().getListOfNeiborCountries(l_country);
            Collections.shuffle(l_neighbors);
            for (Country l_neighbor : l_neighbors) {
                if (!l_neighbor.getPlayerName().equals(p_player.getd_name()) && l_country.getNumOfArmies() > 0) {
                    Order l_newOrder = new AdvanceOrder(p_player, l_country, l_neighbor,
                            Helper.generateRandomIntExclusiveInclusive(l_country.getNumOfArmies()),
                            p_player.getLogBuffer());
                    if (l_newOrder != null) {
                        System.out.println("Created Attack order");
                    }
                    return l_newOrder;
                }
            }
        }
        return null;
    }

    /**
     * Creates a random move order by selecting a random owned country and moving armies to a neighboring owned country.
     *
     * @param p_player the player executing the move order
     * @return an {@code AdvanceOrder} object if a valid move is found, otherwise {@code null}
     */
    @Override
    public Order createMoveOrder(Player p_player) {
        List<Country> l_countries = p_player.getd_ownedCountries();
        Collections.shuffle(l_countries);
        for (Country l_country : l_countries) {
            List<Country> l_neighbors = p_player.getMap().getListOfNeiborCountries(l_country);
            Collections.shuffle(l_neighbors);
            for (Country l_neighbor : l_neighbors) {
                if (l_neighbor.getPlayerName().equals(p_player.getd_name()) && l_country.getNumOfArmies() > 0) {
                    System.out.println("Created Move order");
                    return new AdvanceOrder(p_player, l_country, l_neighbor,
                            Helper.generateRandomIntExclusiveInclusive(l_country.getNumOfArmies()),
                            p_player.getLogBuffer());
                }
            }
        }
        return null;
    }
}

