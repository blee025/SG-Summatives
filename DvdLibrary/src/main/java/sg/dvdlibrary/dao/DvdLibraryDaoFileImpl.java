/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.dvdlibrary.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sg.dvdlibrary.dto.Dvd;

/**
 *
 * @author blee0
 */
public class DvdLibraryDaoFileImpl implements DvdLibraryDao {

    public static final String LIBRARY_FILE = "dvdlibray.txt";
    //public static final String DELIMITER = "::";
    
//instead of passing in string path to dvdlibrary.txt, Constant created above ^    String path;
//
//    public DvdLibraryDaoFileImpl(String path) {
//
//        this.path = path;
//    }

    public DvdLibraryDaoFileImpl() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Dvd> getAll() throws DvdLibraryDaoException {

        List<Dvd> toReturn = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(LIBRARY_FILE);
            Scanner scn = new Scanner(reader);

            while (scn.hasNextLine()) {
                String line = scn.nextLine();
                if (line.length() > 0) {
                    String[] cells = line.split("::");

                    Dvd toAdd = new Dvd();
                    toAdd.setId(Integer.parseInt(cells[0])); //Integer.parseInt
                    toAdd.setTitle(cells[1]);
                    toAdd.setDate(Integer.parseInt(cells[2])); 
                    toAdd.setMpaaRating(cells[3]);
                    toAdd.setDirectorsName(cells[4]);
                    toAdd.setStudio(cells[5]);
                    toAdd.setUserRating(cells[6]);

                    toReturn.add(toAdd);

                }
            }

        } catch (FileNotFoundException ex) {

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw new DvdLibraryDaoException("Could not close reader for " + LIBRARY_FILE, ex);
            }
        }

        return toReturn;
    }

    @Override
    public Dvd getById(int id) throws DvdLibraryDaoException {

        Dvd toReturn = null;

        List<Dvd> allDvds = getAll();

        for (Dvd toCheck : allDvds) {
            if (toCheck.getId() == id) {
                toReturn = toCheck;
            }
        }

        return toReturn;
    }

    @Override
    public Dvd addDvd(Dvd toAdd) throws DvdLibraryDaoException {
        List<Dvd> allDvds = getAll();

        int newId = 0;

        for (Dvd toCheck : allDvds) {
            if (toCheck.getId() > newId) {
                newId = toCheck.getId();
            }
        }

        newId++;
        toAdd.setId(newId);

        allDvds.add(toAdd);

        writeFile(allDvds);

        return toAdd;
    }

    @Override
    public void removeById(int id) throws DvdLibraryDaoException {

        List<Dvd> allDvds = getAll();

        int index = -1;

        for (int i = 0; i < allDvds.size(); i++) {
            Dvd toCheck = allDvds.get(i);

            if (toCheck.getId() == id) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new DvdLibraryDaoException("Error: Dvd could could not be removed with id " + id); //have to add throws exception to interface dao and in method above: public void removeById(String id) throws DvdLibraryDaoException
        }

        allDvds.remove(index);

        writeFile(allDvds);

    }

    @Override
    public void editDvd(int oldId, Dvd edited) throws DvdLibraryDaoException {

        List<Dvd> allDvds = getAll();

        int index = -1;

        for (int i = 0; i < allDvds.size(); i++) {
            Dvd toCheck = allDvds.get(i);

            if (toCheck.getId() == oldId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new DvdLibraryDaoException("Error: Dvd could could not be edited with id " + oldId);
        }

        allDvds.remove(index);
        
        edited.setId(oldId); //set editedDVD to oldId
        allDvds.add(edited);
        
        writeFile(allDvds);

    }

    @Override
    public Dvd getByTitle(String title) throws DvdLibraryDaoException {
        Dvd toReturn = null;

        List<Dvd> allDvds = getAll();

        for (Dvd toCheck : allDvds) {
            if (toCheck.getTitle().equals(title)) { //could add.toLowerCase()
                toReturn = toCheck;
            }
        }

        return toReturn;
    }

    private void writeFile(List<Dvd> allDvds) throws DvdLibraryDaoException {

        FileWriter writer = null;
        try {
            writer = new FileWriter(LIBRARY_FILE);
            PrintWriter pw = new PrintWriter(writer);

            for (Dvd toWrite : allDvds) {
                String line = convertToLine(toWrite);
                pw.println(line);
            }
        } catch (IOException ex) {
            throw new DvdLibraryDaoException("ERROR: could not write to " + LIBRARY_FILE, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                throw new DvdLibraryDaoException("ERROR: could not close writer for " + LIBRARY_FILE, ex);
            }
        }
    }

    private String convertToLine(Dvd toWrite) {

        String line
                = toWrite.getId() + "::"
                + toWrite.getTitle() + "::"
                + toWrite.getDate() + "::"
                + toWrite.getMpaaRating() + "::"
                + toWrite.getDirectorsName() + "::"
                + toWrite.getStudio() + "::"
                + toWrite.getUserRating();

        return line;
    }

}
