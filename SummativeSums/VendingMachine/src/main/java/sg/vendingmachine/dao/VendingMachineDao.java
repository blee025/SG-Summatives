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
public interface VendingMachineDao {
    public List<Item> getAll() throws VendingMachineDaoException;
    public Item getById(int id) throws VendingMachineDaoException;
    public void edit(Item selectedItem, int quantityLeft) throws VendingMachineDaoException;
    
}
