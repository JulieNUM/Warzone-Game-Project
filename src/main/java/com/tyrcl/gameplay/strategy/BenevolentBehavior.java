package com.tyrcl.gameplay.strategy;

import com.tyrcl.gameplay.model.AdvanceOrder;
import com.tyrcl.gameplay.model.DeployOrder;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.utils.Helper;
import com.tyrcl.utils.RandomItemFromList;

import java.util.List;

/**
 * This class represents the benevolent behavior strategy for a player.
 * The player will focus on deploying armies to the weakest country and moving armies to the weakest country.
 */
public class BenevolentBehavior extends PlayerBehaviorBase {

    /**
     * Gets the name of the behavior.
     *
     * @return the name of the behavior.
     */
    @Override
    public String getBehaviorName() {
        return "Benevolent";
    }

    /**
     * Creates a deploy order for the player.
     * The deploy order will deploy all available armies to the weakest country.
     *
     * @param p_player the player for whom the deploy order is created.
     * @return the deploy order or null if no deploy order can be created.
     */
    @Override
    public Order createDeployOrder(Player p_player) {
        Country l_weakest = getWeakestCountry(p_player);
        int l_armies = p_player.getd_reinforcementPool();

        if(l_weakest != null && l_armies > 0) {
            System.out.println("Created Deploy order(Benevolent)");
            return new DeployOrder(p_player, l_weakest, l_armies, p_player.getLogBuffer());
        }
        return null;
    }

    /**
     * Creates an attack order for the player.
     * The benevolent behavior does not create attack orders.
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
     * The move order will move armies from other owned countries to the weakest country.
     *
     * @param p_player the player for whom the move order is created.
     * @return the move order or null if no move order can be created.
     */
    @Override
    public Order createMoveOrder(Player p_player) {
        Country l_weakest = getWeakestCountry(p_player);
        if (l_weakest == null) return null;

        List<Country> l_ownedCountries = p_player.getd_ownedCountries();

        for (Country l_source : l_ownedCountries) {
            if (l_source.equals(l_weakest) || l_source.getNumOfArmies() == 0) continue;

            List<Country> neighbors = p_player.getMap().getListOfNeiborCountries(l_source);
            for (Country l_neighbor : neighbors) {
                if (l_neighbor.equals(l_weakest) && l_neighbor.getPlayerName().equals(p_player.getd_name())) {
                    System.out.println("Created Move order(Benevolent)");
                    return new AdvanceOrder(p_player, l_source, l_neighbor,
                            Helper.generateRandomIntExclusiveInclusive(l_source.getNumOfArmies()),
                            p_player.getLogBuffer());
                }
            }
        }

        return null;
    }

    /**
     * Gets the weakest country owned by the player.
     * The weakest country is the one with the least armies.
     * If all countries have zero armies, a random country is selected.
     *
     * @param p_player the player whose weakest country is to be found.
     * @return the weakest country or null if the player owns no countries.
     */
    private Country getWeakestCountry(Player p_player) {
        List<Country> l_ownedCountries = p_player.getd_ownedCountries();
        if (l_ownedCountries.isEmpty()) return null;

        Country l_weakest = l_ownedCountries.get(0);

        for (Country country : l_ownedCountries) {
            if (country.getNumOfArmies() < l_weakest.getNumOfArmies()) {
                l_weakest = country;
            }
        }

        if (l_weakest.getNumOfArmies() == 0) {
            l_weakest = RandomItemFromList.getRandomItem(l_ownedCountries);
        }

        return l_weakest;
    }
    
}
