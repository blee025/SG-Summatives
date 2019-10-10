/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sg.vendingmachine.dao.AlwaysFailDao;
import sg.vendingmachine.dao.VendingMachineDao;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dao.VendingMachineDaoStub;
import sg.vendingmachine.dto.Change;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class VendingMachineServiceLayerTest {

//    private VendingMachineServiceLayer service;
//    
//    public VendingMachineServiceLayerTest() {
//        VendingMachineDao dao = new VendingMachineDaoStub();
//        
//        service = new VendingMachineServiceLayerImpl(dao);
//    }
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//    VendingMachineServiceLayer service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
//    VendingMachineServiceLayer serviceBadDao = ctx.getBean("serviceLayerBadDao", VendingMachineServiceLayer.class);
//    Do not need Spring DI for ServiceLayerTest
    
    
    //need to ask about separate daostub and alwaysfaildao added above for testing ^^^ 

    
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
     * Test of getAll method, of class VendingMachineServiceLayer.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllGoldenPath() throws Exception { //could try catch for daoexception and set test to fail(), and remove "throws Exception"
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        List<Item> allItems = service.getAll();

        assertEquals(3, allItems.size());

        Item firstItem = allItems.get(0);

        assertEquals(1, firstItem.getId());
        assertEquals("Jolly Rancher", firstItem.getName());
        assertEquals(new BigDecimal("0.50"), firstItem.getPrice());
        assertEquals(10, firstItem.getQuantity());

        Item secondItem = allItems.get(1);

        assertEquals(2, secondItem.getId());
        assertEquals("Reeses", secondItem.getName());
        assertEquals(new BigDecimal("2.00"), secondItem.getPrice());
        assertEquals(10, secondItem.getQuantity());

    }

    @Test
    public void testGetAllBadDao() {
        VendingMachineDao dao = new AlwaysFailDao();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            List<Item> allItems = service.getAll();
            fail("VendingMachineServiceException was not thrown");
        } catch (VendingMachineServiceException ex) {
            return;
        }

    }

    @Test
    public void testCheckIdGoldenPath() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        assertEquals(true, service.checkId(1));
        assertEquals(true, service.checkId(2));
        assertEquals(true, service.checkId(3));

    }

    @Test
    public void testCheckBadId() {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            assertEquals(1, service.checkId(100));
            assertEquals(1, service.checkId(-1));
            service.checkId(100);

            fail("InvalidIdException was not thrown");
        } catch (VendingMachineServiceException ex) {
            fail();
        } catch (InvalidIdException ex) {
            return;
        }
    }

    /**
     * Test of getMinId method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetMinIdGoldenPath() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        assertEquals(1, service.getMinId());

    }

    @Test
    public void testGetMinIdBadDao() {
        VendingMachineDao dao = new AlwaysFailDao();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getMinId();
            fail("VendingMachineServiceException was not thrown");
        } catch (VendingMachineServiceException ex) {
            return;
        }
    }

    /**
     * Test of getMaxId method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetMaxIdGoldenPath() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        assertEquals(3, service.getMaxId());
    }

    /**
     * Test of getItemCost method, of class VendingMachineServiceLayer.
     *
     * @throws sg.vendingmachine.service.NoItemInventoryException
     * @throws sg.vendingmachine.dao.VendingMachineDaoException
     */
    @Test
    public void testGetMaxIdBadDao() {
        VendingMachineDao dao = new AlwaysFailDao();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getMaxId();
            fail("VendingMachineServiceException was not thrown");
        } catch (VendingMachineServiceException ex) {
            return;
        }
    }

    @Test
    public void testGetItemCostGoldenPath() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        assertEquals(new BigDecimal("1.00"), service.getItemCost(1, 2));
        assertEquals(new BigDecimal("2.50"), service.getItemCost(1, 5));

        assertEquals(new BigDecimal("6.00"), service.getItemCost(2, 3));

        assertEquals(new BigDecimal("1.50"), service.getItemCost(3, 2));

    }

    @Test
    public void testGetItemCostBadDao() {
        VendingMachineDao dao = new AlwaysFailDao();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getItemCost(1, 5);
            fail("VendingMachineServiceException was not thrown");
        } catch (NoItemInventoryException ex) {
            fail();
        } catch (VendingMachineServiceException ex) {
            return;
        }
    }

    @Test
    public void testGetItemInventoryError() {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getItemCost(1, 11); //item only has 10 quantity in VM

            fail("NoItemInventoryException was not thrown");
        } catch (VendingMachineServiceException ex) {
            fail();
        } catch (NoItemInventoryException ex) {
            return;
        }
    }

    /**
     * Test of insertMoney method, of class VendingMachineServiceLayer.
     *
     * @throws sg.vendingmachine.service.InsufficientFundsException
     * @throws sg.vendingmachine.dao.VendingMachineDaoException
     * @throws sg.vendingmachine.service.NoItemInventoryException
     */
    @Test
    public void testInsertMoneyGoldenPath() throws Exception { //only allowed to throw Exception for goldenpaths
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        service.getItemCost(1, 2);

        service.insertMoney(new BigDecimal("5.00")); //allow insert money to compute change, then call in service.returnChange to check big decimal 

        assertEquals(new BigDecimal("4.00"), service.returnMoneyLeftInVendingMachine()); //checking change is correct for vended items

        assertEquals(8, service.getAll().get(0).getQuantity()); //dao.getById(1).getQuantity() dao removed  //testing if insertMoney calls dao.edit to set quantity for first element correctly

    }

    @Test
    public void testInsertMoneyBadDao() {
        VendingMachineDao dao = new AlwaysFailDao();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getItemCost(1, 2);
            service.insertMoney(new BigDecimal("5.00"));
            fail("VendingMachineServiceException was not thrown");
        } catch (NoItemInventoryException | InsufficientFundsException ex) {
            fail();
        } catch (VendingMachineServiceException ex) {
            return;
        }
    }

    @Test
    public void testInsertNotEnoughMoney() {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getItemCost(1, 5);

            service.insertMoney(new BigDecimal("2.49"));
            fail("InsufficientFundsException was not thrown");
        } catch (VendingMachineServiceException | NoItemInventoryException ex) {
            fail();
        } catch (InsufficientFundsException ex) {

            try {
                assertEquals(10, service.getAll().get(0).getQuantity()); //dao.getById(1).getQuantity() //testing the state that quantity and change is correct
            } catch (VendingMachineServiceException ex1) {
                fail();
            }
            Change changeToCheck = service.returnMoney(); //since no exceptions are thrown in these methods, no need to do another try catch
            assertEquals(2, changeToCheck.getDollars());
            assertEquals(1, changeToCheck.getQuarters());
            assertEquals(2, changeToCheck.getDimes());
            assertEquals(0, changeToCheck.getNickles());
            assertEquals(4, changeToCheck.getPennies());

            return;
        }

    }

    @Test
    public void testInsertNegativeMoney() {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        try {
            service.getItemCost(1, 5);

            service.insertMoney(new BigDecimal("-2.49"));
            fail("InsufficientFundsException was not thrown");
        } catch (VendingMachineServiceException | NoItemInventoryException ex) {
            fail();
        } catch (InsufficientFundsException ex) {
            return;
        }

    }

    /**
     * Test of returnMoney method, of class VendingMachineServiceLayer.
     *
     * @throws sg.vendingmachine.service.NoItemInventoryException
     * @throws sg.vendingmachine.dao.VendingMachineDaoException
     * @throws sg.vendingmachine.service.InsufficientFundsException
     */
    @Test
    public void testReturnMoneyGoldenPath() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        service.getItemCost(1, 2);
        service.insertMoney(new BigDecimal("5.00"));

        Change changeToCheck = service.returnMoney();

        assertEquals(4, changeToCheck.getDollars());
        assertEquals(0, changeToCheck.getQuarters());
        assertEquals(0, changeToCheck.getDimes());
        assertEquals(0, changeToCheck.getNickles());
        assertEquals(0, changeToCheck.getPennies());

    }

    @Test
    public void testReturnMoneyGoldenPathItemVendedTwoRounds() throws Exception {
        VendingMachineDao dao = new VendingMachineDaoStub();

        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);

        service.getItemCost(1, 2); //user selects item quantity
        service.insertMoney(new BigDecimal("5.00")); //inserts money to vend

        BigDecimal changeNotYetReturned = service.returnMoneyLeftInVendingMachine();

        assertEquals(new BigDecimal("4.00"), changeNotYetReturned);

        service.getItemCost(3, 3); //cost should be 2.25

        service.insertMoney(BigDecimal.ZERO); //total in VM should be 4.00 still

        Change changeToCheck = service.returnMoney(); //money to return should be 1.75

        assertEquals(1, changeToCheck.getDollars());
        assertEquals(3, changeToCheck.getQuarters());
        assertEquals(0, changeToCheck.getDimes());
        assertEquals(0, changeToCheck.getNickles());
        assertEquals(0, changeToCheck.getPennies());

    }

}
