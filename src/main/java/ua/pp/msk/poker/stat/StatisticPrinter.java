/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.stat;

import java.util.Iterator;
import java.util.Map;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class StatisticPrinter {
 public   void printStatistic() {
        System.out.println("\nStatistics:");
        System.out.println("Combinations analyzed: " + HandStatistic.getRegistrationsCount());
        printGameStageStatistic(GameStage.preflop, HandStatistic.getPreFlopStatistic());
        printGameStageStatistic(GameStage.flop, HandStatistic.getFlopStatistic());
        printGameStageStatistic(GameStage.turn, HandStatistic.getTurnStatistic());
        printGameStageStatistic(GameStage.river, HandStatistic.getRiverStatistic());
        
        System.out.println("\n\nWinners statistics:");
        Map<Player, Integer> winners = PlayerWinStatistic.getWinners();
        printWinnersStatistic(winners);
        System.out.println("\n\nWinning pair statistics:");
        Map<Pair, Integer> winPairs = PairWinStatistic.getWinPairs();
        printPairStatistic(winPairs);
    }

    private  void printGameStageStatistic(GameStage stage, Map<Combination, Integer> stats) {
        System.out.println("\n\n---------------------" + stage.getName() + " Statistic---------------------");
        System.out.println(String.format("%15s\t%10s %10s %10s", "Combination", "Occurences", stage.getName() + " %", "Total %"));
        System.out.println("---------------------------------------------------------");
        for (Map.Entry<Combination, Integer> entry : stats.entrySet()) {
            System.out.println(String.format("%15s\t%10s %9.3f%% %9.5f%%", entry.getKey().name(), entry.getValue(),
                    ((double) entry.getValue()) * 100 / HandStatistic.getGameStageAnalyzedCombinationsCount(stage),
                    ((double) entry.getValue()) * 100 / HandStatistic.getRegistrationsCount()));
        }
    }

    private void printWinnersStatistic(Map<Player, Integer> winners){
        System.out.println("Games played: " + PlayerWinStatistic.getGamesCount());
        System.out.println(String.format("%-15s %-5s  %-5s", "Player", "Times", "%"));
        System.out.println("------------------------------------");
        Iterator<Map.Entry<Player, Integer>> iterator = winners.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Player, Integer> next = iterator.next();
            System.out.println(String.format("%15s  %5d %5.2f%%", next.getKey().getName(), next.getValue(), ((double)next.getValue())*100/PlayerWinStatistic.getGamesCount()));
        }
    }
    
     private  void printPairStatistic(Map<Pair, Integer> winPairs){
        System.out.println("Games played: " + PlayerWinStatistic.getGamesCount());
        System.out.println(String.format("%-15s %-5s  %-5s", "Pair", "Times", "%"));
        System.out.println("------------------------------------");
        Iterator<Map.Entry<Pair, Integer>> iterator = winPairs.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Pair, Integer> next = iterator.next();
            System.out.println(String.format("%15s  %5d %5.2f%%", next.getKey(), next.getValue(), ((double)next.getValue())*100/PlayerWinStatistic.getGamesCount()));
        }
    }
}
