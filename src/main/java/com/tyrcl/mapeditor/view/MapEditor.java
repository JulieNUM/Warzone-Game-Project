package com.tyrcl.mapeditor.view;

import java.io.IOException;
import java.util.Scanner;

import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.phase.Startup;
import com.tyrcl.mapeditor.controller.MapEditorController;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.utils.Helper;

/**
 * This class implements the map management system. It allows users to use
 * command lines to manage the map,
 * that includes load map, add/remove continents, countires and borders, display
 * the map, validate the map and save it to a file.
 */
public class MapEditor extends Edit {

    GameEngine d_gameEngine;
    Scanner d_scanner;
    Map d_map;
    MapEditorController d_controller;

    /**
     * Constructor for MapEditor that initializes the map editor with a game engine.
     * 
     * @param p_ge The game engine to associate with the map editor.
     */
    public MapEditor(GameEngine p_ge) {
        super(p_ge);
        d_gameEngine = p_ge;
    }

    /**
     * Constructor for MapEditor that initializes the map editor with a game engine
     * and
     * a scanner for command-line input.
     * 
     * @param p_ge      The game engine to associate with the map editor.
     * @param p_scanner The scanner used for reading user input.
     */
    public MapEditor(GameEngine p_ge, Scanner p_scanner) {
        super(p_ge);
        d_gameEngine = p_ge;
        d_scanner = new Scanner(System.in);
        d_map = p_ge.getGameMap();
        d_controller = new MapEditorController(d_map);
    }

    /**
     * Prompts the user to enter a command to load a map from a file.
     * The command should be in the format {loadmap filename}.
     * 
     */
    @Override
    public void loadMap() {
        System.out.println("--[loadmap filename.txt] to load a domination map.");
        System.out.println("--[loadmap filename.map] to load a conquest map.");
        System.out.println("> ");
        String l_readLineString = d_scanner.nextLine().trim();
        String[] l_params = l_readLineString.split("\s+");
        String l_command = l_params[0].toLowerCase();
        if (l_params.length == 0) {
            System.out.println("Invalid command entered. Usage: [loadmap filename]");
        } else {
            if (l_params.length < 2) {
                System.out.println("Invalid command entered. Usage: [loadmap filename]");
            } else {
                if (l_command.equals("loadmap")) {
                    try {
                        d_controller.loadMap(d_map.getMapType(), l_params[1]);
                    } catch (IOException l_error) {
                        l_error.printStackTrace();
                    }
                } else {
                    System.out.println("Invalid command entered. Usage: [loadmap filename]");
                }
            }
        }
    }

    /**
     * Prompts the user to enter a command to display the map.
     * The command should be in the format {showmap}.
     * 
     */
    @Override
    public void showMap() {
        System.out.println("Please use below command to show the map:");
        System.out.println("--[showmap]");
        System.out.println("> ");
        String l_readLineString = d_scanner.nextLine().trim();
        String[] l_params = l_readLineString.split("\s+");
        String l_command = l_params[0].toLowerCase();
        if (l_params.length == 0) {
            System.out.println("Invalid command entered. Usage: [showmap]");
        } else {
            if (l_params.length == 1) {
                if (l_command.equals("showmap")) {
                    d_controller.showMap();
                } else {
                    System.out.println("Invalid command entered. Usage: [showmap]");
                }
            } else {
                System.out.println("Invalid command entered. Usage: [showmap]");
            }
        }
    }

    /**
     * Prompts the user to enter commands for editing the map, including loading
     * maps,
     * adding/removing continents, countries, borders, validating, and saving the
     * map.
     */
    @Override
    public void editMap() {
        System.out.println("Please use below commands to edit the map:");
        System.out.println("--[editmap filename]");
        System.out.println("--" + Helper.CONTINENT_COMMAND_USAGE);
        System.out.println("--" + Helper.COUNTRY_COMMAND_USAGE);
        System.out.println("--" + Helper.NEIGHBOR_COMMAND_USAGE);
        System.out.println("--" + "[validatemap]");
        System.out.println("--" + "[savemap filename]");
        this.readLineExcuteCommand();
    }

    @Override
    public void reinforce() {
        throw new UnsupportedOperationException("Unimplemented method 'reinforce'");
    }

    /**
     * Ends the current game session by invoking the {endGame} method from the base
     * class.
     */
    @Override
    public void endGame() {
        super.endGame();
    }

    @Override
    public void next() {
        d_ge.setPhase(new Startup(d_ge));
    }

    @Override
    public void startUp() {

    }

    /**
     * Reads a line of input from the user and executes the corresponding command to
     * modify the map.
     * The input can include commands such as editing continents, countries,
     * neighbors, showing the map,
     * validating the map, or saving the map.
     * 
     */
    private void readLineExcuteCommand() {
        System.out.println("> ");
        String l_readLineString = d_scanner.nextLine().trim();
        String[] l_params = l_readLineString.split("\\s+");

        if (l_params.length == 0) {
            System.out.println(Helper.INVALID_COMMAND_USAGE);
        } else {
            String l_command = l_params[0].toLowerCase();

            switch (l_command) {
                case "editmap":
                    if (l_params.length < 2) {
                        System.out.println("Invalid command entered. Usage: [editmap filename]");
                    } else {
                        try {
                            d_controller.loadMap(d_map.getMapType(), l_params[1]);
                        } catch (IOException l_error) {
                            l_error.printStackTrace();
                        }
                    }
                    break;
                case "editcontinent":
                    excuteCommand(d_controller, "editcontinent", Helper.CONTINENT_COMMAND_USAGE, l_params);
                    break;
                case "editcountry":
                    excuteCommand(d_controller, "editcountry", Helper.COUNTRY_COMMAND_USAGE, l_params);
                    break;
                case "editneighbor":
                    excuteCommand(d_controller, "editneighbor", Helper.NEIGHBOR_COMMAND_USAGE, l_params);
                    break;
                case "showmap":
                    d_controller.showMap();
                    break;
                case "validatemap":
                    d_controller.validateMap();
                    break;
                case "savemap":
                    if (l_params.length < 2) {
                        System.out.println("Invalid command entered. Usage: [savemap filename]");
                    } else {
                        System.out.println(">Please choose one of the map formats you would like to save:");
                        System.out.println(">1.Domination");
                        System.out.println(">2.Conquest");
                        String l_input = d_scanner.nextLine().trim();
                        switch (l_input) {
                            case "1":
                                d_controller.saveMap(d_map.getMapType(), l_params[1] + ".txt");
                                break;
                            case "2":
                                d_controller.saveMap(d_map.getMapType(), l_params[1] + ".map");
                                break;
                            default:
                                System.out.println("Invalid command entered.");
                                break;
                        }
                        ;
                    }
                    break;
                default:
                    System.out.println(Helper.INVALID_COMMAND_USAGE);
            }
        }
    }

    /**
     * Excute command with specified information
     * 
     * @param p_map     the map object
     * @param p_command the command line entered by the player
     * @param p_usage   the usage of the command
     * @param p_params  the command params entered by the player
     * @return an updated map object
     */
    private static void excuteCommand(MapEditorController p_controller, String p_command, String p_usage,
            String[] p_params) {
        boolean l_invalidUsage = false;
        if (p_params.length < 2) {
            l_invalidUsage = true;
        } else if (p_params[1].equalsIgnoreCase("-add")) {
            if (p_params.length == 4) {
                if (p_command.equals("editcontinent")) {
                    p_controller.addContinent(p_params[2], p_params[3]);
                } else if (p_command.equals("editcountry")) {
                    p_controller.addCountry(p_params[2], p_params[3]);
                } else if (p_command.equals("editneighbor")) {
                    p_controller.addNeighbor(p_params[2], p_params[3]);
                }
            } else {
                l_invalidUsage = true;
            }
        } else if (p_params[1].equalsIgnoreCase("-remove")) {
            if (p_params.length == 3) {
                if (p_command.equals("editcontinent")) {
                    p_controller.removeContinent(p_params[2]);
                } else if (p_command.equals("editcountry")) {
                    p_controller.removeCountry(p_params[2]);
                }
            } else if (p_params.length == 4) {
                if (p_command.equals("editneighbor")) {
                    p_controller.removeNeighbor(p_params[2], p_params[3]);
                }
            } else {
                l_invalidUsage = true;
            }
        } else {
            l_invalidUsage = true;
        }
        if (l_invalidUsage) {
            System.out.println(
                    "Invalid command entered. Usage: " + p_usage);
        }
    }

    @Override
    public void execution() {
     
    }

    @Override
    public void tournamentGame() {
     
    }

    @Override
    public void gameData() {
        System.out.println("Please enter the Play phase and load a game!");
    }

}
