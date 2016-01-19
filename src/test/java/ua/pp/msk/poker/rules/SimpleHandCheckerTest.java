/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

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
     * @throws java.lang.Exception
     */
    @Test
    public void testCheckHighHand() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tHigh Hand");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.HIGHHAND);
        expResult.setCards(new Card[]{cards[0], cards[1], null, null, null});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }

    @Test
    public void testCheckOnePair() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tOne Pair");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.ONEPAIR);
        expResult.setCards(new Card[]{cards[0], cards[2], cards[1], null, null});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }

    @Test
    public void testCheckTwoPairs() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tTwo Pairs");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        cards[3] = new Card(Suit.SPADES, SuitSet.KING);
        cards[4] = new Card(Suit.HEARTS, SuitSet.QUEEN);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.TWOPAIRS);
        expResult.setCards(new Card[]{cards[1], cards[3], cards[0], cards[2], cards[4]});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }

    @Test
    public void testSort() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tSorting");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        cards[3] = new Card(Suit.SPADES, SuitSet.KING);
        cards[4] = new Card(Suit.HEARTS, SuitSet.QUEEN);
        cards[5] = new Card(Suit.DIAMONS, SuitSet.THREE);
        cards[6] = new Card(Suit.DIAMONS, SuitSet.FOUR);
        Card[] expected = new Card[]{cards[3], cards[1], cards[4], cards[6], cards[5], cards[2], cards[0]};

        SimpleHandChecker.sort(cards);
        for (int i = 0; i < 7; i++) {
            assertEquals(cards[i], expected[i]);
        }
        System.out.println("Passed!");
    }
    
    @Test
    public void testSortWithNulls() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tSorting with null value");
        Card[] cards = new Card[7];
        cards[0] = null;
        cards[1] = null;
        cards[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        cards[3] = new Card(Suit.SPADES, SuitSet.KING);
        cards[4] = new Card(Suit.HEARTS, SuitSet.QUEEN);
        cards[5] = new Card(Suit.DIAMONS, SuitSet.THREE);
        cards[6] = new Card(Suit.DIAMONS, SuitSet.FOUR);
        Card[] expected = new Card[]{cards[3],  cards[4], cards[6], cards[5], cards[2], null, null};

        SimpleHandChecker.sort(cards);
        for (int i = 0; i < 7; i++) {
            assertEquals(cards[i], expected[i]);
        }
        System.out.println("Passed!");
    }


    @Test
    public void testEnsureDescSorted() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tEnsure descending order");
        Card[] unsorted = new Card[7];
        unsorted[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        unsorted[1] = new Card(Suit.HEARTS, SuitSet.KING);
        unsorted[2] = new Card(Suit.DIAMONS, SuitSet.TWO);
        unsorted[3] = new Card(Suit.SPADES, SuitSet.KING);
        unsorted[4] = new Card(Suit.HEARTS, SuitSet.QUEEN);
        unsorted[5] = new Card(Suit.DIAMONS, SuitSet.THREE);
        unsorted[6] = new Card(Suit.DIAMONS, SuitSet.FOUR);

        Card[] sorted = new Card[]{unsorted[3], unsorted[1], unsorted[4], unsorted[6], unsorted[5], unsorted[2], unsorted[0]};
        boolean unsResult = SimpleHandChecker.ensureDescSorted(unsorted);
        boolean sortedResult = SimpleHandChecker.ensureDescSorted(sorted);
        assertTrue(sortedResult);
        assertFalse(unsResult);
System.out.println("Passed!");
    }
    
     @Test
    public void testThreeOfKind() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tThree Of Kind");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.TWO);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.KING);
        cards[3] = new Card(Suit.SPADES, SuitSet.KING);
        cards[4] = new Card(Suit.HEARTS, SuitSet.QUEEN);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.THREEOFKIND);
        expResult.setCards(new Card[]{cards[1], cards[2], cards[3], cards[4], cards[0]});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }
    
     @Test
    public void testFourOfKind() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tFour Of Kind");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.KING);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.KING);
        cards[3] = new Card(Suit.SPADES, SuitSet.KING);
        cards[4] = new Card(Suit.HEARTS, SuitSet.SEVEN);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.FOUROFKIND);
        expResult.setCards(new Card[]{cards[0], cards[2], cards[1], cards[3],  cards[4]});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }
    
      @Test
    public void testFullHouse() throws Exception {
        System.out.println("checkHand");
        System.out.println("\tThree Of Kind");
        Card[] cards = new Card[7];
        cards[0] = new Card(Suit.CLUBS, SuitSet.KING);
        cards[1] = new Card(Suit.HEARTS, SuitSet.KING);
        cards[2] = new Card(Suit.DIAMONS, SuitSet.KING);
        cards[3] = new Card(Suit.SPADES, SuitSet.SEVEN);
        cards[4] = new Card(Suit.HEARTS, SuitSet.SEVEN);
        SimpleHandChecker instance = new SimpleHandChecker();
        Hand expResult = new Hand();
        expResult.setCombination(Combination.FULLHOUSE);
        expResult.setCards(new Card[]{cards[0], cards[2], cards[1], cards[3],  cards[4]});
        Hand result = instance.checkHand(cards);
        assertEquals(expResult, result);
        System.out.println("Passed!");
    }

}
