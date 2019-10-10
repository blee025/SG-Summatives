/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

/**
 *
 * @author blee0
 */
public class FlooringDaoException extends Exception {
    
    public FlooringDaoException (String message) {
        super(message);
    }
    
    public FlooringDaoException (String message, Throwable inner) {
        super(message, inner);
    }
    
}
