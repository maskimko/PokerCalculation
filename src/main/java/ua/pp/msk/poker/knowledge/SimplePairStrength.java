/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.deck.Suit;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.exceptions.ExtraCardException;
import ua.pp.msk.poker.exceptions.MissingCardException;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimplePairStrength implements HandStrength {

    private final Map<Pair, Float> sm;
    private float defStr = 0.01f;

    SimplePairStrength(Map<Pair, Integer> strengthMap) {
        int max = 0;
        this.sm = new HashMap<>();
        Iterator<Map.Entry<Pair, Integer>> iterator = strengthMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Pair, Integer> e = iterator.next();
            if (e.getValue() > max) {
                max = e.getValue();
            }
        }
        iterator = strengthMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Pair, Integer> e = iterator.next();
            float s = (float) e.getValue() * 100 / max;
            sm.put(e.getKey(), s);
        }
    }

    public SimplePairStrength(InputStream is) throws SAXException, ParserConfigurationException, IOException, CardException {
        this.sm = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(is);
        doc.getDocumentElement().normalize();
        NodeList pairElements = doc.getDocumentElement().getChildNodes();
        for (int t = 0; t < pairElements.getLength(); t++) {
            Node item = pairElements.item(t);
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getNodeName().equals(PAIR)) {
                Element pair = (Element) item;
                float s = 0.01f;
                Card[] cards = new Card[2];
                NodeList cardNodes = pair.getElementsByTagName(CARD);
                NodeList strengthNodes = pair.getElementsByTagName(STRENGTH);
                if (cardNodes.getLength() > 2) {
                    throw new ExtraCardException("Too much cards " + cardNodes.getLength() + "Pair can have two cards only");
                }
                if (cardNodes.getLength() < 2) {
                    throw new MissingCardException("Too few cards " + cardNodes.getLength() + "Pair can have two cards only");
                }
                for (int k = 0; k < cardNodes.getLength(); k++) {
                    if (cardNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        Element card = (Element) cardNodes.item(k);
                        Suit suit = Suit.valueOf(card.getAttribute(SUIT));
                        SuitSet suitValue = SuitSet.valueOf(card.getAttribute(VALUE));
                        cards[k] = new Card(suit, suitValue);
                    }
                }
                if (strengthNodes.getLength() != 1) {
                    throw new CardException("Pair of Cars can have only one strength value");
                }
                if (strengthNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
                    Element strength = (Element) strengthNodes.item(0);
                    s = Float.parseFloat(strength.getFirstChild().getNodeValue());
                }
                Pair p = new Pair(cards);
                sm.put(p, s);
            } 
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getNodeName().equals(DEFAULTSTRENGTH)){
                Element ds = (Element) item;
                String defStrVal = ds.getFirstChild().getNodeValue();
                defStr = Float.parseFloat(defStrVal);
            }
        }
    }

    public float estimate(Pair pair    ) {
        float strength =defStr;
        if (sm.containsKey(pair)) {
            strength = sm.get(pair);
        }
        return strength;
    }

    @Override
    public float estimate(Hand hand ) {
        Card[] cards = hand.getCards();
        Card[] pairCards = new Card[2];
        int i = 0;
        for (Card c: cards) {
            if (c != null ){
                if ( i < pairCards.length) pairCards[i++] = c;
                else i++;
            }
        }
        if (i == 2) {
            float estimation = defStr;
            try {
               estimation=  estimate(new Pair(pairCards));
            } catch (CardException ex) {
                LoggerFactory.getLogger(this.getClass()).warn("Only pairs estimation is supported in this class. You got the wrong result");
            }
            return estimation;
        }
        return defStr;
    }

}
