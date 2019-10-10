/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import sg.vendingmachine.dao.VendingMachineDao;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class VendingMachineDaoStub implements VendingMachineDao {

    Item firstItem;
    Item secondItem;
    Item thirdItem;
    List<Item> allItems = new ArrayList<>();

    public VendingMachineDaoStub() {

        firstItem = new Item();
        firstItem.setId(1);
        firstItem.setName("Jolly Rancher");
        firstItem.setPrice(new BigDecimal("0.50"));
        firstItem.setQuantity(10);

        allItems.add(firstItem);

        secondItem = new Item();
        secondItem.setId(2);
        secondItem.setName("Reeses");
        secondItem.setPrice(new BigDecimal("2.00"));
        secondItem.setQuantity(10);

        allItems.add(secondItem);

        thirdItem = new Item();
        thirdItem.setId(3);
        thirdItem.setName("Kisses");
        thirdItem.setPrice(new BigDecimal("0.75"));
        thirdItem.setQuantity(10);

        allItems.add(thirdItem);
    }

    @Override
    public List<Item> getAll() throws VendingMachineDaoException {
        return allItems;
    }

    @Override
    public Item getById(int id) throws VendingMachineDaoException {
//        if (id == firstItem.getId()) {
//            return firstItem;
//        } else {
//            return null;
//        }

        for (Item toCheck : allItems) {
            if (toCheck.getId() == id) {
                Item selectedItem = toCheck;
                return selectedItem;
            }
        }
        return null;
    }

    @Override
    public void edit(Item selectedItem, int quantityLeft) throws VendingMachineDaoException {
        for (int i = 0; i < allItems.size(); i++) {
            Item toCheck = allItems.get(i);

            if (toCheck.getId() == selectedItem.getId()) {
                toCheck.setQuantity(quantityLeft);
                //int index = i;
            }
        }

    }

}
