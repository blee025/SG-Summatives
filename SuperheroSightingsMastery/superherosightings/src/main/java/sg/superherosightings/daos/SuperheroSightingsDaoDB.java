/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
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
@Profile({"production", "test"})
public class SuperheroSightingsDaoDB implements SuperheroSightingsDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Supe> getSupesByLocation(int id) {

        String query = "Select Su.Id SupeId, Su.`Name` SupeName, Su.`Description`, Su.PowerId, P.`Name` PowerName\n"
                + "From Supes Su\n"
                + "Inner Join Powers P on Su.PowerId = P.Id\n"
                + "Inner Join Sightings Si On Su.Id = Si.SupeId\n"
                + "Inner Join Locations L On Si.LocationId = L.Id\n"
                + "Where L.Id = ?";

        List<Supe> toReturn = template.query(query, new SupeMapper(), id);

        return toReturn;
    }

    @Override
    public List<Location> getLocationsBySupe(int id) {

        String query = "Select L.* \n"
                + "From Locations L \n"
                + "Inner Join Sightings Si On L.Id = Si.LocationId\n"
                + "Inner Join Supes Su On Si.SupeId = Su.Id \n"
                + "Where Su.Id = ?";

        List<Location> toReturn = template.query(query, new LocationMapper(), id);

        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {

        //check for date?
        String query = "Select Si.Id, Si.`date`, Si.LocationId, Si.SupeId, L.`Name` LocationName, L.Address, L.Latitude, L.Longitude, Su.`Name` SupeName, Su.`Description` SupeDescription, Su.PowerId, P.`Name` PowerName\n"
                + "From Sightings Si\n"
                + "Inner Join Locations L On Si.LocationId = L.Id\n"
                + "Inner Join Supes Su On Si.SupeId = Su.Id\n"
                + "Inner Join Powers P on Su.PowerId = P.Id\n"
                + "Where `Date` = ?";

        List<Sighting> toReturn = template.query(query, new SightingMapper(), java.sql.Date.valueOf(date));

        return toReturn;
    }

    @Override
    public List<Supe> getSupesByOrganization(int id) {

        //Organization toCheck = getOrganizationById(id);
        String query = "Select Su.Id SupeId, Su.`Name` SupeName, Su.`Description`, Su.PowerId, P.`Name` PowerName\n"
                + "From Supes Su \n"
                + "Inner Join Powers P On Su.PowerId = P.Id\n"
                + "Inner Join SuperOrganizations So On Su.Id = So.SupeId\n"
                + "Inner Join Organizations O On So.OrganizationId = O.Id\n"
                + "Where O.Id = ?";

        List<Supe> toReturn = template.query(query, new SupeMapper(), id);

        return toReturn;
    }

    @Override
    public List<Organization> getOrganizationsBySupe(int id) {

        String query = "Select O.*\n"
                + "From Organizations O\n"
                + "Inner Join SuperOrganizations So On O.Id = So.OrganizationId \n"
                + "Inner Join Supes Su On So.SupeId = Su.Id\n"
                + "Where Su.Id = ?";

        List<Organization> toReturn = template.query(query, new OrganizationMapper(), id);

        return toReturn;
    }

    @Override
    public List<Power> getAllPowers() {
        String query = "Select *\n"
                + "From Powers ";

        List<Power> toReturn = template.query(query, new PowerMapper());

        return toReturn;
    }

    @Override
    public Power getPowerById(int id) throws InvalidIdException {
        String query = "Select *\n"
                + "From Powers \n"
                + "Where Id = ?";

        Power toReturn = null;
        try {
            toReturn = template.queryForObject(query, new PowerMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Power Id.");
        }

        return toReturn;

    }

    @Override
    public Power addPower(Power toAdd) throws SuperheroSightingsDaoException {

        if (toAdd == null) {
            throw new SuperheroSightingsDaoException("Power to add is null.");
        }

        String insert = "Insert into Powers(`Name`) Values (?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newPowerId = holder.getKey().intValue();

        toAdd.setId(newPowerId);

        return toAdd;

    }

    @Override
    public void updatePower(Power updatedPower) throws InvalidIdException, SuperheroSightingsDaoException {

        if (updatedPower == null) {
            throw new SuperheroSightingsDaoException("Power to update is null.");
        }

        String updateStatement = "Update Powers \n"
                + "Set Name = ?\n"
                + "Where id = ?";

        int rowsAffected = template.update(updateStatement, updatedPower.getName(), updatedPower.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Power with id = " + updatedPower.getId());
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: PowerId IS NOT UNIQUE FOR Powers TABLE.");
        }
    }

    @Override
    public void deletePowerById(int id) throws InvalidIdException {

        try {
            Power toCheck = getPowerById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Power Id requested.");
        }

        String deleteSupeOrgs = "Delete So.*\n"
                + "From SuperOrganizations So\n"
                + "Inner Join Supes Su On So.SupeId = Su.Id\n"
                + "Inner Join Powers P On Su.PowerId = P.Id\n"
                + "Where P.Id = ?";

        String deleteSupeSightings = "Delete Si.*\n"
                + "From Sightings Si \n"
                + "Inner Join Supes Su On Si.SupeId = Su.Id\n"
                + "Inner Join Powers P On Su.PowerId = P.Id\n"
                + "Where P.Id = ?";

        String deleteSupes = "Delete \n"
                + "From Supes \n"
                + "Where PowerId = ?";

        String deletePower = "Delete \n"
                + "From Powers \n"
                + "Where Id = ?";

        template.update(deleteSupeOrgs, id);
        template.update(deleteSupeSightings, id);
        template.update(deleteSupes, id);
        template.update(deletePower, id);

    }

    @Override
    public List<Supe> getAllSupes() {
        String query = "Select S.Id SupeId, S.`Name` SupeName, S.`Description`, S.PowerId, P.`Name` PowerName \n"
                + "From Supes S \n"
                + "Inner Join Powers P On S.Powerid = P.Id";

        List<Supe> toReturn = template.query(query, new SupeMapper());

        return toReturn;
    }

    @Override
    public Supe getSupeById(int id) throws InvalidIdException {
        String query = "Select S.Id SupeId, S.`Name` SupeName, S.`Description`, S.PowerId, P.`Name` PowerName \n"
                + "From Supes S \n"
                + "Inner Join Powers P On S.Powerid = P.Id\n"
                + "Where S.Id = ?";

        Supe toReturn = null;
        try {
            toReturn = template.queryForObject(query, new SupeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid SuperHero Id.");
        }

        return toReturn;

    }

    @Override
    public Supe addSupe(Supe toAdd) throws SuperheroSightingsDaoException {

        if (toAdd == null || toAdd.getPower() == null) {
            throw new SuperheroSightingsDaoException("SuperHero to Add is null.");
        }

        try {
            Power toCheck = getPowerById(toAdd.getPower().getId());
        } catch (InvalidIdException ex) {
            throw new SuperheroSightingsDaoException("Power not found", ex);
        }

        String insert = "Insert into Supes(`Name`, `Description`, PowerId)\n"
                + "Values \n"
                + "(?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());
                toReturn.setString(2, toAdd.getDescription());
                toReturn.setInt(3, toAdd.getPower().getId());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newSupeId = holder.getKey().intValue();

        toAdd.setId(newSupeId);

        return toAdd;

    }

    @Override
    public void updateSupe(Supe updatedSupe) throws InvalidIdException, SuperheroSightingsDaoException {

        if (updatedSupe == null || updatedSupe.getPower() == null) {
            throw new SuperheroSightingsDaoException("SuperHero to Add is null.");
        }

        try {
            Power toCheck = getPowerById(updatedSupe.getPower().getId());
        } catch (InvalidIdException ex) {
            throw new SuperheroSightingsDaoException("Power not found", ex);
        }

        String updateStatement = "Update Supes \n"
                + "Set `Name` = ?,\n"
                + "	Description = ?,\n"
                + "    PowerId = ?\n"
                + "Where Id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedSupe.getName(),
                updatedSupe.getDescription(),
                updatedSupe.getPower().getId(),
                updatedSupe.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Supe with id = " + updatedSupe.getId());
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: SupeId IS NOT UNIQUE FOR Powers TABLE.");
        }
    }

    @Override
    public void deleteSupeById(int id) throws InvalidIdException {

        try {
            Supe toCheck = getSupeById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Supe Id requested.");
        }

        String deleteSupeOrgs = "Delete \n"
                + "From SuperOrganizations\n"
                + "Where SupeId = ?";

        String deleteSightings = "Delete \n"
                + "From Sightings\n"
                + "Where SupeId = ?";

        String deleteSupe = "Delete \n"
                + "From Supes \n"
                + "Where Id = ?";

        template.update(deleteSupeOrgs, id);
        template.update(deleteSightings, id);
        template.update(deleteSupe, id);
    }

    @Override
    public List<Organization> getAllOrganizations() {

        String query = "Select * \n"
                + "From Organizations";

        List<Organization> toReturn = template.query(query, new OrganizationMapper());

        return toReturn;
    }

    @Override
    public Organization getOrganizationById(int id) throws InvalidIdException {

        String query = "Select * \n"
                + "From Organizations\n"
                + "Where Id = ?";

        Organization toReturn = null;
        try {
            toReturn = template.queryForObject(query, new OrganizationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Organization Id.");
        }

        return toReturn;
    }

    @Override
    public Organization addOrganization(Organization toAdd) throws SuperheroSightingsDaoException {

        if (toAdd == null) {
            throw new SuperheroSightingsDaoException("Organization to add is null.");
        }

        String insert = "Insert into Organizations(`Name`, `Description`, Address)\n"
                + "Values \n"
                + "(?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());
                toReturn.setString(2, toAdd.getDescription());
                toReturn.setString(3, toAdd.getAddress());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newOrganizationId = holder.getKey().intValue();

        toAdd.setId(newOrganizationId);

        return toAdd;

    }

    @Override
    public void updateOrganization(Organization updatedOrganization) throws InvalidIdException, SuperheroSightingsDaoException {

        if (updatedOrganization == null) {
            throw new SuperheroSightingsDaoException("Organization to update is null.");
        }

        String updateStatement = "Update Organizations\n"
                + "Set `Name` = ?,\n"
                + "`Description` = ?,\n"
                + "Address = ?\n"
                + "Where Id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedOrganization.getName(),
                updatedOrganization.getDescription(),
                updatedOrganization.getAddress(),
                updatedOrganization.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Organization with id = " + updatedOrganization.getId());
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: OrganizationId IS NOT UNIQUE FOR Powers TABLE.");
        }
    }

    @Override
    public void deleteOrganizationById(int id) throws InvalidIdException {

        try {
            Organization toCheck = getOrganizationById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Organization Id requested.");
        }

        String deleteSupeOrgs = "Delete \n"
                + "From SuperOrganizations \n"
                + "Where OrganizationId = ?";

        String deleteOrganization = "Delete \n"
                + "From Organizations \n"
                + "Where Id = ?";

        template.update(deleteSupeOrgs, id);
        template.update(deleteOrganization, id);
    }

    @Override
    public void addSupeOrganization(int organizationId, int supeId) throws InvalidIdException {

        try {
            Organization orgToCheck = getOrganizationById(organizationId);

            Supe supeToCheck = getSupeById(supeId);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Organization Id or Superhero Id is invalid", ex);
        }

        String insert = "Insert into SuperOrganizations(OrganizationId, SupeId)\n"
                + "Values\n"
                + "(?, ?)";

        int rowsAffected = template.update(insert, organizationId, supeId);

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not add to SuperOrganzation with id's = " + organizationId + " , " + supeId); //might need to remove this
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: Invalid insert into SuperOrganization TABLE.");
        }
    }

    @Override
    public void deleteSupeOrganizationBySupeId(int supeId) throws InvalidIdException {

        try {
            Supe supeToCheck = getSupeById(supeId);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Superhero Id is invalid", ex);
        }

        String deleteSupeOrgs = "Delete \n"
                + "From SuperOrganizations \n"
                + "Where SupeId = ?";

        int rowsAffected = template.update(deleteSupeOrgs, supeId);

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not delete from SuperOrganzation with id " + supeId); //might need to remove this
        }

    }

    @Override
    public void deleteSupeOrganizationByOrganizationId(int organizationId) throws InvalidIdException {
        
         try {
            Organization orgToCheck = getOrganizationById(organizationId);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Organization Id is invalid", ex);
        }

        String deleteSupeOrgs = "Delete \n"
                + "From SuperOrganizations \n"
                + "Where OrganizationId = ?";

        int rowsAffected = template.update(deleteSupeOrgs, organizationId);

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not delete from SuperOrganzation with id " + organizationId); //might need to remove this
        }
    }

    @Override
    public List<Location> getAllLocations() {

        String query = "Select *\n"
                + "From Locations";

        List<Location> toReturn = template.query(query, new LocationMapper());

        return toReturn;
    }

    @Override
    public Location getLocationById(int id) throws InvalidIdException {

        String query = "Select *\n"
                + "From Locations\n"
                + "Where id = ?";

        Location toReturn = null;
        try {
            toReturn = template.queryForObject(query, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Location Id.");
        }

        return toReturn;
    }

    @Override
    public Location addLocation(Location toAdd) throws SuperheroSightingsDaoException {

        if (toAdd == null) {
            throw new SuperheroSightingsDaoException("Location to add is null.");
        }

        String insert = "Insert into Locations(`Name`, Address, Latitude, Longitude)\n"
                + "Values \n"
                + "(?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, toAdd.getName());
                toReturn.setString(2, toAdd.getAddress());
                toReturn.setBigDecimal(3, toAdd.getLatitude());
                toReturn.setBigDecimal(4, toAdd.getLongitude());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newLocationId = holder.getKey().intValue();

        toAdd.setId(newLocationId);

        return toAdd;

    }

    @Override
    public void updateLocation(Location updatedLocation) throws InvalidIdException, SuperheroSightingsDaoException {

        if (updatedLocation == null) {
            throw new SuperheroSightingsDaoException("Location to update is null.");
        }

        String updateStatement = "Update Locations \n"
                + "Set `Name` = ?,\n"
                + "Address = ?,\n"
                + "Latitude = ?,\n"
                + "Longitude = ?\n"
                + "Where Id = ?";

        int rowsAffected = template.update(updateStatement,
                updatedLocation.getName(),
                updatedLocation.getAddress(),
                updatedLocation.getLatitude(),
                updatedLocation.getLongitude(),
                updatedLocation.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Location with id = " + updatedLocation.getId());
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: LocationId IS NOT UNIQUE FOR Powers TABLE.");
        }
    }

    @Override
    public void deleteLocationById(int id) throws InvalidIdException {

        try {
            Location toCheck = getLocationById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Location Id requested.");
        }

        String deleteSightings = "Delete \n"
                + "From Sightings \n"
                + "Where LocationId = ?";

        String deleteLocation = "Delete \n"
                + "From Locations \n"
                + "Where Id = ?";

        template.update(deleteSightings, id);
        template.update(deleteLocation, id);
    }

    @Override
    public List<Sighting> getAllSightings() {

        String query = "Select Si.Id, Si.`date`, Si.LocationId, Si.SupeId, L.`Name` LocationName, L.Address, L.Latitude, L.Longitude, Su.`Name` SupeName, Su.`Description` SupeDescription, Su.PowerId, P.`Name` PowerName\n"
                + "From Sightings Si\n"
                + "Inner Join Locations L On Si.LocationId = L.Id\n"
                + "Inner Join Supes Su On Si.SupeId = Su.Id\n"
                + "Inner Join Powers P on Su.PowerId = P.Id";

        List<Sighting> toReturn = template.query(query, new SightingMapper());

        return toReturn;
    }

    @Override
    public Sighting getSightingById(int id) throws InvalidIdException {

        String query = "Select Si.Id, Si.`date`, Si.LocationId, Si.SupeId, L.`Name` LocationName, L.Address, L.Latitude, L.Longitude, Su.`Name` SupeName, Su.`Description` SupeDescription, Su.PowerId, P.`Name` PowerName\n"
                + "From Sightings Si\n"
                + "Inner Join Locations L On Si.LocationId = L.Id\n"
                + "Inner Join Supes Su On Si.SupeId = Su.Id\n"
                + "Inner Join Powers P on Su.PowerId = P.Id\n"
                + "Where Si.Id = ?";

        Sighting toReturn = null;
        try {
            toReturn = template.queryForObject(query, new SightingMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
        }

        if (toReturn == null) {
            throw new InvalidIdException("Invalid Sighting Id.");
        }

        return toReturn;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) throws SuperheroSightingsDaoException, InvalidIdException {

        if (toAdd == null || toAdd.getLocation() == null || toAdd.getSupe() == null) {
            throw new SuperheroSightingsDaoException("Sighting to add is null.");
        }

        try {
            Location locToCheck = getLocationById(toAdd.getLocation().getId());

            Supe supeToCheck = getSupeById(toAdd.getSupe().getId());

        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Location id or Supe id cannot be found.");
        }

        String insert = "Insert into Sightings(`Date`, LocationId, SupeId)\n"
                + "Values \n"
                + "(?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setDate(1, java.sql.Date.valueOf(toAdd.getDate())); //check this 
                toReturn.setInt(2, toAdd.getLocation().getId());
                toReturn.setInt(3, toAdd.getSupe().getId());

                return toReturn;
            }
        };

        template.update(psc, holder);
        int newSightingId = holder.getKey().intValue();

        toAdd.setId(newSightingId);

        return toAdd;

    }

    @Override
    public void updateSighting(Sighting updatedSighting) throws InvalidIdException, SuperheroSightingsDaoException {

        if (updatedSighting == null || updatedSighting.getLocation() == null || updatedSighting.getSupe() == null) {
            throw new SuperheroSightingsDaoException("Sighting to update is null.");
        }

        try {
            Location locToCheck = getLocationById(updatedSighting.getLocation().getId());

            Supe supeToCheck = getSupeById(updatedSighting.getSupe().getId());

        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Location id or Supe id cannot be found.");
        }

        String updateStatement = "Update Sightings \n"
                + "Set `Date` = ?,\n"
                + "LocationId = ?,\n"
                + "SupeId = ?\n"
                + "Where Id = ?";

        int rowsAffected = template.update(updateStatement,
                java.sql.Date.valueOf(updatedSighting.getDate()), //check this 
                updatedSighting.getLocation().getId(),
                updatedSighting.getSupe().getId(),
                updatedSighting.getId());

        if (rowsAffected == 0) {
            throw new InvalidIdException("Could not edit Location with id = " + updatedSighting.getId());
        }

        if (rowsAffected > 1) {
            throw new InvalidIdException("ERROR: SightingId IS NOT UNIQUE FOR Powers TABLE.");
        }

    }

    @Override
    public void deleteSightingById(int id) throws InvalidIdException {
        try {
            Sighting toCheck = getSightingById(id);
        } catch (InvalidIdException ex) {
            throw new InvalidIdException("Invalid Sighting Id requested.");
        }

        String deleteStatement = "Delete \n"
                + "From Sightings \n"
                + "Where Id = ?";

        template.update(deleteStatement, id);
    }

    @Override
    public void deleteAll() {

        String deleteSuperOrganizations = "Delete \n"
                + "From SuperOrganizations\n"
                + "Where SupeId > 0";

        String deleteSightings = "Delete \n"
                + "From Sightings\n"
                + "Where Id > 0";

        String deleteLocations = "Delete \n"
                + "From Locations\n"
                + "Where Id > 0";

        String deleteSupes = "Delete \n"
                + "From Supes\n"
                + "Where Id > 0";

        String deletePowers = "Delete \n"
                + "From Powers\n"
                + "Where Id > 0";

        String deleteOrganizations = "Delete \n"
                + "From Organizations\n"
                + "Where Id > 0";

        template.update(deleteSuperOrganizations);
        template.update(deleteSightings);
        template.update(deleteLocations);
        template.update(deleteSupes);
        template.update(deletePowers);
        template.update(deleteOrganizations);

    }


    private class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet results, int rowNum) throws SQLException {
            Power toReturn = new Power();
            toReturn.setId(results.getInt("Id"));
            toReturn.setName(results.getString("Name"));

            return toReturn;
        }
    }

    private class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet results, int rowNum) throws SQLException {
            Location toReturn = new Location();
            toReturn.setId(results.getInt("Id"));
            toReturn.setName(results.getString("Name"));
            toReturn.setAddress(results.getString("Address"));
            toReturn.setLatitude(results.getBigDecimal("Latitude"));
            toReturn.setLongitude(results.getBigDecimal("Longitude"));

            return toReturn;
        }

    }

    private class SupeMapper implements RowMapper<Supe> {

        @Override
        public Supe mapRow(ResultSet results, int rowNum) throws SQLException {
            Supe toReturn = new Supe();
            toReturn.setId(results.getInt("SupeId"));
            toReturn.setName(results.getString("SupeName"));
            toReturn.setDescription(results.getString("Description"));

            Power toAdd = new Power();
            toAdd.setId(results.getInt("PowerId"));
            toAdd.setName(results.getString("PowerName"));
            toReturn.setPower(toAdd);

            return toReturn;
        }
    }

    private class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet results, int rowNum) throws SQLException {
            Organization toReturn = new Organization();
            toReturn.setId(results.getInt("Id"));
            toReturn.setName(results.getString("Name"));
            toReturn.setDescription(results.getString("Description"));
            toReturn.setAddress(results.getString("Address"));

            return toReturn;
        }
    }

    private class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet results, int rowNum) throws SQLException {
            Sighting toReturn = new Sighting();
            toReturn.setId(results.getInt("Id"));

            Location locToAdd = new Location();
            locToAdd.setId(results.getInt("LocationId"));
            locToAdd.setName(results.getString("LocationName"));
            locToAdd.setAddress(results.getString("Address"));
            locToAdd.setLatitude(results.getBigDecimal("Latitude"));
            locToAdd.setLongitude(results.getBigDecimal("Longitude"));
            toReturn.setLocation(locToAdd);

            Power powerToAdd = new Power();
            powerToAdd.setId(results.getInt("PowerId"));
            powerToAdd.setName(results.getString("PowerName"));

            Supe supeToAdd = new Supe();
            supeToAdd.setId(results.getInt("SupeId"));
            supeToAdd.setName(results.getString("SupeName"));
            supeToAdd.setDescription(results.getString("SupeDescription"));
            supeToAdd.setPower(powerToAdd);

            toReturn.setSupe(supeToAdd);

            LocalDate date = results.getDate("Date").toLocalDate();
            toReturn.setDate(date);

            return toReturn;
        }
    }

}

//getsupesbylocation may need invalidIdException instead 
//need to test for invalid dates? 
//need to test for getall bad inputs??? Or just return blank list. Blank list is okay.

//transactions needed
