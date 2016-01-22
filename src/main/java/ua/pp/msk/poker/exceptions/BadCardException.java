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
public class BadCardException extends CardException {
 
    public BadCardException(String message) {
        super(message);
    }

    public BadCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCardException(Throwable cause) {
        super(cause);
    }

}
