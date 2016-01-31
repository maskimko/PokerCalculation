/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class CombinationStatistic {

    private static final Map<Combination, Integer> preFlopStatistic = new HashMap<>();
    private static final Map<Combination, Integer> flopStatistic = new HashMap<>();
    private static final Map<Combination, Integer> turnStatistic = new HashMap<>();
    private static final Map<Combination, Integer> riverStatistic = new HashMap<>();
    private static final Map<GameStage, Integer> stageOccurences = new HashMap<>();
    private static long counter = 0;

    static {
        for (Combination c : Combination.values()) {
            preFlopStatistic.put(c, 0);
            flopStatistic.put(c, 0);
            turnStatistic.put(c, 0);
            riverStatistic.put(c, 0);
        }
        for (GameStage gs : GameStage.values()) {
            stageOccurences.put(gs, 0);
        }
    }

     static synchronized void registerOccurance(Combination combination, GameStage stage) {
        if (stage.equals(GameStage.preflop)) {
            int current = preFlopStatistic.get(combination);
            preFlopStatistic.put(combination, ++current);
        }
        if (stage.equals(GameStage.flop)) {
            int current = flopStatistic.get(combination);
            flopStatistic.put(combination, ++current);
        }
        if (stage.equals(GameStage.turn)) {
            int current = turnStatistic.get(combination);
            turnStatistic.put(combination, ++current);
        }
        if (stage.equals(GameStage.river)) {
            int current = riverStatistic.get(combination);
            riverStatistic.put(combination, ++current);
        }
        incrementStageOccurance(stage);
        counter++;
    }

    public static Map<Combination, Integer> getPreFlopStatistic() {
        return preFlopStatistic;
    }

    public static Map<Combination, Integer> getFlopStatistic() {
        return flopStatistic;
    }

    public static Map<Combination, Integer> getTurnStatistic() {
        return turnStatistic;
    }

    public static Map<Combination, Integer> getRiverStatistic() {
        return riverStatistic;
    }

    public static long getRegistrationsCount() {
        return counter;
    }

    


    private static void incrementStageOccurance(GameStage gs) {
        int times = stageOccurences.get(gs);
        stageOccurences.put(gs, ++times);
    }
    /**
     * 
     * @param gs
     * @return Returns how much combinations of given GameStage where analyzed 
     */
    public static int getGameStageAnalyzedCombinationsCount(GameStage gs){
        return stageOccurences.get(gs);
    }
}
