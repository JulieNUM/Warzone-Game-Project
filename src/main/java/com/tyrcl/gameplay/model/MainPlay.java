package com.tyrcl.gameplay.model;

import com.tyrcl.gameplay.engine.GameEngine;

public class MainPlay extends Play {

    public MainPlay(GameEngine p_ge) {
        super(p_ge);
    }
    
    @Override
    public void loadMap() {
        System.out.println("The map cannot be reloaded during the mainplay phase");
    }
}
