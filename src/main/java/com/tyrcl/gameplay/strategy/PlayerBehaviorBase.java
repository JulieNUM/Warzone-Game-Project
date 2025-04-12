package com.tyrcl.gameplay.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.tyrcl.gameplay.model.Card;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.model.cards.AirliftOrder;
import com.tyrcl.gameplay.model.cards.BlockadeOrder;
import com.tyrcl.gameplay.model.cards.BombOrder;
import com.tyrcl.gameplay.model.cards.DiplomacyOrder;
import com.tyrcl.mapeditor.model.Country;
import com.tyrcl.utils.Helper;

/**
 * The {@code PlayerBehaviorBase} class provides a base implementation of the {@code PlayerBehavior} interface.
 * It defines default behaviors for creating various types of orders in a game scenario.
 * This class is designed to be extended by specific behavior implementations.
 */
public class PlayerBehaviorBase implements PlayerBehavior {
    
    /**
     * Tracks the number of orders placed in a turn.
     */
    private int d_numOfOrders = 0;

    /**
     * Creates an order based on the current number of orders placed in a turn.
     * The order sequence follows: Deploy, Attack, Move, and Card usage.
     *
     * @param p_player the player executing the order
     * @param p_players the list of all players in the game
     * @return an {@code Order} object representing the chosen action
     */
    @Override
    public Order createOrder(Player p_player, List<Player> p_players) {
        switch (d_numOfOrders) {
            case 0:
                d_numOfOrders++;
                return createDeployOrder(p_player);
            case 1:
                d_numOfOrders++;
                return createAttackOrder(p_player);
            case 2:
                d_numOfOrders++;
                return createMoveOrder(p_player);
            case 3:
                p_player.setHasPlacedAllOrders();
                return createCardOrder(p_player, p_players);
            default:
                return null;
        }
    }

    /**
     * Returns the name of the behavior strategy.
     *
     * @return a string representing the behavior name
     */
    @Override
    public String getBehaviorName() {
       return "Base";
    }

    /**
     * Resets the order count at the beginning of a new turn.
     */
    @Override
    public void turnReset() {
        d_numOfOrders = 0;
    }

    /**
     * Creates a diplomacy order by selecting a random opponent.
     *
     * @param p_player the player executing the diplomacy order
     * @param p_players the list of all players in the game
     * @return a {@code DiplomacyOrder} if a valid player is found, otherwise {@code null}
     */
    @Override
    public Order createDiplomacyOrder(Player p_player, List<Player> p_players) {
       Collections.shuffle(p_players);
        for (Player l_player : p_players) {
            if (!l_player.getd_name().equals(p_player.getd_name())) {
                return new DiplomacyOrder(p_player, l_player, p_player.getLogBuffer());
            }
        }
        return null;
    }

    /**
     * Creates a blockade order for a random owned country with armies.
     *
     * @param p_player the player executing the blockade order
     * @return a {@code BlockadeOrder} if a valid country is found, otherwise {@code null}
     */
    @Override
    public Order createBlockadeOrder(Player p_player) {
        List<Country> l_countries = p_player.getd_ownedCountries();
        Collections.shuffle(l_countries);
        for (Country l_country : l_countries) {
            if (l_country.getNumOfArmies() > 0) {
                return new BlockadeOrder(p_player, l_country, p_player.getLogBuffer());
            }
        }
        return null;
    }

    /**
     * Creates a bomb order targeting a valid enemy country.
     *
     * @param p_player the player executing the bomb order
     * @return a {@code BombOrder} if a valid target is found, otherwise {@code null}
     */
    @Override
    public Order createBombOrder(Player p_player) {
        List<Country> l_countries = p_player.getd_ownedCountries();
        Collections.shuffle(l_countries);
        for (Country l_country : l_countries) {
            List<Country> l_neighbors = p_player.getMap().getListOfNeiborCountries(l_country);
            Collections.shuffle(l_neighbors);
            for (Country l_neighbor : l_neighbors) {
                if (!l_neighbor.getPlayerName().equals(p_player.getd_name())
                        && !l_neighbor.getPlayerName().equals("Neutral") && l_neighbor.getNumOfArmies() > 1) {
                    return new BombOrder(p_player, l_neighbor, p_player.getLogBuffer());
                }
            }
        }
        return null;
    }

    /**
     * Creates an airlift order to move armies to another country.
     *
     * @param p_player the player executing the airlift order
     * @return an {@code AirliftOrder} if a valid move is found, otherwise {@code null}
     */
    @Override
    public Order createAirliftOrder(Player p_player) {
        List<Country> l_countries = p_player.getd_ownedCountries();
        Collections.shuffle(l_countries);
        for (Country l_country : l_countries) {
            List<Country> l_allCountries = new ArrayList<>(p_player.getMap().getCountries().values());
            Collections.shuffle(l_allCountries);
            for (Country l_c : l_allCountries) {
                if (!l_c.getPlayerName().equals(p_player.getd_name()) && l_country.getNumOfArmies() > 0) {
                    return new AirliftOrder(p_player, l_country, l_c,
                            Helper.generateRandomIntExclusiveInclusive(l_country.getNumOfArmies()),
                            p_player.getLogBuffer());
                }
            }
        }
        return null;
    }

    /**
     * Creates a card order by selecting an available card from the player's hand.
     *
     * @param p_player the player executing the card order
     * @param p_players the list of all players in the game
     * @return an {@code Order} if a valid card is used, otherwise {@code null}
     */
    @Override
    public Order createCardOrder(Player p_player, List<Player> p_players) {
        for (Card l_card : p_player.getCards()) {
            Order l_order = getCardOrder(p_player, p_players, l_card);
            if (l_order != null) {
                System.out.println("Created card order");
                return l_order;
            }
        }
        return null;
    }

    /**
     * Determines the appropriate order based on the type of card played.
     *
     * @param p_player the player using the card
     * @param p_players the list of all players in the game
     * @param p_card the card being played
     * @return an {@code Order} representing the card action
     */
    private Order getCardOrder(Player p_player, List<Player> p_players, Card p_card) {
        switch (p_card.getType()) {
            case "airlift":
                return createAirliftOrder(p_player);
            case "bomb":
                return createBombOrder(p_player);
            case "blockade":
                return createBlockadeOrder(p_player);
            case "diplomacy":
                return createDiplomacyOrder(p_player, p_players);
            default:
                return null;
        }
    }


    /**
     * Creates a deploy order to assign reinforcements to a country.
     * 
     * @param p_player the player executing the deploy order
     * @return an {@code Order} object representing the deployment action
     */
    @Override
    public Order createDeployOrder(Player p_player) {
        return null;
    }

    /**
     * Creates an attack order to engage enemy-controlled territories.
     * 
     * @param p_player the player executing the attack order
     * @return an {@code Order} object representing the attack action
     */
    @Override
    public Order createAttackOrder(Player p_player) {
        return null;
    }

    /**
     * Creates a move order to reposition armies between owned territories.
     * 
     * @param p_player the player executing the move order
     * @return an {@code Order} object representing the move action
     */
    @Override
    public Order createMoveOrder(Player p_player) {
        return null;
    }
}
