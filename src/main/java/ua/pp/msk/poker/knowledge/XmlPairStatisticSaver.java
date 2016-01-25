/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class XmlPairStatisticSaver implements PairStatisticSaver, AutoCloseable {

    private OutputStream os = null;

    XmlPairStatisticSaver() {
    os = System.out;
    }
    
    XmlPairStatisticSaver(OutputStream os){
        this.os = os;
    }
    
    XmlPairStatisticSaver(File file) throws IOException{
        os = new FileOutputStream(file);
    }
    
    @Override
    public void save(Map<Pair, Integer> strengthMap) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            DOMImplementation domImplementation = docBuilder.getDOMImplementation();
            Document doc = domImplementation.createDocument(null, null, null);
            Element pairs = doc.createElement(PAIRS);
            doc.appendChild(pairs);
            int max = 0;
//            for (Map.Entry<Pair, Integer> pair: strength.entrySet()){
//                int times = pair.getValue();
//                if (times > max) max = times;
//            }
            //let's use lambda here.
            max = strengthMap.entrySet().stream()
                    .max((Map.Entry<Pair, Integer> one, Map.Entry<Pair, Integer> another) -> one.getValue()
                            .compareTo(another.getValue())).get().getValue();
            Iterator<Map.Entry<Pair, Integer>> pairEntries = strengthMap.entrySet().iterator();
            while (pairEntries.hasNext()) {
                Map.Entry<Pair, Integer> pairEntry = pairEntries.next();
                int times = pairEntry.getValue();
                Pair p = pairEntry.getKey();
                Card first = p.getCards()[0];
                Card second = p.getCards()[1];
                Element pair = doc.createElement(PAIR);
                Element card1 = doc.createElement(CARD);
                card1.setAttribute(SUIT, first.getSuit().getName());
                card1.setAttribute(VALUE, first.getValue().getName());
                Element card2 = doc.createElement(CARD);
                card2.setAttribute(SUIT, second.getSuit().getName());
                card2.setAttribute(VALUE, second.getValue().getName());
                Element strength = doc.createElement(STRENGTH);
                strength.setAttribute(STRENGTH, "" + ((float) times) * 100 / max);
                pair.appendChild(card1);
                pair.appendChild(card2);
                pair.appendChild(strength);
                pairs.appendChild(pair);
            }

            DOMSource ds = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            Writer writer = new OutputStreamWriter(os);
            StreamResult sr = new StreamResult(writer);
            transformer.transform(ds, sr);
        } catch (ParserConfigurationException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Cannot configure XML builder", ex);
        } catch (TransformerConfigurationException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Cannot configure XML transformer", ex);
        } catch (TransformerException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Transformation error", ex);
        }
    }

    @Override
    public void close() throws Exception {
        os.flush();
        os.close();
    }

}
