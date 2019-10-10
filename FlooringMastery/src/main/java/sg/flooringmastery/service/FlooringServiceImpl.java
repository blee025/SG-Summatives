/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.flooringmastery.daos.FlooringDaoException;
import sg.flooringmastery.dtos.Order;
import sg.flooringmastery.dtos.Product;
import sg.flooringmastery.dtos.Tax;
import sg.flooringmastery.daos.FlooringOrderDao;
import sg.flooringmastery.daos.FlooringProductDao;
import sg.flooringmastery.daos.FlooringTaxDao;

/**
 *
 * @author blee0
 */
public class FlooringServiceImpl implements FlooringService {

    FlooringOrderDao ordersFileDao;
    FlooringProductDao productsDao;
    FlooringTaxDao taxesDao;

    //Order OrderForDaoToAdd;
    Order orderRequestedToEdit = null;
    

    public FlooringServiceImpl(FlooringOrderDao ordersFileDao, FlooringProductDao productsDao, FlooringTaxDao taxesDao) {
        this.ordersFileDao = ordersFileDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws FlooringServiceException {

        //requestedDate = date;

        //1. get all orders from dao 
        List<Order> allOrders = null;
        try {
            allOrders = ordersFileDao.getAllOrderByDateDao(date);
        } catch (FlooringDaoException ex) {
            throw new FlooringServiceException("Could not get all Orders from dao.", ex);
        }

        //2. return orders
        return allOrders;
    }

    @Override
    public Order addOrder(Order orderToAdd, String commit) throws FlooringServiceException, InvalidProductException, InvalidStateException {

        Order orderVerified = null;

        if (commit.equals("yes")) {
            try {

                orderVerified = verifyOrderData(orderToAdd);

                ordersFileDao.addOrder(orderVerified);

            } catch (FlooringDaoException ex) {
                throw new FlooringServiceException("Unable to add Order", ex);
            }

        }

        return orderVerified;
    }

    @Override
    public Order getOrderFromDateFile(Order requestedOrder) throws InvalidOrderNumberException, FlooringServiceException {

        LocalDate requestedDate = requestedOrder.getDate();

        int orderNumber = requestedOrder.getOrderNumber();

        List<Order> allOrders;
        try {
            allOrders = ordersFileDao.getAllOrderByDateDao(requestedDate);
        } catch (FlooringDaoException ex) {
            throw new FlooringServiceException("Unable to get orders by requested date", ex);
        }

        for (Order toCheck : allOrders) {
            if (toCheck.getOrderNumber() == orderNumber) {
                orderRequestedToEdit = toCheck;
            }
        }

        if (orderRequestedToEdit == null) {
            throw new InvalidOrderNumberException("Unable to find requested order number " + orderNumber + " for " + requestedDate);

        }

        return orderRequestedToEdit;
    }

    @Override
    public void checkAndEditOrder(Order editedOrder) throws FlooringServiceException, InvalidStateException, InvalidProductException {

        Order inputVerified = checkBlankInput(editedOrder);

        Order verifiedEditedOrder = verifyOrderData(inputVerified);
        
        //Order allDataSet = setRestOfData(verifiedEditedOrder);

        try {
            ordersFileDao.editOrder(verifiedEditedOrder);
        } catch (FlooringDaoException ex) {
            throw new FlooringServiceException("Unable to edit order", ex);
        }

    }

    @Override
    public void removeOrder(Order orderToRemove, String commit) throws FlooringServiceException {

        if (commit.equals("yes")) {
            try {

                ordersFileDao.removeOrder(orderToRemove);
            } catch (FlooringDaoException ex) {
                throw new FlooringServiceException("Unable to remove order", ex);
            }
        }

    }

    private Order verifyOrderData(Order orderToAdd) throws InvalidProductException, InvalidStateException, FlooringServiceException {

        LocalDate requestedDate = orderToAdd.getDate();

        //boolean toReturn = false;
        //OrderForDaoToAdd = orderToAdd;
        
        BigDecimal stateTax = BigDecimal.ZERO;
        BigDecimal materialUnitCost = BigDecimal.ZERO;
        BigDecimal laborUnitCost = BigDecimal.ZERO;

        //1. retrieve state and product type 
        String inputState = orderToAdd.getState();
        String inputProductType = orderToAdd.getProductType();

        String actualProduct = "Vinyl";

        String actualState = "TX";

        //2. check in product and taxes dao if valid entries
        List<Product> allProducts;

        List<Tax> allTaxes;

        try {
            allProducts = productsDao.getProducts();
            allTaxes = taxesDao.getTaxes();
        } catch (FlooringDaoException ex) {
            throw new FlooringServiceException("Unable to get all products or state taxes", ex);
        }

        boolean validProduct = false;
        boolean validState = false;

        for (Product product : allProducts) {
            if (product.getProductType().toLowerCase().equals(inputProductType.toLowerCase())) {
                actualProduct = product.getProductType();
                materialUnitCost = product.getCostPerSquareFoot();
                laborUnitCost = product.getLaborCostPerSquareFoot();
                validProduct = true;
            }
        }

        for (Tax tax : allTaxes) {
            if (tax.getState().toUpperCase().equals(inputState.toUpperCase())) {
                actualState = tax.getState();
                stateTax = tax.getTaxRate();
                validState = true;
            }
        }

        if (validProduct == false) {
            throw new InvalidProductException(inputProductType + " is not a valid product.");
        }

        if (validState == false) {
            throw new InvalidStateException(inputState + " is not a valid state.");
        }

        orderToAdd.setState(actualState);
        orderToAdd.setProductType(actualProduct);

        orderToAdd.setDate(requestedDate);
        orderToAdd.setTaxRate(stateTax);
        orderToAdd.setCostPerSquareFoot(materialUnitCost);
        orderToAdd.setLaborCostPerSquareFoot(laborUnitCost);

        //3. return true or false if Order is valid 
        return orderToAdd;
    }

    private Order checkBlankInput(Order editedOrder) {

        if (editedOrder.getCustomerName().isBlank()) {
            editedOrder.setCustomerName(orderRequestedToEdit.getCustomerName());
        }

        if (editedOrder.getState().isBlank()) {
            editedOrder.setState(orderRequestedToEdit.getState());
        }

        if (editedOrder.getProductType().isBlank()) {
            editedOrder.setProductType(orderRequestedToEdit.getProductType());
        }

        if (editedOrder.getArea().compareTo(BigDecimal.ZERO) == 0) {
            editedOrder.setArea(orderRequestedToEdit.getArea());
            
        }

         editedOrder.setOrderNumber(orderRequestedToEdit.getOrderNumber());
         
         editedOrder.setDate(orderRequestedToEdit.getDate());
        
        return editedOrder;
    }

}
