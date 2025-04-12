package com.tyrcl.common.model;

import com.tyrcl.gameplay.engine.GameEngine;

/**
 * The class represents the End phanse of the game
 */
public class End extends Phase  {

    /**
     * Constructor for {End} that initializes the End phase with a game engine.
     * 
     * @param p_ge The game engine to associate with the End phase.
     */
    public End(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap() {
        
        throw new UnsupportedOperationException("Unimplemented method 'loadMap'");
    }

    @Override
    public void showMap() {
        
        throw new UnsupportedOperationException("Unimplemented method 'showMap'");
    }

    @Override
    public void editMap() {
        
        throw new UnsupportedOperationException("Unimplemented method 'editMap'");
    }


    @Override
    public void reinforce() {
        
        throw new UnsupportedOperationException("Unimplemented method 'reinforce'");
    }

    @Override
    public void endGame() {
       
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public void next() {
        
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }

    @Override
    public void startUp() {
        
        throw new UnsupportedOperationException("Unimplemented method 'startUp'");
    }

    @Override
    public void execution() {
        throw new UnsupportedOperationException("Unimplemented method 'execution'");
    }

    @Override
    public void tournamentGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tournamentGame'");
    }

    @Override
    public void gameData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameData'");
    }
    
}
