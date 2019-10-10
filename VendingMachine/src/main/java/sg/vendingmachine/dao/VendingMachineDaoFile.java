/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.vendingmachine.dto.Item;

/**
 *
 * @author blee0
 */
public class VendingMachineDaoFile implements VendingMachineDao {

    String path;

    public VendingMachineDaoFile(String path) {
        this.path = path;
    }

    @Override
    public List<Item> getAll() throws VendingMachineDaoException {
        List<Item> allItems = new ArrayList<>();

        FileReader reader = null;
        try {
            reader = new FileReader(path);
            Scanner scn = new Scanner(reader);

            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                if (line.length() > 0) {
                    String[] cells = line.split("::");

                    Item toAdd = new Item();
                    toAdd.setId(Integer.parseInt(cells[0]));
                    toAdd.setName(cells[1]);
                    toAdd.setPrice(new BigDecimal(cells[2]));
                    toAdd.setQuantity(Integer.parseInt(cells[3]));

                    allItems.add(toAdd);

                }
            }

        } catch (FileNotFoundException ex) {
            //throw new VendingMachineDaoException("Could not locate file location: " + path, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw new VendingMachineDaoException("Could not close reader for: " + path, ex);
            }
        }

        return allItems;
    }

    @Override
    public Item getById(int id) throws VendingMachineDaoException {

        Item selectedItem = null;

        List<Item> allItems = getAll();

        for (Item toCheck : allItems) {
            if (toCheck.getId() == id) {
                selectedItem = toCheck;
            }
        }
        
        if (selectedItem == null) {
            throw new VendingMachineDaoException("Unable to get Item by ID " + id);
        }

        return selectedItem;

    }

    
    @Override
    public void edit(Item selectedItem, int quantityLeft) throws VendingMachineDaoException {
        if (selectedItem == null) {
            throw new VendingMachineDaoException("Cannot edit null item");
        }
        
        List<Item> allItems = getAll();

        int index = -1;

        for (int i = 0; i < allItems.size(); i++) {
            Item toCheck = allItems.get(i);

            if (toCheck.getId() == selectedItem.getId()) {
                index = i;
                break;
            }
        }
        
        
        Item editedItem = allItems.get(index);
        editedItem.setQuantity(quantityLeft);
        

        writeFile(allItems);
    }

    private void writeFile(List<Item> allItems) throws VendingMachineDaoException {

        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            PrintWriter pw = new PrintWriter(writer);

            for (Item toWrite : allItems) {
                String line = convertToLine(toWrite);
                pw.println(line);
            }
        } catch (IOException ex) {
            throw new VendingMachineDaoException("ERROR: could not write to " + path, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                throw new VendingMachineDaoException("ERROR: could not close writer for " + path, ex);
            }
        }
    }

    private String convertToLine(Item toWrite) {

        String line
                = toWrite.getId() + "::"
                + toWrite.getName() + "::"
                + toWrite.getPrice() + "::"
                + toWrite.getQuantity();

        return line;
    }

    

}
