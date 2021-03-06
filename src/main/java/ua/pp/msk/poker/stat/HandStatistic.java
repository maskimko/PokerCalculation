/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandStatistic {

    private static final Map<GameStage, Map<Hand, Integer>> wins = new HashMap<>(), 
            loses = new HashMap<>();
    
    private static int handsCount = 0, games = 0;
    

    static {
        for (GameStage gs : GameStage.values()) {
            wins.put(gs, new ConcurrentHashMap<>());
            loses.put(gs, new ConcurrentHashMap<>());
        }
    }

    public static Map<GameStage, Map<Hand, Integer>> getWinHands() {
        return wins;
    }
    public static Map<GameStage, Map<Hand, Integer>> getLoseHands() {
        return loses;
    }

    static  void register(Player.History history, boolean isWinner) {
        Map<GameStage, Hand> hands = history.getHands();
        hands.forEach((gs, h) -> {
            Map<Hand, Integer> stageReg = isWinner ? wins.get(gs) : loses.get(gs);
            if (stageReg.containsKey(h)) {
                int current = stageReg.get(h);
                stageReg.put(h, ++current);
            } else {
                stageReg.put(h, 1);
            }
        });
        handsCount++;
    }

    static  void addGame(){
        games++;
    }
    
    public static int getGamesPlayed() {
        return games;
    }

    
    public static int getHandsCount(){
        return handsCount;
    }
}
