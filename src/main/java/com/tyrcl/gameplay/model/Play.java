package com.tyrcl.gameplay.model;

import com.tyrcl.common.model.End;
import com.tyrcl.common.model.Phase;
import com.tyrcl.gameplay.engine.GameEngine;
import com.tyrcl.gameplay.model.data.GameData;
import com.tyrcl.utils.Helper;
import com.tyrcl.utils.MapFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * The class represents the Play phanse of the game
 */
public class Play extends Phase {

  /**
   * Constructor for {Play} that initializes the Play phase with a game engine.
   * 
   * @param p_ge The game engine to associate with the Play phase.
   */
  public Play(GameEngine p_ge) {
    super(p_ge);
  }

  /**
   * Loads the game map.
   */
  @Override
  public void loadMap() {

  }

  /**
   * Displays the game map.
   */
  @Override
  public void showMap() {
    d_ge.getGameMap().showMap();
  }

  /**
   * Edits the game map.
   */
  @Override
  public void editMap() {

  }

  /**
   * Starts up the game.
   */
  @Override
  public void startUp() {

  }

  /**
   * Reinforces the game state.
   */
  @Override
  public void reinforce() {

  }

  /**
   * Ends the current game session by invoking the {endGame} method from the base
   * class.
   */
  @Override
  public void endGame() {
    d_ge.getGameMap().cleanup();
    d_ge.setPhase(new End(d_ge));
  }

  /**
   * Proceeds to the next phase of the game.
   */
  @Override
  public void next() {

  }

  /**
   * Executes the current phase of the game.
   */
  @Override
  public void execution() {

  }

  /**
   * Execute the tournament game
   */
  @Override
  public void tournamentGame() {
      System.out.println("Please enter the Tournament phase to start playing the tournament game.");
  }

  /**
   * execute game data operations
   */
  @Override
  public void gameData() {
      System.out.println(">Please choose one of the options below:");
      System.out.println(">1. Load Game");
      System.out.println(">2. Quit");
  
      String l_readLineString = d_scanner.nextLine().trim();  
      switch (l_readLineString) {
          case "1":
              System.out.println("> Enter 'filename.json' to load a game from saved file");
              String loadFilename = d_scanner.nextLine().trim();
              try {
                  // Calling loadGame method from GameEngine (d_ge is the GameEngine object)
                 GameData l_data = MapFileHandler.loadGame(loadFilename);
                 d_ge.loadFromGameData(l_data);
                 List<String> l_list = new ArrayList<String>();
                 d_ge.startAutoPlay(5000,l_list);
              } catch (IOException e) {
                  System.out.println("Error loading game: " + e.getMessage());
              }
              break;
          case "2":
            
              break;
          default:
              System.out.println("Invalid option. Please choose again.");
              break;
      }
  }
}