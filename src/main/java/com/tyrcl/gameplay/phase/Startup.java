package com.tyrcl.gameplay.phase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tyrcl.gameplay.engine.GameEngine;

import com.tyrcl.gameplay.model.Play;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.strategy.AggressiveBehavior;
import com.tyrcl.gameplay.strategy.BenevolentBehavior;
import com.tyrcl.gameplay.strategy.CheaterBehavior;
import com.tyrcl.gameplay.strategy.HumanBehavior;
import com.tyrcl.gameplay.strategy.PlayerBehavior;
import com.tyrcl.gameplay.strategy.RandomBehavior;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.utils.Helper;

/**
 * Represents the startup phase of the game, where players load maps,
 * manage players, and assign countries before the main game loop starts.
 */
public class Startup extends Play {

    public Startup(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Executes the startup phase, including loading a map, adding/removing players,
     * and assigning countries to players.
     */
    @Override
    public void startUp() {
        System.out.println(Helper.generateGameplayTitle("Startup Phase"));
        if (d_ge.getGameMap() == null || d_ge.getCountryByID(1) == null) {
            System.out.println(">No map loaded.");
            System.out.println(">Enter 'loadmap filename.txt' to load a domination map.");
            System.out.println(">Enter 'loadmap filename.map' to load a conquest map.");

            boolean isLoadmap = false;
            while (!isLoadmap) {
                String l_input = d_scanner.nextLine();
                String[] l_tokens = l_input.split("\\s+");

                if (l_tokens[0].equals("loadmap")) {
                    if (l_tokens.length != 2) {
                        System.out.println(
                                "Invalid command. Type 'loadmap filename.txt' or'loadmap filename.map' to proceed.");
                    } else {
                        isLoadmap = handleLoadMap(l_tokens[1]);
                    }
                } else {
                    System.out.println(
                            "Invalid command. Type 'loadmap filename.txt' or 'loadmap filename.map' to proceed.");
                }
            }
        } else {
            System.out.println("Map has been loaded.");
        }
        Integer l_index = 1;
        while (true) {
            System.out.println("> Please select players to add to the game:");
            System.out.println("> 1. Human");
            System.out.println("> 2. Aggressive");
            System.out.println("> 3. Benevolent");
            System.out.println("> 4. Random");
            System.out.println("> 5. Cheater");
            System.out.println("> Enter done to start the game.");

            String l_input = d_scanner.nextLine().trim();
            String[] l_tokens = l_input.split("\\s+");

            switch (l_tokens[0].toLowerCase()) {
                case "1":
                    handlePlayerCreation(l_index, new HumanBehavior());
                    l_index++;
                    break;
                case "2":
                    handlePlayerCreation(l_index, new AggressiveBehavior());
                    l_index++;
                    break;
                case "3":
                    handlePlayerCreation(l_index, new BenevolentBehavior());
                    l_index++;
                    break;
                case "4":
                    handlePlayerCreation(l_index, new RandomBehavior());
                    l_index++;
                    break;
                case "5":
                    handlePlayerCreation(l_index, new CheaterBehavior());
                    l_index++;
                    break;
                case "done":
                    if (d_ge.getPlayers().size() < 2) {
                        System.out.println("At least 2 players are required to start the game.");
                    } else {
                        boolean l_humanInGame = d_ge.verifyIfHumanPlayerExists();
                        Map l_map = d_ge.getGameMap();
                        l_map.assignAdjacentCountriesToPlayers(d_ge.getPlayers(), Helper.INITIAL_ARMY);
                        l_map.initMapReiforcement();
                        List<String> l_list = new ArrayList<String>();
                        d_ge.startAutoPlay(5000, l_list);
                        if (!l_humanInGame) {
                            d_ge.setPhase(new Startup(d_ge));
                            return;
                        } else {
                            d_ge.setPhase(new Startup(d_ge));
                            return;
                        }
                    }
                    break;
                default:
                    System.out.println(Helper.generateCommandString(
                            "Invalid command. "));
                    break;
            }
        }
    }

    /**
     * Handles the process of loading a map.
     *
     * @param p_filename The filename of the map to load.
     * @return True if the map was loaded successfully, false otherwise.
     */
    private boolean handleLoadMap(String p_filename) {
        try {
            d_ge.loadMap(p_filename);
            System.out.println("Map '" + p_filename + "' loaded successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handles adding or removing a player based on input commands.
     *
     * @param p_index    the index of the player
     * @param p_behavior the behavior of the player
     */
    private void handlePlayerCreation(Integer p_index, PlayerBehavior p_behavior) {
        Player l_player = new Player("Player" + p_index.toString(), d_ge.d_logEntryBuffer);
        l_player.setOrderStrategy(p_behavior);
        d_ge.addPlayer(l_player);
    }

    /**
     * Determines the next state of the game after the startup phase.
     */
    @Override
    public void next() {
        d_ge.setPhase(new OrderCreation(d_ge));
    }

}
