/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Collector {

    private static Collector c = null;
    private final HandWinStatistic handWinStatistic;
    private final PairWinStatistic pairWinStatistic;

    public static Collector getCollector() {
        if (c == null) {
            synchronized (Collector.class) {
                if (c == null) {
                    c = new Collector();
                }
            }
        }
        return c;
    }

    private Collector() {
        handWinStatistic = new HandWinStatistic();
        pairWinStatistic = new PairWinStatistic();
    }
    
    public void registerHand(Combination combination, GameStage stage){
            HandStatistic.registerOccurance(combination, stage);
    }
    
    public void registerWinner(Player p){
        PlayerWinStatistic.registerWinner(p);
    }

    public void registerWinningPair(Pair p){
        PairWinStatistic.register(p);
    }
    
}
