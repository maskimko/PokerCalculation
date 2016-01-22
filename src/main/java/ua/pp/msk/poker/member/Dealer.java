/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Deck;
import ua.pp.msk.poker.deck.Deck52;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.Collector;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Dealer {

    private Table table;

    public Dealer(Table table) {
        this.table = table;
    }

    public void startGame() {
        LoggerFactory.getLogger(this.getClass()).debug("A new game has started");
        Deck deck = new Deck52();
        Player[] players = table.getPlayers();
        Card[] onTable = getCardPool();
        LoggerFactory.getLogger(this.getClass()).debug("Suffleing the deck");
        deck.shuffle();
        LoggerFactory.getLogger(this.getClass()).debug("Issuing cards and instant Pre-flop Checking hands");
        int deckPointer = giveCards(players, deck);
        // No need to check the cards here noe
        //checkHands(onTable);
        deckPointer = showFlop(deck, deckPointer, onTable);
        checkHands(onTable, GameStage.flop);
        deckPointer = showTurn(deck, deckPointer, onTable);
        checkHands(onTable, GameStage.turn);
        showRiver(deck, deckPointer, onTable);
        Map<Hand, Player> hands = checkHands(onTable, GameStage.river);
        getWinner(hands);
        getWinPair(onTable, hands);
    }

    /**
     *
     * @param players
     * @param deck
     * @return Deck card pointer index. Means which card was issue last plus two
     */
    private int giveCards(Player[] players, Deck deck) {
        int pointer = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                Card[] playerCards = new Card[]{deck.getCards()[pointer], deck.getCards()[pointer + 2]};
                try {
                    players[i].receiveCards(playerCards);
                    pointer += 4;
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Player " + players[i].getName() + " cannot receive cards", ex);
                }

            }
        }
        return pointer;
    }

    private Map<Hand, Player> checkHands(Card[] onTable, GameStage stage) {
        Map<Hand, Player> hands = new TreeMap<>(Collections.reverseOrder());
        Player[] players = table.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                try {
                    Hand hand = players[i].checkHand(onTable, stage);
                    hands.put(hand, players[i]);
                } catch (ExtraCardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error(String.format("Extra cards %d are on the table for player %s", onTable.length - 5, players[i].getName()), ex);
                }
            }
        }
        return hands;
    }

    public Player getWinner(Map<Hand, Player> hands) {
        Map.Entry<Hand, Player> winner = hands.entrySet().iterator().next();
        LoggerFactory.getLogger(this.getClass()).info(String.format("Winner: %s won with hand %s", winner.getValue().getName(), winner.getKey()));
        Collector.getCollector().registerWinner(winner.getValue());
        return winner.getValue();
    }

    private Pair getWinPair(Card[] onTable, Map<Hand, Player> hands) {
        Hand hand = hands.entrySet().iterator().next().getKey();
        List<Card> handCards = Arrays.asList(hand.getCards());
        handCards.removeAll(Arrays.asList(onTable));
        Pair p = null;
        try {
            p = new Pair(handCards.toArray(new Card[2]));

            LoggerFactory.getLogger(this.getClass()).debug("Winning pair " + p);
            Collector.getCollector().registerWinningPair(p);
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).error("cannot create pair", ex);
        }
        return p;
    }

    /**
     * Makes a place for cards on the playing table
     *
     * @return
     */
    private Card[] getCardPool() {
        Card[] onTable = new Card[5];
        Arrays.fill(onTable, null);
        return onTable;
    }

    /**
     * Issues a flop cards
     *
     * @param deck Deck of Cards
     * @param deckPointer pointer after last card which has been given to a
     * player
     * @param table Array of five table cards in game. Flop should put there 3
     * of 5 cards.
     * @return pointer after the flop cards.
     */
    private int showFlop(Deck deck, int deckPointer, Card[] table) {
        //TODO Check how how card should be issued to the flop. Sequentially or every other one
        Card[] cardsInDeck = deck.getCards();
        //By default we will put to flop every other card
        for (int i = 0; i < 3; i++) {
            table[i] = cardsInDeck[deckPointer];
            deckPointer += 2;
        }
        return deckPointer;
    }

    private int showTurn(Deck deck, int deckPointer, Card[] table) {
        Card[] cardsInDeck = deck.getCards();
        table[3] = cardsInDeck[deckPointer];
        deckPointer += 2;
        return deckPointer;
    }

    private int showRiver(Deck deck, int deckPointer, Card[] table) {
        Card[] cardsInDeck = deck.getCards();
        table[4] = cardsInDeck[deckPointer];
        deckPointer += 2;
        return deckPointer;
    }
}
