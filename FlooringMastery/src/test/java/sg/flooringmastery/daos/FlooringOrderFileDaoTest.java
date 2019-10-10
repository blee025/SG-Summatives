/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public class FlooringOrderFileDaoTest {

    public FlooringOrderFileDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {

        Path testPath = Paths.get("testData");
        Path seedPath = Paths.get("seedData");

        File testFolder = testPath.toFile();
        File seedFolder = seedPath.toFile();

        if (!testFolder.exists()) {
            testFolder.mkdir(); //make testFolder Directory if there is no folder named testFolder
        }

        File[] testFiles = testFolder.listFiles();
        for (File testFile : testFiles) {
            testFile.delete(); //deletes all files in the testFolder 
        }

        File[] seedFiles = seedFolder.listFiles();
        for (File seedFile : seedFiles) {
            Files.copy(seedFile.toPath(), Paths.get(testPath.toString(), seedFile.getName()), StandardCopyOption.REPLACE_EXISTING); //copies seed files into testData folder
        }

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrderByDateDao method, of class FlooringOrderFileDao.
     */
    @Test
    public void testGetAllOrderByDateDaoGoldenPath() {

        try {
            FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

            //2. Act
            List<Order> allOrders09092019 = toTest.getAllOrderByDateDao(LocalDate.of(2019, 9, 9));

            //3. Assert
            assertEquals(3, allOrders09092019.size());

            Order o1 = allOrders09092019.get(0);

            //1,Will,OH,6.25,Wood,100.00,5.15,4.75
            assertEquals(1, o1.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 9), o1.getDate());
            assertEquals("Will", o1.getCustomerName());
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

            Order o2 = allOrders09092019.get(1);

            //2,Fred,WA,6.50,Stone,83.00,6.86,6.34
            assertEquals(2, o2.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 9), o2.getDate());
            assertEquals("Fred", o2.getCustomerName());
            assertEquals("WA", o2.getState());
            assertEquals(new BigDecimal("6.50"), o2.getTaxRate());
            assertEquals("Stone", o2.getProductType());
            assertEquals(new BigDecimal("83.00"), o2.getArea());
            assertEquals(new BigDecimal("6.86"), o2.getCostPerSquareFoot());
            assertEquals(new BigDecimal("6.34"), o2.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("569.38"), o2.getMaterialCost());
            assertEquals(new BigDecimal("526.22"), o2.getLaborCost());
            assertEquals(new BigDecimal("71.21"), o2.getTax());
            assertEquals(new BigDecimal("1166.81"), o2.getTotal());

            List<Order> allOrders09082019 = toTest.getAllOrderByDateDao(LocalDate.of(2019, 9, 8));

            assertEquals(3, allOrders09082019.size());

            Order o3 = allOrders09082019.get(0);

            //1,Sally,FL,6.00,Laminate,103.00,1.75,2.10
            assertEquals(1, o3.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 8), o3.getDate());
            assertEquals("Sally", o3.getCustomerName());
            assertEquals("FL", o3.getState());
            assertEquals(new BigDecimal("6.00"), o3.getTaxRate());
            assertEquals("Laminate", o3.getProductType());
            assertEquals(new BigDecimal("103.00"), o3.getArea());
            assertEquals(new BigDecimal("1.75"), o3.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), o3.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("180.25"), o3.getMaterialCost());
            assertEquals(new BigDecimal("216.30"), o3.getLaborCost());
            assertEquals(new BigDecimal("23.79"), o3.getTax());
            assertEquals(new BigDecimal("420.34"), o3.getTotal());

            List<Order> allOrders09072019 = toTest.getAllOrderByDateDao(LocalDate.of(2019, 9, 7));

            assertEquals(3, allOrders09072019.size());

            Order o4 = allOrders09072019.get(1);

            //2,Noel,IN,6.00,Laminate,89.00,1.75,2.10
            assertEquals(2, o4.getOrderNumber());
            assertEquals(LocalDate.of(2019, 9, 7), o4.getDate());
            assertEquals("Noel", o4.getCustomerName());
            assertEquals("IN", o4.getState());
            assertEquals(new BigDecimal("6.00"), o4.getTaxRate());
            assertEquals("Laminate", o4.getProductType());
            assertEquals(new BigDecimal("89.00"), o4.getArea());
            assertEquals(new BigDecimal("1.75"), o4.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), o4.getLaborCostPerSquareFoot());
            assertEquals(new BigDecimal("155.75"), o4.getMaterialCost());
            assertEquals(new BigDecimal("186.90"), o4.getLaborCost());
            assertEquals(new BigDecimal("20.56"), o4.getTax());
            assertEquals(new BigDecimal("363.21"), o4.getTotal());

        } catch (FlooringDaoException ex) {
            fail();
        }

    }
    
    @Test
    public void testGetAllOrderByNullDateDao() {
        LocalDate date = null;
        
        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");
        
        try {
            toTest.getAllOrderByDateDao(date);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }
    }
    
    @Test
    public void testGetAllOrderByInvalidDate() {
        LocalDate date = LocalDate.of(2019, 9, 10);
        
        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");
        
        try {
            List<Order> shouldBeBlank = toTest.getAllOrderByDateDao(date);
            
            assertEquals(0, shouldBeBlank.size());
            
        } catch (FlooringDaoException ex) {
            fail();
        }
    }
    

    @Test
    public void addOrderGoldenPath() {

        Order orderToAdd = new Order();

        orderToAdd.setDate(LocalDate.of(2019, 9, 9));
        orderToAdd.setCustomerName("Tom");
        orderToAdd.setState("MI");
        orderToAdd.setTaxRate(new BigDecimal("5.75"));
        orderToAdd.setProductType("Tile");
        orderToAdd.setArea(new BigDecimal("134.00"));
        orderToAdd.setCostPerSquareFoot(new BigDecimal("3.50"));
        orderToAdd.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

        try {
            toTest.addOrder(orderToAdd);
        } catch (FlooringDaoException ex) {
            fail();
        }

        List<Order> allOrders09092019 = null;
        try {
            allOrders09092019 = toTest.getAllOrderByDateDao(orderToAdd.getDate());

        } catch (FlooringDaoException ex) {
            fail();
        }

        assertEquals(4, allOrders09092019.size());

        Order orderAddedToCheck = allOrders09092019.get(3);

        assertEquals(4, orderAddedToCheck.getOrderNumber());
        assertEquals(LocalDate.of(2019, 9, 9), orderAddedToCheck.getDate());
        assertEquals("Tom", orderAddedToCheck.getCustomerName());
        assertEquals("MI", orderAddedToCheck.getState());
        assertEquals(new BigDecimal("5.75"), orderAddedToCheck.getTaxRate());
        assertEquals("Tile", orderAddedToCheck.getProductType());
        assertEquals(new BigDecimal("134.00"), orderAddedToCheck.getArea());
        assertEquals(new BigDecimal("3.50"), orderAddedToCheck.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.15"), orderAddedToCheck.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("469.00"), orderAddedToCheck.getMaterialCost());
        assertEquals(new BigDecimal("556.10"), orderAddedToCheck.getLaborCost());
        assertEquals(new BigDecimal("58.94"), orderAddedToCheck.getTax());
        assertEquals(new BigDecimal("1084.04"), orderAddedToCheck.getTotal());

    }

    @Test
    public void addNullOrder() {

        Order orderToAdd = null;

        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

        try {
            toTest.addOrder(orderToAdd);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }
    }

    @Test
    public void editOrderGoldenPath() {

        Order orderToEdit = new Order();

        orderToEdit.setOrderNumber(3);
        orderToEdit.setDate(LocalDate.of(2019, 9, 9));
        orderToEdit.setCustomerName("Vickeyy");
        orderToEdit.setState("TX");
        orderToEdit.setTaxRate(new BigDecimal("6.25"));
        orderToEdit.setProductType("Carpet");
        orderToEdit.setArea(new BigDecimal("97.00"));
        orderToEdit.setCostPerSquareFoot(new BigDecimal("2.25"));
        orderToEdit.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

        try {
            toTest.editOrder(orderToEdit);
        } catch (FlooringDaoException ex) {
            fail();
        }

        List<Order> allOrders09092019 = null;
        try {
            allOrders09092019 = toTest.getAllOrderByDateDao(orderToEdit.getDate());

        } catch (FlooringDaoException ex) {
            fail();
        }

        assertEquals(3, allOrders09092019.size());

        Order orderEditedToCheck = allOrders09092019.get(2);

        assertEquals(3, orderEditedToCheck.getOrderNumber());
        assertEquals(LocalDate.of(2019, 9, 9), orderEditedToCheck.getDate());
        assertEquals("Vickeyy", orderEditedToCheck.getCustomerName());
        assertEquals("TX", orderEditedToCheck.getState());
        assertEquals(new BigDecimal("6.25"), orderEditedToCheck.getTaxRate());
        assertEquals("Carpet", orderEditedToCheck.getProductType());
        assertEquals(new BigDecimal("97.00"), orderEditedToCheck.getArea());
        assertEquals(new BigDecimal("2.25"), orderEditedToCheck.getCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), orderEditedToCheck.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("218.25"), orderEditedToCheck.getMaterialCost());
        assertEquals(new BigDecimal("203.70"), orderEditedToCheck.getLaborCost());
        assertEquals(new BigDecimal("26.37"), orderEditedToCheck.getTax());
        assertEquals(new BigDecimal("448.32"), orderEditedToCheck.getTotal());

    }

    @Test
    public void editBadOrderNumber() {

        Order orderToEdit = new Order();

        orderToEdit.setOrderNumber(9);
        orderToEdit.setDate(LocalDate.of(2019, 9, 9));
        
        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

        try {
            toTest.editOrder(orderToEdit);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }
    }
    
    @Test
    public void editNullOrder() {
        Order orderToEdit = null;
        
         FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");
         
        try {
            toTest.editOrder(orderToEdit);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }
    }

    @Test
    public void removeOrderGoldenPath() {

        Order orderToRemove = new Order();

        orderToRemove.setOrderNumber(2);
        orderToRemove.setDate(LocalDate.of(2019, 9, 9));
        orderToRemove.setCustomerName("Fred");
        orderToRemove.setState("WA");
        orderToRemove.setTaxRate(new BigDecimal("6.50"));
        orderToRemove.setProductType("Stone");
        orderToRemove.setArea(new BigDecimal("83.00"));
        orderToRemove.setCostPerSquareFoot(new BigDecimal("6.86"));
        orderToRemove.setLaborCostPerSquareFoot(new BigDecimal("6.34"));

        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");

        try {
            toTest.removeOrder(orderToRemove);
        } catch (FlooringDaoException ex) {
            fail();
        }

        List<Order> allOrders09092019 = null;
        try {
            allOrders09092019 = toTest.getAllOrderByDateDao(orderToRemove.getDate());

        } catch (FlooringDaoException ex) {
            fail();
        }

        assertEquals(2, allOrders09092019.size()); //should only be two orders in list

        Order firstOrder = allOrders09092019.get(0); //first Order

        assertEquals(1, firstOrder.getOrderNumber());
        assertEquals(LocalDate.of(2019, 9, 9), firstOrder.getDate());
        assertEquals("Will", firstOrder.getCustomerName());
        assertEquals("OH", firstOrder.getState());
        assertEquals(new BigDecimal("6.25"), firstOrder.getTaxRate());
        assertEquals("Wood", firstOrder.getProductType());
        assertEquals(new BigDecimal("100.00"), firstOrder.getArea());
        assertEquals(new BigDecimal("5.15"), firstOrder.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.75"), firstOrder.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("515.00"), firstOrder.getMaterialCost());
        assertEquals(new BigDecimal("475.00"), firstOrder.getLaborCost());
        assertEquals(new BigDecimal("61.88"), firstOrder.getTax());
        assertEquals(new BigDecimal("1051.88"), firstOrder.getTotal());

        Order secondOrder = allOrders09092019.get(1); //second Order

        assertEquals(3, secondOrder.getOrderNumber());
        assertEquals(LocalDate.of(2019, 9, 9), secondOrder.getDate());
        assertEquals("George", secondOrder.getCustomerName());
        assertEquals("CO", secondOrder.getState());
        assertEquals(new BigDecimal("2.90"), secondOrder.getTaxRate());
        assertEquals("Bamboo", secondOrder.getProductType());
        assertEquals(new BigDecimal("143.00"), secondOrder.getArea());
        assertEquals(new BigDecimal("6.25"), secondOrder.getCostPerSquareFoot());
        assertEquals(new BigDecimal("5.65"), secondOrder.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("893.75"), secondOrder.getMaterialCost());
        assertEquals(new BigDecimal("807.95"), secondOrder.getLaborCost());
        assertEquals(new BigDecimal("49.35"), secondOrder.getTax());
        assertEquals(new BigDecimal("1751.05"), secondOrder.getTotal());

    }

    @Test
    public void removeBadOrderNumber() {
        Order orderToRemove = new Order();
        
        orderToRemove.setOrderNumber(8);
        orderToRemove.setDate(LocalDate.of(2019, 9, 9));

        FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");
        
        try {
            toTest.removeOrder(orderToRemove);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }

    }
    
    @Test
    public void removeNullOrder() {
        Order orderToRemove = null;
        
         FlooringOrderFileDao toTest = new FlooringOrderFileDao("testData");
         
        try {
            toTest.removeOrder(orderToRemove);
            fail();
        } catch (FlooringDaoException ex) {
            return;
        }
    }
        
         

}
