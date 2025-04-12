package com.tyrcl.gameplay.phase;

import java.util.Iterator;
import java.util.List;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.MainPlay;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;
/**
 * The Execution class extends MainPlay and is responsible for executing orders
 * for all players in the game. It processes each player's orders sequentially
 * until there are no more orders left to execute.
 */
public class OrderExecution extends MainPlay {

    /**
     * Constructs an Execution instance with the specified GameEngine.
     * 
     * @param p_ge the game engine instance that manages the game state
     */
    public OrderExecution(GameEngine p_ge) {
        super(p_ge);
    }
    
    /**
     * Executes all orders for all players in the game. This method iterates over
     * all players, retrieving and executing their orders one by one, until no
     * more orders are left to process.
     */
    @Override
    public void execution() {
       List<Player> l_players = d_ge.getPlayers();
       System.out.println("=============== BEGIN EXECUTING ALL ORDERS =================");
       Order order;
       boolean still_more_orders = false;
       do {
           still_more_orders = false;
           for (Player l_p : l_players) {
            order = l_p.next_order();
               if (order != null) {
                   still_more_orders = true;
                   order.execute();
                   order.print();
                   if(order.getName().equals("quit"))
                   {
                        d_ge.d_isGameEnd = true;
                        d_ge.setPhase(new Startup(d_ge));
                   }
               }
           }
       } while (still_more_orders);
       
       //remove players who have no countries after excution
       Iterator<Player> iterator = l_players.iterator();
       while (iterator.hasNext()) {
           Player l_iplayer = iterator.next();
           if (l_iplayer.getd_ownedCountries().size() == 0) {
               iterator.remove();
               System.out.println(l_iplayer.getd_name() + " has been eliminated from the game.");
           }
       }

       System.out.println("=============== END EXECUTING ALL ORDERS ==================");   

    }

    /**
     * Proceeds to the next phase of the game.
     */
    @Override
    public void next() {
        d_ge.setPhase(new TournamentPlay(d_ge));
    }
}

