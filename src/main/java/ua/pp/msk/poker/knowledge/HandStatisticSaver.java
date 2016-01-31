/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.util.Map;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author maskimko
 */
public interface HandStatisticSaver extends KnowledgeConsts, AutoCloseable{
    
    @Deprecated
    public void save(Map<Pair, Integer> strength);
    /**
     * 
     * @param strength
     * @param defStrength default hand strength in percents. Should be between 0 and 100
     */
    @Deprecated
    public void save(Map<Pair, Integer> strength, float defStrength);
    public void save(Map<GameStage, Map<Hand, Integer>> wins, Map<GameStage, Map<Hand, Integer>> loses, int gamesPlayed, float defStrength);
    public void save(Map<GameStage, Map<Hand, Integer>> wins, Map<GameStage, Map<Hand, Integer>> loses, int gamesPlayed);
    
}
