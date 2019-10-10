/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.ui;

import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public interface FlooringView {

    public MenuOption displayMenuAndGetChoice();

    public void displayUnknownCommandError();

    public void displayExitMessage();

    public void displayAllOrders(List<Order> allOrders);

    public LocalDate getOrderDateToView();

    public void displayErrorMessage(String message);

    public Order getOrderInfo();

    public String getUserCommit();

    public void printDetails(Order verifiedOrder);

    public Order displayOrderAndPromptNewData(Order requestedOrder);

    public Order getOrderDateAndOrderNumber();

    
}
