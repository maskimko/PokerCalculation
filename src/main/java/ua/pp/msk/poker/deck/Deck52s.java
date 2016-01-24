/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.deck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Suit based Deck implementation
 * @author Maksym Shkolnyi aka maskimko
 */
public class Deck52s implements Deck{
 private Card[] deck = new Card[52];
    
    public Deck52s(){
        int i = 1;
        for (SuitSet ss : SuitSet.values()){
            for (Suit s : Suit.values()){
                Card c = new Card(s, ss);
                deck[52-i++] = c;
            }
        }
        
    }
    
    @Override
    public Card[] getCards(){
        return deck;
    }
    
    @Override
    public void shuffle(){
        List<Card> cardList = Arrays.asList(deck);
        Collections.shuffle(cardList);
        deck = cardList.toArray(deck);
    }
}
