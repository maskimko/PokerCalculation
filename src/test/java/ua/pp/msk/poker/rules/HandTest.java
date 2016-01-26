/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Suit;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.CardException;

/**
 *
 * @author maskimko
 */
public class HandTest {

    public HandTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of equals method, of class Hand.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Hand();
        Hand h2 = new Hand();
        Hand instance = new Hand();
        try {
            instance.setCombination(Combination.FULLHOUSE);
            instance.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.ACE), new Card(Suit.HEARTS, SuitSet.ACE),
                new Card(Suit.SPADES, SuitSet.ACE), new Card(Suit.DIAMONS, SuitSet.KING),
                new Card(Suit.HEARTS, SuitSet.KING)});
            h2.setCombination(Combination.FULLHOUSE);
            h2.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.HEARTS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.JACK), new Card(Suit.SPADES, SuitSet.TEN),
                new Card(Suit.HEARTS, SuitSet.TEN)});
            ((Hand) obj).setCombination(Combination.FULLHOUSE);
            ((Hand) obj).setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.ACE), new Card(Suit.HEARTS, SuitSet.ACE),
                new Card(Suit.HEARTS, SuitSet.KING), new Card(Suit.SPADES, SuitSet.ACE),
                new Card(Suit.DIAMONS, SuitSet.KING)});
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        boolean expResult = false;
        boolean expResult2 = true;
        boolean result = instance.equals(h2);
        boolean result2 = instance.equals(obj);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of compareTo method, of class Hand.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Hand instance1 = new Hand();
        Hand instance2 = new Hand();
        Hand instance3 = new Hand();
        try {
            instance1.setCombination(Combination.TWOPAIRS);
            instance1.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.HEARTS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.SEVEN), new Card(Suit.SPADES, SuitSet.TEN),
                new Card(Suit.HEARTS, SuitSet.TEN)});
            instance2.setCombination(Combination.THREEOFKIND);
            instance2.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.HEARTS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.SEVEN), new Card(Suit.SPADES, SuitSet.JACK),
                new Card(Suit.HEARTS, SuitSet.TEN)});
            instance3.setCombination(Combination.THREEOFKIND);
            instance3.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.HEARTS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.QUEEN), new Card(Suit.SPADES, SuitSet.JACK),
                new Card(Suit.HEARTS, SuitSet.TEN)});
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        int expResult1 = -1;
        int expResult2 = -1;
        int expResult3 = -1;
        int result1 = instance1.compareTo(instance2);
        int result2 = instance2.compareTo(instance3);
        int result3 = instance1.compareTo(instance3);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);

    }

    @Test
    public void testClone() throws Exception {
        System.out.println("Test of clone method");

        try {
            Hand h = new Hand();
            h.setCombination(Combination.TWOPAIRS);
            h.setCards(new Card[]{new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.HEARTS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.SEVEN), new Card(Suit.SPADES, SuitSet.TEN),
                new Card(Suit.HEARTS, SuitSet.TEN)});
            Hand cloned = (Hand) h.clone();
            assertFalse(cloned == h);
            for (int i = 0; i < 5; i++) {
                assertFalse(h.getCards()[i] == cloned.getCards()[i]);
                assertTrue(h.getCards()[i].equals(cloned.getCards()[i]));
            }
            assertTrue(h.getCombination() == cloned.getCombination());
        } catch (CardException ex) {
            throw new Exception(ex);
        }
    }
}
