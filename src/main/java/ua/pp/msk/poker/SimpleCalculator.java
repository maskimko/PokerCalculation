/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.stat.StatisticPrinter;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimpleCalculator {

    public static void main(String[] args) {
        int playersNumber = 5;
        int gamesNumber = 10;
        PrintStream ps = null;
        int threadsNumber = -1;
        Options cliOpts = new Options();
        cliOpts.addOption("p", "players", true, "Number of players (between 2 and 10)");
        cliOpts.addOption("g", "games", true, "Number of games");
        cliOpts.addOption("h", false, "Get help");
        cliOpts.addOption("o", "outputfile", true, "File to redirect output. Default is standard out");
        cliOpts.addOption("t", "threads", true, "Number of threads to use");
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
            if (cl.hasOption("o")){
                String outputFile = cl.getOptionValue("o");
                ps = new PrintStream(outputFile);
            } else {
                ps = System.out;
            }
            if (cl.hasOption("t")){
                threadsNumber = Integer.parseInt(cl.getOptionValue("t"));
            } 
            long startTime = System.currentTimeMillis();
            startSimulation(playersNumber, gamesNumber, threadsNumber);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Calculations for %d players adn %d games played:", playersNumber, gamesNumber));
            StatisticPrinter statisticPrinter =  new StatisticPrinter(ps);
            statisticPrinter.printStatistic();
            System.out.println(String.format("It took %d milliseconds to finish", endTime - startTime));
        } catch (ParseException ex) {
            LoggerFactory.getLogger(SimpleCalculator.class).error("Cannot parse arguments", ex);
        } catch (FileNotFoundException ex) {
           LoggerFactory.getLogger(SimpleCalculator.class).error("Cannot find output file ", ex);
        }
    }

   
    
    private static void startSimulation(int players, int games, int threads) {
        try {
            //        Simulator simulator = new Simulator(games, players, true);
//        simulator.run();
            SimulatorManager sm = new SimulatorManager(players, games, true, threads);
            sm.start();
        } catch (InterruptedException ex) {
            LoggerFactory.getLogger(SimpleCalculator.class).error("Calculation has been interrupted", ex);
            System.exit(3);
        }
    }
}
