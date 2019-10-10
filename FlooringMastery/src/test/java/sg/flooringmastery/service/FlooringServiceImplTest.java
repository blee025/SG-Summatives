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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sg.flooringmastery.daos.AlwaysFailOrderDao;
import sg.flooringmastery.daos.AlwaysFailProductDao;
import sg.flooringmastery.daos.AlwaysFailTaxDao;
import sg.flooringmastery.daos.FlooringOrderInMemoDao;
import sg.flooringmastery.dtos.Order;
import sg.flooringmastery.daos.FlooringOrderDao;
import sg.flooringmastery.daos.FlooringProductDao;
import sg.flooringmastery.daos.FlooringProductInMemoDao;
import sg.flooringmastery.daos.FlooringTaxDao;
import sg.flooringmastery.daos.FlooringTaxInMemoDao;

/**
 *
 * @author blee0
 */
public class FlooringServiceImplTest {

    public FlooringServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrdersByDate method, of class FlooringServiceImpl.
     *
     * @param date
     */
    @Test
    public void testGetAllOrdersByDateGoldenPath() {
        try {
            //LocalDate date parameter

            //1. arrange
            FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
            FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
            FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
            FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

            //2. act (call method)
            List<Order> allOrders = toTest.getAllOrdersByDate(LocalDate.of(2019, 9, 29)); //date parameter passed in

            //3. assert
            assertEquals(3, allOrders.size());

            Order o1 = allOrders.get(0);

            assertEquals(1, o1.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 29), o1.getDate());
            assertEquals("Joe", o1.getCustomerName());
            assertEquals("OH", o1.getState());
            assertEquals(new BigDecimal("6.25"), o1.getTaxRate());
            assertEquals("Wood", o1.getProductType());
            assertEquals(new BigDecimal("100.00"), o1.getArea());
            assertEquals(new BigDecimal("5.15"), o1.getCostPerSquareFoot());
            assertEquals(new BigDecimal("4.75"), o1.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("515.00"), o1.getMaterialCost());
            assertEquals(new BigDecimal("475.00"), o1.getLaborCost());
            assertEquals(new BigDecimal("61.88"), o1.getTax());
            assertEquals(new BigDecimal("1051.88"), o1.getTotal());

            Order o2 = allOrders.get(1);

            assertEquals(2, o2.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 29), o2.getDate());
            assertEquals("Sam", o2.getCustomerName());
            assertEquals("PA", o2.getState());
            assertEquals(new BigDecimal("6.75"), o2.getTaxRate());
            assertEquals("Carpet", o2.getProductType());
            assertEquals(new BigDecimal("150.00"), o2.getArea());
            assertEquals(new BigDecimal("2.25"), o2.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), o2.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("337.50"), o2.getMaterialCost());
            assertEquals(new BigDecimal("315.00"), o2.getLaborCost());
            assertEquals(new BigDecimal("44.04"), o2.getTax());
            assertEquals(new BigDecimal("696.54"), o2.getTotal());

            Order o3 = allOrders.get(2);

            assertEquals(3, o3.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 29), o3.getDate());
            assertEquals("Tom", o3.getCustomerName());
            assertEquals("MI", o3.getState());
            assertEquals(new BigDecimal("5.75"), o3.getTaxRate());
            assertEquals("Tile", o3.getProductType());
            assertEquals(new BigDecimal("134.00"), o3.getArea());
            assertEquals(new BigDecimal("3.50"), o3.getCostPerSquareFoot());
            assertEquals(new BigDecimal("4.15"), o3.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("469.00"), o3.getMaterialCost());
            assertEquals(new BigDecimal("556.10"), o3.getLaborCost());
            assertEquals(new BigDecimal("58.94"), o3.getTax());
            assertEquals(new BigDecimal("1084.04"), o3.getTotal());

        } catch (FlooringServiceException ex) {
            fail();
        }

    }

    @Test
    public void testGetAllOrdersByDateBadOrderDao() {
        FlooringOrderDao ordersFileDao = new AlwaysFailOrderDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        try {
            List<Order> allOrders = toTest.getAllOrdersByDate(LocalDate.of(2019, 9, 29)); //date parameter passed in
        } catch (FlooringServiceException ex) {
            return;
        }

    }

    //want to add test for getting all orders for a day with no orders
    
    
//    @Test
//    public void testverifyOrderDataGoldenPath() {
//
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("OH");
//        orderToAdd.setProductType("Wood");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            Order verifiedOrder = toTest.verifyOrderData(orderToAdd, requestedDate);
//
//            assertEquals("Jamie", verifiedOrder.getCustomerName());
//            assertEquals("OH", verifiedOrder.getState());
//            assertEquals(new BigDecimal("6.25"), verifiedOrder.getTaxRate());
//            assertEquals("Wood", verifiedOrder.getProductType());
//            assertEquals(new BigDecimal("123.00"), verifiedOrder.getArea());
//            assertEquals(new BigDecimal("5.15"), verifiedOrder.getCostPerSquareFoot());
//            assertEquals(new BigDecimal("4.75"), verifiedOrder.getLaborCostPerSquareFoot());
//            assertEquals(new BigDecimal("633.45"), verifiedOrder.getMaterialCost());
//            assertEquals(new BigDecimal("584.25"), verifiedOrder.getLaborCost());
//            assertEquals(new BigDecimal("76.11"), verifiedOrder.getTax());
//            assertEquals(new BigDecimal("1293.81"), verifiedOrder.getTotal());
//
//        } catch (InvalidProductException | InvalidStateException | FlooringServiceException ex) {
//            fail();
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataBadProductDao() {
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new AlwaysFailProductDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("OH");
//        orderToAdd.setProductType("Wood");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            Order verifiedOrder = toTest.verifyOrderData(orderToAdd, requestedDate);
//        } catch (InvalidProductException | InvalidStateException ex) {
//            fail();
//        } catch (FlooringServiceException ex) {
//            return;
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataLowerCaseProductAndState() {
//
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("oh");
//        orderToAdd.setProductType("wood");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            Order verifiedOrder = toTest.verifyOrderData(orderToAdd, requestedDate);
//
//            assertEquals("Jamie", verifiedOrder.getCustomerName());
//            assertEquals("OH", verifiedOrder.getState());
//            assertEquals(new BigDecimal("6.25"), verifiedOrder.getTaxRate());
//            assertEquals("Wood", verifiedOrder.getProductType());
//            assertEquals(new BigDecimal("123.00"), verifiedOrder.getArea());
//            assertEquals(new BigDecimal("5.15"), verifiedOrder.getCostPerSquareFoot());
//            assertEquals(new BigDecimal("4.75"), verifiedOrder.getLaborCostPerSquareFoot());
//            assertEquals(new BigDecimal("633.45"), verifiedOrder.getMaterialCost());
//            assertEquals(new BigDecimal("584.25"), verifiedOrder.getLaborCost());
//            assertEquals(new BigDecimal("76.11"), verifiedOrder.getTax());
//            assertEquals(new BigDecimal("1293.81"), verifiedOrder.getTotal());
//
//        } catch (InvalidProductException | InvalidStateException | FlooringServiceException ex) {
//            fail();
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataUpperCaseProductAndState() {
//
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("OH");
//        orderToAdd.setProductType("WOOD");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            Order verifiedOrder = toTest.verifyOrderData(orderToAdd, requestedDate);
//
//            assertEquals("Jamie", verifiedOrder.getCustomerName());
//            assertEquals("OH", verifiedOrder.getState());
//            assertEquals(new BigDecimal("6.25"), verifiedOrder.getTaxRate());
//            assertEquals("Wood", verifiedOrder.getProductType());
//            assertEquals(new BigDecimal("123.00"), verifiedOrder.getArea());
//            assertEquals(new BigDecimal("5.15"), verifiedOrder.getCostPerSquareFoot());
//            assertEquals(new BigDecimal("4.75"), verifiedOrder.getLaborCostPerSquareFoot());
//            assertEquals(new BigDecimal("633.45"), verifiedOrder.getMaterialCost());
//            assertEquals(new BigDecimal("584.25"), verifiedOrder.getLaborCost());
//            assertEquals(new BigDecimal("76.11"), verifiedOrder.getTax());
//            assertEquals(new BigDecimal("1293.81"), verifiedOrder.getTotal());
//
//        } catch (InvalidProductException | InvalidStateException | FlooringServiceException ex) {
//            fail();
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataInvalidState() {
//
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("MN");
//        orderToAdd.setProductType("Wood");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            toTest.verifyOrderData(orderToAdd, requestedDate);
//        } catch (InvalidProductException | FlooringServiceException ex) {
//            fail();
//        } catch (InvalidStateException ex) {
//            return;
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataInvalidProduct() {
//
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("OH");
//        orderToAdd.setProductType("Water");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            toTest.verifyOrderData(orderToAdd, requestedDate);
//        } catch (InvalidStateException | FlooringServiceException ex) {
//            fail();
//        } catch (InvalidProductException ex) {
//            return;
//        }
//
//    }
//
//    @Test
//    public void testverifyOrderDataBadTaxDao() {
//        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
//        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
//        FlooringTaxDao taxesFiledao = new AlwaysFailTaxDao();
//        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);
//
//        Order orderToAdd = new Order();
//
//        orderToAdd.setCustomerName("Jamie");
//        orderToAdd.setState("OH");
//        orderToAdd.setProductType("Wood");
//        orderToAdd.setArea(new BigDecimal("123.00"));
//
//        //need to check state and productType is available in product/tax dao in memo
//        LocalDate requestedDate = LocalDate.of(2019, 9, 29);
//
//        try {
//            Order verifiedOrder = toTest.verifyOrderData(orderToAdd, requestedDate);
//        } catch (InvalidProductException | InvalidStateException ex) {
//            fail();
//        } catch (FlooringServiceException ex) {
//            return;
//        }
//
//    }

    @Test
    public void testaddOrderGoldenPath() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        Order orderToAdd = new Order();

        LocalDate requestedDate = LocalDate.of(2019, 9, 29);

        orderToAdd.setCustomerName("Jamie");
        orderToAdd.setDate(requestedDate);
        orderToAdd.setState("OH");
        orderToAdd.setProductType("Wood");
        orderToAdd.setArea(new BigDecimal("123.00"));

        try {

            String commit = "yes";

            toTest.addOrder(orderToAdd, commit);

            List<Order> toCheck = toTest.getAllOrdersByDate(requestedDate);

            Order addedOrder = toCheck.get(3);

            //assert that the ID is what we expect
            
            assertEquals("Jamie", addedOrder.getCustomerName());
            assertEquals("OH", addedOrder.getState());
            assertEquals(new BigDecimal("6.25"), addedOrder.getTaxRate());
            assertEquals("Wood", addedOrder.getProductType());
            assertEquals(new BigDecimal("123.00"), addedOrder.getArea());
            assertEquals(new BigDecimal("5.15"), addedOrder.getCostPerSquareFoot());
            assertEquals(new BigDecimal("4.75"), addedOrder.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("633.45"), addedOrder.getMaterialCost());
            assertEquals(new BigDecimal("584.25"), addedOrder.getLaborCost());
            assertEquals(new BigDecimal("76.11"), addedOrder.getTax());
            assertEquals(new BigDecimal("1293.81"), addedOrder.getTotal());

        } catch (InvalidProductException | InvalidStateException | FlooringServiceException ex) {
            fail();
        }

    }

    @Test
    public void testaddOrderBadOrderDao() {

        FlooringOrderDao ordersFileDao = new AlwaysFailOrderDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        Order orderToAdd = new Order();

        LocalDate requestedDate = LocalDate.of(2019, 9, 29);

        orderToAdd.setCustomerName("Jamie");
        orderToAdd.setDate(requestedDate);
        orderToAdd.setState("OH");
        orderToAdd.setProductType("Wood");
        orderToAdd.setArea(new BigDecimal("123.00"));

        try {

            String commit = "yes";

            toTest.addOrder(orderToAdd, commit);

        } catch (InvalidProductException | InvalidStateException ex) {
            fail();
        } catch (FlooringServiceException ex) {
            return;
        }

    }
    
    //test for lowercase and uppercase state and product type for addOrder 

    @Test
    public void testgetOrderFromDateFileGoldenPath() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        LocalDate date = LocalDate.of(2019, 9, 29);

        int orderNumber = 1;

        Order requestedOrder = new Order();

        requestedOrder.setDate(date);
        requestedOrder.setOrderNumber(orderNumber);

        try {
            Order orderFound = toTest.getOrderFromDateFile(requestedOrder);

            assertEquals(1, orderFound.getOrderNumber());
            assertEquals("Joe", orderFound.getCustomerName());
            assertEquals("OH", orderFound.getState());
            assertEquals(new BigDecimal("6.25"), orderFound.getTaxRate());
            assertEquals("Wood", orderFound.getProductType());
            assertEquals(new BigDecimal("100.00"), orderFound.getArea());
            assertEquals(new BigDecimal("5.15"), orderFound.getCostPerSquareFoot());
            assertEquals(new BigDecimal("4.75"), orderFound.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("515.00"), orderFound.getMaterialCost());
            assertEquals(new BigDecimal("475.00"), orderFound.getLaborCost());
            assertEquals(new BigDecimal("61.88"), orderFound.getTax());
            assertEquals(new BigDecimal("1051.88"), orderFound.getTotal());

        } catch (InvalidOrderNumberException | FlooringServiceException ex) {
            fail();
        }

    }

    @Test
    public void testgetOrderFromDateFileBadOrderDao() {

        FlooringOrderDao ordersFileDao = new AlwaysFailOrderDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        LocalDate date = LocalDate.of(2019, 9, 29);

        int orderNumber = 1;

        Order requestedOrder = new Order();

        requestedOrder.setDate(date);
        requestedOrder.setOrderNumber(orderNumber);

        try {
            Order orderFound = toTest.getOrderFromDateFile(requestedOrder);
        } catch (InvalidOrderNumberException ex) {
            fail();
        } catch (FlooringServiceException ex) {
            return;
        }

    }
    
    
    @Test
    public void testgetOrderFromDateFileBadOrderNumber() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        LocalDate date = LocalDate.of(2019, 9, 29);

        int orderNumber = 8;

        Order requestedOrder = new Order();

        requestedOrder.setDate(date);
        requestedOrder.setOrderNumber(orderNumber);

        try {
            Order orderFound = toTest.getOrderFromDateFile(requestedOrder);

        } catch (FlooringServiceException ex) {
            fail();
        } catch (InvalidOrderNumberException ex) {
            return;
        }

    }
    
            

    @Test
    public void testgetOrderFromDateFileInvalidIdNumber() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        LocalDate date = LocalDate.of(2019, 9, 29);

        int orderNumber = 5;

        Order requestedOrder = new Order();

        requestedOrder.setDate(date);
        requestedOrder.setOrderNumber(orderNumber);

        try {
            toTest.getOrderFromDateFile(requestedOrder);
        } catch (FlooringServiceException ex) {
            fail();
        } catch (InvalidOrderNumberException ex) {
            return;
        }

    }

    @Test
    public void testcheckAndEditOrderGoldenPath() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {
            Order actualOrder = toTest.getOrderFromDateFile(requestedOrder);

            Order editedOrder = new Order();

            editedOrder.setCustomerName("Xander");
            //editedOrder.setDate(actualOrder.getDate());
            editedOrder.setState("VA");
            editedOrder.setProductType("Laminate");
            editedOrder.setArea(new BigDecimal("75.00"));

            toTest.checkAndEditOrder(editedOrder);

            Order toCheck = toTest.getOrderFromDateFile(editedOrder);

            assertEquals(1, toCheck.getOrderNumber());
            assertEquals("Xander", toCheck.getCustomerName());
            assertEquals("VA", toCheck.getState());
            assertEquals(new BigDecimal("4.30"), toCheck.getTaxRate());
            assertEquals("Laminate", toCheck.getProductType());
            assertEquals(new BigDecimal("75.00"), toCheck.getArea());
            assertEquals(new BigDecimal("1.75"), toCheck.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), toCheck.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("131.25"), toCheck.getMaterialCost());
            assertEquals(new BigDecimal("157.50"), toCheck.getLaborCost());
            assertEquals(new BigDecimal("12.42"), toCheck.getTax());
            assertEquals(new BigDecimal("301.17"), toCheck.getTotal());

        } catch (InvalidOrderNumberException | InvalidStateException | InvalidProductException | FlooringServiceException ex) {
            fail();
        }

    }

    @Test
    public void testcheckAndEditOrderBadOrderDao() {

        FlooringOrderDao ordersFileDao = new AlwaysFailOrderDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {
            Order actualOrder = toTest.getOrderFromDateFile(requestedOrder);

            Order editedOrder = new Order();

            editedOrder.setCustomerName("Xander");
            editedOrder.setDate(date);
            editedOrder.setState("VA");
            editedOrder.setProductType("Laminate");
            editedOrder.setArea(new BigDecimal("75.00"));

            toTest.checkAndEditOrder(editedOrder);
            fail();

        } catch (InvalidOrderNumberException | InvalidStateException | InvalidProductException ex) {
            fail();
        } catch (FlooringServiceException ex) {
            return;
        }

    }

    @Test
    public void testcheckAndEditOrderInvalidProduct() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {
            Order actualOrder = toTest.getOrderFromDateFile(requestedOrder);

            Order editedOrder = new Order();

            editedOrder.setCustomerName("Xander");
            editedOrder.setDate(date);
            editedOrder.setState("VA");
            editedOrder.setProductType("Dirt");
            editedOrder.setArea(new BigDecimal("75.00"));

            toTest.checkAndEditOrder(editedOrder);
            fail();
        } catch (InvalidOrderNumberException | InvalidStateException | FlooringServiceException ex) {
            fail();
        } catch (InvalidProductException ex) {
            return;
        }

    }

    @Test
    public void testcheckAndEditOrderInvalidState() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {
            Order actualOrder = toTest.getOrderFromDateFile(requestedOrder);

            Order editedOrder = new Order();

            editedOrder.setCustomerName("Xander");
            editedOrder.setDate(date);
            editedOrder.setState("ND");
            editedOrder.setProductType("Laminate");
            editedOrder.setArea(new BigDecimal("75.00"));

            toTest.checkAndEditOrder(editedOrder);
            fail();
        } catch (InvalidOrderNumberException | InvalidProductException | FlooringServiceException ex) {
            fail();
        } catch (InvalidStateException ex) {
            return;
        }

    }

    @Test
    public void testremoveOrderGoldenPath() {

        FlooringOrderDao ordersFileDao = new FlooringOrderInMemoDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {

            Order orderToRemove = toTest.getOrderFromDateFile(requestedOrder);

            String commit = "yes";

            toTest.removeOrder(orderToRemove, commit);

            List<Order> toCheck = toTest.getAllOrdersByDate(date);

            assertEquals(2, toCheck.size());

            Order firstOrder = toCheck.get(0);

            assertEquals(2, firstOrder.getOrderNumber());
            assertEquals("Sam", firstOrder.getCustomerName());
            assertEquals("PA", firstOrder.getState());
            assertEquals(new BigDecimal("6.75"), firstOrder.getTaxRate());
            assertEquals("Carpet", firstOrder.getProductType());
            assertEquals(new BigDecimal("150.00"), firstOrder.getArea());
            assertEquals(new BigDecimal("2.25"), firstOrder.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), firstOrder.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("337.50"), firstOrder.getMaterialCost());
            assertEquals(new BigDecimal("315.00"), firstOrder.getLaborCost());
            assertEquals(new BigDecimal("44.04"), firstOrder.getTax());
            assertEquals(new BigDecimal("696.54"), firstOrder.getTotal());

            Order secondOrder = toCheck.get(1);

            assertEquals(3, secondOrder.getOrderNumber());
            assertEquals("Tom", secondOrder.getCustomerName());
            assertEquals("MI", secondOrder.getState());
            assertEquals(new BigDecimal("5.75"), secondOrder.getTaxRate());
            assertEquals("Tile", secondOrder.getProductType());
            assertEquals(new BigDecimal("134.00"), secondOrder.getArea());
            assertEquals(new BigDecimal("3.50"), secondOrder.getCostPerSquareFoot());
            assertEquals(new BigDecimal("4.15"), secondOrder.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("469.00"), secondOrder.getMaterialCost());
            assertEquals(new BigDecimal("556.10"), secondOrder.getLaborCost());
            assertEquals(new BigDecimal("58.94"), secondOrder.getTax());
            assertEquals(new BigDecimal("1084.04"), secondOrder.getTotal());

        } catch (InvalidOrderNumberException | FlooringServiceException ex) {
            fail();
        }

    }

    @Test
    public void testremoveOrderBadOrderDao() {

        FlooringOrderDao ordersFileDao = new AlwaysFailOrderDao();
        FlooringProductDao productsFileDao = new FlooringProductInMemoDao();
        FlooringTaxDao taxesFiledao = new FlooringTaxInMemoDao();
        FlooringService toTest = new FlooringServiceImpl(ordersFileDao, productsFileDao, taxesFiledao);

        int orderNumber = 1;

        LocalDate date = LocalDate.of(2019, 9, 29);

        Order requestedOrder = new Order();

        requestedOrder.setOrderNumber(orderNumber);
        requestedOrder.setDate(date);

        try {

            Order orderToRemove = toTest.getOrderFromDateFile(requestedOrder);

            String commit = "yes";

            toTest.removeOrder(orderToRemove, commit);
            fail();
        } catch (InvalidOrderNumberException ex) {
            fail();
        } catch (FlooringServiceException ex) {
            return;
        }

    }

}

//test for bad inputs 

