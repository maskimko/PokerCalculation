/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import java.util.Map;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleCalculator {

    public static void main(String[] args) {
        Simulator simulator = new Simulator(3);
        for (int i=0; i<10; i++){
            simulator.run();
        }
        printStatistic();
    }
    
    private static void printStatistic(){
        System.out.println("\nStatistics:\n");
        for (Map.Entry<Combination, Integer> entry : Statistic.getStatistic().entrySet()){
            System.out.println(String.format("%15s\t%s", entry.getKey().name(), entry.getValue()));
        }
    }
}
