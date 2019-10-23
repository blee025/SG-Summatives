/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sg.flooringmastery.dtos.Product;

/**
 *
 * @author blee0
 */
public class FlooringProductFileDao implements FlooringProductDao {

    String path;

    public FlooringProductFileDao(String path) {
        this.path = path;
    }

    @Override
    public List<Product> getProducts() throws FlooringDaoException {

        List<Product> allProducts = new ArrayList<>();

        FileReader reader = null;
        try {
            reader = new FileReader(path);
            Scanner scn = new Scanner(reader);
            scn.nextLine(); //skips header line
            while (scn.hasNextLine()) {
                String line = scn.nextLine();

                String[] cells = line.split(",");

                Product toAdd = new Product();
                toAdd.setProductType(cells[0]);
                toAdd.setCostPerSquareFoot(new BigDecimal(cells[1]));
                toAdd.setLaborCostPerSquareFoot(new BigDecimal(cells[2]));

                allProducts.add(toAdd);

            }

        } catch (FileNotFoundException ex) {
            throw new FlooringDaoException("Could not find file during getProducts()", ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new FlooringDaoException("Could not close file reader during getProducts()", ex);
                }
            }
        }
        return allProducts;
    }

}
