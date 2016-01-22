/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.stat;

import java.util.HashMap;
import java.util.Map;
import ua.pp.msk.poker.rules.Hand;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class HandWinStatistic {
private static Map<Hand, Integer> wins = new HashMap<>();
public static Map<Hand, Integer> getWinHands(){
    return wins;
}
}
