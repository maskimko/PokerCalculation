/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker;

import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Statistic {
private static Map<Combination, Integer> statistic = new HashMap<>();
private static long counter = 0;
static {
    for (Combination c: Combination.values()){
        statistic.put(c, 0);
    }
}

public static synchronized void registerOccurance(Combination combination){
    int current = statistic.get(combination);
    current++;
    statistic.put(combination, current);
    counter++;
}

public static Map<Combination, Integer> getStatistic(){
    return statistic;
}

public static long getRegistrationsCount(){
    return counter;
}

}
