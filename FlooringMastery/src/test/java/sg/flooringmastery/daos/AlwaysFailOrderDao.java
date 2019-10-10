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
public class AlwaysFailOrderDao implements FlooringOrderDao {

    @Override
    public List<Order> getAllOrderByDateDao(LocalDate date) throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

    @Override
    public void addOrder(Order orderToAdd) throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

    @Override
    public void editOrder(Order editedOrder) throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

    @Override
    public void removeOrder(Order orderForDaoToAddOrDelete) throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

}
