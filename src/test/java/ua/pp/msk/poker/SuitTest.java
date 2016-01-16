/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import ua.pp.msk.poker.deck.Suit;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maskimko
 */
public class SuitTest {
    
    public SuitTest() {
    }
    
   

  

    @Test
    public void testSymbol(){
        for (Suit s : Suit.values()){
        System.out.println(String.format("%8s\t%s", s.getName(), s.getSymbol()));
        }
        assertEquals(Suit.SPADES.getSymbol(), "\u2660");
        assertEquals(Suit.HEARTS.getSymbol(), "\u2665");
        assertEquals(Suit.DIAMONS.getSymbol(), "\u2666");
        assertEquals(Suit.CLUBS.getSymbol(), "\u2663");
    }
    
}
