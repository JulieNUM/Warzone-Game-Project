package com.tyrcl;

import com.tyrcl.gameplay.engine.GameEngine;

/**
 * Main class to launch the game
 */
public class Main {
    
    /**
     * Initiate the game engine and launch the game
     * @param args system params
     */
    public static void main(String[] args) {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.start();
    }
}