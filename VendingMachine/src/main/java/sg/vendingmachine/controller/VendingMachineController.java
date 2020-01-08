/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.controller;

import java.math.BigDecimal;
import java.util.List;
import sg.vendingmachine.dao.VendingMachineDao;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dto.Item;
import sg.vendingmachine.service.InsufficientFundsException;
import sg.vendingmachine.service.InvalidIdException;
import sg.vendingmachine.service.NoItemInventoryException;
import sg.vendingmachine.service.VendingMachineServiceException;
import sg.vendingmachine.service.VendingMachineServiceLayer;
import sg.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author blee0
 */
public class VendingMachineController {

    VendingMachineView view;
    VendingMachineServiceLayer service;


    public VendingMachineController(VendingMachineServiceLayer myService, VendingMachineView myView) {
        this.view = myView;
        this.service = myService;
    }

    public void run() {

        boolean keepGoing = true;

        while (keepGoing) {

            getAllItems();

            int menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    getItemCost();
                    break;
                case 2:
                    insertMoney();
                    break;
                case 3:
                    returnMoney();
                    break;
                case 4:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();

    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();

    }

    private void getAllItems() {

        try {
            List<Item> allItems = service.getAll();
            view.displayItemsList(allItems);
        } catch (VendingMachineServiceException ex) {
            view.displayErrorMessage(ex.getMessage());
        }

    }

    private void getItemCost() {

        boolean valid = false;
        while (!valid) {
            int userChosenId = view.getItemIdSelection();
            try {
                valid = service.checkId(userChosenId); //keep on looping if ID is not valid

                int itemQuantity = view.getItemQuantity();

                BigDecimal requiredCost = service.getItemCost(userChosenId, itemQuantity);
                view.printCost(userChosenId, requiredCost);

            } catch (NoItemInventoryException | InvalidIdException | VendingMachineServiceException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

    private void insertMoney() {
            BigDecimal userMoney = BigDecimal.ZERO;   
            try {
                userMoney = view.getUserMoney();
                service.insertMoney(userMoney);
                //view.printSuccessfulVend(2); //print to user that Item ID has been vended //userChosenId
            } catch (InsufficientFundsException | VendingMachineServiceException ex) {
                view.displayErrorMessage(ex.getMessage());
                view.displayReenter();
            }
            BigDecimal expectedChange = service.returnMoneyLeftInVendingMachine();
            if (userMoney.compareTo(expectedChange) > 0) { //if statement: if items vended, successful message printed
            view.printSuccessfulVend();
            }
           
            view.printMoneyLeft(expectedChange);
            
            
        }

    private void returnMoney() {
        view.printChange(service.returnMoney());
        service.resetChange();
    }

}

//vending machine controller needs to getallitems, and dispense items successfully 
