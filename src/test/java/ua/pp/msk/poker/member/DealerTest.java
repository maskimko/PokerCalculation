/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Suit;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.TableException;
import ua.pp.msk.poker.rules.Combination;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author maskimko
 */
public class DealerTest {

    public DealerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of getWinner method, of class Dealer.
     */
    @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        Map<Hand, Player> hands = new HashMap<>();
        Dealer instance;
        try {
            instance = new Dealer(new Table(5));
            Player p1 = new Player(0);
            Player p2 = new Player(2);
            Player p3 = new Player(3);
            Hand p1h = new Hand();
            p1h.setCards(new Card[]{new Card(Suit.HEARTS, SuitSet.SEVEN),
                new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.DIAMONS, SuitSet.JACK),
                new Card(Suit.HEARTS, SuitSet.QUEEN), new Card(Suit.HEARTS, SuitSet.KING)});
            p1h.setCombination(Combination.ONEPAIR);
            hands.put(p1h, p1);
            Hand p2h = new Hand();
            p2h.setCards(new Card[]{new Card(Suit.DIAMONS, SuitSet.FOUR),
                new Card(Suit.SPADES, SuitSet.FOUR), new Card(Suit.SPADES, SuitSet.ACE),
                new Card(Suit.HEARTS, SuitSet.QUEEN), new Card(Suit.HEARTS, SuitSet.KING)});
            hands.put(p2h, p2);
            //Two pairs
            Hand p3h = new Hand();
            p3h.setCards(new Card[]{new Card(Suit.SPADES, SuitSet.FOUR),
                new Card(Suit.CLUBS, SuitSet.JACK), new Card(Suit.DIAMONS, SuitSet.JACK),
                new Card(Suit.DIAMONS, SuitSet.FOUR), new Card(Suit.HEARTS, SuitSet.KING)});
            hands.put(p3h, p3);

            Player result = instance.getWinner(hands);
            assertEquals(p3, result);

        } catch (TableException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Connot create a table", ex);
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Connot create a card", ex);
        }

    }

}
