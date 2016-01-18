/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.deck;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maskimko
 */
public class CardTest {

    public CardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of getSymbol method, of class Card.
     */
    @Test
    public void testGetSymbol() {
        System.out.println("getSymbol");
        Card spades = new Card(Suit.SPADES, SuitSet.ACE);
        Card hearts = new Card(Suit.HEARTS, SuitSet.ACE);
        Card diamodbs = new Card(Suit.DIAMONS, SuitSet.ACE);
        Card clubs = new Card(Suit.CLUBS, SuitSet.ACE);

        String expSpades = "\u2660";
        String expHearts = "\u2665";
        String expDiamonds = "\u2666";
        String expClubs = "\u2663";

        assertEquals(expHearts, hearts.getSuit().getSymbol());
        assertEquals(expSpades, spades.getSuit().getSymbol());
        assertEquals(expDiamonds, diamodbs.getSuit().getSymbol());
        assertEquals(expClubs, clubs.getSuit().getSymbol());

    }

    /**
     * Test of equals method, of class Card.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Card card2 = new Card(Suit.CLUBS, SuitSet.ACE);
        Card card1 = new Card(Suit.CLUBS, SuitSet.TEN);
        Card card3 = new Card(Suit.CLUBS, SuitSet.ACE);
        Object obj = new Object();
        boolean expResult1 = false;
        boolean expResult2 = true;
        boolean expResult3 = false;
        boolean expResult4 = false;
        boolean result1 = card1.equals(card2);
        boolean result2 = card2.equals(card3);
        boolean result3 = card3.equals(card1);
        boolean result4 = card1.equals(obj);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }

    /**
     * Test of compareTo method, of class Card.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Card card1 = new Card(Suit.DIAMONS, SuitSet.JACK);
        Card card2 = new Card(Suit.CLUBS, SuitSet.QUEEN);
        Card card3 = new Card(Suit.HEARTS, SuitSet.QUEEN);
        Card card4 = new Card(Suit.CLUBS, SuitSet.QUEEN);
        int expResult1 = -1;
        int expResult2 = 1;
        int expResult3 = 1;
        int expResult4 = 0;
        int result1 = card1.compareTo(card2);
        int result2 = card2.compareTo(card3);
        int result3 = card3.compareTo(card1);
        int result4 = card4.compareTo(card2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
    }

}
