/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public class FlooringViewProd implements FlooringView {

    UserIO io;
    LocalDate userRequestedDate;

    public FlooringViewProd(UserIO io) {
        this.io = io;
    }

    @Override
    public MenuOption displayMenuAndGetChoice() {
        MenuOption menuSelection;

        displayMenu();

        menuSelection = getMenuSelection();

        return menuSelection;
    }

    @Override
    public void displayUnknownCommandError() {
        io.print("Unknown Command!!! \n" );
    }

    @Override
    public void displayExitMessage() {
        io.print("Good Bye!!!");
    }

    private void displayMenu() {
        io.print( "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" );
        io.print( "* 1. Display Orders");
        io.print( "* 2. Add an Order");
        io.print( "* 3. Edit an Order");
        io.print( "* 4. Remove an Order");
        io.print( "* 5. Exit");
        io.print( "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" );

    }

    private MenuOption getMenuSelection() {
        MenuOption menuSelection = MenuOption.DisplayAllByDate;

        int userChoice = io.readInt("Please select your menu option: ", 1, 5);

        switch (userChoice) {
            case 1:
                menuSelection = MenuOption.DisplayAllByDate;
                break;
            case 2:
                menuSelection = MenuOption.Add;
                break;
            case 3:
                menuSelection = MenuOption.Edit;
                break;
            case 4:
                menuSelection = MenuOption.Remove;
                break;
            case 5:
                menuSelection = MenuOption.Exit;
                break;

        }

        return menuSelection;
    }

    @Override
    public LocalDate getOrderDateToView() {
        LocalDate date = io.readDate("Please enter date: ", LocalDate.of(1900, 1, 1), LocalDate.of(2020, 1, 1));
        return date;
    }

    @Override
    public void displayAllOrders(List<Order> allOrders) {
        if (allOrders.isEmpty()) {
            io.print("No orders found for selected date.");
        } else {
            for (Order toPrint : allOrders) {
                printDetails(toPrint);
            }
        }
    }

    @Override
    public void displayErrorMessage(String message) {
        io.print("=== ERROR ===");
        io.print(message + " \n");
    }

    @Override
    public Order getOrderInfo() {
        
        LocalDate date = io.readDate("Please enter requested date: ", LocalDate.now().minusDays(1), LocalDate.of(2020, 1, 1));

        String customerName = io.readString("Please enter your name: ");
        String state = io.readString("Please enter State abbreviation: ");
        String productType = io.readString("Please enter selected material type: ");
        BigDecimal area = io.readBigDecimal("Please enter your Area dimensions in total sqaure footage: ", BigDecimal.ZERO, new BigDecimal("1000.00"));

        Order orderToAdd = new Order();
        
        
        orderToAdd.setDate(date);
        orderToAdd.setCustomerName(customerName);
        orderToAdd.setState(state);
        orderToAdd.setProductType(productType);
        orderToAdd.setArea(area);

        return orderToAdd;
    }

    @Override
    public String getUserCommit() {
        String toBeFormatted = io.readString("Would you like to commit? (Yes/No)");
        String toReturn = toBeFormatted.toLowerCase();
        return toReturn;
    }

    @Override
    public void printDetails(Order toPrint) {
        //if statement for null order
        if (toPrint == null) {
            io.print("There is no order to view.");
        }
        
        if (toPrint.getOrderNumber() != 0) {
            io.print("ID: " + toPrint.getOrderNumber());
        }
        
        io.print("Name: " + toPrint.getCustomerName());
        io.print("State: " + toPrint.getState());
        io.print("Tax Rate: $" + toPrint.getTaxRate());
        io.print("Product Type: " + toPrint.getProductType());
        io.print("Area: " + toPrint.getArea());
        io.print("Cost per Squarefoot: $" + toPrint.getCostPerSquareFoot());
        io.print("Labor Cost per Squarefoot: $" + toPrint.getLaborCostPerSquareFoot() + " Sqft");
        io.print("Total Material Cost: $" + toPrint.getMaterialCost());
        io.print("Total Labor Cost: $" + toPrint.getLaborCost());
        io.print("Taxes: $" + toPrint.getTax());
        io.print("Total: $" + toPrint.getTotal() + "\n");
    }

    @Override
    public Order displayOrderAndPromptNewData(Order requestedOrder) {
        //Enter customer name (Wise):
        
        Order editedOrder = new Order();
        
        String name = requestedOrder.getCustomerName();
        String state = requestedOrder.getState();
        String material = requestedOrder.getProductType();
        BigDecimal area = requestedOrder.getArea();
        
        String nameEdited = io.readString("Enter customer name (" + name + "):");
        String stateEdited = io.readString("Enter state" + "(" + state + "):");
        String materialEdited = io.readString("Enter product type" + "(" + material + "):");
        BigDecimal areaEdited = io.readBigDecimal("Enter area in sqft (" + area + "):", BigDecimal.ZERO, new BigDecimal("1000.00"));
        
        editedOrder.setCustomerName(nameEdited);
        editedOrder.setState(stateEdited);
        editedOrder.setProductType(materialEdited);
        editedOrder.setArea(areaEdited);
        
        return editedOrder;
    }

    @Override
    public Order getOrderDateAndOrderNumber() {
        LocalDate requestedDate = io.readDate("Please enter date: ", LocalDate.of(1900, 1, 1), LocalDate.of(2020, 1, 1));
        int requestedOrderNumber = io.readInt("Please enter Order Number: ", 1, 100);
        
        Order requestedOrder = new Order();
        
        requestedOrder.setDate(requestedDate);
        requestedOrder.setOrderNumber(requestedOrderNumber);
        
        return requestedOrder;
    }

}
