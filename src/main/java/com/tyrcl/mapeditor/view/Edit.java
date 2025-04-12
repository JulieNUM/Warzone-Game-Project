package com.tyrcl.mapeditor.view;

import com.tyrcl.common.model.End;
import com.tyrcl.common.model.Phase;
import com.tyrcl.gameplay.engine.GameEngine;


/**
 * The class represents the Edit phase of the game
 */
public abstract class Edit extends Phase {

    /**
     * Constructor for {Edit} that initializes the edit phase with a game engine.
     * 
     * @param p_ge The game engine to associate with the edit phase.
     */
    public Edit(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap() {
     
    }

    @Override
    public void showMap() {
     
    }

    @Override
    public void editMap() {
   
    }

    @Override
    public void reinforce() {
   
    }

    /**
     * Ends the current game session by invoking the {endGame} method from the base class.
     */
    @Override
    public void endGame() {
        d_ge.setPhase(new End(d_ge));
    }

    @Override
    public void next() {
      
    }


    @Override
    public void tournamentGame() {
    
    }
    
}
