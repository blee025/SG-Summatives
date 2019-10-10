/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.dvdlibrary;

import java.util.logging.Level;
import java.util.logging.Logger;
import sg.dvdlibrary.controller.DvdLibraryController;
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
public class App {
    public static void main(String[] args) {
        UserIO myIO = new UserIOConsoleImpl();
        DvdLibraryView myView = new DvdLibraryView(myIO);
        DvdLibraryDao myDao = new DvdLibraryDaoFileImpl(); //could insert file path here, if using path in DaoFileImpl
        DvdLibraryController controller = new DvdLibraryController(myDao, myView);
        controller.run();
    }   
}
