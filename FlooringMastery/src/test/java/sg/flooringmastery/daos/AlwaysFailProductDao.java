/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.util.List;
import sg.flooringmastery.dtos.Product;

/**
 *
 * @author blee0
 */
public class AlwaysFailProductDao implements FlooringProductDao {

    @Override
    public List<Product> getProducts() throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

}
