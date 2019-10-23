/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.services;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sg.superherosightings.daos.SuperheroSightingsDao;
import sg.superherosightings.daos.SuperheroSightingsDaoException;
import sg.superherosightings.models.Location;
import sg.superherosightings.models.Organization;
import sg.superherosightings.models.Power;
import sg.superherosightings.models.Sighting;
import sg.superherosightings.models.Supe;

/**
 *
 * @author blee0
 */
@Repository
public class SuperheroSightingsServiceImpl implements SuperheroSightingsService {

    SuperheroSightingsDao dao;

    @Autowired
    public SuperheroSightingsServiceImpl(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Supe> getAllSupes() {

        List<Supe> toReturn = dao.getAllSupes();
        
        for (Supe supe : toReturn) {
            List<Organization> toAdd = dao.getOrganizationsBySupe(supe.getId());
            supe.setOrganizations(toAdd); 
        }

        return toReturn;
    }

    @Override
    public List<Supe> getSupesByOrganization(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Supe> getSupesByLocation(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Supe getSupeById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Supe addSupe(Supe toAdd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSupe(Supe updatedSupe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSupebyId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Power> getAllPowers() {

        List<Power> toReturn = dao.getAllPowers();

        return toReturn;
    }

    @Override
    public Power getPowerById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Power addPower(Power toAdd) throws SuperheroSightingsServiceException {
        Power toReturn = null;
        try {
            toReturn = dao.addPower(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Power", ex);
        }
        
        return toReturn;
    }

    @Override
    public void updatePower(Power updatedPower) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePowerById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Organization> getAllOrganizations() {

        List<Organization> toReturn = dao.getAllOrganizations();
        
        for (Organization organization : toReturn) {
            List<Supe> toAdd = dao.getSupesByOrganization(organization.getId());
            organization.setSupes(toAdd);
        }

        return toReturn;
    }

    @Override
    public List<Organization> getOrganizationsBySupe(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization getOrganizationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsServiceException {
        Organization toReturn = null;
        try {
            toReturn = dao.addOrganization(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Organization", ex);
        }
        
        return toReturn;
    }

    @Override
    public void updateOrganization(Organization updatedOrganization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteOrganziationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Location> getAllLocations() {

        List<Location> toReturn = dao.getAllLocations();

        return toReturn;
    }

    @Override
    public List<Location> getLocationsBySupe(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location getLocationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location addLocation(Location toAdd) throws SuperheroSightingsServiceException {
        Location toReturn = null;
        try {
            toReturn = dao.addLocation(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Location", ex);
        }
        
        return toReturn;
    }

    @Override
    public void updateLocation(Location updatedLocation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteLocationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getAllSightings() {

        List<Sighting> toReturn = dao.getAllSightings();

        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sighting getSightingById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sighting addSighting(Sighting toAdd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSighting(Sighting updatedSighting) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSightingById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
