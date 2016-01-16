/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.deck;

/**
 *
 * @author maskimko
 */
public interface Deck {
    public Card[] getCards();
    public void shuffle();
}
