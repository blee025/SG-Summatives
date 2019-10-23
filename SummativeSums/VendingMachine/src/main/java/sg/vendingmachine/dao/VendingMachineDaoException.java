/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.dao;

/**
 *
 * @author blee0
 */
public class VendingMachineDaoException extends Exception {
    
    public VendingMachineDaoException (String Message) {
        super (Message);
    }
    
    public VendingMachineDaoException (String Message, Throwable cause) {
        super (Message, cause);
    }
    
}
