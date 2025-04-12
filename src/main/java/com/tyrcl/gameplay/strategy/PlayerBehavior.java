package com.tyrcl.gameplay.strategy;

import java.util.List;

import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;

/**
 * The {@code PlayerBehavior} interface defines the behavior of a player
 * in terms of order creation and turn management.
 * Implementing classes should define how different orders are created
 * based on the player's strategy.
 */
public interface PlayerBehavior {
    
    /**
     * Creates an order for the player based on the game state and other players.
     * 
     * @param p_player the player executing the order
     * @param p_players the list of all players in the game
     * @return an {@code Order} object representing the chosen action
     */
    public Order createOrder(Player p_player, List<Player> p_players);

    /**
     * Returns the name of the behavior strategy.
     * 
     * @return a string representing the behavior name
     */
    public String getBehaviorName();

    /**
     * Resets the state of the behavior at the beginning of a new turn.
     */
    public void turnReset();

    /**
     * Creates a diplomacy order to negotiate alliances between players.
     * 
     * @param p_player the player executing the diplomacy order
     * @param p_players the list of all players in the game
     * @return an {@code Order} object representing the diplomacy action
     */
    public Order createDiplomacyOrder(Player p_player, List<Player> p_players);

    /**
     * Creates a blockade order, which increases defenses in a chosen country.
     * 
     * @param p_player the player executing the blockade order
     * @return an {@code Order} object representing the blockade action
     */
    public Order createBlockadeOrder(Player p_player);

    /**
     * Creates a bomb order to attack an enemy country.
     * 
     * @param p_player the player executing the bomb order
     * @return an {@code Order} object representing the bombing action
     */
    public Order createBombOrder(Player p_player);

    /**
     * Creates an airlift order to move armies between distant owned territories.
     * 
     * @param p_player the player executing the airlift order
     * @return an {@code Order} object representing the airlift action
     */
    public Order createAirliftOrder(Player p_player);

    /**
     * Creates a deploy order to assign reinforcements to a country.
     * 
     * @param p_player the player executing the deploy order
     * @return an {@code Order} object representing the deployment action
     */
    public Order createDeployOrder(Player p_player);

    /**
     * Creates an attack order to engage enemy-controlled territories.
     * 
     * @param p_player the player executing the attack order
     * @return an {@code Order} object representing the attack action
     */
    public Order createAttackOrder(Player p_player);

    /**
     * Creates a move order to reposition armies between owned territories.
     * 
     * @param p_player the player executing the move order
     * @return an {@code Order} object representing the move action
     */
    public Order createMoveOrder(Player p_player);

    /**
     * Creates a card order to play a special game card effect.
     * 
     * @param p_player the player executing the card order
     * @param p_players the list of all players in the game
     * @return an {@code Order} object representing the card action
     */
    public Order createCardOrder(Player p_player, List<Player> p_players);
}

