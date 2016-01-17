/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.MissingCardException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleHandChecker implements HandChecker {

    private Hand hand = new Hand();

    

    @Override
    public Hand checkHand(Card[] cards) throws CardException {
        
        if (cards.length < 2) {
            throw new MissingCardException(String.format("Missing  %d cards. it should be at least 2 cards", 2 - cards.length));
        }
        if (cards.length > 7) {
            throw new ExtraCardException(String.format("Extra %d cards. it should be at most 7 cards", cards.length - 7));
        }
        isPair(cards, hand);
            
        
        if (hand.getCombination() == Combination.HIGHHAND) {
            Card[] handCards = fillUpTheHand(cards, new Card[5]);
            hand.setCards(handCards);
            LoggerFactory.getLogger(this.getClass()).debug(String.format("Found High Hand combination %s", hand.toString()));
        }
        return hand;
    }

    private void isPair(Card[] cards, Hand hand) throws CardException{
        boolean result = false;
        sort(cards);
        for (int i = 0; i < cards.length - 1; i++) {
          if (cards[i] != null){
            if (cards[i+1] != null && cards[i].getValue().equals(cards[i + 1].getValue())) {
                try {
                    result = true;
                    Card[] handCards = fillUpTheHand(cards, new Card[]{cards[i],cards[i+1]});
                    hand.setCards(handCards);
                    hand.setCombination(Combination.ONEPAIR);
                    break;
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Card quantity error", ex);
                }
            }
          }
        }
//        Map<SuitSet, Integer> cardMap = getCardValueMap(cards);
//        Iterator<Map.Entry<SuitSet, Integer>> iterator = cardMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<SuitSet, Integer> entry = iterator.next();
//            if (entry.getValue() == 2) {
//                LoggerFactory.getLogger(this.getClass()).debug(String.format("Found One Pair combination  of %ss %s", entry.getKey(), Arrays.toString(cards)));
//                result = true;
//                break;
//            }
//        }
        
    }

    @Deprecated
    private Map<SuitSet, Integer> getCardValueMap(Card[] cards) {
        Map<SuitSet, Integer> cardMap = new HashMap<>();
        //Initialize Map with zeroes
        for (SuitSet ss : SuitSet.values()) {
            cardMap.put(ss, 0);
        }
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                SuitSet value = cards[i].getValue();
                int previous = cardMap.get(value);
                cardMap.put(value, previous + 1);
            }
        }
        return cardMap;
    }

    
    //TODO Test this method;
    private Card[] fillUpTheHand(Card[] cardsInGame, Card[] combinationCards) {
        sort(cardsInGame);
        //Copy combination cards to the hand
        Card[] handCards = new Card[5];
        int handPointer = 0;
        for (int i = 0; handPointer < combinationCards.length && i < combinationCards.length; i++) {
           if (combinationCards[handPointer] != null){
               handCards[handPointer] = combinationCards[handPointer++];
           }
        }
        boolean stop = false;
        while (!stop && !isHandComplete(handCards)) {
            Card kicker = null;
            for (int i = 0; i < cardsInGame.length; i++) {
                if (cardsInGame[i] == null){
                    stop = true;
                    break;
                }
                boolean alreadyInHand = false;
                for (int j = 0; j < handCards.length; j++) {
                    if (handCards[j] != null) {
                        if (handCards[j].equals(cardsInGame[i])) {
                            alreadyInHand = true;
                        }
                    }
                }
                if (!alreadyInHand) {

                    if (kicker == null) {
                        kicker = cardsInGame[i];
                    } else {
                        if (kicker.compareTo(cardsInGame[i]) < 0) {
                            kicker = cardsInGame[i];
                        }
                    }
                    handCards[handPointer++] = kicker;
                }
            }
        }
        return handCards;
    }

    private void sort(Card[] cards) {
         
                 Arrays.sort(cards, (Card t, Card t1) -> {
                     if (t == null && t1 == null){
                         return 0;
                     } else if (t == null){
                         return 1;
                     } else if (t1 == null) {
                         return -1;
                     } else return t.compareTo(t1);
                 });
    }

    private boolean isHandComplete(Card[] handCards) {
        for (int i = 0; i < handCards.length; i++) {
            if (handCards[i] == null) {
                return false;
            }
        }
        return true;
    }
}
