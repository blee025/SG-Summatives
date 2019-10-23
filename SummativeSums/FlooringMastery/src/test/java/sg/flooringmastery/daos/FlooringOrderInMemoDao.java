/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public class FlooringOrderInMemoDao implements FlooringOrderDao {

    List<Order> allOrders = new ArrayList<>();
    
    public FlooringOrderInMemoDao () {
        
        Order o1 = new Order();
        
        o1.setOrderNumber(1);
        o1.setDate(LocalDate.of(2019, 9, 29));
        o1.setCustomerName("Joe");
        o1.setState("OH");
        o1.setTaxRate(new BigDecimal("6.25"));
        o1.setProductType("Wood");
        o1.setArea(new BigDecimal("100.00"));
        o1.setCostPerSquareFoot(new BigDecimal("5.15"));
        o1.setLaborCostPerSquareFoot(new BigDecimal("4.75"));

        
        allOrders.add(o1);
        
        Order o2 = new Order();
        
        o2.setOrderNumber(2);
        o2.setDate(LocalDate.of(2019, 9, 29));
        o2.setCustomerName("Sam");
        o2.setState("PA");
        o2.setTaxRate(new BigDecimal("6.75"));
        o2.setProductType("Carpet");
        o2.setArea(new BigDecimal("150.00"));
        o2.setCostPerSquareFoot(new BigDecimal("2.25"));
        o2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        
        allOrders.add(o2);
        
        Order o3 = new Order();
        
        o3.setOrderNumber(3);
        o3.setDate(LocalDate.of(2019, 9, 29));
        o3.setCustomerName("Tom");
        o3.setState("MI");
        o3.setTaxRate(new BigDecimal("5.75"));
        o3.setProductType("Tile");
        o3.setArea(new BigDecimal("134.00"));
        o3.setCostPerSquareFoot(new BigDecimal("3.50"));
        o3.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        
        allOrders.add(o3);
        
        
    }
    

    @Override
    public List<Order> getAllOrderByDateDao(LocalDate date) {
        return allOrders;
    }

    @Override
    public void addOrder(Order orderToAdd) throws FlooringDaoException {
        
        int newId = 0;

        for (Order toCheck : allOrders) {
            if (toCheck.getOrderNumber() > newId) {
                newId = toCheck.getOrderNumber();
            }
        }

        newId++;
        orderToAdd.setOrderNumber(newId);
        allOrders.add(orderToAdd);
        
    }

    @Override
    public void editOrder(Order editedOrder) throws FlooringDaoException {

        int orderNumber = editedOrder.getOrderNumber();

        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new FlooringDaoException("Cannot find Order number " + orderNumber);
        }

        allOrders.remove(index);

        allOrders.add(editedOrder);
        
    }

    @Override
    public void removeOrder(Order orderToRemove) throws FlooringDaoException {

        int orderNumber = orderToRemove.getOrderNumber();

        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new FlooringDaoException("Cannot find Order number " + orderNumber);
        }

        allOrders.remove(index);
        
    }
    
}

//may still need to fix this 