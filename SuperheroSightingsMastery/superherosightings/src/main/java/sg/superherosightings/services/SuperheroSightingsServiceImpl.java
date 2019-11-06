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
import sg.superherosightings.daos.InvalidIdException;
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
    public Supe getSupeById(int id) throws InvalidIdException {

        Supe toReturn = dao.getSupeById(id);

        List<Organization> toAdd = dao.getOrganizationsBySupe(id);

        toReturn.setOrganizations(toAdd);

        return toReturn;
    }

    @Override
    public Supe addSupe(Supe toAdd) throws SuperheroSightingsServiceException, InvalidIdException {
        Supe toReturn = null;
        try {
            toReturn = dao.addSupe(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Super", ex);
        }

        int newSupeId = toReturn.getId();
        List<Organization> allOrganizations = toAdd.getOrganizations();

        for (Organization org : allOrganizations) {
            dao.addSupeOrganization(org.getId(), newSupeId);
        }

        return toReturn;
    }

    @Override
    public void updateSupe(Supe updatedSupe) throws InvalidIdException, SuperheroSightingsServiceException {

        try {
            dao.updateSupe(updatedSupe);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to update Super", ex);
        }

        dao.deleteSupeOrganizationBySupeId(updatedSupe.getId());

        List<Organization> allOrgs = updatedSupe.getOrganizations();

        for (Organization org : allOrgs) {
            dao.addSupeOrganization(org.getId(), updatedSupe.getId());
        }
    }

    @Override
    public void deleteSupebyId(int id) throws InvalidIdException {
        dao.deleteSupeById(id);
    }

    @Override
    public List<Power> getAllPowers() {

        List<Power> toReturn = dao.getAllPowers();

        return toReturn;
    }

    @Override
    public Power getPowerById(int id) throws InvalidIdException {

        Power toReturn = dao.getPowerById(id);

        return toReturn;
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
    public void updatePower(Power updatedPower) throws InvalidIdException, SuperheroSightingsServiceException {
        try {
            dao.updatePower(updatedPower);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to update Power", ex);
        }
    }

    @Override
    public void deletePowerById(int id) throws InvalidIdException {
        dao.deletePowerById(id);
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
    public Organization getOrganizationById(int id) throws InvalidIdException {

        Organization toReturn = dao.getOrganizationById(id);

        List<Supe> supes = dao.getSupesByOrganization(id);

        toReturn.setSupes(supes);

        return toReturn;
    }

    @Override
    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsServiceException, InvalidIdException {
        Organization toReturn = null;
        try {
            toReturn = dao.addOrganization(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Organization", ex);
        }

        int newOrgId = toReturn.getId();
        List<Supe> allSupes = toAdd.getSupes();

        for (Supe superHero : allSupes) {
            dao.addSupeOrganization(newOrgId, superHero.getId());
        }

        return toReturn;
    }

    @Override
    public void updateOrganization(Organization updatedOrganization) throws SuperheroSightingsServiceException, InvalidIdException {

        try {
            dao.updateOrganization(updatedOrganization);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to update Organization", ex);
        }

        dao.deleteSupeOrganizationByOrganizationId(updatedOrganization.getId());

        List<Supe> allSupes = updatedOrganization.getSupes();

        for (Supe supe : allSupes) {
            dao.addSupeOrganization(updatedOrganization.getId(), supe.getId());
        }

    }

    @Override
    public void deleteOrganizationById(int id) throws InvalidIdException {
        dao.deleteOrganizationById(id);
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
    public Location getLocationById(int id) throws InvalidIdException {
        Location toReturn = dao.getLocationById(id);

        return toReturn;
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
    public void updateLocation(Location updatedLocation) throws InvalidIdException, SuperheroSightingsServiceException {

        try {
            dao.updateLocation(updatedLocation);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to update Location", ex);
        }
    }

    @Override
    public void deleteLocationById(int id) throws InvalidIdException {
        dao.deleteLocationById(id);
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
    public Sighting getSightingById(int id) throws InvalidIdException {

        Sighting toReturn = dao.getSightingById(id);

        return toReturn;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SuperheroSightingsServiceException, InvalidIdException {
        Sighting toReturn = null;

        try {
            toReturn = dao.addSighting(toAdd);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to add Sighting", ex);
        }

        return toReturn;
    }

    @Override
    public void updateSighting(Sighting updatedSighting) throws InvalidIdException, SuperheroSightingsServiceException {
        try {
            dao.updateSighting(updatedSighting);
        } catch (SuperheroSightingsDaoException ex) {
            throw new SuperheroSightingsServiceException("Unable to update Sighting", ex);
        }
    }

    @Override
    public void deleteSightingById(int id) throws InvalidIdException {
        dao.deleteSightingById(id);
    }

}
