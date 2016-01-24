/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.deck;

import java.util.Arrays;
import java.util.Objects;
import ua.pp.msk.poker.exceptions.BadCardException;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.MissingCardException;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Pair {
    private final Card first;
    private final Card second;
    
    public Pair(Card[] cards) throws CardException{
        if (cards == null)  throw new BadCardException("Cannot initialize pair class with null value array");
        if (cards.length < 2) throw new MissingCardException("Cannot set less than 2 cards to Pair object");
        if (cards.length > 2) throw new ExtraCardException("Cannot set more than 2 cards to Pair object");
        if ( cards[0] == null || cards[1] == null) throw new BadCardException("Cards cannot be null value");
        if (cards[0] == cards[1] || cards[0].equals(cards[1])) throw new BadCardException("Cards cannot be equal or same");
        
        first = cards[0].compareTo(cards[1]) > 0 ?  cards[0] : cards[1];
        second = cards[1] != first ? cards[1] : cards[0];
    }
    
    public Pair(Card c1, Card c2) throws BadCardException{
         if ( c1 == null || c2 == null) throw new BadCardException("Cards cannot be null value");
        if (c1 == c2 || c1.equals(c2)) throw new BadCardException("Cards cannot be equal or same");
        first = c1.compareTo(c2) > 0 ? c1 : c2;
        second = c2 != first ? c2 : c1;
    }
    
    public Card[] getCards(){
        return new Card[]{first, second};
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.first);
        hash = 97 * hash + Objects.hashCode(this.second);
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
        final Pair other = (Pair) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "Pair of "+ first + " and " + second;
    }
    
    
}
