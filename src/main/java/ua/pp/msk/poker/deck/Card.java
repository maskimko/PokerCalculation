/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.deck;

import java.util.Objects;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Card implements Comparable<Card>{
private final Suit suit;
private final SuitSet value;
private boolean visible = true;

/*
You may need to have some special font installed in order to see the card pic
*/

public Card (Suit suit, SuitSet value){
    this.suit = suit;
    this.value = value;
}

    public Suit getSuit() {
        return suit;
    }

    public SuitSet getValue() {
        return value;
    }

    public String getSymbol(){
        int number = suit.getPrefix() + value.getNumber();
        String symbol = visible ? Character.toString((char) number) : Character.toString((char)0x1f0a0);
        return symbol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.suit);
        hash = 29 * hash + Objects.hashCode(this.value);
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
        final Card other = (Card) obj;
        if (this.suit != other.suit) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return String.format("%2s%s", value.getLetter(), suit.getSymbol());
    }

    @Override
    public int compareTo(Card t) {
        if (value.ordinal() < t.value.ordinal()){
            return 1;
        } else if (value.ordinal() > t.value.ordinal()){
            return -1;
        } else return 0;
    }
    
    

}
