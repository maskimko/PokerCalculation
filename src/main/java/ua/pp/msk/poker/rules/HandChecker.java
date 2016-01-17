/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.rules;

import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.exceptions.CardException;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public interface HandChecker {
    public Hand checkHand(Card[] cards) throws CardException;
}
