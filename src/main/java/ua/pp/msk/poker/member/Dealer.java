/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.Arrays;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.Statistic;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Deck;
import ua.pp.msk.poker.deck.Deck52;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.rules.Hand;

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
    }
    
    /**
     * 
     * @param players
     * @param deck
     * @return Deck card pointer index. Means which card was issue last plus two
     */
    private int giveCards(Player[] players, Deck deck){
        int pointer = 0;
        for (int i = 0; i < players.length; ){
            if (players[i] != null) {
                Card[] playerCards = new Card[]{deck.getCards()[i], deck.getCards()[i+2]};
                try {
                    players[i].receiveCards(playerCards);
                    i +=4;
                    pointer = i;
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Player " + players[i].getName() + " cannot receive cards", ex);
                }
                
            }
        }
        return pointer;
    }
    
    
    private void checkHands(Card[] onTable) {
        Player[] players = table.getPlayers();
        for (int i =0; i< players.length; i++){
            if (players[i] != null){
                try {
                    Hand hand = players[i].checkHand(onTable);
                } catch (ExtraCardException ex) {
                   LoggerFactory.getLogger(this.getClass()).error(String.format("Extra cards %d are on the table for player %s", onTable.length -5, players[i].getName()), ex);
                }
            }
        }
    }
    
    /**
     * Makes a place for cards on the playing table
     * @return 
     */
    private Card[] getCardPool(){
         Card[] onTable = new Card[5];
         Arrays.fill(onTable, null);
         return onTable;
    }
}
