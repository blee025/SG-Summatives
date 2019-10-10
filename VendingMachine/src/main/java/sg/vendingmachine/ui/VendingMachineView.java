/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.List;
import sg.vendingmachine.dto.Change;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Select Item");
        io.print("2. Insert Money/Vend Item");
        io.print("3. Return Money");
        io.print("4. Exit");

        return io.readInt("Please select from the"
                + " above choices.", 1, 4);
    }

    public int getItemIdSelection() { //min and max id removed from here as test
        return io.readInt("Please enter the Item ID: "); //min and max id removed from here as test
    }

    public int getItemQuantity() {
        return io.readInt("Please enter the quantity of selected item: ");
    }

    public BigDecimal getUserMoney() {
        return io.readBigDecimal("Please insert money."); //new BigDecimal(".01"), new BigDecimal("100")
    }

    public void displayItemsList(List<Item> allItems) {
        for (Item currentItems : allItems) {
            io.print("ID: " + currentItems.getId() + " - "
                    + "Name: " + currentItems.getName() + " - "
                    + "Price: " + currentItems.getPrice() + " - "
                    + "Quantity: " + currentItems.getQuantity());
        }

    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!! \n" );
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg + " \n");
    }

    public void displayReenter() {
        io.print("Please enter more money to vend. \n");
    }

    public void displayRequestItemSelection() {
        io.print("Please select an item to vend first. \n");
    }

    public void displayZeroReturn() {
        io.print("There is no money to return. \n"); //Please select an item to vend first then insert an amount of money higher than required cost.
    }

    public void printCost(int itemId, BigDecimal requiredCost) {
        io.print("Total cost for item " + itemId + " is $" + requiredCost + " \n");
    }

    public void printSuccessfulVend() {
        io.print("Item(s) successfully vended. \n");
    }

    public void printMoneyLeft(BigDecimal excpectedChange) {
        io.print("Vending Machine has $" + excpectedChange + " left. \n");
    }

    public void printChange(Change returnedchange) {
        //Change sortedChange = new Change(change);

        int dollars = returnedchange.getDollars();
        int quarters = returnedchange.getQuarters();
        int dimes = returnedchange.getDimes();
        int nickels = returnedchange.getNickles();
        int pennies = returnedchange.getPennies();

        io.print("Dollars: " + dollars);
        io.print("Quarters: " + quarters);
        io.print("Dimes: " + dimes);
        io.print("nickels: " + nickels);
        io.print("pennies: " + pennies);

    }

}
