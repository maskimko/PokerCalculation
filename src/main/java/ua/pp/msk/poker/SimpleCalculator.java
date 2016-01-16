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
        int playersNumber = 5;
        int gamesNumber = 1000000;
        long startTime = System.currentTimeMillis();
        startSimulation(playersNumber, gamesNumber);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Calculations for %d players adn %d games played:", playersNumber, gamesNumber));
        printStatistic();
        System.out.println(String.format("It took %d milliseconds to finish", endTime-startTime));
    }
    
    private static void printStatistic(){
        System.out.println("\nStatistics:");
        System.out.println("Combinations analyzed: " + Statistic.getRegistrationsCount());
        System.out.println(String.format("\n%15s\t%9s %9s", "Combination", "Occurences", "Percentage"));
        System.out.println("-------------------------------------");
        for (Map.Entry<Combination, Integer> entry : Statistic.getStatistic().entrySet()){
            System.out.println(String.format("%15s\t%9s %9.3f%%", entry.getKey().name(), entry.getValue(), ((double)entry.getValue()) *100 / Statistic.getRegistrationsCount()));
        }
    }
    private static void startSimulation(int players, int games){
        Simulator simulator = new Simulator(players);
        for (int i=0; i<games; i++){
            simulator.run();
        }
    }
}
