/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.knowledge;

import java.io.OutputStream;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.CARD;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.CHANCE;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.COMBINATION;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.DEFAULTSTRENGTH;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.ESTIMATION;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.GAMES;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.HAND;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.HANDS;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.LOSETIMES;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.STATISTIC;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.STRENGTH;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.SUIT;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.VALUE;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.WINTIMES;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;
import ua.pp.msk.poker.stat.HandStatistic;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class XmlHandStatisticStreamSaver implements HandStatisticSaver {

    private final  XMLStreamWriter w;
    
    
     XmlHandStatisticStreamSaver() throws XMLStreamException {
         this(System.out);
    }

    XmlHandStatisticStreamSaver(OutputStream os) throws XMLStreamException {
       XMLOutputFactory xmlOutFactory = XMLOutputFactory.newInstance();
        w = xmlOutFactory.createXMLStreamWriter(os, "utf-8");
    }
    
    @Override
    public void save(Map<Pair, Integer> strength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Map<Pair, Integer> strength, float defStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Map<GameStage, Map<Hand, Integer>> wins, Map<GameStage, Map<Hand, Integer>> loses, int gamesPlayed, float defStrength) {
        try {
            
           
           w.writeStartDocument("utf-8", "1.0");
           w.writeStartElement(STATISTIC);
            w.writeStartElement(GAMES);
            w.writeCharacters(""+HandStatistic.getGamesPlayed());
            w.writeEndElement();
            wins.forEach((gs, mhi) -> {
            try {
                w.writeStartElement(gs.name());
               w.writeStartElement(HANDS);
                

             
                int max
                        = mhi.entrySet().stream()
                        .max((one, another) -> one.getValue()
                                .compareTo(another.getValue())).get().getValue();
                LoggerFactory.getLogger(this.getClass()).debug(String.format("At %s the most successful hand won %d times", gs.getName(), max));
                mhi.forEach((h, times) -> {
                   try {
                    w.writeStartElement(HAND);
                    for (Card c : h.getCards()) {
                        if (c != null) {
                            w.writeStartElement(CARD);
                            w.writeAttribute(SUIT, c.getSuit().name());
                            w.writeAttribute(VALUE, c.getValue().name());
                           w.writeEndElement();
                        }
                    }
                  
                   
                   w.writeStartElement(COMBINATION);
                   w.writeCharacters(h.getCombination().name());
                   w.writeEndElement();
                   
                  w.writeStartElement(STRENGTH);
                  w.writeCharacters(String.format("%7.4f", ((float) times) * 100 / max));
                  w.writeEndElement();
                  
                   w.writeStartElement(CHANCE);
                  w.writeCharacters(String.format("%7.7f", ((float) times) * 100 / gamesPlayed));
                  w.writeEndElement();
                   
                     w.writeStartElement(ESTIMATION);
                       int loseTimes = (loses.get(gs).get(h) == null) ? 0 : loses.get(gs).get(h);
                  w.writeCharacters(String.format("%7.4f", (loseTimes > 0) ? ((float) times) * 100 / loseTimes : 100f));
                  w.writeEndElement();
                  
                     w.writeStartElement(WINTIMES);
                  w.writeCharacters("" + times);
                  w.writeEndElement();

                      w.writeStartElement(LOSETIMES);
                  w.writeCharacters("" + loseTimes);
                  w.writeEndElement();
                  w.writeEndElement();

                  
 } catch (XMLStreamException ex){
                        LoggerFactory.getLogger(this.getClass()).error("Cannot store hand", ex);
                   }
                });
                  w.writeEndElement();
                  w.writeEndElement();
            }catch(XMLStreamException ex){
                 LoggerFactory.getLogger(this.getClass()).error("Cannot store game stage", ex);
            }
            });
          
            w.writeStartElement(DEFAULTSTRENGTH);
            w.writeCharacters("" + defStrength);
            w.writeEndElement();
            
            w.writeEndElement();
          
           w.writeEndDocument();
           w.flush();
         
         
        }catch (XMLStreamException ex) {
           LoggerFactory.getLogger(this.getClass()).error("XML Stream error", ex);
        }
       
    }

    @Override
    public void save(Map<GameStage, Map<Hand, Integer>> wins, Map<GameStage, Map<Hand, Integer>> loses, int gamesPlayed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws Exception {
       if (w != null){
           w.close();
       }
    }

}
