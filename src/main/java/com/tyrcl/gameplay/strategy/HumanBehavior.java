package com.tyrcl.gameplay.strategy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.tyrcl.gameplay.model.Order;
import com.tyrcl.gameplay.model.Player;

import com.tyrcl.utils.Helper;
import com.tyrcl.utils.MapFileHandler;

/**
 * This class represents the human behavior strategy for a player.
 */
public class HumanBehavior extends PlayerBehaviorBase {

    private Player d_player;
    
    /**
     * Get the name of the behavior
     * @return the name of the behavior.
     */
    @Override
    public String getBehaviorName() {
        return "Human";
    }

    /**
     * Creates an order for the player.
     * The deploy order will deploy all available armies to the strongest country.
     *
     * @param p_player the player for whom the deploy order is created.
     * @param p_players all players in the game
     * @return the deploy order or null if no deploy order can be created.
     */
    @Override
    public Order createOrder(Player p_player, List<Player> p_players) {
        this.d_player = p_player;
        boolean l_validated = false;
        Order l_order = null;
        Scanner l_scanner = new Scanner(System.in);

        while (!l_validated) {
            System.out.println(Helper.generateCommandString("Please use commands below to play the game:"));
            System.out.println(Helper.generateCommandString("[deploy]:    'deploy countryID numarmies'"));
            System.out.println(Helper.generateCommandString("[advance]:   'advance countryFrom countryTo numarmies'"));
            System.out.println(Helper.generateCommandString("[blockade]:  'blockade countryID'"));
            System.out.println(
                    Helper.generateCommandString("[Airlift]:   'airlift soourcecountryID targetcountryID numarmies'"));
            System.out.println(Helper.generateCommandString("[negotiate]: 'negotiate playerID'"));
            System.out.println(Helper.generateCommandString("Type 'done' to finish"));
            System.out.println(Helper.generateCommandString("Type 'quit' to save and quit the game"));
  
            String l_input = l_scanner.nextLine().trim();
            if (handleDoneCommand(l_input)) {
                d_player.setHasPlacedAllOrders();
                return null;
            }

            l_order = processOrderInput(l_input);
            if (l_order == null)
                continue;

            l_validated = validateOrder(l_order);

            if (l_order.getName().equals("quit")) {
                System.out.println(Helper.generateCommandString("Would you like to save the game? enter 'filename.json' or 'no' to quit the game."));
                String l_saveInput = l_scanner.nextLine().trim();
                if(!l_saveInput.equals("no"))
                {
                    try {
                        MapFileHandler.saveGame(l_saveInput, p_players);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                d_player.setHasPlacedAllOrders();
            }
        }
        return l_order;
    }

    /**
     * Handles 'done' command input to mark the player as having placed all orders.
     *
     * @param input The user input string.
     * @return True if the input is 'done', false otherwise.
     */
    private boolean handleDoneCommand(String input) {
        if (input.equals("done")) {
            return true;
        }
        return false;
    }

    /**
     * Validates the user input and creates an Order object based on the input.
     *
     * @param input The user input string.
     * @return The created Order object, or null if the input is invalid.
     */
    private Order processOrderInput(String input) {
        String[] parts = input.split(" ");
        Order order = d_player.create_Order(parts);
        if (order == null) {
            System.out.println("Invalid command. Please try again.");
        }
        return order;
    }

    /**
     * Validates the created order.
     *
     * @param order The created order to validate.
     * @return True if the order is valid, false otherwise.
     */
    private boolean validateOrder(Order order) {
        boolean isValid = order.preValidate();
        if (!isValid) {
            System.out.println("Validation failed. Check your input.");
        }
        return isValid;
    }

}
