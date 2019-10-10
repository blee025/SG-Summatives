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
import sg.flooringmastery.dtos.Product;

/**
 *
 * @author blee0
 */
public class FlooringProductFileDaoTest {
    
    public FlooringProductFileDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        Path testPath = Path.of("testProducts.txt");
        Path seedPath = Path.of("seedProducts.txt");
        if (Files.exists(testPath, LinkOption.NOFOLLOW_LINKS)) { //if file does exist, delete existing test file
            Files.delete(Path.of("testProducts.txt"));
        }

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getProducts method, of class FlooringProductFileDao.
     */
    @Test
    public void testGetProductsGoldenPath() {
        
        try {
            FlooringProductDao toTest = new FlooringProductFileDao("testProducts.txt");
            List<Product> allProducts = toTest.getProducts();
            
            assertEquals(9, allProducts.size());
            
            Product firstProduct = allProducts.get(0);
            
            assertEquals("Carpet", firstProduct.getProductType());
            assertEquals(new BigDecimal("2.25"), firstProduct.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.10"), firstProduct.getLaborCostPerSquareFoot());
            
            Product lastProduct = allProducts.get(8);
            
            assertEquals("Rubber", lastProduct.getProductType());
            assertEquals(new BigDecimal("2.53"), lastProduct.getCostPerSquareFoot());
            assertEquals(new BigDecimal("2.03"), lastProduct.getLaborCostPerSquareFoot());
 
            
        } catch (FlooringDaoException ex) {
            fail();
        }
        
        
    }
    
}
