/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.deck.Suit;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.BadCardException;

/**
 *
 * @author maskimko
 */
public class XmlPairStatisticSaverTest {
    
    public XmlPairStatisticSaverTest() {
    }
    
  
    

    /**
     * Test of save method, of class XmlPairStatisticSaver.
     */
    @Test
    public void testSave() {
        System.out.println("save as xml");
        PairStatisticSaverFactory pairStatisticSaverFactory = PairStatisticSaverFactory.getPairStatisticSaverFactory();
        PairStatisticSaver instance = null;
        try {
        instance = pairStatisticSaverFactory.getXmlInstance("/tmp/stat_test.xml");
        Map<Pair, Integer> strengthMap = new HashMap<>();
        strengthMap.put(new Pair(new Card(Suit.CLUBS, SuitSet.TWO), new Card(Suit.CLUBS, SuitSet.THREE)), 34);
        strengthMap.put(new Pair(new Card(Suit.SPADES, SuitSet.FOUR), new Card(Suit.SPADES, SuitSet.FIVE)), 43);
        strengthMap.put(new Pair(new Card(Suit.DIAMONS, SuitSet.SIX), new Card(Suit.DIAMONS, SuitSet.SEVEN)), 45);
        strengthMap.put(new Pair(new Card(Suit.HEARTS, SuitSet.EIGHT), new Card(Suit.HEARTS, SuitSet.NINE)), 54);
        instance.save(strengthMap);
        } catch (IOException ex){
            LoggerFactory.getLogger(this.getClass()).error("Cannot save statistic to the file during the test");
        } catch (BadCardException ex) {
             LoggerFactory.getLogger(this.getClass()).error("Cannot create Pair of cards during the test");
        } finally {
            if (instance != null) {
                try {
                    instance.close();
                } catch (Exception ex) {
                   LoggerFactory.getLogger(this.getClass()).error("Cannot close saver during the test");
                }
            }
        }
    }


}
