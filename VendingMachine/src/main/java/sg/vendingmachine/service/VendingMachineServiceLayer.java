/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.service;

import java.math.BigDecimal;
import java.util.List;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dto.Change;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public interface VendingMachineServiceLayer { 
    
    public List<Item> getAll() throws VendingMachineServiceException;
    
    public int getMinId()throws VendingMachineServiceException; 
            
    public int getMaxId()throws VendingMachineServiceException;
    
    public BigDecimal getItemCost(int id, int quantityRequested) throws NoItemInventoryException, VendingMachineServiceException;
            
    public void insertMoney(BigDecimal userMoney) throws InsufficientFundsException, VendingMachineServiceException;
    
    public BigDecimal returnMoneyLeftInVendingMachine();
    
    public Change returnMoney();
    
    public void resetChange();

    public boolean checkId(int userChosenId) throws InvalidIdException, VendingMachineServiceException;
           
}
