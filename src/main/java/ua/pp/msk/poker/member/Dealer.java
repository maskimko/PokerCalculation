/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Deck;
import ua.pp.msk.poker.deck.Deck52;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;

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
         LoggerFactory.getLogger(this.getClass()).debug("Issuing cards");
        int deckPointer = giveCards(players, deck);
         LoggerFactory.getLogger(this.getClass()).debug("Pre-flop Checking hands");
        checkHands(onTable);
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
                    players[i].checkHand(onTable);
                } catch (ExtraCardException ex) {
                   LoggerFactory.getLogger(this.getClass()).error(String.format("Extra cards %d are on the table for player %s", onTable.length -5, players[i].getName()), ex);
                }
            }
        }
    }
    
    private Card[] getCardPool(){
         Card[] onTable = new Card[5];
         Arrays.fill(onTable, null);
         return onTable;
    }
}
