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

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandStrengthFactory {
    
    
    public HandStrengthFactory(){    }
    
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
             hs = new SimpleHandStrength(is);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            throw new PokerException("Internal error", ex);
        } 
        return hs;
    }
    
    public HandStrength build(Map<Pair, Integer> strengthMap){
        HandStrength hs = new SimpleHandStrength(strengthMap);
        return hs;
    }
    
    
}
