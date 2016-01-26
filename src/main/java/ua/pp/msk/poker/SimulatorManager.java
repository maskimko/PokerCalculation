/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class SimulatorManager {

    private final int playersNumber;
    private final int gamesNumber;
    private final boolean progress;
    private final int threads;

    public SimulatorManager( int players, int games, boolean progress, int threads) {
        this.playersNumber = players;
        this.gamesNumber = games;
        this.progress = progress;
        if (threads < 1) {
            this.threads = Runtime.getRuntime().availableProcessors();
        } else  {
            this.threads = threads;
        }
        
    }

    public SimulatorManager(int playersNumber, int gamesNumber, boolean progress) {
         this.playersNumber = playersNumber;
        this.gamesNumber = gamesNumber;
        this.progress = progress;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.threads = availableProcessors;
    }

    public SimulatorManager(int playersNumber, int gamesNumber) {
        this(playersNumber, gamesNumber, true);
    }
    
    public void start() throws InterruptedException{
        LoggerFactory.getLogger(this.getClass()).debug("Calculations will be done in " + threads + " threads.");
        int gpt = gamesNumber / threads;
        int restOfGames = gamesNumber % threads;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threads);
        
        for (int i = 0; i < threads; i++){
            int games = gpt;
            if (i == 0) {
                games = gpt + restOfGames;
            }
            Simulator simulator = new Simulator(games, playersNumber, progress);
            fixedThreadPool.submit(simulator);
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(10, TimeUnit.DAYS);
    }

}
