/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

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
import sg.flooringmastery.dtos.Tax;

/**
 *
 * @author blee0
 */
public class FlooringTaxFileDaoTest {
    
    public FlooringTaxFileDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        Path testPath = Path.of("testTaxes.txt");
        Path seedPath = Path.of("seedTaxes.txt");
        if (Files.exists(testPath, LinkOption.NOFOLLOW_LINKS)) { //if file does exist, delete existing test file
            Files.delete(Path.of("testTaxes.txt"));
        }

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTaxes method, of class FlooringTaxFileDao.
     */
    @Test
    public void testGetTaxesGoldenPath() {
        
        try {
            FlooringTaxDao toTest = new FlooringTaxFileDao("testTaxes.txt");
            List<Tax> allStateTaxes = toTest.getTaxes();
            
            assertEquals(11, allStateTaxes.size());
            
            Tax firstStateTax = allStateTaxes.get(0);
            
            assertEquals("OH", firstStateTax.getState());
            assertEquals(new BigDecimal("6.25"), firstStateTax.getTaxRate());
            
             Tax lastStateTax = allStateTaxes.get(10);
            
            assertEquals("CA", lastStateTax.getState());
            assertEquals(new BigDecimal("7.25"), lastStateTax.getTaxRate());
            
            
        } catch (FlooringDaoException ex) {
            fail();
        }
        
        
        
        
        
    }
    
}
