/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.daos;

import java.time.LocalDate;
import java.util.List;
import sg.superherosightings.models.Location;
import sg.superherosightings.models.Organization;
import sg.superherosightings.models.Power;
import sg.superherosightings.models.Sighting;
import sg.superherosightings.models.Supe;

/**
 *
 * @author blee0
 */
public interface SuperheroSightingsDao {

    public List<Supe> getSupesByLocation(int id);

    public List<Location> getLocationsBySupe(int id);

    public List<Sighting> getSightingsByDate(LocalDate date);

    public List<Supe> getSupesByOrganization(int id);

    public List<Organization> getOrganizationsBySupe(int id);

    public List<Power> getAllPowers();

    public Power getPowerById(int id) throws InvalidIdException;

    public Power addPower(Power toAdd) throws SuperheroSightingsDaoException;

    public void updatePower(Power updatedPower) throws InvalidIdException, SuperheroSightingsDaoException;

    public void deletePowerById(int id) throws InvalidIdException;

    public List<Supe> getAllSupes();

    public Supe getSupeById(int id) throws InvalidIdException;

    public Supe addSupe(Supe toAdd) throws SuperheroSightingsDaoException ;

    public void updateSupe(Supe updatedSup) throws InvalidIdException, SuperheroSightingsDaoException;

    public void deleteSupeById(int id) throws InvalidIdException;

    public List<Organization> getAllOrganizations();

    public Organization getOrganizationById(int id) throws InvalidIdException;

    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsDaoException;

    public void updateOrganization(Organization updatedOrganization) throws InvalidIdException, SuperheroSightingsDaoException;

    public void deleteOrganizationById(int id) throws InvalidIdException;
    
    public void addSupeOrganization(int organizationId, int supeId) throws InvalidIdException;
    
    public void deleteSupeOrganizationBySupeId(int supeId) throws InvalidIdException; 
    
    public void deleteSupeOrganizationByOrganizationId(int organizationId) throws InvalidIdException; 

    public List<Location> getAllLocations();

    public Location getLocationById(int id) throws InvalidIdException;

    public Location addLocation(Location toAdd) throws SuperheroSightingsDaoException;

    public void updateLocation(Location updatedLocation) throws InvalidIdException, SuperheroSightingsDaoException;

    public void deleteLocationById(int id) throws InvalidIdException;

    public List<Sighting> getAllSightings();

    public Sighting getSightingById(int id) throws InvalidIdException;

    public Sighting addSighting(Sighting toAdd) throws SuperheroSightingsDaoException, InvalidIdException ;

    public void updateSighting(Sighting updatedSighting) throws InvalidIdException, SuperheroSightingsDaoException;

    public void deleteSightingById(int id) throws InvalidIdException;

    public void deleteAll();

}

