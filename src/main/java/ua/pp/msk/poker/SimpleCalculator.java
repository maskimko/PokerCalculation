/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import ua.pp.msk.poker.stat.Statistic;
import java.util.Map;
import ua.pp.msk.poker.rules.Combination;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleCalculator {

    public static void main(String[] args) {
        int playersNumber = 7;
        int gamesNumber = 1000;
        long startTime = System.currentTimeMillis();
        startSimulation(playersNumber, gamesNumber);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Calculations for %d players adn %d games played:", playersNumber, gamesNumber));
        printStatistic();
        System.out.println(String.format("It took %d milliseconds to finish", endTime - startTime));
    }

    private static void printStatistic() {
        System.out.println("\nStatistics:");
        System.out.println("Combinations analyzed: " + Statistic.getRegistrationsCount());
        printGameStageStatistic(GameStage.preflop, Statistic.getPreFlopStatistic());
        printGameStageStatistic(GameStage.flop, Statistic.getFlopStatistic());
        printGameStageStatistic(GameStage.turn, Statistic.getTurnStatistic());
        printGameStageStatistic(GameStage.river, Statistic.getRiverStatistic());
    }
    
    private static void printGameStageStatistic(GameStage stage, Map<Combination, Integer> stats){
        System.out.println("\n\n---------------------"+stage.getName()+" Statistic---------------------");
        System.out.println(String.format("%15s\t%10s %10s %10s", "Combination", "Occurences", stage.getName() + " %", "Total %"));
        System.out.println("---------------------------------------------------------");
        for (Map.Entry<Combination, Integer> entry : stats.entrySet()) {
            System.out.println(String.format("%15s\t%10s %9.3f%% %9.5f%%", entry.getKey().name(), entry.getValue(), 
                    ((double) entry.getValue()) * 100/Statistic.getGameStageAnalyzedCombinationsCount(stage),
                    ((double) entry.getValue()) * 100 / Statistic.getRegistrationsCount()));
        }
    }

    private static void startSimulation(int players, int games) {
        Simulator simulator = new Simulator(games, players, true);
        simulator.run();
    }
}
