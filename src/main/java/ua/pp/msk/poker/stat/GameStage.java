/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

/**
 *
 * @author maskimko
 */
public enum GameStage {
    preflop("Pre-Flop"),flop("Flop"),turn("Turn"),river("River");
    
    private final String name;
    GameStage(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
