/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.rules;

import java.util.Arrays;
import java.util.Objects;
import ua.pp.msk.poker.deck.Card;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Hand implements Comparable<Hand>{
private Combination combination;
private Card[] cards;
public Hand(){
    combination = Combination.HIGHHAND;
    cards = new Card[5];
    Arrays.fill(cards, null);
}

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.combination);
        hash = 79 * hash + Arrays.deepHashCode(this.cards);
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
        final Hand other = (Hand) obj;
        if (this.combination != other.combination) {
            return false;
        }
        if (!cardsEquals(this.cards, other.getCards())) {
            return false;
        }
        return true;
    }
    
    
    //TODO Test this method
    private boolean cardsEquals(Card[] one, Card[] second){
        if (one.length != second.length) {
            return false;
        }
        for (int i = 0; i < one.length; i++){
            boolean hasThisCard = false;
            for (int j = 0; j < second.length; j++){
                if (one[i].equals(second[j])){
                    hasThisCard = true;
                    break;
                }
            }
            if (!hasThisCard) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%15s\t%s", combination.name(), Arrays.toString(cards));
    }

    
    //TODO Test this method
    @Override
    public int compareTo(Hand t) {
       if (this.combination.ordinal() > t.combination.ordinal()){
           return 1;
       } else if (this.combination.ordinal() < t.combination.ordinal()){
           return -1;
       } else if (this.equals(t)){
           return 0;
       } else {
           //TODO check if it is descending after the sorting
           Arrays.sort(this.cards);
           Arrays.sort(t.cards);
           for (int i = 0; i < cards.length; i++){
               if (this.cards[i].compareTo(t.cards[i]) != 0){
                   return this.cards[i].compareTo(t.cards[i]);
               }
           }
       }
       return 0;
    }



}
