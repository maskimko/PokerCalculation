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

/**
 *
 * @author maskimko
 */
public class SimpleHandCheckerTest {

    public SimpleHandCheckerTest() {
    }

    /**
     * Test of checkHand method, of class SimpleHandChecker.
     */
    @Test
    public void testCheckHighHand() throws Exception {
        System.out.println("checkHand");
        System.out.println("High Hand");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        SimpleHandChecker instance = new SimpleHandChecker();
        Combination expResult = Combination.HIGHHAND;
        Combination result = instance.checkHand(cards);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckOnePair() throws Exception {
        System.out.println("checkHand");
        System.out.println("One Pair");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        SimpleHandChecker instance = new SimpleHandChecker();
        Combination expResult = Combination.ONEPAIR;
        Combination result = instance.checkHand(cards);
        assertEquals(expResult, result);
    }

}
