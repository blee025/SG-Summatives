/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.service;

/**
 *
 * @author blee0
 */
public class InvalidProductException extends Exception{ 
    public InvalidProductException (String message) {
        super (message);
    }
    
    public InvalidProductException (String message, Throwable inner) {
        super (message, inner);
    }
    
}
