/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.exceptions.TableException;
import ua.pp.msk.poker.member.Dealer;
import ua.pp.msk.poker.member.Player;
import ua.pp.msk.poker.member.Table;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Simulator implements Runnable{

    private int playersNumber = 1;
    public Simulator(){
    }
    public Simulator(int playersNumber){
        //TODO Check acceptance on players quantity
        this.playersNumber = playersNumber;
    }
    
    @Override
    public void run() {
        try {
            Table table = new Table(playersNumber);
            for (int i = 0; i < playersNumber; i++){
                Player p  = new Player(i, "SimulatedPlayer"+i);
                p.takeASeat(table);
            }
            Dealer dealer = new Dealer(table);
            dealer.startGame();
        } catch (TableException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Table capacity error", ex);
        }
    }

}
