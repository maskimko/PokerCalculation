/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker.member;

import org.slf4j.LoggerFactory;
import ua.pp.msk.poker.exceptions.BigTableException;
import ua.pp.msk.poker.exceptions.FullTableException;
import ua.pp.msk.poker.exceptions.SmallTableException;
import ua.pp.msk.poker.exceptions.TableException;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class Table {

    private Player[] players;

    public Table(int seats) throws TableException {
        if (seats < 2) {
            throw new SmallTableException("Table cannot be less than 2 seats big");
        }
        if (seats > 10) {
            throw new BigTableException("Table cannot be bigger than 10 seats");
        }
        players = new Player[seats];
    }

    public void registerPlayer(Player p) throws FullTableException {
        boolean registered = false;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = p;
                LoggerFactory.getLogger(this.getClass()).debug(String.format("Player %s took a seat %d", p.getName(), i));
                registered = true;
                break;
            }
        }
        if (!registered) {
            throw new FullTableException("Table is full. Cannot take a seat");
        }

    }

    /**
     *
     * @param p
     * @param seatNumber Optional parameter which indicates desired seat number.
     * If the seat is not available this parameter will be ignored.
     * @throws ua.pp.msk.poker.exceptions.FullTableException is thrown in case
     * of full table
     */
    public void registerPlayer(Player p, int seatNumber) throws FullTableException {
        if (seatNumber < players.length && seatNumber > 0) {
            if (players[seatNumber] == null) {
                players[seatNumber] = p;
                return;
            }
        } else {
            LoggerFactory.getLogger(this.getClass()).warn("Wrong seat number was provided. " + seatNumber + " It will be ignored. Seat number must be in range 0..9 inclusively");
        }
        registerPlayer(p);
    }

    public Player[] getPlayers() {
        return players;
    }

}
