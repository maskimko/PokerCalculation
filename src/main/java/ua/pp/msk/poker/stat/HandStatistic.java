/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandStatistic {

    private static final Map<GameStage, Map<Hand, Integer>> wins = new HashMap<>(), 
            loses = new HashMap<>();
    
    private static int games = 0;

    static {
        for (GameStage gs : GameStage.values()) {
            wins.put(gs, new HashMap<>());
            loses.put(gs, new HashMap<>());
        }
    }

    public static Map<GameStage, Map<Hand, Integer>> getWinHands() {
        return wins;
    }
    public static Map<GameStage, Map<Hand, Integer>> getLoseHands() {
        return loses;
    }

    static synchronized void register(Player.History history, boolean isWinner) {
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
        games++;
    }

    
    public static int getPlayedGames(){
        return games;
    }
}
