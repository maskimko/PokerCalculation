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
private static final Map<Hand, Integer> flopWins = new HashMap<>();
private static final Map<Hand, Integer> turnWins = new HashMap<>();
private static final Map<Hand, Integer> riverWins = new HashMap<>();

public static Map<Hand, Integer> getFlopWinHands(){
    return flopWins;
}

public static Map<Hand, Integer> getTurnWinHands(){
    return turnWins;
}
public static Map<Hand, Integer> getRiverWinHands(){
    return riverWins;
}

}
