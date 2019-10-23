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
import sg.flooringmastery.dtos.Tax;

/**
 *
 * @author blee0
 */
public class FlooringTaxFileDao implements FlooringTaxDao {

    String path;

    public FlooringTaxFileDao(String path) {
        this.path = path;
    }

    @Override
    public List<Tax> getTaxes() throws FlooringDaoException {

        List<Tax> allTaxes = new ArrayList<>();

        FileReader reader = null;
        try {

            reader = new FileReader(path);
            Scanner scn = new Scanner(reader);
            scn.nextLine(); //skips header line
            while (scn.hasNextLine()) {
                String line = scn.nextLine();

                String[] cells = line.split(",");

                Tax toAdd = new Tax();
                toAdd.setState(cells[0]);
                toAdd.setTaxRate(new BigDecimal(cells[1])); //need to check cells[1]

                allTaxes.add(toAdd);

            }

        } catch (FileNotFoundException ex) {
            throw new FlooringDaoException("Could not find file during getProducts()", ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new FlooringDaoException("Could not close file reader during getTaxes()", ex);
                }
            }
        }

        return allTaxes;

    }
    
}
