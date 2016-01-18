/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.exceptions.TableException;
import ua.pp.msk.poker.member.Dealer;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.member.Table;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Simulator implements Runnable {

    private int playersNumber = 2;
    private int gamesNumber = 1;
    private boolean progress = false;

    public Simulator() {
    }

    public Simulator(int gamesNumber) {
        this(gamesNumber, 2);
    }

    public Simulator(int gamesNumber, int playersNumber) {
        this(gamesNumber, playersNumber, false);
    }

    public Simulator(int gamesNumber, int playersNumber, boolean showProgress) {
        this.gamesNumber = gamesNumber;
        this.playersNumber = playersNumber;
        this.progress = showProgress;
    }

    @Override
    public void run() {
        try {
            for (int g = 0; g < gamesNumber; g++) {
                if (progress && g % 100 == 0) {
                    System.out.print(".");
                    System.out.flush();
                }
                Table table = new Table(playersNumber);
                for (int i = 0; i < playersNumber; i++) {
                    Player p = new Player(i, "SimulatedPlayer" + i);
                    p.takeASeat(table);
                }
                Dealer dealer = new Dealer(table);
                dealer.startGame();
            }
        } catch (TableException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Table capacity error", ex);
        }
    }

}
