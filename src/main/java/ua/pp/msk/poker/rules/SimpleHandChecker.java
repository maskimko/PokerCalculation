/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.MissingCardException;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.exceptions.CardOrderException;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleHandChecker implements HandChecker {


    /**
     * Check what combination is in game
     *
     * @param cards Cards which are in the game. Up to 7 cards. 5 on a table and
     * 2 player cards
     * @return Players best Hand
     * @throws CardException
     */
    @Override
    public Hand checkHand(Card[] cards) throws CardException {

        if (cards.length < 2) {
            throw new MissingCardException(String.format("Missing  %d cards. it should be at least 2 cards", 2 - cards.length));
        }
        if (cards.length > 7) {
            throw new ExtraCardException(String.format("Extra %d cards. it should be at most 7 cards", cards.length - 7));
        }
        Hand hand = new Hand();
        Map<SuitSet, List<Card>> cardMap = getCardValueMap(cards);
        boolean onePair = isOnePair(cards, cardMap);
        boolean twoPairs = false;
        boolean threeOfKind = false;
        boolean fourOfKind = false;

        if (onePair) {
            twoPairs = isTwoPairs(cards, cardMap);
            if (twoPairs) {
                hand = makeTwoPairsHand(cards, cardMap);
            } else {
                hand = makeOnePairHand(cards, cardMap);
            }
        } else {
            threeOfKind = isThreeOfKind(cards, cardMap);
            fourOfKind = isFourOfKind(cards, cardMap);
            if (threeOfKind){
                hand = makeThreeOfKindHand(cards, cardMap);
            }
            if (fourOfKind) {
                hand = makeFourOfKindHand(cards, cardMap);
            }
        }

        //TODO implement Straight
        //TODO implement Flush
        //TODO implement Straight Flush
        //TODO implement Royal Flush
        if (hand.getCombination().equals(Combination.HIGHHAND)) {
            Card[] handCards = fillUpTheHand(cards, new Card[5]);
            hand.setCards(handCards);
            LoggerFactory.getLogger(this.getClass()).debug(String.format("Found High Hand combination %s", hand.toString()));
        }
        return hand;
    }

    private boolean isFourOfKind(Card[] cards, Map<SuitSet, List<Card>> cardMap) throws CardException {
        boolean result = false;
        Iterator<Map.Entry<SuitSet, List<Card>>> iterator = cardMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SuitSet, List<Card>> entry = iterator.next();
            if (entry.getValue().size() == 4) {
                LoggerFactory.getLogger(this.getClass()).debug(String.format("Found Four Of Kind combination: %s %s %s %s", entry.getValue().get(0), entry.getValue().get(1), entry.getValue().get(2), entry.getValue().get(3)));
                result = true;
                break;
            }
        }
        return result;

//        sort(cards);
//        //Check for nulls
//        int notNulls = cards.length;
//        for (int j = 0; j < cards.length; j++) {
//            if (cards[j] == null) {
//                notNulls--;
//            }
//        }
//        if (notNulls >= 4) {
//            Card[] handCards = hand.getCards();
//            Arrays.fill(handCards, null);
//            for (int i = 0; i < cards.length - 3; i++) {
//                if (cards[i] != null && cards[i + 1] != null && cards[i + 2] != null && cards[i + 3] != null) {
//                    if (cards[i].getValue().equals(cards[i + 1].getValue()) && cards[i].getValue().equals(cards[i + 2].getValue()) && cards[i].getValue().equals(cards[i + 3].getValue())) {
//                        handCards[0] = cards[i];
//                        handCards[1] = cards[i + 1];
//                        handCards[2] = cards[i + 2];
//                        handCards[3] = cards[i + 3];
//                        handCards = fillUpTheHand(cards, handCards);
//                        hand.setCards(handCards);
//                        hand.setCombination(Combination.FOUROFKIND);
//                        break;
//                    }
//                }
//            }
//        }
    }

    private boolean isThreeOfKind(Card[] cards, Map<SuitSet, List<Card>> cardMap) throws CardException {
        boolean result = false;
        Iterator<Map.Entry<SuitSet, List<Card>>> iterator = cardMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SuitSet, List<Card>> entry = iterator.next();
            if (entry.getValue().size() == 3) {
                LoggerFactory.getLogger(this.getClass()).debug(String.format("Found Three Of Kind combination: %s %s %s", entry.getValue().get(0), entry.getValue().get(1), entry.getValue().get(2)));
                result = true;
                break;
            }
        }
        return result;

//        sort(cards);
//        //Check for nulls
//        int notNulls = cards.length;
//        for (int j = 0; j < cards.length; j++) {
//            if (cards[j] == null) {
//                notNulls--;
//            }
//        }
//        if (notNulls >= 3) {
//            Card[] handCards = hand.getCards();
//            Arrays.fill(handCards, null);
//            for (int i = 0; i < cards.length - 2; i++) {
//                if (cards[i] != null && cards[i + 1] != null && cards[i + 2] != null) {
//                    if (cards[i].getValue().equals(cards[i + 1].getValue()) && cards[i].getValue().equals(cards[i + 2].getValue())) {
//                        handCards[0] = cards[i];
//                        handCards[1] = cards[i + 1];
//                        handCards[2] = cards[i + 2];
//                        handCards = fillUpTheHand(cards, handCards);
//                        hand.setCards(handCards);
//                        hand.setCombination(Combination.THREEOFKIND);
//                        break;
//                    }
//                }
//            }
//        }
    }

    private Hand makeThreeOfKindHand(Card[] cardsInGame, Map<SuitSet, List<Card>> cardMap) {
        Hand hand = new Hand();
        hand.setCombination(Combination.THREEOFKIND);
        for (Map.Entry<SuitSet, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() == 3) {
                try {
                    Card[] handCards = fillUpTheHand(cardsInGame, entry.getValue().toArray(new Card[0]));
                    hand.setCards(handCards);
                    break;
                } catch (CardOrderException e) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make Three Of Kind combination ", e);
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make hand with Three Of Kind combination ", ex);
                }
            }
        }
        return hand;
    }

    private Hand makeFourOfKindHand(Card[] cardsInGame, Map<SuitSet, List<Card>> cardMap) {
        Hand hand = new Hand();
        hand.setCombination(Combination.FOUROFKIND);
        for (Map.Entry<SuitSet, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() == 4) {
                try {
                    Card[] handCards = fillUpTheHand(cardsInGame, entry.getValue().toArray(new Card[0]));
                    hand.setCards(handCards);
                    break;
                } catch (CardOrderException e) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make Four Of Kind combination ", e);
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make hand with Four Of Kind combination ", ex);
                }
            }
        }
        return hand;
    }

    private boolean isOnePair(Card[] cards, Map<SuitSet, List<Card>> cardMap) throws CardException {
        boolean result = false;
//        sort(cards);
//        for (int i = 0; i < cards.length - 1; i++) {
//            if (cards[i] != null) {
//                if (cards[i + 1] != null && cards[i].getValue().equals(cards[i + 1].getValue())) {
//                    try {
//                        result = true;
//                        Card[] handCards = fillUpTheHand(cards, new Card[]{cards[i], cards[i + 1]});
//                        hand.setCards(handCards);
//                        hand.setCombination(Combination.ONEPAIR);
//                        break;
//                    } catch (CardException ex) {
//                        LoggerFactory.getLogger(this.getClass()).error("Card quantity error", ex);
//                    }
//                }
//            }
//        }

        Iterator<Map.Entry<SuitSet, List<Card>>> iterator = cardMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SuitSet, List<Card>> entry = iterator.next();
            if (entry.getValue().size() == 2) {
                LoggerFactory.getLogger(this.getClass()).debug(String.format("Found One Pair combination: %s %s", entry.getValue().get(0), entry.getValue().get(1)));;
                result = true;
                break;
            }
        }
        return result;
    }

    private Hand makeOnePairHand(Card[] cardsInGame, Map<SuitSet, List<Card>> cardMap) {
        Hand hand = new Hand();
        hand.setCombination(Combination.ONEPAIR);
        for (Map.Entry<SuitSet, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                try {
                    Card[] handCards = fillUpTheHand(cardsInGame, entry.getValue().toArray(new Card[0]));
                    hand.setCards(handCards);
                    break;
                } catch (CardOrderException e) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make One Pair combination ", e);
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make hand with One Pair combination ", ex);
                }
            }
        }
        return hand;
    }

    public boolean isTwoPairs(Card[] cards, Map<SuitSet, List<Card>> cardMap) throws CardException {
        byte pairs = 0;
//        byte pairsDetected = 0;
//        byte handPointer = 0;
//        Card[] handCards = hand.getCards();
//        Arrays.fill(handCards, null);
//        sort(cardsInGame);
//        for (int i = 0; i < cardsInGame.length - 1 && pairsDetected != 2; i++) {
//            if (cardsInGame[i] != null && cardsInGame[i + 1] != null && cardsInGame[i].getValue().equals(cardsInGame[i + 1].getValue())) {
//                handCards[handPointer++] = cardsInGame[i];
//                handCards[handPointer++] = cardsInGame[i + 1];
//                pairsDetected++;
//            }
//        }
//        handCards = fillUpTheHand(cardsInGame, handCards);
//        hand.setCards(handCards);
//        if (pairsDetected == 2) {
//            hand.setCombination(Combination.TWOPAIRS);
//        }
        for (Map.Entry<SuitSet, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                pairs++;
            }
            if (pairs == 2) {
                break;
            }
        }
        return pairs == 2;
    }

    private Hand makeTwoPairsHand(Card[] cardsInGame, Map<SuitSet, List<Card>> cardMap) {
        Hand hand = new Hand();
        hand.setCombination(Combination.TWOPAIRS);
        List<Card> combinationCards = new LinkedList<>();
        for (Map.Entry<SuitSet, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                combinationCards.add(entry.getValue().get(0));
                combinationCards.add(entry.getValue().get(1));
            }
            if (combinationCards.size() == 4) {
                try {
                    LoggerFactory.getLogger(this.getClass()).debug(String.format("Found Two Pairs combination: %s %s %s %s", combinationCards.get(0), combinationCards.get(1), combinationCards.get(2), combinationCards.get(3)));
                    Card[] handCards = fillUpTheHand(cardsInGame, entry.getValue().toArray(new Card[0]));
                    hand.setCards(handCards);
                } catch (CardOrderException e) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make One Pair combination ", e);
                } catch (CardException ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot make hand with One Pair combination ", ex);
                }
                break;
            }
        }

        return hand;
    }

    private Map<SuitSet, List<Card>> getCardValueMap(Card[] cards) {
        Map<SuitSet, List<Card>> cardMap = new TreeMap<>(Collections.reverseOrder());
        for (SuitSet ss : SuitSet.values()) {
            cardMap.put(ss, new LinkedList<>());
        }
        for (Card card : cards) {
            if (card != null) {
                SuitSet value = card.getValue();
                cardMap.get(value).add(card);
            }
        }
        return cardMap;
    }

    private Card[] fillUpTheHand(Card[] cardsInGame, Card[] combinationCards) throws CardOrderException {
        sort(cardsInGame);
        boolean descSorted = ensureDescSorted(cardsInGame);
        if (!descSorted) {
            throw new CardOrderException("Cards in shold be sorted in descending order");
        }
        //Copy combination cards to the hand
        Card[] handCards = new Card[5];
        int handPointer = 0;
        for (int i = 0; handPointer < combinationCards.length && i < combinationCards.length; i++) {
            if (combinationCards[handPointer] != null) {
                handCards[handPointer] = combinationCards[handPointer++];
            }
        }
        for (int i = 0; i < cardsInGame.length && !isHandComplete(handCards); i++) {
            if (cardsInGame[i] == null) {
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
            if (!alreadyInHand && cardsInGame[i] != null) {
                handCards[handPointer++] = cardsInGame[i];
            }
        }
        return handCards;
    }

    public static void sort(Card[] cards) {

        Arrays.sort(cards, Collections.reverseOrder((Card t, Card t1) -> {
            if (t == null && t1 == null) {
                return 0;
            } else if (t == null) {
                return -1;
            } else if (t1 == null) {
                return 1;
            } else {
                return t.compareTo(t1);
            }
        }));
    }

    private boolean isHandComplete(Card[] handCards) {
        for (int i = 0; i < handCards.length; i++) {
            if (handCards[i] == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean ensureDescSorted(Card[] cards) {
        boolean result = true;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i - 1] == null || cards[i] == null) {
                continue;
            }
            if (cards[i - 1].compareTo(cards[i]) < 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}
