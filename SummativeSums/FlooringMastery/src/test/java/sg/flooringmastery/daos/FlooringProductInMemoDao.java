/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import sg.flooringmastery.dtos.Product;

/**
 *
 * @author blee0
 */
public class FlooringProductInMemoDao implements FlooringProductDao {

    List<Product> allProducts = new ArrayList<>();

    public FlooringProductInMemoDao() {

        Product p1 = new Product();

        p1.setProductType("Carpet");
        p1.setCostPerSquareFoot(new BigDecimal("2.25"));
        p1.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        allProducts.add(p1);

        Product p2 = new Product();

        p2.setProductType("Laminate");
        p2.setCostPerSquareFoot(new BigDecimal("1.75"));
        p2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        allProducts.add(p2);

        Product p3 = new Product();

        p3.setProductType("Tile");
        p3.setCostPerSquareFoot(new BigDecimal("3.50"));
        p3.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        allProducts.add(p3);

        Product p4 = new Product();

        p4.setProductType("Wood");
        p4.setCostPerSquareFoot(new BigDecimal("5.15"));
        p4.setLaborCostPerSquareFoot(new BigDecimal("4.75"));

        allProducts.add(p4);

        Product p5 = new Product();

        p5.setProductType("Bamboo");
        p5.setCostPerSquareFoot(new BigDecimal("6.25"));
        p5.setLaborCostPerSquareFoot(new BigDecimal("5.65"));

        allProducts.add(p5);

    }

    @Override
    public List<Product> getProducts() {
        return allProducts;
    }

}


