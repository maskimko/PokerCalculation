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
public class PairStatisticSaverFactory {

    public PairStatisticSaverFactory() {
    }

    public PairStatisticSaver getXmlInstance(File statsFile) throws IOException{
        XmlPairStatisticSaver xmlPairStatisticSaver = new XmlPairStatisticSaver(statsFile);
        return xmlPairStatisticSaver;
        
    }
    
    public PairStatisticSaver getXmlInstance(String statsFilePath) throws IOException{
        File pairStatFile = new File(statsFilePath);
        return getXmlInstance(pairStatFile);
    } 
    
     public PairStatisticSaver getXmlInstance(OutputStream os){
        XmlPairStatisticSaver xmlPairStatisticSaver = new XmlPairStatisticSaver(os);
        return xmlPairStatisticSaver;
    } 
    
}
