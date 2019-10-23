/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public interface FlooringOrderDao {

    public List<Order> getAllOrderByDateDao(LocalDate date) throws FlooringDaoException;

    public void addOrder(Order orderToAdd) throws FlooringDaoException;

    public void editOrder(Order editedOrder) throws FlooringDaoException;

    public void removeOrder(Order orderForDaoToAddOrDelete) throws FlooringDaoException;
    
}
