/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.deck;

/**
 *
 * @author maskimko
 */
public enum SuitSet {

     TWO((byte)0x2, "2", "2"), THREE((byte)0x3, "3", "3"), 
    FOUR((byte)0x4, "4", "4"), FIVE((byte)0x5, "5", "5"), SIX((byte)0x6, "6", "6"), 
    SEVEN((byte)0x7, "7", "7"), EIGHT((byte)0x8, "8", "8"), NINE((byte)0x9, "9", "9"), TEN((byte)0xA, "10", "10"), 
    JACK((byte)0xB, "Jack", "J"), QUEEN((byte)0xD, "Queen", "Q"), KING((byte)0xE, "King", "K"), ACE((byte)0x1, "Ace", "A");

    private String name;
    private String letter;
    private byte number;

    SuitSet(byte number, String name, String letter) {
        this.number = number;
        this.name = name;
        this.letter = letter;
    }
    
    public byte getNumber(){
        return number;
    }
    
    public String getLetter(){
        return letter;
    }
    
    public String getName(){
        return name;
    }
    
}
