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
 * This class represents the aggressive behavior strategy for a player.
 */
public class AggressiveBehavior extends PlayerBehaviorBase {

    /**
     * Gets the name of the behavior.
     *
     * @return the name of the behavior.
     */
    @Override
    public String getBehaviorName() {
        return "Aggressive";
    }

    /**
     * Creates a deploy order for the player.
     * The deploy order will deploy all available armies to the strongest country.
     *
     * @param p_player the player for whom the deploy order is created.
     * @return the deploy order or null if no deploy order can be created.
     */
    @Override
    public Order createDeployOrder(Player p_player) {
        Country l_strongest = getStrongestCountry(p_player);
        int l_armies = p_player.getd_reinforcementPool();

        if(l_strongest != null && l_armies > 0) {
            System.out.println("Created Deploy order(Aggressive)");
            return new DeployOrder(p_player, l_strongest, l_armies, p_player.getLogBuffer());
        }
        return null;
    }

    /**
     * Creates an attack order for the player.
     * The attack order will attack a neighboring country with a random number of armies from the strongest country.
     *
     * @param p_player the player for whom the attack order is created.
     * @return the attack order or null if no attack order can be created.
     */
    @Override
    public Order createAttackOrder(Player p_player) {
        Country l_strongest = getStrongestCountry(p_player);
        if (l_strongest == null || l_strongest.getNumOfArmies() == 0) return null;

        List<Country> l_neighbors  = p_player.getMap().getListOfNeiborCountries(l_strongest);
        Collections.shuffle(l_neighbors );

        for (Country neighbor : l_neighbors ) {
            if (!neighbor.getPlayerName().equals(p_player.getd_name())) {
                int l_attackArmies  = l_strongest.getNumOfArmies();
                if (l_attackArmies  > 0) {
                    System.out.println("Created Attack order(Aggressive)");
                    return new AdvanceOrder(p_player, l_strongest, neighbor, l_attackArmies, p_player.getLogBuffer());
                }
            }
        }

        return null;
    }

    /**
     * Creates a move order for the player.
     * The move order will move armies from other owned countries to the strongest country.
     *
     * @param p_player the player for whom the move order is created.
     * @return the move order or null if no move order can be created.
     */
    @Override
    public Order createMoveOrder(Player p_player) {
        Country l_strongest = getStrongestCountry(p_player);
        if (l_strongest == null) return null;

        List<Country> l_ownedCountries = p_player.getd_ownedCountries();

        for (Country l_source : l_ownedCountries) {
            if (l_source.equals(l_strongest) || l_source.getNumOfArmies() == 0) continue;

            List<Country> neighbors = p_player.getMap().getListOfNeiborCountries(l_source);
            for (Country l_neighbor : neighbors) {
                if (l_neighbor.equals(l_strongest) && l_neighbor.getPlayerName().equals(p_player.getd_name())) {
                    System.out.println("Created Move order(Aggressive)");
                    return new AdvanceOrder(p_player, l_source, l_neighbor,
                            Helper.generateRandomIntExclusiveInclusive(l_source.getNumOfArmies()),
                            p_player.getLogBuffer());
                }
            }
        }

        return null;

    }

    /**
     * Gets the strongest country owned by the player.
     * The strongest country is the one with the most armies.
     * If all countries have zero armies, a random country is selected.
     *
     * @param p_player the player whose strongest country is to be found.
     * @return the strongest country or null if the player owns no countries.
     */
    private Country getStrongestCountry(Player p_player) {
        List<Country> ownedCountries = p_player.getd_ownedCountries();
        if (ownedCountries.isEmpty()) return null;

        Country l_strongest = ownedCountries.get(0);

        for (Country country : ownedCountries) {
            if (country.getNumOfArmies() > l_strongest.getNumOfArmies()) {
                l_strongest = country;
            }
        }

        if (l_strongest.getNumOfArmies() == 0) {
            l_strongest = RandomItemFromList.getRandomItem(ownedCountries);
        }

        return l_strongest;
    }

}
