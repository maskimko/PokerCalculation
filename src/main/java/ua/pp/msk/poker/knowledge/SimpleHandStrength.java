/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.knowledge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleHandStrength implements HandStrength {

    private final Map<GameStage, Map<Hand, Info>> knl;
    private final float defStrength = 0.1f;

    public SimpleHandStrength(Map<GameStage, Map<Hand, Integer>> wins, final int gamesPlayed) {
        knl = new HashMap<>();
        for (GameStage gs : GameStage.values()) {
            knl.put(gs, new HashMap<>());
        }
        wins.forEach((gs, hw) -> {
            int max = 0;
            Iterator<Map.Entry<Hand, Integer>> it = hw.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Hand, Integer> next = it.next();
                if (next.getValue() > max) {
                    max = next.getValue();
                }
            }
            final int stageMax = max;
            final Map<Hand, Info> infoMap = new HashMap<>();
            hw.forEach((h, c) -> {
                float chance = (float) c * 100 / gamesPlayed;
                float rs = (float) c * 100 / stageMax;
                infoMap.put(h, new Info(0f, rs, chance));
            });
            knl.put(gs, infoMap);
        });
    }

    @Override
    public float estimate(Hand hand, GameStage gs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float strength(Hand hand, GameStage gs) {
        float stren = defStrength;
        Map<Hand, Info> gsi = knl.get(gs);
        if (gsi.containsKey(hand)) {
            stren = gsi.get(hand).relativeStrength;
        }
        return stren;
    }

    @Override
    public float chance(Hand hand, GameStage gs) {
        float chan = defStrength;
        Map<Hand, Info> gsi = knl.get(gs);
        if (gsi.containsKey(hand)) {
            chan = gsi.get(hand).chance;
        }
        return chan;
    }

    public static class Info {

        private final float gamesWon;
        private final float relativeStrength;
        private final float chance;

        private Info(float gamesWon, float relativeStrength, float chance) {
            this.gamesWon = gamesWon;
            this.relativeStrength = relativeStrength;
            this.chance = chance;
        }
    }
}
