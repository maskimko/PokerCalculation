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

    @Override
    public void run() {
        try {
            Table table = new Table(2);
            Player one = new Player(0, "Player 1");
            Player two = new Player(1, "Player 2");
            one.takeASeat(table,1);
            two.takeASeat(table);            
            Dealer dealer = new Dealer(table);
            dealer.startGame();
        } catch (TableException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Table capacity error", ex);
        }
    }

}
