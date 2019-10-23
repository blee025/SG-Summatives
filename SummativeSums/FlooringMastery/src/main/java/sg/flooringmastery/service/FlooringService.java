/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.service;

import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public interface FlooringService {

    public List<Order> getAllOrdersByDate(LocalDate date) throws FlooringServiceException;

    //public Order verifyOrderData(Order orderToAdd, LocalDate date) throws InvalidProductException, InvalidStateException, FlooringServiceException;

    public Order addOrder(Order orderToAdd, String commit) throws FlooringServiceException, InvalidProductException, InvalidStateException;

    public Order getOrderFromDateFile(Order requestedOrder) throws InvalidOrderNumberException, FlooringServiceException;

    public void checkAndEditOrder(Order editedOrder) throws FlooringServiceException, InvalidStateException, InvalidProductException;

    public void removeOrder(Order orderToRemove, String commit) throws FlooringServiceException;
    
}
 