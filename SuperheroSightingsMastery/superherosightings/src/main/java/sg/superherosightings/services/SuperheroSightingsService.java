/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.services;

import java.time.LocalDate;
import java.util.List;
import sg.superherosightings.daos.InvalidIdException;
import sg.superherosightings.models.Location;
import sg.superherosightings.models.Organization;
import sg.superherosightings.models.Power;
import sg.superherosightings.models.Sighting;
import sg.superherosightings.models.Supe;

/**
 *
 * @author blee0
 */
public interface SuperheroSightingsService {

    public List<Supe> getAllSupes();

    public List<Supe> getSupesByOrganization(int id);

    public List<Supe> getSupesByLocation(int id);

    public Supe getSupeById(int id) throws InvalidIdException;

    public Supe addSupe(Supe toAdd) throws SuperheroSightingsServiceException, InvalidIdException;

    public void updateSupe(Supe updatedSupe) throws InvalidIdException, SuperheroSightingsServiceException;

    public void deleteSupebyId(int id) throws InvalidIdException;

    public List<Power> getAllPowers();

    public Power getPowerById(int id) throws InvalidIdException;

    public Power addPower(Power toAdd) throws SuperheroSightingsServiceException;

    public void updatePower(Power updatedPower) throws InvalidIdException, SuperheroSightingsServiceException;

    public void deletePowerById(int id) throws InvalidIdException;

    public List<Organization> getAllOrganizations();

    public List<Organization> getOrganizationsBySupe(int id);

    public Organization getOrganizationById(int id) throws InvalidIdException;

    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsServiceException, InvalidIdException;

    public void updateOrganization(Organization updatedOrganization) throws SuperheroSightingsServiceException, InvalidIdException;

    public void deleteOrganizationById(int id) throws InvalidIdException;
    
    public List<Location> getAllLocations();
    
    public List<Location> getLocationsBySupe(int id);
    
    public Location getLocationById(int id) throws InvalidIdException;
    
    public Location addLocation(Location toAdd) throws SuperheroSightingsServiceException ;
    
    public void updateLocation(Location updatedLocation) throws InvalidIdException, SuperheroSightingsServiceException;
    
    public void deleteLocationById(int id) throws InvalidIdException;
    
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getSightingsByDate(LocalDate date);
    
    public Sighting getSightingById(int id) throws InvalidIdException;
    
    public Sighting addSighting(Sighting toAdd) throws SuperheroSightingsServiceException, InvalidIdException;
    
    public void updateSighting(Sighting updatedSighting) throws InvalidIdException, SuperheroSightingsServiceException;
    
    public void deleteSightingById(int id) throws InvalidIdException;
    
}
