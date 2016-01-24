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
import ua.pp.msk.poker.stat.StatisticPrinter;

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
            StatisticPrinter statisticPrinter = new StatisticPrinter();
            statisticPrinter.printStatistic();
            System.out.println(String.format("It took %d milliseconds to finish", endTime - startTime));
        } catch (ParseException ex) {
            LoggerFactory.getLogger(SimpleCalculator.class).error("Cannot parse arguments", ex);
        }
    }

   
    
    private static void startSimulation(int players, int games) {
        Simulator simulator = new Simulator(games, players, true);
        simulator.run();
    }
}
