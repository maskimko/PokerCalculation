/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import java.util.Iterator;
import ua.pp.msk.poker.stat.HandStatistic;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.deck.Pair;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.rules.Combination;
import ua.pp.msk.poker.stat.GameStage;
import ua.pp.msk.poker.stat.PairWinStatistic;
import ua.pp.msk.poker.stat.PlayerWinStatistic;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleCalculator {

    public static void main(String[] args) {
        int playersNumber = 5;
        int gamesNumber = 10;
        Options cliOpts = new Options();
        cliOpts.addOption("p", "players", true, "Number of players (between 2 and 10)");
        cliOpts.addOption("g", "games", true, "Number of games");
        cliOpts.addOption("h", false, "Get help");
        CommandLineParser clp = new DefaultParser();
        try {
            CommandLine cl = clp.parse(cliOpts, args);
            if (cl.hasOption("h")) {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("java -jar PokerCalculator.jar [-g number of games] [-p number of players]", cliOpts);
                System.exit(0);
            }
            if (cl.hasOption("g")) {
                String optionValue = cl.getOptionValue("g");
                gamesNumber = Integer.parseInt(optionValue);
            }
            if (cl.hasOption("p")) {
                String optionValue = cl.getOptionValue("p");
                playersNumber = Integer.parseInt(optionValue);
            }

            long startTime = System.currentTimeMillis();
            startSimulation(playersNumber, gamesNumber);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Calculations for %d players adn %d games played:", playersNumber, gamesNumber));
            printStatistic();
            System.out.println(String.format("It took %d milliseconds to finish", endTime - startTime));
        } catch (ParseException ex) {
            LoggerFactory.getLogger(SimpleCalculator.class).error("Cannot parse arguments", ex);
        }
    }

    private static void printStatistic() {
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

    private static void printGameStageStatistic(GameStage stage, Map<Combination, Integer> stats) {
        System.out.println("\n\n---------------------" + stage.getName() + " Statistic---------------------");
        System.out.println(String.format("%15s\t%10s %10s %10s", "Combination", "Occurences", stage.getName() + " %", "Total %"));
        System.out.println("---------------------------------------------------------");
        for (Map.Entry<Combination, Integer> entry : stats.entrySet()) {
            System.out.println(String.format("%15s\t%10s %9.3f%% %9.5f%%", entry.getKey().name(), entry.getValue(),
                    ((double) entry.getValue()) * 100 / HandStatistic.getGameStageAnalyzedCombinationsCount(stage),
                    ((double) entry.getValue()) * 100 / HandStatistic.getRegistrationsCount()));
        }
    }

    private static void printWinnersStatistic(Map<Player, Integer> winners){
        System.out.println("Games played: " + PlayerWinStatistic.getGamesCount());
        System.out.println(String.format("%-15s %-5s  %-5s", "Player", "Times", "%"));
        System.out.println("------------------------------------");
        Iterator<Map.Entry<Player, Integer>> iterator = winners.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Player, Integer> next = iterator.next();
            System.out.println(String.format("%15s  %5d %5.2f%%", next.getKey().getName(), next.getValue(), ((double)next.getValue())*100/PlayerWinStatistic.getGamesCount()));
        }
    }
    
     private static void printPairStatistic(Map<Pair, Integer> winPairs){
        System.out.println("Games played: " + PlayerWinStatistic.getGamesCount());
        System.out.println(String.format("%-15s %-5s  %-5s", "Pair", "Times", "%"));
        System.out.println("------------------------------------");
        Iterator<Map.Entry<Pair, Integer>> iterator = winPairs.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Pair, Integer> next = iterator.next();
            System.out.println(String.format("%15s  %5d %5.2f%%", next.getKey(), next.getValue(), ((double)next.getValue())*100/PlayerWinStatistic.getGamesCount()));
        }
    }
    
    private static void startSimulation(int players, int games) {
        Simulator simulator = new Simulator(games, players, true);
        simulator.run();
    }
}
