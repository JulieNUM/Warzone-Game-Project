package com.tyrcl.common.model;

import java.io.IOException;
import java.util.Scanner;

import com.tyrcl.gameplay.engine.GameEngine;

/**
 *	The Phases represent states in the board game Risk. 
 */
public abstract class Phase {

	/**
	 *  Contains a reference to the State of the GameEngine 
	 *  so that the state object can change the state of 
	 *  the GameEngine to transition between states. 
	 */
	public GameEngine d_ge;
	public Scanner d_scanner;

	/**
	 * Initiate the phase by passing the game engine
	 * @param p_ge
	 */
	public Phase(GameEngine p_ge) {
		d_ge = p_ge;
		d_scanner = new Scanner(System.in);
	}

	/* 
	 * Load the map from a txt file.
	*/
	abstract public void loadMap();

	/* 
	 * Show the map data on the console.
	*/
	abstract public void showMap();

	/*
	 * Edit map using a few commands
	 */
    abstract public void editMap();
  
	/*
	 * Start up the game by creating players and assign countries
	 */
	abstract public void startUp();

	/*
	 * Enter the main game loop by issuing orders.
	 */
	abstract public void reinforce();

	/*
	 * Enter the main game loop by excuting orders.
	 */
	abstract public void execution();

	/*
	 * End game 
	 */
	abstract public void endGame();

	/*
	 * Go to the next phase
	 */
	abstract public void next();

	/**
	 * Start a tournament game
	 * @throws IOException 
	 */
	abstract public void tournamentGame() throws IOException;

	/**
	 * Save/load a game
	 */
	abstract public void gameData();
	
	/**
	 *  Common method to all States warning an invalid command.
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
	}
}
