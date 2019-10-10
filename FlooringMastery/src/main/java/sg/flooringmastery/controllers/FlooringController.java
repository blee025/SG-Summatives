/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.flooringmastery.dtos.Order;
import sg.flooringmastery.service.FlooringService;
import sg.flooringmastery.service.FlooringServiceException;
import sg.flooringmastery.service.InvalidOrderNumberException;
import sg.flooringmastery.service.InvalidProductException;
import sg.flooringmastery.service.InvalidStateException;
import sg.flooringmastery.ui.FlooringView;
import sg.flooringmastery.ui.MenuOption;

/**
 *
 * @author blee0
 */
public class FlooringController {

    FlooringView view;
    FlooringService service;

    public FlooringController(FlooringView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        while (keepGoing) {

            try {
                MenuOption menuSelection = view.displayMenuAndGetChoice();

                switch (menuSelection) {
                    case DisplayAllByDate:
                        displayAllByDate();
                        break;
                    case Add:
                        add();
                        break;
                    case Edit:
                        edit();
                        break;
                    case Remove:
                        remove();
                        break;
                    case Exit:
                        keepGoing = false;
                        break;
                    default:
                        view.displayUnknownCommandError();
                        break;
                }
            } catch (FlooringServiceException | InvalidProductException | InvalidStateException | InvalidOrderNumberException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }

        view.displayExitMessage();

    }

    private void displayAllByDate() throws FlooringServiceException {

        LocalDate date = view.getOrderDateToView();
        List<Order> allOrders = service.getAllOrdersByDate(date);
       
        view.displayAllOrders(allOrders);
    }

    private void add() throws InvalidProductException, InvalidStateException, FlooringServiceException {
        
        Order orderToAdd = view.getOrderInfo();
        String commit = view.getUserCommit();
        
        Order orderAdded = service.addOrder(orderToAdd, commit);

        view.printDetails(orderAdded);

    }

    private void edit() throws InvalidOrderNumberException, FlooringServiceException, InvalidProductException, InvalidStateException {

        Order requestedOrder = view.getOrderDateAndOrderNumber();
        Order actualOrder = service.getOrderFromDateFile(requestedOrder);
        Order editedOrder = view.displayOrderAndPromptNewData(actualOrder);

        service.checkAndEditOrder(editedOrder);

    }

    private void remove() throws InvalidOrderNumberException, FlooringServiceException {

        Order requestedOrder = view.getOrderDateAndOrderNumber();
        Order orderToRemove = service.getOrderFromDateFile(requestedOrder);
        
        view.printDetails(orderToRemove);
        String commit = view.getUserCommit();

        service.removeOrder(orderToRemove, commit);

    }

}
