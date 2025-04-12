package com.tyrcl.gameplay.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.phase.OrderCreation;
import com.tyrcl.gameplay.phase.OrderExecution;

public class BenevolentBehaviorTest {
    @Test
    public void TestAutoPlay() throws IOException
    {
         GameEngine l_ge = new GameEngine();

        BenevolentBehavior l_b1 = new BenevolentBehavior();
        BenevolentBehavior l_b2 = new BenevolentBehavior();

        Player l_p1 = new Player("Player1", l_ge.d_logEntryBuffer);
        Player l_p2 = new Player("Player2", l_ge.d_logEntryBuffer);


        l_p1.setOrderStrategy(l_b1);
        l_p2.setOrderStrategy(l_b2);

        l_ge.loadMap("map1.txt");
        l_ge.addPlayer(l_p1);
        l_ge.addPlayer(l_p2);
        l_ge.getGameMap().assignAdjacentCountriesToPlayers(l_ge.getPlayers(), 1);
        l_ge.getGameMap().initMapReiforcement();
        OrderCreation l_ocPhase = new OrderCreation(l_ge);
        OrderExecution l_oePhase = new OrderExecution(l_ge);
        int l_currentTurn = 0;
        int l_maxTurn = 20;
        while (!l_ge.d_isGameEnd) {
            l_ocPhase.reinforce();
            l_oePhase.execution();
            l_currentTurn++;
            if (l_currentTurn >= l_maxTurn) {
                l_ge.d_isGameEnd = true;
                break;
            }
        }

        assertTrue(l_ge.d_isGameEnd);
    }

}
