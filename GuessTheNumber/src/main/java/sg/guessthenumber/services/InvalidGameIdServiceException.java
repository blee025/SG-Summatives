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
public class InvalidGameIdServiceException extends Exception {

    public InvalidGameIdServiceException(String message) {
        super(message);
    }

    public InvalidGameIdServiceException(String message, Throwable inner) {
        super(message, inner);
    }
}
