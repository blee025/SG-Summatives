/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.service;

/**
 *
 * @author blee0
 */
public class VendingMachineServiceException extends Exception{
    public VendingMachineServiceException(String message) {
        super(message);
    }
    
    public VendingMachineServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
