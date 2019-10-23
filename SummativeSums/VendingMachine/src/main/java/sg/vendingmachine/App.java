/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sg.vendingmachine.controller.VendingMachineController;
import sg.vendingmachine.dao.VendingMachineDao;
import sg.vendingmachine.dao.VendingMachineDaoException;
import sg.vendingmachine.dao.VendingMachineDaoFile;
import sg.vendingmachine.service.InsufficientFundsException;
import sg.vendingmachine.service.InvalidIdException;
import sg.vendingmachine.service.NoItemInventoryException;
import sg.vendingmachine.service.VendingMachineServiceException;
import sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import sg.vendingmachine.service.VendingMachineServiceLayer;
import sg.vendingmachine.ui.UserIO;
import sg.vendingmachine.ui.UserIOConsoleImpl;
import sg.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author blee0
 */
public class App {
    public static void main(String[] args) {
//        UserIO myIO = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIO);
//        VendingMachineDao myDao = new VendingMachineDaoFile("item.txt"); //C:\\Users\\blee0\\OneDrive\\Desktop\\java-mlps-0819-blee025\\Summatives\\VendingMachine\\item.txt
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao);
//        VendingMachineController controller = new VendingMachineController(myService, myView);
//        controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
