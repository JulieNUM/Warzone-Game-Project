package com.tyrcl.gameplay.model.data;

import java.util.ArrayList;
import java.util.List;

import com.tyrcl.gameplay.model.Card;
import com.tyrcl.gameplay.strategy.AggressiveBehavior;
import com.tyrcl.gameplay.strategy.BenevolentBehavior;
import com.tyrcl.gameplay.strategy.CheaterBehavior;
import com.tyrcl.gameplay.strategy.HumanBehavior;
import com.tyrcl.gameplay.strategy.PlayerBehavior;
import com.tyrcl.gameplay.strategy.RandomBehavior;


/**
 * Represents the data associated with a player, including their name, behavior, 
 * and the cards they possess.
 */
public class PlayerData {

    /**
     * The name of the player.
     */
    public String d_name;

    /**
     * The behavior strategy of the player.
     */
    public String d_behavior;

    /**
     * A list of card names that the player possesses.
     */
    public ArrayList<String> d_cards = new ArrayList<>();

    /**
     * Default constructor initializing an empty PlayerData object.
     */
    public PlayerData() {}

    /**
     * Generates a player behavior strategy based on the stored behavior type.
     *
     * @return An instance of the corresponding PlayerBehavior subclass, or {@code null} 
     *         if the behavior type is unrecognized.
     */
    public PlayerBehavior GeneratePlayerBehavior() {
        if (d_behavior.equals("Aggressive")) {
            return new AggressiveBehavior();
        } else if (d_behavior.equals("Random")) {
            return new RandomBehavior();
        } else if (d_behavior.equals("Cheater")) {
            return new CheaterBehavior();
        } else if (d_behavior.equals("Human")) {
            return new HumanBehavior();
        } else if (d_behavior.equals("Benevolent")) {
            return new BenevolentBehavior();
        }
        return null;
    }

    /**
     * Converts the list of stored card names into a list of Card objects.
     *
     * @return A list of Card objects corresponding to the player's stored card names.
     */
    public List<Card> GetListOfCards() {
        List<Card> l_cards = new ArrayList<>();
        for (String l_cardStr : d_cards) {
            Card l_card = Card.BOMB; // Default value
            switch (l_cardStr.toLowerCase()) {
                case "bomb":
                    l_card = Card.BOMB;
                    break;
                case "airlift":
                    l_card = Card.AIRLIFT;
                    break;
                case "blockade":
                    l_card = Card.BLOCKADE;
                    break;
                case "diplomacy":
                    l_card = Card.DIPLOMACY;
                    break;
                default:
                    break;
            }
            l_cards.add(l_card);
        }
        return l_cards;
    }
}
