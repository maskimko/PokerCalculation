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
public class Statistic {

    private static final Map<Combination, Integer> preFlopStatistic = new HashMap<>();
    private static final Map<Combination, Integer> flopStatistic = new HashMap<>();
    private static final Map<Combination, Integer> turnStatistic = new HashMap<>();
    private static final Map<Combination, Integer> riverStatistic = new HashMap<>();
    private static long counter = 0;
    private static GameStage stage = GameStage.preflop;

    static {
        for (Combination c : Combination.values()) {
            preFlopStatistic.put(c, 0);
            flopStatistic.put(c, 0);
            turnStatistic.put(c, 0);
            riverStatistic.put(c, 0);
        }
    }
    
    public static synchronized void registerOccurance(Combination combination) {
        if (stage.equals(GameStage.preflop)) {
            int current = preFlopStatistic.get(combination);
            current++;
            preFlopStatistic.put(combination, current);
        }
         if (stage.equals(GameStage.flop)) {
            int current = flopStatistic.get(combination);
            current++;
            flopStatistic.put(combination, current);
        }
          if (stage.equals(GameStage.turn)) {
            int current = turnStatistic.get(combination);
            current++;
            turnStatistic.put(combination, current);
        }
           if (stage.equals(GameStage.river)) {
            int current = riverStatistic.get(combination);
            current++;
            riverStatistic.put(combination, current);
        }
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
    
    public static void nextGameStage(){
        int ordinal = stage.ordinal();
        stage = GameStage.values()[++ordinal];
    }
    /**
     * It should be called every time a new game starts
     */
    public static void resetStage(){
        stage = GameStage.preflop;
    }
}
