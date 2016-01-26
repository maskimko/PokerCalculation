/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.deck.Suit;
import ua.pp.msk.poker.deck.SuitSet;
import ua.pp.msk.poker.exceptions.BadCardException;
import ua.pp.msk.poker.exceptions.PokerException;

/**
 *
 * @author maskimko
 */
public class XmlPairStatisticSaveRestoreTest {

    private static Map<Pair, Integer> strengthMap;
    private static String filePath = "/tmp/stat_test.xml";

    public XmlPairStatisticSaveRestoreTest() {
    }

    @BeforeClass
    public static void beforeClass() {
        try {
            strengthMap = new HashMap<>();
            strengthMap.put(new Pair(new Card(Suit.CLUBS, SuitSet.TWO), new Card(Suit.CLUBS, SuitSet.THREE)), 34);
            strengthMap.put(new Pair(new Card(Suit.SPADES, SuitSet.FOUR), new Card(Suit.SPADES, SuitSet.FIVE)), 43);
            strengthMap.put(new Pair(new Card(Suit.DIAMONS, SuitSet.SIX), new Card(Suit.DIAMONS, SuitSet.SEVEN)), 45);
            strengthMap.put(new Pair(new Card(Suit.HEARTS, SuitSet.EIGHT), new Card(Suit.HEARTS, SuitSet.NINE)), 54);
        } catch (BadCardException ex) {
            LoggerFactory.getLogger(XmlPairStatisticSaveRestoreTest.class).error("Cannot create Pair of cards during the test", ex);
        }
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
            instance = pairStatisticSaverFactory.getXmlInstance(filePath);
            instance.save(strengthMap);
        } catch (IOException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Cannot save statistic to the file during the test", ex);
        } finally {
            if (instance != null) {
                try {
                    instance.close();
                } catch (Exception ex) {
                    LoggerFactory.getLogger(this.getClass()).error("Cannot close saver during the test", ex);
                }
            }
        }
        assertTrue(Files.exists(Paths.get(filePath)));
    }

    @Test
    public void testRestore() {
        HandStrengthFactory handStrengthFactory = HandStrengthFactory.getHandStrengthFactory();
        try {
            HandStrength hs = handStrengthFactory.build(new File(filePath));
            float pstr = hs.estimate(new Pair(new Card(Suit.HEARTS, SuitSet.EIGHT), new Card(Suit.HEARTS, SuitSet.NINE)));
            assertTrue(Math.abs(100f - pstr) < 0.1f);
             pstr = hs.estimate(new Pair(new Card(Suit.DIAMONS, SuitSet.SIX), new Card(Suit.DIAMONS, SuitSet.SEVEN)));
            assertTrue(Math.abs(83.3f - pstr) < 0.1f);
        } catch (PokerException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Cannot build HandStrength during the test", ex);
        }
    }
}
