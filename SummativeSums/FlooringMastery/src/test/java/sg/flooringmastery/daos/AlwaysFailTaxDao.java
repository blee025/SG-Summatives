/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.util.List;
import sg.flooringmastery.dtos.Tax;

/**
 *
 * @author blee0
 */
public class AlwaysFailTaxDao implements FlooringTaxDao {

    @Override
    public List<Tax> getTaxes() throws FlooringDaoException {
        throw new FlooringDaoException("ALWAYS FAIL DAO");
    }

}
