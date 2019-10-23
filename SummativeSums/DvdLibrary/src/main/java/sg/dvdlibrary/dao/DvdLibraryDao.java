/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.dvdlibrary.dao;

import java.util.List;
import sg.dvdlibrary.dto.Dvd;

/**
 *
 * @author blee0
 */
public interface DvdLibraryDao {
    List<Dvd> getAll() throws DvdLibraryDaoException;
    Dvd getById(int id) throws DvdLibraryDaoException;
    Dvd addDvd(Dvd toAdd) throws DvdLibraryDaoException;
    void removeById(int id) throws DvdLibraryDaoException;
    void editDvd(int oldId, Dvd toEdit) throws DvdLibraryDaoException;
    Dvd getByTitle(String title) throws DvdLibraryDaoException;

}
