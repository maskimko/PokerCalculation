/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.Arrays;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.rules.Combination;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.FullTableException;
import ua.pp.msk.poker.exceptions.MissingCardException;
import ua.pp.msk.poker.rules.HandChecker;
import ua.pp.msk.poker.rules.SimpleHandChecker;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Player {

    private final int id;
    private String name;
    private Card[] pair = new Card[2];

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    Player(int id) {
        this(id, "Anonymous" + id);
    }

    public void receiveCards(Card[] cards) throws CardException {
        if (cards.length != 2) {
            if (cards.length > 2) {
                throw new ExtraCardException(String.format("Player received %d extra cards. Player should receive exactly 2 cards", cards.length - 2));
            } else {
                throw new MissingCardException(String.format("Player received %d cards. Missing %d cars. Player should receive exactly 2 cards", cards.length, 2 - cards.length));
            }
        } else {
            this.pair = cards;
        }
    }

    /**
     * get rid of cards after game is over
     */
    public void foldCards() {
        pair[0] = null;
        pair[1] = null;
    }

    public Combination checkHand(Card[] cards) throws ExtraCardException {
        Combination combination = null;
        if (cards.length > 5) {
            throw new ExtraCardException(String.format("%d extra cards are on table. There can be from 0 till 5 card maximum after River ", cards.length - 5));
        }
        HandChecker hc = new SimpleHandChecker();
        try {
            Card[] inGameCards = new Card[7];
            int i = 0;
            for (; i < pair.length; i++) {
                inGameCards[i] = pair[i];
            }
            for (; i < cards.length + 2; i++) {
                inGameCards[i] = cards[i - 2];
            }
           combination = hc.checkHand(inGameCards);
            LoggerFactory.getLogger(this.getClass()).debug(
                    String.format("Player \"%s\" Got a combination %s having cards: %s\tTable: %s",
                            getName(), combination.name(), Arrays.toString(pair), Arrays.toString(cards)));
           // System.out.println(String.format("Player \"%s\" Got a combination %s", getName(), combination.name()));
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).warn("Wrong cards amount ", ex);
        }
        return combination;
    }

    public void takeASeat(Table t) throws FullTableException {
        t.registerPlayer(this);
    }

    public void takeASeat(Table t, int place) throws FullTableException {
        t.registerPlayer(this, place);
    }

    public String getName() {
        return name;
    }

}
