/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.dvdlibrary.ui;

import java.util.List;
import sg.dvdlibrary.dto.Dvd;

/**
 *
 * @author blee0
 */
public class DvdLibraryView {

    private UserIO io; //= new UserIOConsoleImpl();

    public DvdLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List Dvds by IDs");
        io.print("2. Add New DVD to Library");
        io.print("3. Remove DVD from Library");
        io.print("4. Edit DVD from Library");
        io.print("5. Display the info or a DVD");
        io.print("6. Search for DVD by title");
        io.print("7. Exit");

        return io.readInt("Please select from the"
                + " above choices.", 1, 7);
    }

    
    public Dvd addNewDvdInfo() {
        //String id = io.readString("Please enter DVD ID");
        String title = io.readString("Please enter DVD title");
        int date = io.readInt("Please enter DVD year");
        String mpaaRating = io.readString("Please enter MPAA rating");
        String directorsName = io.readString("Please enter Director's name");
        String studio = io.readString("Please enter Studio name");
        String userRating = io.readString("Please enter user rating");
        Dvd currentDvd = new Dvd();
        //currentDvd.setId(id);
        currentDvd.setTitle(title);
        currentDvd.setDate(date);
        currentDvd.setMpaaRating(mpaaRating);
        currentDvd.setDirectorsName(directorsName);
        currentDvd.setStudio(studio);
        currentDvd.setUserRating(userRating);
        return currentDvd;
    }

    //could create new method here to getNewDvdInfo for adding a new dvd. Method above allows user to edit all. addDvd will increment from last known id
    
    public void displayDvdLibraryList(List<Dvd> dvdList) {
        for (Dvd currentDvd : dvdList) {
            io.print(currentDvd.getId() + ":"
                    + currentDvd.getTitle() + ":"
                    + currentDvd.getDate() + ":"
                    + currentDvd.getMpaaRating() + ":"
                    + currentDvd.getDirectorsName() + ":"
                    + currentDvd.getStudio() + ":"
                    + currentDvd.getUserRating());
        }
        io.readString("Please hit enter to continue.");
    }

    public int getDvdIdSelection() {
        return io.readInt("Please enter the DVD ID");
    }

    public void displayDvd(Dvd dvd) {
        if (dvd != null) {
            io.print(dvd.getId() + "");
            io.print(dvd.getTitle());
            io.print(dvd.getDate() + "");
            io.print(dvd.getMpaaRating());
            io.print(dvd.getDirectorsName());
            io.print(dvd.getStudio());
            io.print(dvd.getUserRating());
        } else {
            io.print("No DVD match found");
        }
        io.readString("Please hit enter to continue");
    }

    public String getDvdTitleSelection() {
        return io.readString("Please enter DVD name to search");
    }

    public void displayEditDvdBanner() {
        io.print("=== Edit DVD ===");
    }

    public void displayEditSuccessBanner() {
        io.readString("DVD successfully edited. Please hit enter to continue.");
    }

    public void displayRemoveDvdBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveSuccessBanner() {
        io.readString("DVD successfully removed. Please hit enter to continue.");
    }

    public void displayDisplayDvdBanner() {
        io.print("=== Display DVD ===");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
    }

    public void displayCreateDvdBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("DVD successfully created.  Please hit enter to continue");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
