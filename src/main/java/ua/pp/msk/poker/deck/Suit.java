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
public enum Suit {
    
    SPADES("\u2660", "Spades", (byte)5, 0x1F0A0),HEARTS("\u2665", "Hearts", (byte)1, 0x1F0B0),DIAMONS("\u2666", "Diamonds", (byte)3, 0x1F0C0),CLUBS("\u2663", "Clubs", (byte)2, 0x1F0D0);
    
    private final String symbol;
    private final String name;
    private final byte number;
    private int unicodePrefix;
    
    Suit(String symbol, String name, byte number, int unicodePrefix){
        this.symbol = symbol;
        this.name = name;
        this.number = number;
        this.unicodePrefix = unicodePrefix;
    }
    
    public String getSymbol(){
        return symbol;
    }
    
    public String getName(){
        return name;
    }
    
    public byte getNumber(){
        return number;
    }
    
    public int getPrefix(){
        return unicodePrefix;
    }
}
