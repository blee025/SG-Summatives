/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.services;

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
public interface SuperheroSightingsService {

    public List<Supe> getAllSupes();

    public List<Supe> getSupesByOrganization(int id);

    public List<Supe> getSupesByLocation(int id);

    public Supe getSupeById(int id);

    public Supe addSupe(Supe toAdd);

    public void updateSupe(Supe updatedSupe);

    public void deleteSupebyId(int id);

    public List<Power> getAllPowers();

    public Power getPowerById(int id);

    public Power addPower(Power toAdd) throws SuperheroSightingsServiceException;

    public void updatePower(Power updatedPower);

    public void deletePowerById(int id);

    public List<Organization> getAllOrganizations();

    public List<Organization> getOrganizationsBySupe(int id);

    public Organization getOrganizationById(int id);

    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsServiceException;

    public void updateOrganization(Organization updatedOrganization);

    public void deleteOrganziationById(int id);
    
    public List<Location> getAllLocations();
    
    public List<Location> getLocationsBySupe(int id);
    
    public Location getLocationById(int id);
    
    public Location addLocation(Location toAdd) throws SuperheroSightingsServiceException ;
    
    public void updateLocation(Location updatedLocation);
    
    public void deleteLocationById(int id);
    
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getSightingsByDate(LocalDate date);
    
    public Sighting getSightingById(int id);
    
    public Sighting addSighting(Sighting toAdd);
    
    public void updateSighting(Sighting updatedSighting);
    
    public void deleteSightingById(int id);
    
}
