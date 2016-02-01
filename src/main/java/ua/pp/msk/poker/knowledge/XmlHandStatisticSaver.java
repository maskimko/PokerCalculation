/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
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
import org.w3c.dom.Text;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Pair;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.CARD;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.DEFAULTSTRENGTH;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.STRENGTH;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.SUIT;
import static ua.pp.msk.poker.knowledge.KnowledgeConsts.VALUE;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;
import ua.pp.msk.poker.stat.HandStatistic;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class XmlHandStatisticSaver implements HandStatisticSaver {

    private OutputStream os;

    XmlHandStatisticSaver() {
        os = System.out;
    }

    XmlHandStatisticSaver(OutputStream os) {
        this.os = os;
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
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            DOMImplementation domImplementation = docBuilder.getDOMImplementation();
            Document doc = domImplementation.createDocument(null, null, null);
            Element stats = doc.createElement(STATISTIC);
            Element games = doc.createElement(GAMES);
            Text gamVal = doc.createTextNode("" + HandStatistic.getGamesPlayed());
            games.appendChild(gamVal);
            stats.appendChild(games);
            doc.appendChild(stats);
            wins.forEach((gs, mhi) -> {
                Element gameStage = doc.createElement(gs.name());
                stats.appendChild(gameStage);

                Element hands = doc.createElement(HANDS);
                gameStage.appendChild(hands);
                int max
                        = mhi.entrySet().stream()
                        .max((one, another) -> one.getValue()
                                .compareTo(another.getValue())).get().getValue();
                LoggerFactory.getLogger(this.getClass()).debug(String.format("At %s the most successful hand won %d times", gs.getName(), max));
                mhi.forEach((h, times) -> {

                    Element hand = doc.createElement(HAND);
                    for (Card c : h.getCards()) {
                        if (c != null) {
                            Element card = doc.createElement(CARD);
                            card.setAttribute(SUIT, c.getSuit().name());
                            card.setAttribute(VALUE, c.getValue().name());
                            hand.appendChild(card);
                        }
                    }
                    Element combination = doc.createElement(COMBINATION);
                    Text cmb = doc.createTextNode(h.getCombination().name());
                    combination.appendChild(cmb);
                    hand.appendChild(combination);
                    Element strength = doc.createElement(STRENGTH);
                    Text strVal = doc.createTextNode(String.format("%7.4f", ((float) times) * 100 / max));
                    strength.appendChild(strVal);
                    hand.appendChild(strength);
                    Element chance = doc.createElement(CHANCE);
                    Text chanVal = doc.createTextNode(String.format("%7.4f", ((float) times) * 100 / gamesPlayed));
                    chance.appendChild(chanVal);
                    hand.appendChild(chance);
                    Element estimation = doc.createElement(ESTIMATION);
                    int loseTimes = (loses.get(gs).get(h) == null) ? 0 : loses.get(gs).get(h);
                    Text estiVal = doc.createTextNode(String.format("%7.4f", (loseTimes > 0) ? ((float) times) * 100 / loseTimes : 100f));
                    estimation.appendChild(estiVal);
                    hand.appendChild(estimation);

                    Element winTimes = doc.createElement(WINTIMES);
                    Text winTimesVal = doc.createTextNode("" + times);
                    winTimes.appendChild(winTimesVal);
                    hand.appendChild(winTimes);
                    Element loseTimesElement = doc.createElement(LOSETIMES);
                    Text loseTimesVal = doc.createTextNode("" + loseTimes);
                    loseTimesElement.appendChild(loseTimesVal);
                    hand.appendChild(loseTimesElement);

                    hands.appendChild(hand);

                });
            });
            Element dstr = doc.createElement(DEFAULTSTRENGTH);
            dstr.appendChild(doc.createTextNode("" + defStrength));
            stats.appendChild(dstr);
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
        if (os != System.out && !os.equals(System.out)) {
            os.close();
        }
    }

    @Override
    public void save(Map<GameStage, Map<Hand, Integer>> wins, Map<GameStage, Map<Hand, Integer>> loses, int gamesPlayed) {
        save(wins, loses, gamesPlayed, 0.01f);
    }

}
