/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.dvdlibrary.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.dvdlibrary.dao.DvdLibraryDao;
import sg.dvdlibrary.dao.DvdLibraryDaoException;
import sg.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import sg.dvdlibrary.dto.Dvd;
import sg.dvdlibrary.ui.DvdLibraryView;
import sg.dvdlibrary.ui.UserIO;
import sg.dvdlibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author blee0
 */
public class DvdLibraryController {

    DvdLibraryView view; //= new DvdLibraryView();
    DvdLibraryDao dao; //= new DvdLibraryDaoFileImpl("C:\\Users\\blee0\\OneDrive\\Desktop\\java-mlps-0819-blee025\\Summatives\\DvdLibrary\\dvdlibrary.txt"); //put path in arguement
    //private UserIO io = new UserIOConsoleImpl();

    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listDvds();
                        break;
                    case 2:
                        createDvd();
                        break;
                    case 3:
                        removeDvd();
                        break;
                    case 4:
                        editDvd();
                        break;
                    case 5:
                        viewDvd();
                        break;
                    case 6:
                        searchDvd();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (DvdLibraryDaoException ex) {
            view.displayErrorMessage(ex.getMessage());
        }

    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void createDvd() throws DvdLibraryDaoException {
        view.displayCreateDvdBanner();
        Dvd newDvd = view.addNewDvdInfo();
        try {
            dao.addDvd(newDvd);
        } catch (DvdLibraryDaoException ex) {
           throw new DvdLibraryDaoException("Failed to add Dvd");
        }
        view.displayCreateSuccessBanner();
    }

    private void listDvds() throws DvdLibraryDaoException {
        try {
            view.displayDisplayAllBanner();
            List<Dvd> dvdList = dao.getAll();
            view.displayDvdLibraryList(dvdList);
        } catch (DvdLibraryDaoException ex) {
            throw new DvdLibraryDaoException("Could not display Dvd list");
        }
    }

    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        int dvdId = view.getDvdIdSelection();
        Dvd dvd = null;
        try {
            dvd = dao.getById(dvdId);
        } catch (DvdLibraryDaoException ex) {
            throw new DvdLibraryDaoException("Could not find DVD ID");
        }
        view.displayDvd(dvd);
    }

    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        int dvdId = view.getDvdIdSelection();
        try {
            dao.removeById(dvdId);
        } catch (DvdLibraryDaoException ex) {
            throw new DvdLibraryDaoException("Unable to remove DVD");
        }
        view.displayRemoveSuccessBanner();
    }

    private void searchDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String dvdName = view.getDvdTitleSelection();
        Dvd dvd = null;
        try {
            dvd = dao.getByTitle(dvdName);
        } catch (DvdLibraryDaoException ex) {
            throw new DvdLibraryDaoException("Could not locate DVD title");
        }
        view.displayDvd(dvd);
    }

    private void editDvd() throws DvdLibraryDaoException {
        view.displayEditDvdBanner();
        int dvdId = view.getDvdIdSelection();
        Dvd editedDvd = view.addNewDvdInfo();
        try {
            dao.editDvd(dvdId, editedDvd);
        } catch (DvdLibraryDaoException ex) {
            throw new DvdLibraryDaoException("Unable to edit DVD");
        }
        view.displayEditSuccessBanner();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
