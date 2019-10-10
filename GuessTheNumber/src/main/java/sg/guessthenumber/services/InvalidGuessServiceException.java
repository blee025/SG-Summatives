/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.services;

/**
 *
 * @author blee0
 */
public class InvalidGuessServiceException extends Exception{

    public InvalidGuessServiceException(String message) {
        super(message);
    }

    public InvalidGuessServiceException(String message, Throwable inner) {
        super(message, inner);
    }
}
