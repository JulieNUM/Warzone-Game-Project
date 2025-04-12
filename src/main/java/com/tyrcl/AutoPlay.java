package com.tyrcl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.strategy.AggressiveBehavior;
import com.tyrcl.gameplay.strategy.BenevolentBehavior;
import com.tyrcl.gameplay.strategy.CheaterBehavior;
import com.tyrcl.gameplay.strategy.RandomBehavior;

/**
 * This class is created to test the game using auto play
 */
public class AutoPlay {

    /**
     * The entry point of the game simulation.
     * 
     * @param args command-line arguments (not used).
     * @throws IOException if an error occurs while loading the game map.
     */
    public static void main(String[] args) throws IOException {

        GameEngine l_ge = new GameEngine();

        RandomBehavior l_b1 = new RandomBehavior();
        // CheaterBehavior l_b1 = new CheaterBehavior();
        AggressiveBehavior l_b2 = new AggressiveBehavior();
        AggressiveBehavior l_b3 = new AggressiveBehavior();

        Player l_p1 = new Player("Player1", l_ge.d_logEntryBuffer);
        Player l_p2 = new Player("Player2", l_ge.d_logEntryBuffer);
        Player l_p3 = new Player("Player3", l_ge.d_logEntryBuffer);

        l_p1.setOrderStrategy(l_b1);
        l_p2.setOrderStrategy(l_b2);
        l_p3.setOrderStrategy(l_b3);

        l_ge.loadMap("map3.txt");
        l_ge.addPlayer(l_p1);
        l_ge.addPlayer(l_p2);
        l_ge.addPlayer(l_p3);

        l_ge.getGameMap().assignAdjacentCountriesToPlayers(l_ge.getPlayers(), 2);
        l_ge.getGameMap().initMapReiforcement();
        List<String> l_list = new ArrayList<String>();
        l_ge.startAutoPlay(5000, l_list);
        l_ge.reset();

    }
}
