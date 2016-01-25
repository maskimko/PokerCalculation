/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.util.Map;
import ua.pp.msk.poker.deck.Pair;

/**
 *
 * @author maskimko
 */
public interface PairStatisticSaver {
    public static final String SUIT="suit";
    public static final String VALUE="value";
    public static final String PAIR="pair";
    public static final String STRENGTH="strength";
    public static final String CARD="card";
    public static final String PAIRS="pairs";
    public void save(Map<Pair, Integer> strength);
}
