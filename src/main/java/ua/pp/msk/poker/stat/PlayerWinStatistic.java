/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ua.pp.msk.poker.member.Player;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class PlayerWinStatistic {
    private static Map<Player, Integer> wins = new ConcurrentHashMap<>();
    private static int counter = 0;
    
    public static Map<Player, Integer> getWinners(){
        return wins;
    }
    
    static  void registerWinner(Player p){
        if (wins.containsKey(p)){
            Integer count = wins.get(p);
            wins.put(p, ++count);
        } else {
            wins.put(p, 1);
        }
        counter++;
    }
    
    
    public static int getGamesCount(){
        return counter;
    }
}
