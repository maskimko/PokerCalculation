/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ua.pp.msk.poker.deck.Pair;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class PairWinStatistic {
private static final Map<Pair, Integer> wins = new ConcurrentHashMap<>();
private static int counter = 0;

public static Map<Pair, Integer> getWinPairs(){
    return wins;
}

static  void register(Pair pair){
    if (wins.containsKey(pair)){
        int alreadyWon = wins.get(pair);
        wins.put(pair, ++alreadyWon);
    } else {
         wins.put(pair, 1);
    }
    counter++;
}

public static int getGamesCount(){
    return counter;
}

}
