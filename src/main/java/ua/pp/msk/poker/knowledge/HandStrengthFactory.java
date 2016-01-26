/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.knowledge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.exceptions.PokerException;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandStrengthFactory {
    
    private static HandStrengthFactory hsf = null;
    
    public static HandStrengthFactory getHandStrengthFactory(){
        if (hsf == null) {
            synchronized(HandStrengthFactory.class){
                if (hsf == null) {
                    hsf = new HandStrengthFactory();
                }
            }
        }
        return hsf;
    }
    
    private HandStrengthFactory(){    }
    
    public HandStrength build(File handStats) throws PokerException{
        HandStrength hs = null;
        try {
        FileInputStream fis = new FileInputStream(handStats);
        hs =  build(fis);
        } catch (FileNotFoundException ex){
            throw new PokerException("Cannot find the file " + handStats.getAbsolutePath(), ex);
        }
        return hs;
    }
    
    public HandStrength build(InputStream is) throws PokerException{
        HandStrength hs = null;
        try {
             hs = new SimplePairStrength(is);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            throw new PokerException("Internal error", ex);
        } 
        return hs;
    }
    
    public HandStrength build(Map<GameStage, Map<Hand, Integer>> wins, int gamesPlayed){
        HandStrength hs = new SimpleHandStrength(wins, gamesPlayed);
        return hs;
    }
    
    
}
