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
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Deck52 implements Deck {
    private Card[] deck = new Card[52];
    
    public Deck52(){
        int i = 0;
        for (Suit s : Suit.values()){
            for (SuitSet ss : SuitSet.values()){
                Card c = new Card(s, ss);
                deck[i++] = c;
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
