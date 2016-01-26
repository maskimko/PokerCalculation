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
public interface PairStatisticSaver extends KnowledgeConsts, AutoCloseable{
    
    public void save(Map<Pair, Integer> strength);
}
