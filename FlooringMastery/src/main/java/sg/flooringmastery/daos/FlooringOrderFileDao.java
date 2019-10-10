/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.flooringmastery.dtos.Order;

/**
 *
 * @author blee0
 */
public class FlooringOrderFileDao implements FlooringOrderDao {

    String directoryPath;
    String orderFilePath;

    public FlooringOrderFileDao(String path) {
        this.directoryPath = path;
    }

    @Override
    public List<Order> getAllOrderByDateDao(LocalDate date) throws FlooringDaoException {

        if (date == null) {
            throw new FlooringDaoException("Cannot locate null date.");
        }

        List<Order> allOrders = new ArrayList<>();

        orderFilePath = convertOrderDatePath(date);

        FileReader reader = null;
        try {

            reader = new FileReader(orderFilePath);
            Scanner scn = new Scanner(reader);
            scn.nextLine(); //skips header line

            while (scn.hasNextLine()) {
                String line = scn.nextLine();

                String[] cells = line.split(",");

                Order toAdd = new Order();
                toAdd.setOrderNumber(Integer.parseInt(cells[0]));
                toAdd.setDate(date);
                toAdd.setCustomerName(cells[1]);
                toAdd.setState(cells[2]);
                toAdd.setTaxRate(new BigDecimal(cells[3]));
                toAdd.setProductType(cells[4]);
                toAdd.setArea(new BigDecimal(cells[5]));
                toAdd.setCostPerSquareFoot(new BigDecimal(cells[6]));
                toAdd.setLaborCostPerSquareFoot(new BigDecimal(cells[7]));

                allOrders.add(toAdd);
            }

        } catch (FileNotFoundException ex) {
            //throw new FlooringDaoException("No file was found with the associated date: " + date, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new FlooringDaoException("Could not close file reader during getAllOrderByDateDao()", ex);
                }
            }
        }

        return allOrders;

    }

    @Override
    public void addOrder(Order orderToAdd) throws FlooringDaoException {
        //1. check if date test file already exists, if not, create new file in orders directory
        if (orderToAdd == null) {
            throw new FlooringDaoException("Cannot add null order.");
        }

        LocalDate date = orderToAdd.getDate();

        orderFilePath = convertOrderDatePath(date);

        //2. add order to orders text file 
        List<Order> allOrders = getAllOrderByDateDao(date);

        int newId = 0;

        for (Order toCheck : allOrders) {
            if (toCheck.getOrderNumber() > newId) {
                newId = toCheck.getOrderNumber();
            }
        }

        newId++;
        orderToAdd.setOrderNumber(newId);
        allOrders.add(orderToAdd);

        writeFile(allOrders);

    }

    @Override
    public void editOrder(Order editedOrder) throws FlooringDaoException {

        if (editedOrder == null) {
            throw new FlooringDaoException("Cannot edit null order");
        }

        LocalDate date = editedOrder.getDate();

        orderFilePath = convertOrderDatePath(date);

        List<Order> allOrders = getAllOrderByDateDao(date);

        int orderNumber = editedOrder.getOrderNumber();

        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new FlooringDaoException("Cannot find Order number " + orderNumber);
        }

        allOrders.remove(index);

        allOrders.add(editedOrder);

        writeFile(allOrders);

    }

    @Override
    public void removeOrder(Order orderToRemove) throws FlooringDaoException {

        if (orderToRemove == null) {
            throw new FlooringDaoException("Cannot remove null order");
        }

        LocalDate date = orderToRemove.getDate();

        orderFilePath = convertOrderDatePath(date);

        List<Order> allOrders = getAllOrderByDateDao(date);

        int orderNumber = orderToRemove.getOrderNumber();

        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new FlooringDaoException("Cannot find Order number " + orderNumber);
        }

        allOrders.remove(index);

        writeFile(allOrders);

    }

    private String convertOrderDatePath(LocalDate date) {

        String stringDatePath;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = date.format(formatter);
        String fileDateFormat = "orders_" + stringDate + ".txt";

        Path datePath = Paths.get(directoryPath, fileDateFormat);

        stringDatePath = datePath.toString();

        return stringDatePath;

    }

    private void writeFile(List<Order> allOrders) throws FlooringDaoException {

        FileWriter writer = null;
        try {
            writer = new FileWriter(orderFilePath);
            PrintWriter pw = new PrintWriter(writer);

            pw.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

            for (Order toWrite : allOrders) {
                String line = convertToLine(toWrite);
                pw.println(line);

            }
        } catch (IOException ex) {
            throw new FlooringDaoException("ERROR: could not write to " + orderFilePath, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    throw new FlooringDaoException("ERROR: could not close writer for " + orderFilePath, ex);

                }

            }
        }
    }

    private String convertToLine(Order toWrite) {

        String line
                = toWrite.getOrderNumber() + ","
                + toWrite.getCustomerName() + ","
                + toWrite.getState() + ","
                + toWrite.getTaxRate() + ","
                + toWrite.getProductType() + ","
                + toWrite.getArea() + ","
                + toWrite.getCostPerSquareFoot() + ","
                + toWrite.getLaborCostPerSquareFoot() + ","
                + toWrite.getMaterialCost() + ","
                + toWrite.getLaborCost() + ","
                + toWrite.getTax() + ","
                + toWrite.getTotal();

        return line;
    }

}
