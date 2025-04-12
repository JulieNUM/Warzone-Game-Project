package com.tyrcl.gameplay.engine;

import com.tyrcl.mapeditor.controller.MapEditorController;
import com.tyrcl.mapeditor.model.Continent;
import com.tyrcl.mapeditor.model.Country;

import java.io.IOException;

import java.util.*;


import com.tyrcl.common.log.LogEntryBuffer;
import com.tyrcl.common.log.LogFileWriter;
import com.tyrcl.common.model.End;
import com.tyrcl.common.model.Phase;
import com.tyrcl.gameplay.model.Player;
import com.tyrcl.gameplay.model.data.GameData;
import com.tyrcl.gameplay.phase.OrderCreation;
import com.tyrcl.gameplay.phase.OrderExecution;
import com.tyrcl.gameplay.phase.Startup;
import com.tyrcl.gameplay.phase.TournamentPlay;
import com.tyrcl.gameplay.strategy.PlayerBehavior;
import com.tyrcl.mapeditor.model.Map;
import com.tyrcl.mapeditor.view.MapEditor;

/**
 * Represents the game engine that manages game states, players, and the game
 * map.
 */
public class GameEngine {

    private List<Player> d_players;
    private Map d_gameMap;
    private Scanner d_scanner;
    private Phase d_gamePhase;
    private int d_mystart;
    private int d_mycommand;

    public LogEntryBuffer d_logEntryBuffer; // This will not be serialized

    public boolean d_isGameEnd; // This will be serialized

    private MapEditorController d_mapController;

    /**
     * Initiate the game with objects
     */
    public GameEngine() {
        this.d_players = new ArrayList<>();
        this.d_gameMap = new Map();
        this.d_scanner = new Scanner(System.in);
        this.d_isGameEnd = false;
        d_logEntryBuffer = new LogEntryBuffer();
        LogFileWriter l_logFileWriter = new LogFileWriter();
        d_logEntryBuffer.addObserver(l_logFileWriter);
        d_mapController = new MapEditorController(d_gameMap);
    }

    /**
     * Method that allows the GameEngine object to change its state.
     * 
     * @param p_phase new state to be set for the GameEngine object.
     */
    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
        String l_print = "[New phase: " + p_phase.getClass().getSimpleName() + "]";
        d_logEntryBuffer.setLogEntry(l_print);
        System.out.println("");
        System.out.println(l_print);
        System.out.println("");
    }

    /**
     * This method will ask the user:
     * 1. What part of the game they want to start with (edit map or play game).
     * Depending on the choice, the state will be set to a different object,
     * which will set different behavior.
     * 2. What command they want to execute from the game.
     * Depending on the state of the GameEngine, each command will potentially
     * have a different behavior.
     */
    public void start() {
        do {
            System.out.println("1. Edit Map");
            System.out.println("2. Single Game Mode");
            System.out.println("3. Tournament Mode");
            System.out.println("4. Quit");
            System.out.println("Where do you want to start?: ");
            d_mystart = d_scanner.nextInt();
            switch (d_mystart) {
                case 1:
                    setPhase(new MapEditor(this, d_scanner));
                    break;
                case 2:
                    setPhase(new Startup(this));
                    break;
                case 3:
                    setPhase(new TournamentPlay(this));
                    break;
                case 4:
                    System.out.println("Bye!");
                    return;
            }
            do {
                System.out.println(" ============================================================");
                System.out.println("| #   PHASE                         : command               |");
                System.out.println(" ============================================================");
                System.out.println("| 1.  Any except MainPlay           : load map              |");
                System.out.println("| 2.  Any                           : show map              |");
                System.out.println("| 3.  Edit:MapEditor                : edit/load/save map    |");
                System.out.println("| 4.  Play                          : load game             |");
                System.out.println("| 5.  Play:Startup                  : set players/countires |");
                System.out.println("| 6.  Play:MainPlay:OrderCreation   : issue orders          |");
                System.out.println("| 7.  Play:MainPlay:Execution       : execute orders        |");
                System.out.println("| 8.  Play:TournamentGame           : tournament            |");
                System.out.println(" ============================================================");
                System.out.println("| 0.  Next                          : next phase            |");
                System.out.println(" ============================================================");
                System.out.println("| 9.  End                           : end game              |");
                System.out.println(" ============================================================");
                System.out.println("Enter a " + d_gamePhase.getClass().getSimpleName() + " phase command: ");
                boolean validInput = false;

                while (!validInput) {
                    try {
                        d_mycommand = d_scanner.nextInt();
                        validInput = true; // If no exception is thrown, input is valid
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        d_scanner.next(); // Clear the invalid input from the scanner
                    }
                }
                System.out.println(" =================================================");
                //
                // Calls the method corresponding to the action that the user has selected.
                // Depending on what it the current state object, these method calls will
                // have a different implementation.
                //
                switch (d_mycommand) {
                    case 1:
                        d_gamePhase.loadMap();
                        break;
                    case 2:
                        d_gamePhase.showMap();
                        break;
                    case 3:
                        d_gamePhase.editMap();
                        break;
                    case 4:
                        d_gamePhase.gameData();
                        break;
                    case 5:
                        d_gamePhase.startUp();
                        break;
                    case 6:
                        d_gamePhase.reinforce();
                        break;
                    case 7:
                        d_gamePhase.execution();
                        break;
                    case 8:
                        try {
                            d_gamePhase.tournamentGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 0:
                        d_gamePhase.next();
                        break;
                    case 9:
                        d_gamePhase.endGame();
                        break;
                    default:
                        System.out.println("this command does not exist");
                }
            } while (!(d_gamePhase instanceof End));
        } while (d_mycommand != 3);
        d_scanner.close();
    }

    /**
     * Proceeds to the next game state.
     */
    public void nextStep() {
        // d_gamestate.execute();
        // d_gamestate = d_gamestate.nextState();
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return this.d_players;
    }

    /**
     * Checks if a player with the given name exists.
     *
     * @param p_playername The name of the player.
     * @return True if the player exists, false otherwise.
     */
    public boolean isPlayerExists(String p_playername) {
        boolean l_found = false;
        for (Player p : this.d_players) {
            if (p.getd_name().equals(p_playername)) {
                l_found = true;
                break;
            }
        }
        return l_found;
    }

    /**
     * Adds a player to the game.
     *
     * @param p_player The player to add.
     */
    public void addPlayer(Player p_player) {
        if (isPlayerExists(p_player.getd_name())) {
            System.out.println("Player " + p_player.getd_name() + " exists!");
        } else {
            this.d_players.add(p_player);
            System.out.println("Player " + p_player.getd_name() + " added!");
        }
    }

    /**
     * Adds a player with the specified name.
     *
     * @param p_playername The name of the player.
     */
    public void addPlayer(String p_playername) {
        addPlayer(new Player(p_playername, d_logEntryBuffer));
    }

    /**
     * Removes a player by name.
     *
     * @param p_playername The name of the player to remove.
     * @return True if the player was removed, false otherwise.
     */
    public boolean removePlayer(String p_playername) {
        boolean removed = d_players.removeIf(p -> p.getd_name().equals(p_playername));
        if (removed) {
            System.out.println("Player " + p_playername + " removed!");
        } else {
            System.out.println("Player " + p_playername + " not found!");
        }
        return removed;
    }

    /**
     * Removes a player from the game by using the Player object.
     *
     * @param p_player The player to be removed.
     * @return True if the player was successfully removed, false otherwise.
     */
    public boolean removePlayer(Player p_player) {
        return removePlayer(p_player.getd_name());
    }

    /**
     * Retrieves a country by its ID.
     *
     * @param p_countryID The ID of the country.
     * @return The country object, or null if not found.
     */
    public Country getCountryByID(int p_countryID) {
        return d_gameMap.getCountries().get(p_countryID);
    }

    /**
     * Loads a map from the specified filename.
     *
     * @param p_filename The filename of the map.
     * @throws IOException If an error occurs during loading.
     */
    public void loadMap(String p_filename) throws IOException {
        d_gameMap = d_mapController.loadMap(d_gameMap.d_mapType, p_filename);
    }

    /**
     * Retrieves the game map.
     *
     * @return The game map.
     */
    public Map getGameMap() {
        return this.d_gameMap;
    }

    /**
     * Retrieves the scanner used for user input.
     *
     * @return The scanner object.
     */
    public Scanner getScanner() {
        return this.d_scanner;
    }

    /**
     * Closes the scanner to release resources.
     */
    public void closeScanner() {
        d_scanner.close();
    }

    /**
     * Start the game by its own
     * 
     * @param p_gameResults list to store game results (winner name or draw)
     * @param p_maxTurn the max of the turn number
     */
    public void startAutoPlay(Integer p_maxTurn, List<String> p_gameResults) {
        OrderCreation l_ocPhase = new OrderCreation(this);
        OrderExecution l_oePhase = new OrderExecution(this);
        int l_currentTurn = 0;
        while (!this.d_isGameEnd) {
            l_ocPhase.reinforce();
            l_oePhase.execution();
            l_currentTurn++;
            Player l_winner = calcWinner();
            if (l_winner != null) {
                d_isGameEnd = true;
                p_gameResults.add(l_winner.getd_name());
                d_gameMap.showMap();
                System.out.println("Game ended. Winner: " + l_winner.getd_name() + "(" + l_winner.getOrderStrategy().getBehaviorName() + ")\n");
                this.setPhase(new OrderCreation(this));
            }
            if (l_currentTurn >= p_maxTurn) {
                d_isGameEnd = true;
                p_gameResults.add("Draw");
                d_gameMap.showMap();
                System.out.println("Game ended in draw.\n");
                this.setPhase(new OrderCreation(this));
            }
        }
        this.reset();
    }

    /**
     * Verify if human player exists
     * 
     * @return true human player exists, false doesn't exist
     */
    public boolean verifyIfHumanPlayerExists() {
        if (d_players != null) {
            for (Player l_player : d_players) {
                if (l_player.getOrderStrategy().getBehaviorName().equals("Human")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * reset the game engine for a new game
     */
    public void reset() {
        d_gameMap.cleanup();
        d_players.clear();
        d_isGameEnd = false;
    }

    /**
     * This method is used to check if there is a winner in the game.
     * 
     * @return Player object of the winner or null if no winner
     */
    public Player calcWinner() {
        Player l_winner = null;
        if(d_players.size() == 1 && d_players.get(0).getd_ownedCountries().size() == this.getGameMap().getCountries().size())
        {
            l_winner = d_players.get(0);
        }
        return l_winner;
    }

    /**
     * Load the game from saved state
     * 
     * @param p_data
     */
    public void loadFromGameData(GameData p_data) {
        if (p_data != null) {
            reset();

            d_gameMap.d_mapType = p_data.d_mapType;

            p_data.d_players.forEach(c -> {
                Player l_player = new Player(c.d_name, d_logEntryBuffer);
                l_player.setOrderStrategy(c.GeneratePlayerBehavior());
                l_player.setMap(d_gameMap);
                c.GetListOfCards().forEach(l_card -> {
                    l_player.getCards().add(l_card);
                });
                this.addPlayer(l_player);
            });

            p_data.d_continents.forEach(c -> {
                Continent l_continent = new Continent(c.d_ID, c.d_name, c.d_army);
                d_gameMap.d_continents.put(c.d_ID, l_continent);
            });

            p_data.d_countries.forEach(c -> {
                Country l_Country = new Country(c.d_ID, c.d_name, c.d_cID);
                l_Country.setNumOfArmies(c.d_army);
                Player l_player = getPlayerByName(c.d_playerName);
                if (l_player != null) {
                    l_Country.setPlayer(l_player);
                }
                d_gameMap.d_countries.put(c.d_ID, l_Country);
            });

            d_gameMap.d_adjacencyList = p_data.d_adjacencyList;
        }
    }

    /**
     * Get player from its name
     * 
     * @param p_name the name of the player
     * @return the object of the player
     */
    public Player getPlayerByName(String p_name) {
        for (Player l_player : d_players) {
            if (l_player.getd_name().equals(p_name)) {
                return l_player;
            }
        }
        return null;
    }
}
