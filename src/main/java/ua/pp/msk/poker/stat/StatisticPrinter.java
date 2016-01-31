/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.stat;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Card;
import ua.pp.msk.poker.deck.Deck52s;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.exceptions.CardException;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Combination;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class StatisticPrinter {

    private PrintStream ps;
    
    public StatisticPrinter() {
        ps = System.out;
    }

    public StatisticPrinter(PrintStream out){
        ps = out;
    }
    
    public void printStatistic() {
        ps.println("\nStatistics:");
        ps.println("Combinations analyzed: " + CombinationStatistic.getRegistrationsCount());
        printGameStageStatistic(GameStage.preflop, CombinationStatistic.getPreFlopStatistic());
        printGameStageStatistic(GameStage.flop, CombinationStatistic.getFlopStatistic());
        printGameStageStatistic(GameStage.turn, CombinationStatistic.getTurnStatistic());
        printGameStageStatistic(GameStage.river, CombinationStatistic.getRiverStatistic());

        ps.println("\n\nWinners statistics:");
        Map<Player, Integer> winners = PlayerWinStatistic.getWinners();
        printWinnersStatistic(winners);
        ps.println("\n\nWinning pair statistics:");
        Map<Pair, Integer> winPairs = PairWinStatistic.getWinPairs();
        printPairStatistic(winPairs);
        printPairTableStatistic(winPairs);
        printPairTablePercentStatistic(winPairs);
    }

    private void printGameStageStatistic(GameStage stage, Map<Combination, Integer> stats) {
        ps.println("\n\n---------------------" + stage.getName() + " Statistic---------------------");
        ps.println(String.format("%15s\t%10s %10s %10s", "Combination", "Occurences", stage.getName() + " %", "Total %"));
        ps.println("---------------------------------------------------------");
        for (Map.Entry<Combination, Integer> entry : stats.entrySet()) {
            ps.println(String.format("%15s\t%10s %9.3f%% %9.5f%%", entry.getKey().name(), entry.getValue(),
                    ((double) entry.getValue()) * 100 / CombinationStatistic.getGameStageAnalyzedCombinationsCount(stage),
                    ((double) entry.getValue()) * 100 / CombinationStatistic.getRegistrationsCount()));
        }
    }

    private void printWinnersStatistic(Map<Player, Integer> winners) {
        ps.println("Games played: " + PlayerWinStatistic.getGamesCount());
        ps.println(String.format("%-15s %-5s  %-5s", "Player", "Times", "%"));
        ps.println("------------------------------------");
        Iterator<Map.Entry<Player, Integer>> iterator = winners.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, Integer> next = iterator.next();
            ps.println(String.format("%15s  %5d %5.2f%%", next.getKey().getName(), next.getValue(), ((double) next.getValue()) * 100 / PlayerWinStatistic.getGamesCount()));
        }
    }

    @Deprecated
    private void printPairStatistic(Map<Pair, Integer> winPairs) {
        ps.println("Games played: " + PlayerWinStatistic.getGamesCount());
        ps.println(String.format("%-15s      %-5s      %-5s %-6s", "Pair", "Times", "%", "Relative %"));
        ps.println("------------------------------------");
        List<Map.Entry<Pair, Integer>> sorted = new ArrayList<>();
        sorted.addAll(winPairs.entrySet());
        Collections.sort(sorted, (Map.Entry<Pair, Integer> t, Map.Entry<Pair, Integer> u) -> -1 * t.getValue().compareTo(u.getValue()));
        int best = sorted.get(0).getValue();
        Iterator<Map.Entry<Pair, Integer>> iterator = sorted.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Pair, Integer> next = iterator.next();
            ps.println(String.format("%15s  %5d %5.2f%%     %5.2f%%", next.getKey(), next.getValue(), ((double) next.getValue()) * 100 / PlayerWinStatistic.getGamesCount(), ((double) next.getValue()) * 100 / best));
        }
    }

    private void printPairTableStatistic(Map<Pair, Integer> winPairs) {
        try {
            Card[] cards = new Deck52s().getCards();
            ps.print("      ");
            for (int i = 0; i < cards.length; i++) {
                ps.printf(" %3s  ", cards[i]);
            }
            for (int i = 0; i < cards.length; i++) {
                ps.printf("\n %3s  ", cards[i]);
                for (int j = 0; j < cards.length; j++) {
                    if (i == j) {
                        ps.printf(" %4d ", 0);
                        continue;
                    }
                    Pair pair = new Pair(cards[i], cards[j]);
                    if (winPairs.containsKey(pair)) {
                        ps.printf(" %4d ", winPairs.get(pair));
                    } else {
                        ps.printf(" %4d ", 0);
                    }
                }
            }
            ps.println();
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).warn("Cannot create table of card pairs", ex);
        }
    }
    
    private void printPairTablePercentStatistic(Map<Pair, Integer> winPairs) {
        try {
            int best = 0;
            Iterator<Map.Entry<Pair, Integer>> wpi = winPairs.entrySet().iterator();
            while (wpi.hasNext()){
                int current = wpi.next().getValue();
                if (current > best) best = current;
            }
            Card[] cards = new Deck52s().getCards();
            ps.print("          ");
            for (int i = 0; i < cards.length; i++) {
                ps.printf("   %3s  ", cards[i]);
            }
            for (int i = 0; i < cards.length; i++) {
                ps.printf("\n   %3s  ", cards[i]);
                for (int j = 0; j < cards.length; j++) {
                    if (i == j) {
                        ps.printf(" %6.2f%%", 0f);
                        continue;
                    }
                    Pair pair = new Pair(cards[i], cards[j]);
                    if (winPairs.containsKey(pair)) {
                        ps.printf(" %6.2f%%", ((double)winPairs.get(pair)) * 100 / best);
                    } else {
                        ps.printf(" %6.2f%%", 0f);
                    }
                }
            }
            ps.println();
        } catch (CardException ex) {
            LoggerFactory.getLogger(this.getClass()).warn("Cannot create table of card pairs", ex);
        }
    }
}
