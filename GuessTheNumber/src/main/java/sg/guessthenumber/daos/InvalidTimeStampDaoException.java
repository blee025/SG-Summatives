/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.daos;

/**
 *
 * @author blee0
 */
public class InvalidTimeStampDaoException extends Exception {
     public InvalidTimeStampDaoException(String message) {
        super(message);
    }

    public InvalidTimeStampDaoException(String message, Throwable inner) {
        super(message, inner);
    }
}
