/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandWinStatistic {

    private static final Map<GameStage, Map<Hand, Integer>> wins = new HashMap<>();
    private static int games = 0;

    static {
        for (GameStage gs : GameStage.values()) {
            wins.put(gs, new HashMap<>());
        }
    }

    public static Map<GameStage, Map<Hand, Integer>> getWinHands() {
        return wins;
    }

    static synchronized void register(Player.History history) {
        Map<GameStage, Hand> hands = history.getHands();
        hands.forEach((gs, h) -> {
            Map<Hand, Integer> stageWins = wins.get(gs);
            if (stageWins.containsKey(h)) {
                int current = stageWins.get(h);
                stageWins.put(h, ++current);
            } else {
                stageWins.put(h, 1);
            }
        });
        games++;
    }

    
    public static int getPlayedGames(){
        return games;
    }
}
