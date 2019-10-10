/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.service;

import sg.vendingmachine.dto.Change;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.vendingmachine.dao.VendingMachineDao;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    BigDecimal cost = BigDecimal.ZERO;
    BigDecimal requiredCost = cost.setScale(2, RoundingMode.HALF_UP);

    BigDecimal preChange = BigDecimal.ZERO;
    BigDecimal change = preChange.setScale(2, RoundingMode.HALF_UP);

    Item selectedItem = null;
    int quantityRequestedService = 0;

    VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao myDao) {
        this.dao = myDao;
    }

    @Override
    public List<Item> getAll() throws VendingMachineServiceException {

        try {
            return dao.getAll();
        } catch (VendingMachineDaoException ex) {
            throw new VendingMachineServiceException("Unable to get item list" + ex.getMessage(), ex);
        }
    }

    @Override
    public int getMinId() throws VendingMachineServiceException { //check for min ID in list
        int minId = Integer.MAX_VALUE;
        try {
            List<Item> allItems = dao.getAll();
            for (Item toCheck : allItems) {
                if (toCheck.getId() < minId) {
                    minId = toCheck.getId();
                }
            }

        } catch (VendingMachineDaoException ex) {
            throw new VendingMachineServiceException("Unable to get Min ID", ex);
        }
        return minId;
    }

    @Override
    public int getMaxId() throws VendingMachineServiceException { //check for max ID in list
        int maxId = Integer.MIN_VALUE;
        try {
            List<Item> allItems = dao.getAll();
            for (Item toCheck : allItems) {
                if (toCheck.getId() > maxId) {
                    maxId = toCheck.getId();
                }
            }

        } catch (VendingMachineDaoException ex) {
            throw new VendingMachineServiceException("Unable to get Max ID", ex);
        }
        return maxId;
    }

    @Override
    public boolean checkId(int userChosenId) throws InvalidIdException, VendingMachineServiceException { //check if ID is valid
        boolean validId = false;
        try {
            if (userChosenId >= getMinId() && userChosenId <= getMaxId()) {
                validId = true;
            } else {
                throw new InvalidIdException("Invalid ID");
            }
        } catch (VendingMachineServiceException ex) {
            throw new VendingMachineServiceException("Unable to check ID " + userChosenId, ex); //not sure if this is correct exception to be thrown 
        }
        return validId;
    }

    @Override
    public BigDecimal getItemCost(int userChosenId, int quantityRequested) throws NoItemInventoryException, VendingMachineServiceException {
        //calculates if VM has enough item quantity to vend, otherwise throw exception
        //if enough quantity, calculate cost
        
        try {
            selectedItem = dao.getById(userChosenId);
            boolean valid = false;
            while (!valid) {
                if (selectedItem.getQuantity() >= quantityRequested) {
                    quantityRequestedService = quantityRequested;
                    requiredCost = selectedItem.getPrice().multiply(BigDecimal.valueOf(quantityRequestedService));
                    valid = true;
                } else {
                    throw new NoItemInventoryException("Not enough quantity.");
                }
            }
        } catch (VendingMachineDaoException ex) {
            throw new VendingMachineServiceException("Unable get cost for ID " + userChosenId, ex);
        }

        return requiredCost;

    }

    @Override
    public void insertMoney(BigDecimal userMoney) throws InsufficientFundsException, VendingMachineServiceException {
        BigDecimal moneyLeft = userMoney.add(change);
        if (userMoney.compareTo(BigDecimal.ZERO) < 0) { //checking if userMoney is a negative number 
            throw new InsufficientFundsException("Cannot input negative Money value!");
        } else if (moneyLeft.compareTo(requiredCost) < 0 ) { //checking if userMoney + previous change is less than requiredCost, if not, throw exception
            change = moneyLeft;
            throw new InsufficientFundsException("NOT ENOUGH MONEY!");
        } else if (selectedItem == null) {
            change = moneyLeft;
        } else {
            if (moneyLeft.compareTo(requiredCost) >= 0) { //check if usermoney + previous change is more than cost
                change = moneyLeft.subtract(requiredCost); //if true, calculate money left in VM, correctly set difference in quantity for item 
                requiredCost = BigDecimal.ZERO;

                int quantityLeft = selectedItem.getQuantity();
                quantityLeft -= quantityRequestedService;

                try {
                    if (quantityLeft == 0) {
                        dao.edit(selectedItem, 20); //reset vending machine item quantity back to 20 if no more quantity left, or remove this if statement
                    } else {
                        dao.edit(selectedItem, quantityLeft);
                    }
                    quantityRequestedService = 0;
                    selectedItem = null;
                } catch (VendingMachineDaoException ex) {
                    throw new VendingMachineServiceException("Unable to Vend ID " + selectedItem.getId(), ex); //"Unable to edit item quantity"
                }
            }
        }
    }

    @Override
    public BigDecimal returnMoneyLeftInVendingMachine() {
        return change;
    }
    
    @Override
    public Change returnMoney() {
        Change returnedChange = new Change(change);
        return returnedChange;
    }

    @Override
    public void resetChange() {
        change = BigDecimal.ZERO;
    }

}
