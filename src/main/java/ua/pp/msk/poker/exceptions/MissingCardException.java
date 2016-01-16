/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.exceptions;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public class MissingCardException extends CardException {

    public MissingCardException(String message) {
        super(message);
    }

    public MissingCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingCardException(Throwable cause) {
        super(cause);
    }

}
