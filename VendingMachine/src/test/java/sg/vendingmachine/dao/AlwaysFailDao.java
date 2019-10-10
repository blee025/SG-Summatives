/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.dao;

import java.util.List;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class AlwaysFailDao implements VendingMachineDao {

    @Override
    public List<Item> getAll() throws VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAIL DAO");
    }

    @Override
    public Item getById(int id) throws VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAIL DAO");
    }

    @Override
    public void edit(Item selectedItem, int quantityLeft) throws VendingMachineDaoException {
        throw new VendingMachineDaoException("ALWAYS FAIL DAO");
    }

}
