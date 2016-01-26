/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

import java.util.Arrays;
import java.util.Objects;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.MissingCardException;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Hand implements Comparable<Hand>, Cloneable {

    private Combination combination;
    private Card[] cards;

    public Hand() {
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

    public void setCards(Card[] cards) throws CardException {
        if (cards.length > 5) {
            throw new ExtraCardException("Hand cannot be bigger than 5 cards");
        }
        if (cards.length < 5) {
            throw new MissingCardException("Hand cannot be smaller than 5 cards");
        }
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
    private boolean cardsEquals(Card[] one, Card[] second) {
        if (one.length != second.length) {
            return false;
        }
        int n = 0;
        for (int i = 0; i < one.length; i++) {

            if (one[i] != null) {
                boolean hasThisCard = false;
                for (int j = 0; j < second.length; j++) {
                    if (one[i].equals(second[j])) {
                        hasThisCard = true;
                        break;
                    };
                }
                if (!hasThisCard) {
                    return false;
                }
            } else {
                n++;
            }
        }
        if (n != 0) {
            int sn = 0;
            for (int k = 0; k < second.length; k++) {
                if (second[k] == null) {
                    sn++;
                }
            }
            if (n != sn) {
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
        if (this.combination.ordinal() > t.combination.ordinal()) {
            return 1;
        } else if (this.combination.ordinal() < t.combination.ordinal()) {
            return -1;
        } else if (this.equals(t)) {
            return 0;
        } else {
            //TODO check if it is descending after the sorting
            Arrays.sort(this.cards);
            Arrays.sort(t.cards);
            for (int i = 0; i < cards.length; i++) {
                if (this.cards[i].compareTo(t.cards[i]) != 0) {
                    return this.cards[i].compareTo(t.cards[i]);
                }
            }
        }
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Hand cloned = null;
        try {
            cloned = new Hand();
            Card[] clonedCards = new Card[cards.length];
            for (int i = 0; i < cards.length; i++) {
                clonedCards[i] = (Card) cards[i].clone();
            }
            cloned.setCards(clonedCards);
            cloned.setCombination(combination);
        } catch (CardException ex) {
            throw new CloneNotSupportedException("Cannot clone Hand beacause of " + ex.getMessage());
        }
        return cloned;
    }

}
