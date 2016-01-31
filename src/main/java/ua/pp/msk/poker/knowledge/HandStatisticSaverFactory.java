/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.knowledge;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandStatisticSaverFactory {

    private static HandStatisticSaverFactory pssf = null;
    
    public static HandStatisticSaverFactory getPairStatisticSaverFactory(){
        if (pssf ==  null){
            synchronized(HandStatisticSaverFactory.class){
                if (pssf == null) {
                    pssf = new HandStatisticSaverFactory();
                }
            }
        }
        return pssf;
    }
    
    private HandStatisticSaverFactory() {
    }

    public HandStatisticSaver getXmlInstance(File statsFile) throws IOException{
        XmlPairStatisticSaver xmlPairStatisticSaver = new XmlPairStatisticSaver(statsFile);
        return xmlPairStatisticSaver;
        
    }
    
    public HandStatisticSaver getXmlInstance(String statsFilePath) throws IOException{
        File pairStatFile = new File(statsFilePath);
        return getXmlInstance(pairStatFile);
    } 
    
     public HandStatisticSaver getXmlInstance(OutputStream os){
        XmlPairStatisticSaver xmlPairStatisticSaver = new XmlPairStatisticSaver(os);
        return xmlPairStatisticSaver;
    } 
    
}
