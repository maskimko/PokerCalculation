/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.Arrays;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.stat.HandStatistic;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.FullTableException;
import ua.pp.msk.poker.exceptions.MissingCardException;
import ua.pp.msk.poker.rules.Combination;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.rules.HandChecker;
import ua.pp.msk.poker.rules.SimpleHandChecker;
import ua.pp.msk.poker.stat.Collector;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Player {

    private final int id;
    private String name;
    private Card[] pair = new Card[2];
    private Hand hand = null;

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
            checkHand(new Card[5], GameStage.preflop);
        }
    }
    
    public void receiveCards(Pair pair) throws CardException {
        receiveCards(pair.getCards());
    }

    /**
     * get rid of cards after game is over
     */
    public void foldCards() {
        pair[0] = null;
        pair[1] = null;
    }

    /**
     *
     * @param cards Card array from table up to 5 cards
     * @return
     * @throws ExtraCardException
     */
    Hand checkHand(Card[] cards, GameStage stage) throws ExtraCardException {
        if (cards.length > 5) {
            throw new ExtraCardException(String.format("%d extra cards are on table. There can be from 0 till 5 card maximum after River ", cards.length - 5));
        }
        HandChecker hc = new SimpleHandChecker();
        try {
            //Consider all available cards for player
            Card[] inGameCards = new Card[7];
            int i = 0;
            for (; i < pair.length; i++) {
                inGameCards[i] = pair[i];
            }
            for (; i < cards.length + 2; i++) {
                inGameCards[i] = cards[i - 2];
            }

            hand = hc.checkHand(inGameCards);
            LoggerFactory.getLogger(this.getClass()).debug(
                    String.format("Player \"%s\" Got a combination %s having cards: %s\tTable: %s",
                            getName(), hand.toString(), Arrays.toString(pair), Arrays.toString(cards)));
            Collector.getCollector().registerHand(Combination.HIGHHAND, stage);
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).warn("Wrong cards amount ", ex);
        }
        return hand;
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

    @Override
    public String toString() {
        return "Player(" + name + ')';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    
}
