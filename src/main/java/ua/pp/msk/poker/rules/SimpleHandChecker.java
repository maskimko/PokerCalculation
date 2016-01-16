/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

    private Card[] cardCombination = new Card[5];
    
    public SimpleHandChecker(){
        Arrays.fill(cardCombination, null);
    }
    
    @Override
    public Combination checkHand(Card[] cards) throws CardException {
       Combination state = Combination.HIGHHAND;
        if (cards.length < 2) {
            throw new MissingCardException(String.format("Missing  %d cards. it should be at least 2 cards", 2 - cards.length));
        }
        if (cards.length > 7) {
            throw new ExtraCardException(String.format("Extra %d cards. it should be at most 7 cards", cards.length - 7));
        }
        if (isPair(cards)) {
            state = Combination.ONEPAIR;
        }
        if (state == Combination.HIGHHAND) LoggerFactory.getLogger(this.getClass()).debug(String.format("Found High Hand combination %s", Arrays.toString(cards)));
        return state;
    }

    private boolean isPair(Card[] cards) {
        boolean result = false;
        Map<SuitSet, Integer> cardMap = getCardValueMap(cards);
        Iterator<Map.Entry<SuitSet, Integer>> iterator = cardMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SuitSet, Integer> entry = iterator.next();
            if (entry.getValue() == 2) {
                LoggerFactory.getLogger(this.getClass()).debug(String.format("Found One Pair combination  of %ss %s", entry.getKey(), Arrays.toString(cards)));
                result = true;
                break;
            }
        }
        return result;
    }

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
    
    
    @Deprecated
    private Card getKicker(Card[] cardsInGame, Card[] combinationCards ){
        sort(cardsInGame);
        Card kicker = null;
        for (int i = 0; i < cardsInGame.length; i++){
            boolean inCombination = false;
            for (int j=0; j< combinationCards.length; j++){
                if (combinationCards[j] != null){
                    if (combinationCards[j].equals(cardsInGame[i])){
                        inCombination = true;
                    }
                }
            }
            if (!inCombination){
                
                if (kicker == null){
                    kicker = cardsInGame[i];
                } else  { 
                    if ( kicker.compareTo(cardsInGame[i]) < 0){
                    kicker = cardsInGame[i];
                }
                }
            }
        }
        return kicker;
    }
    
    private void sort(Card[] cards){
        Arrays.sort(cards);
    }

}
