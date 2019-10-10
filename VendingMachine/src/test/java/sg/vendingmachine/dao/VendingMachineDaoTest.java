/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sg.vendingmachine.dto.Item;
import sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import sg.vendingmachine.service.VendingMachineServiceLayer;

/**
 *
 * @author blee0
 */
public class VendingMachineDaoTest {
   
    public VendingMachineDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        Path testPath = Path.of("daoTest.txt");
        Path seedPath = Path.of("seed.txt");
        if (Files.exists(testPath, LinkOption.NOFOLLOW_LINKS)) { //if file does exist, delete existing test file
            Files.delete(Path.of("daoTest.txt"));
        }

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllGoldenPath() {
        //never want throws exception for test methods

        //no input, so can't put in bad input
        try {
            VendingMachineDao toTest = new VendingMachineDaoFile("daoTest.txt");
            List<Item> allItems = toTest.getAll();

            assertEquals(1, allItems.size());
            Item only = allItems.get(0);

            assertEquals(1, only.getId());
            assertEquals("Jelly Beans", only.getName());
            assertEquals(new BigDecimal("2.50"), only.getPrice());
            assertEquals(13, only.getQuantity());

        } catch (VendingMachineDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getById method, of class VendingMachineDao.
     */
    @Test
    public void testGetByIdGoldenPath() {
        try {
            VendingMachineDao toTest = new VendingMachineDaoFile("daoTest.txt");
            List<Item> allItems = toTest.getAll();

            Item only = allItems.get(0);

            assertEquals(toTest.getById(1), only);
            //is this correct? 
        } catch (VendingMachineDaoException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetByBadId() {
        try {
            VendingMachineDao toTest = new VendingMachineDaoFile("daoTest.txt");
            List<Item> allItems = toTest.getAll();

            Item only = toTest.getById(5);

            fail();
        } catch (VendingMachineDaoException ex) {
            return;
        }
    }
    
    /**
     * Test of edit method, of class VendingMachineDao.
     */
    @Test
    public void testEditGoldenPath() {
        try {
            VendingMachineDao toTest = new VendingMachineDaoFile("daoTest.txt");
            List<Item> allItems = toTest.getAll();
                       
            Item only = allItems.get(0);

            toTest.edit(only, 12);
           
            assertEquals(12, toTest.getAll().get(0).getQuantity());
       
        } catch (VendingMachineDaoException ex) {
            fail();
        } 
    }
    
     @Test
    public void testEditNullItem() {
        try {
            VendingMachineDao toTest = new VendingMachineDaoFile("daoTest.txt");
            List<Item> allItems = toTest.getAll();
                       
            Item only = null;

            toTest.edit(only, 12);
           
            fail();
            
        } catch (VendingMachineDaoException ex) {
            return;
        } 
    }

}
