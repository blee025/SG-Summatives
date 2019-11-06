/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.daos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sg.superherosightings.TestApplicationConfiguration;
import sg.superherosightings.models.Location;
import sg.superherosightings.models.Organization;
import sg.superherosightings.models.Power;
import sg.superherosightings.models.Sighting;
import sg.superherosightings.models.Supe;

/**
 *
 * @author blee0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles(profiles = "test")
public class SuperheroSightingsDaoDBTest {

    @Autowired
    SuperheroSightingsDao toTest;

    public SuperheroSightingsDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        toTest.deleteAll();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSupesByLocation method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetSupesByLocationGoldenPath() {
        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            List<Supe> toCheck = toTest.getSupesByLocation(locAdded.getId());

            assertEquals(2, toCheck.size());

            Supe firstSupe = toCheck.get(0);
            assertEquals(addedSupe1.getId(), firstSupe.getId());
            assertEquals("Rocky", firstSupe.getName());
            assertEquals("Rock in your face!", firstSupe.getDescription());
            assertEquals(addedPower.getId(), firstSupe.getPower().getId());
            assertEquals(addedPower.getName(), firstSupe.getPower().getName());

            Supe secondSupe = toCheck.get(1);
            assertEquals(addedSupe2.getId(), secondSupe.getId());
            assertEquals("Sandy", secondSupe.getName());
            assertEquals("Throws Sand - Ow!", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());
            assertEquals("Earth", secondSupe.getPower().getName());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getLocationsBySupe method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetLocationsBySupeGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Kid Swings");
            locToAdd2.setAddress("2664 Whitner Ave.");
            locToAdd2.setLatitude(new BigDecimal("21.887322"));
            locToAdd2.setLongitude(new BigDecimal("-32.992284"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Lemonade Stand");
            locToAdd3.setAddress("53 Willy Rd.");
            locToAdd3.setLatitude(new BigDecimal("-32.476623"));
            locToAdd3.setLongitude(new BigDecimal("100.239984"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 5, 19));
            sightingToAdd2.setLocation(locAdded2);
            sightingToAdd2.setSupe(addedSupe1);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 7, 3));
            sightingToAdd3.setLocation(locAdded3);
            sightingToAdd3.setSupe(addedSupe1);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            List<Location> toCheck = toTest.getLocationsBySupe(addedSupe1.getId());

            assertEquals(3, toCheck.size());

            Location firstLocation = toCheck.get(0);
            Location secondLocation = toCheck.get(1);
            Location thirdLocation = toCheck.get(2);

            assertEquals(locAdded.getId(), firstLocation.getId());
            assertEquals("Sewers", firstLocation.getName());
            assertEquals("1 Twill Street", firstLocation.getAddress());
            assertEquals(new BigDecimal("-54.398272"), firstLocation.getLatitude());
            assertEquals(new BigDecimal("134.922347"), firstLocation.getLongitude());

            assertEquals(locAdded2.getId(), secondLocation.getId());
            assertEquals("Kid Swings", secondLocation.getName());
            assertEquals("2664 Whitner Ave.", secondLocation.getAddress());
            assertEquals(new BigDecimal("21.887322"), secondLocation.getLatitude());
            assertEquals(new BigDecimal("-32.992284"), secondLocation.getLongitude());

            assertEquals(locAdded3.getId(), thirdLocation.getId());
            assertEquals("Lemonade Stand", thirdLocation.getName());
            assertEquals("53 Willy Rd.", thirdLocation.getAddress());
            assertEquals(new BigDecimal("-32.476623"), thirdLocation.getLatitude());
            assertEquals(new BigDecimal("100.239984"), thirdLocation.getLongitude());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getSightingsByDate method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetSightingsByDateGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Kid Swings");
            locToAdd2.setAddress("2664 Whitner Ave.");
            locToAdd2.setLatitude(new BigDecimal("21.887322"));
            locToAdd2.setLongitude(new BigDecimal("-32.992284"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Lemonade Stand");
            locToAdd3.setAddress("53 Willy Rd.");
            locToAdd3.setLatitude(new BigDecimal("-32.476623"));
            locToAdd3.setLongitude(new BigDecimal("100.239984"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 5, 19));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 5, 19));
            sightingToAdd2.setLocation(locAdded2);
            sightingToAdd2.setSupe(addedSupe1);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 5, 19));
            sightingToAdd3.setLocation(locAdded3);
            sightingToAdd3.setSupe(addedSupe1);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            List<Sighting> toCheck = toTest.getSightingsByDate(LocalDate.of(2019, 5, 19));

            assertEquals(3, toCheck.size());

            Sighting firstSighting = toCheck.get(0);
            Sighting secondSighting = toCheck.get(1);
            Sighting thirdSighting = toCheck.get(2);

            assertEquals(sightingAdded1.getId(), firstSighting.getId());
            assertEquals(LocalDate.of(2019, 5, 19), firstSighting.getDate());
            assertEquals(locAdded.getId(), firstSighting.getLocation().getId());
            assertEquals("Sewers", firstSighting.getLocation().getName());
            assertEquals("1 Twill Street", firstSighting.getLocation().getAddress());
            assertEquals(new BigDecimal("-54.398272"), firstSighting.getLocation().getLatitude());
            assertEquals(new BigDecimal("134.922347"), firstSighting.getLocation().getLongitude());
            assertEquals(addedSupe1.getId(), firstSighting.getSupe().getId());
            assertEquals("Rocky", firstSighting.getSupe().getName());
            assertEquals("Rock in your face!", firstSighting.getSupe().getDescription());
            assertEquals("Rocky", firstSighting.getSupe().getName());
            assertEquals(addedPower.getId(), firstSighting.getSupe().getPower().getId());
            assertEquals("Earth", firstSighting.getSupe().getPower().getName());

            assertEquals(sightingAdded3.getId(), thirdSighting.getId());
            assertEquals(LocalDate.of(2019, 5, 19), thirdSighting.getDate());
            assertEquals(locAdded3.getId(), thirdSighting.getLocation().getId());
            assertEquals("Lemonade Stand", thirdSighting.getLocation().getName());
            assertEquals("53 Willy Rd.", thirdSighting.getLocation().getAddress());
            assertEquals(new BigDecimal("-32.476623"), thirdSighting.getLocation().getLatitude());
            assertEquals(new BigDecimal("100.239984"), thirdSighting.getLocation().getLongitude());
            assertEquals(addedSupe1.getId(), thirdSighting.getSupe().getId());
            assertEquals("Rocky", thirdSighting.getSupe().getName());
            assertEquals("Rock in your face!", thirdSighting.getSupe().getDescription());
            assertEquals("Rocky", thirdSighting.getSupe().getName());
            assertEquals(addedPower.getId(), thirdSighting.getSupe().getPower().getId());
            assertEquals("Earth", thirdSighting.getSupe().getPower().getName());
        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getSupesByOrganization method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetSupesByOrganizationGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());

            List<Supe> toCheck = toTest.getSupesByOrganization(orgAdded.getId());

            assertEquals(3, toCheck.size());

            Supe firstSupe = toCheck.get(0);
            Supe secondSupe = toCheck.get(1);
            Supe thirdSupe = toCheck.get(2);

            assertEquals(addedSupe1.getId(), firstSupe.getId());
            assertEquals("Rocky", firstSupe.getName());
            assertEquals("Rock in your face!", firstSupe.getDescription());
            assertEquals(addedPower.getId(), firstSupe.getPower().getId());

            assertEquals(addedSupe2.getId(), secondSupe.getId());
            assertEquals("Sandy", secondSupe.getName());
            assertEquals("Throws Sand - Ow!", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());

            assertEquals(addedSupe3.getId(), thirdSupe.getId());
            assertEquals("Rainbow Gal", thirdSupe.getName());
            assertEquals("Pretty colors!", thirdSupe.getDescription());
            assertEquals(addedPower.getId(), thirdSupe.getPower().getId());
        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getOrganizationsBySupe method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetOrganizationsBySupeGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Organization orgToAdd1 = new Organization();
            orgToAdd1.setName("Slimey Threes");
            orgToAdd1.setDescription("So deceiving");
            orgToAdd1.setAddress("12 Grand Ave.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();
            orgToAdd2.setName("Never on Time");
            orgToAdd2.setDescription("Late!!");
            orgToAdd2.setAddress("1 Forest Rd.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();
            orgToAdd3.setName("Green Boys");
            orgToAdd3.setDescription("Money Rules");
            orgToAdd3.setAddress("3395 Whitner Blvd.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            toTest.addSupeOrganization(orgAdded1.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded2.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded3.getId(), addedSupe1.getId());

            List<Organization> toCheck = toTest.getOrganizationsBySupe(addedSupe1.getId());

            assertEquals(3, toCheck.size());

            Organization firstOrg = toCheck.get(0);
            Organization secondOrg = toCheck.get(1);
            Organization thirdOrg = toCheck.get(2);

            assertEquals(orgAdded1.getId(), firstOrg.getId());
            assertEquals("Slimey Threes", firstOrg.getName());
            assertEquals("So deceiving", firstOrg.getDescription());
            assertEquals("12 Grand Ave.", firstOrg.getAddress());

            assertEquals(orgAdded2.getId(), secondOrg.getId());
            assertEquals("Never on Time", secondOrg.getName());
            assertEquals("Late!!", secondOrg.getDescription());
            assertEquals("1 Forest Rd.", secondOrg.getAddress());

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("Green Boys", thirdOrg.getName());
            assertEquals("Money Rules", thirdOrg.getDescription());
            assertEquals("3395 Whitner Blvd.", thirdOrg.getAddress());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getAllPowers method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetAllPowersGoldenPath() {

        try {
            Power toAdd1 = new Power();
            Power toAdd2 = new Power();

            toAdd1.setName("Crystals");
            toAdd2.setName("Bugs");
            Power powerAdded1 = toTest.addPower(toAdd1);
            Power powerAdded2 = toTest.addPower(toAdd2);

            List<Power> toCheck = toTest.getAllPowers();
            Power firstPower = toCheck.get(0);
            Power secondPower = toCheck.get(1);

            assertEquals(2, toCheck.size());
            assertEquals(powerAdded1.getId(), firstPower.getId());
            assertEquals("Crystals", firstPower.getName());
            assertEquals(powerAdded2.getId(), secondPower.getId());
            assertEquals("Bugs", secondPower.getName());
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getPowerById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetPowerByIdGoldenPath() {

        try {
            Power toAdd1 = new Power();
            Power toAdd2 = new Power();

            toAdd1.setName("Crystals");
            toAdd2.setName("Bugs");
            Power powerAdded1 = toTest.addPower(toAdd1);
            Power powerAdded2 = toTest.addPower(toAdd2);

            Power firstToCheck = toTest.getPowerById(powerAdded1.getId());
            Power secondToCheck = toTest.getPowerById(powerAdded2.getId());

            assertEquals(powerAdded1.getId(), firstToCheck.getId());
            assertEquals("Crystals", firstToCheck.getName());
            assertEquals(powerAdded2.getId(), secondToCheck.getId());
            assertEquals("Bugs", secondToCheck.getName());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetPowerByBadId() {

        try {
            Power toAdd1 = new Power();
            Power toAdd2 = new Power();

            toAdd1.setName("Crystals");
            toAdd2.setName("Bugs");
            Power powerAdded1 = toTest.addPower(toAdd1);
            Power powerAdded2 = toTest.addPower(toAdd2);

            Power toCheck = toTest.getPowerById(9522);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addPower method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddPowerGoldenPath() {

        try {
            Power toAdd = new Power();
            toAdd.setName("Thunder");

            Power powerAdded = toTest.addPower(toAdd);

            Power toCheck = toTest.getPowerById(powerAdded.getId());

            assertEquals(powerAdded.getId(), toCheck.getId());
            assertEquals("Thunder", toCheck.getName());
        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddNullPower() {

        try {
            Power toAdd = null;

            Power powerAdded = toTest.addPower(toAdd);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    /**
     * Test of updatePower method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testUpdatePowerGoldenPath() {

        try {
            Power toAdd = new Power();
            toAdd.setName("Thunder");

            Power powerAdded = toTest.addPower(toAdd);

            Power updatedPower = new Power();
            updatedPower.setId(powerAdded.getId());
            updatedPower.setName("Lightning");
            toTest.updatePower(updatedPower);

            List<Power> toCheck = toTest.getAllPowers();

            assertEquals(powerAdded.getId(), toCheck.get(0).getId());
            assertEquals("Lightning", toCheck.get(0).getName());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullPower() {

        try {
            Power toAdd = new Power();
            toAdd.setName("Thunder");

            Power powerAdded = toTest.addPower(toAdd);

            Power updatedPower = null;
            toTest.updatePower(updatedPower);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateBadPowerId() {

        try {
            Power toAdd = new Power();
            toAdd.setName("Thunder");

            Power powerAdded = toTest.addPower(toAdd);

            Power updatedPower = new Power();
            updatedPower.setId(639);
            updatedPower.setName("Lightning");
            toTest.updatePower(updatedPower);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deletePowerById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeletePowerByIdGoldenPath() {

        try {
            Power toAdd1 = new Power();
            Power toAdd2 = new Power();

            toAdd1.setName("Crystals");
            toAdd2.setName("Bugs");
            Power powerAdded1 = toTest.addPower(toAdd1);
            Power powerAdded2 = toTest.addPower(toAdd2);

            toTest.deletePowerById(powerAdded1.getId());

            List<Power> toCheck = toTest.getAllPowers();

            assertEquals(1, toCheck.size());

            assertEquals(powerAdded2.getId(), toCheck.get(0).getId());
            assertEquals("Bugs", toCheck.get(0).getName());
        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeletePowerByInvalidId() {

        try {
            Power toAdd1 = new Power();
            Power toAdd2 = new Power();

            toAdd1.setName("Crystals");
            toAdd2.setName("Bugs");
            Power powerAdded1 = toTest.addPower(toAdd1);
            Power powerAdded2 = toTest.addPower(toAdd2);

            toTest.deletePowerById(5568);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllSupes method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetAllSupesGoldenPath() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Time freak");
            supeToAdd2.setDescription("Controls time");
            supeToAdd2.setPower(newPower);
            Supe supeAdded2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Medium Speedy");
            supeToAdd3.setDescription("Just about right");
            supeToAdd3.setPower(newPower);
            Supe supeAdded3 = toTest.addSupe(supeToAdd3);

            List<Supe> toCheck = toTest.getAllSupes();
            Supe firstSupe = toCheck.get(0);
            Supe secondSupe = toCheck.get(1);
            Supe thirdSupe = toCheck.get(2);

            assertEquals(3, toCheck.size());

            assertEquals(supeAdded1.getId(), firstSupe.getId());
            assertEquals("Speedster", firstSupe.getName());
            assertEquals("Fastest guy", firstSupe.getDescription());
            assertEquals(addedPower.getId(), firstSupe.getPower().getId());

            assertEquals(supeAdded2.getId(), secondSupe.getId());
            assertEquals("Time freak", secondSupe.getName());
            assertEquals("Controls time", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());

            assertEquals(supeAdded3.getId(), thirdSupe.getId());
            assertEquals("Medium Speedy", thirdSupe.getName());
            assertEquals("Just about right", thirdSupe.getDescription());
            assertEquals(addedPower.getId(), thirdSupe.getPower().getId());

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getSupeById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetSupeByIdGoldenPath() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe toCheck = toTest.getSupeById(supeAdded1.getId());

            assertEquals(supeAdded1.getId(), toCheck.getId());
            assertEquals("Speedster", toCheck.getName());
            assertEquals("Fastest guy", toCheck.getDescription());
            assertEquals(addedPower.getId(), toCheck.getPower().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetSupeByBadId() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe toCheck = toTest.getSupeById(3986);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addSupe method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddSupeGoldenPath() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe toCheck = toTest.getSupeById(supeAdded1.getId());

            assertEquals(supeAdded1.getId(), toCheck.getId());
            assertEquals("Speedster", toCheck.getName());
            assertEquals("Fastest guy", toCheck.getDescription());
            assertEquals(addedPower.getId(), toCheck.getPower().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddNullSupe() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = null;
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testAddSupeNullPower() {

        try {
            Power newPower = null;

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testAddSupeBadPowerId() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Power badPower = new Power();
            badPower.setName("Speedier");
            badPower.setId(9337);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(badPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    /**
     * Test of updateSupe method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testUpdateSupeGoldenPath() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(addedPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Power testPower = new Power();
            testPower.setName("Wind");
            Power addedPower2 = toTest.addPower(testPower);

            Supe updatedSupe = new Supe();
            updatedSupe.setId(supeAdded1.getId());
            updatedSupe.setName("High Speed");
            updatedSupe.setDescription("Still pretty fast");
            updatedSupe.setPower(addedPower2);
            toTest.updateSupe(updatedSupe);

            List<Supe> toCheck = toTest.getAllSupes();
            Supe onlySupe = toCheck.get(0);

            assertEquals(1, toCheck.size());
            assertEquals(supeAdded1.getId(), onlySupe.getId());
            assertEquals("High Speed", onlySupe.getName());
            assertEquals("Still pretty fast", onlySupe.getDescription());
            assertEquals(addedPower2.getId(), onlySupe.getPower().getId());
        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullSupe() {

        try {
            Supe updatedSupe = null;

            toTest.updateSupe(updatedSupe);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateSupeBadPowerId() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(addedPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Power badPower = new Power();
            badPower.setName("Wind");
            badPower.setId(6703);

            Supe updatedSupe = new Supe();
            updatedSupe.setId(supeAdded1.getId());
            updatedSupe.setName("High Speed");
            updatedSupe.setDescription("Still pretty fast");
            updatedSupe.setPower(badPower);
            toTest.updateSupe(updatedSupe);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateSupeBadNullPower() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(addedPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Power badPower = null;

            Supe updatedSupe = new Supe();
            updatedSupe.setId(supeAdded1.getId());
            updatedSupe.setName("High Speed");
            updatedSupe.setDescription("Still pretty fast");
            updatedSupe.setPower(badPower);
            toTest.updateSupe(updatedSupe);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateInvalidSupeId() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(addedPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Power testPower = new Power();
            testPower.setName("Wind");
            Power addedPower2 = toTest.addPower(testPower);

            Supe updatedSupe = new Supe();
            updatedSupe.setId(8377);
            updatedSupe.setName("High Speed");
            updatedSupe.setDescription("Still pretty fast");
            updatedSupe.setPower(addedPower2);
            toTest.updateSupe(updatedSupe);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteSupebyId method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeleteSupebyIdGoldenPath() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Time freak");
            supeToAdd2.setDescription("Controls time");
            supeToAdd2.setPower(newPower);
            Supe supeAdded2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Medium Speedy");
            supeToAdd3.setDescription("Just about right");
            supeToAdd3.setPower(newPower);
            Supe supeAdded3 = toTest.addSupe(supeToAdd3);

            toTest.deleteSupeById(supeAdded2.getId());

            List<Supe> toCheck = toTest.getAllSupes();
            Supe firstSupe = toCheck.get(0);
            Supe secondSupe = toCheck.get(1);

            assertEquals(2, toCheck.size());
            assertEquals(supeAdded1.getId(), firstSupe.getId());
            assertEquals("Speedster", firstSupe.getName());
            assertEquals("Fastest guy", firstSupe.getDescription());
            assertEquals(addedPower.getId(), firstSupe.getPower().getId());

            assertEquals(supeAdded3.getId(), secondSupe.getId());
            assertEquals("Medium Speedy", secondSupe.getName());
            assertEquals("Just about right", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteSupebyInvalidId() {

        try {
            Power newPower = new Power();
            newPower.setName("Speed");
            Power addedPower = toTest.addPower(newPower);

            Supe supeToAdd1 = new Supe();
            supeToAdd1.setName("Speedster");
            supeToAdd1.setDescription("Fastest guy");
            supeToAdd1.setPower(newPower);
            Supe supeAdded1 = toTest.addSupe(supeToAdd1);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Time freak");
            supeToAdd2.setDescription("Controls time");
            supeToAdd2.setPower(newPower);
            Supe supeAdded2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Medium Speedy");
            supeToAdd3.setDescription("Just about right");
            supeToAdd3.setPower(newPower);
            Supe supeAdded3 = toTest.addSupe(supeToAdd3);

            toTest.deleteSupeById(9345);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllOrganizations method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetAllOrganizationsGoldenPath() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            List<Organization> toCheck = toTest.getAllOrganizations();

            assertEquals(3, toCheck.size());

            Organization firstOrg = toCheck.get(0);
            Organization secondOrg = toCheck.get(1);
            Organization thirdOrg = toCheck.get(2);

            assertEquals(orgAdded1.getId(), firstOrg.getId());
            assertEquals("Dark Venom", firstOrg.getName());
            assertEquals("Darkest Heroes", firstOrg.getDescription());
            assertEquals("443 Dodd Rd.", firstOrg.getAddress());

            assertEquals(orgAdded2.getId(), secondOrg.getId());
            assertEquals("Pixie Fairy", secondOrg.getName());
            assertEquals("Nice Heroes", secondOrg.getDescription());
            assertEquals("26 Jordan Ave.", secondOrg.getAddress());

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("Unknowns", thirdOrg.getName());
            assertEquals("Random", thirdOrg.getDescription());
            assertEquals("26 Nelson Ave.", thirdOrg.getAddress());

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getOrganizationById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetOrganizationByIdGoldenPath() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            Organization firstOrg = toTest.getOrganizationById(orgAdded1.getId());

            assertEquals(orgAdded1.getId(), firstOrg.getId());
            assertEquals("Dark Venom", firstOrg.getName());
            assertEquals("Darkest Heroes", firstOrg.getDescription());
            assertEquals("443 Dodd Rd.", firstOrg.getAddress());

            Organization thirdOrg = toTest.getOrganizationById(orgAdded3.getId());

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("Unknowns", thirdOrg.getName());
            assertEquals("Random", thirdOrg.getDescription());
            assertEquals("26 Nelson Ave.", thirdOrg.getAddress());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetOrganizationByInvalidId() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            Organization toCheck = toTest.getOrganizationById(2933);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addOrganization method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddOrganizationGoldenPath() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            Organization firstOrg = toTest.getOrganizationById(orgAdded1.getId());

            assertEquals(orgAdded1.getId(), firstOrg.getId());
            assertEquals("Dark Venom", firstOrg.getName());
            assertEquals("Darkest Heroes", firstOrg.getDescription());
            assertEquals("443 Dodd Rd.", firstOrg.getAddress());

            Organization thirdOrg = toTest.getOrganizationById(orgAdded3.getId());

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("Unknowns", thirdOrg.getName());
            assertEquals("Random", thirdOrg.getDescription());
            assertEquals("26 Nelson Ave.", thirdOrg.getAddress());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddNullOrganization() {

        try {
            Organization orgToAdd1 = null;
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    /**
     * Test of updateOrganization method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testUpdateOrganizationGoldenPath() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization updatedOrg = new Organization();
            updatedOrg.setId(orgAdded1.getId());
            updatedOrg.setName("Red Venom");
            updatedOrg.setDescription("Scary boys");
            updatedOrg.setAddress("223 Grey Ave.");
            toTest.updateOrganization(updatedOrg);

            List<Organization> allOrgs = toTest.getAllOrganizations();

            Organization toCheck = allOrgs.get(0);

            assertEquals(orgAdded1.getId(), toCheck.getId());
            assertEquals("Red Venom", toCheck.getName());
            assertEquals("Scary boys", toCheck.getDescription());
            assertEquals("223 Grey Ave.", toCheck.getAddress());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullOrganization() {

        try {
            Organization orgToAdd1 = null;
            toTest.updateOrganization(orgToAdd1);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationByInvalidId() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization updatedOrg = new Organization();
            updatedOrg.setId(1358);
            updatedOrg.setName("Red Venom");
            updatedOrg.setDescription("Scary boys");
            updatedOrg.setAddress("223 Grey Ave.");
            toTest.updateOrganization(updatedOrg);

            List<Organization> allOrgs = toTest.getAllOrganizations();
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteOrganziationById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeleteOrganziationByIdGoldenPath() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            toTest.deleteOrganizationById(orgAdded2.getId());

            List<Organization> allOrgs = toTest.getAllOrganizations();

            assertEquals(2, allOrgs.size());

            Organization firstOrg = allOrgs.get(0);

            assertEquals(orgAdded1.getId(), firstOrg.getId());
            assertEquals("Dark Venom", firstOrg.getName());
            assertEquals("Darkest Heroes", firstOrg.getDescription());
            assertEquals("443 Dodd Rd.", firstOrg.getAddress());

            Organization thirdOrg = allOrgs.get(1);

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("Unknowns", thirdOrg.getName());
            assertEquals("Random", thirdOrg.getDescription());
            assertEquals("26 Nelson Ave.", thirdOrg.getAddress());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteOrganziationByInvalidId() {

        try {
            Organization orgToAdd1 = new Organization();

            orgToAdd1.setName("Dark Venom");
            orgToAdd1.setDescription("Darkest Heroes");
            orgToAdd1.setAddress("443 Dodd Rd.");
            Organization orgAdded1 = toTest.addOrganization(orgToAdd1);

            Organization orgToAdd2 = new Organization();

            orgToAdd2.setName("Pixie Fairy");
            orgToAdd2.setDescription("Nice Heroes");
            orgToAdd2.setAddress("26 Jordan Ave.");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();

            orgToAdd3.setName("Unknowns");
            orgToAdd3.setDescription("Random");
            orgToAdd3.setAddress("26 Nelson Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            toTest.deleteOrganizationById(4386);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addSupeOrganization method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddSupeOrganizationGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());

            List<Supe> toCheck = toTest.getSupesByOrganization(orgAdded.getId());

            assertEquals(3, toCheck.size());

            Supe firstSupe = toCheck.get(0);
            Supe secondSupe = toCheck.get(1);
            Supe thirdSupe = toCheck.get(2);

            assertEquals(addedSupe1.getId(), firstSupe.getId());
            assertEquals("Rocky", firstSupe.getName());
            assertEquals("Rock in your face!", firstSupe.getDescription());
            assertEquals(addedPower.getId(), firstSupe.getPower().getId());

            assertEquals(addedSupe2.getId(), secondSupe.getId());
            assertEquals("Sandy", secondSupe.getName());
            assertEquals("Throws Sand - Ow!", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());

            assertEquals(addedSupe3.getId(), thirdSupe.getId());
            assertEquals("Rainbow Gal", thirdSupe.getName());
            assertEquals("Pretty colors!", thirdSupe.getDescription());
            assertEquals(addedPower.getId(), thirdSupe.getPower().getId());
        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddSupeOrganizationByInvalidOrgId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(5227, addedSupe1.getId());
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testAddSupeOrganizationByInvalidSupeId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(orgAdded.getId(), 3448);
            fail();
        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void deleteSupeOrganizationBySupeIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());

            toTest.deleteSupeOrganizationBySupeId(addedSupe1.getId());

            List<Supe> toCheck = toTest.getSupesByOrganization(orgAdded.getId());

            assertEquals(2, toCheck.size());

            Supe secondSupe = toCheck.get(0);
            Supe thirdSupe = toCheck.get(1);

            assertEquals(addedSupe2.getId(), secondSupe.getId());
            assertEquals("Sandy", secondSupe.getName());
            assertEquals("Throws Sand - Ow!", secondSupe.getDescription());
            assertEquals(addedPower.getId(), secondSupe.getPower().getId());

            assertEquals(addedSupe3.getId(), thirdSupe.getId());
            assertEquals("Rainbow Gal", thirdSupe.getName());
            assertEquals("Pretty colors!", thirdSupe.getDescription());
            assertEquals(addedPower.getId(), thirdSupe.getPower().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }
    
    @Test
    public void deleteSupeOrganizationByInvalidSupeIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());

            toTest.deleteSupeOrganizationBySupeId(3928);

            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void deleteSupeOrganizationByOrganizationIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            Organization orgToAdd2 = new Organization();
            orgToAdd2.setName("Old Kids on the Block");
            orgToAdd2.setDescription("Old Kids");
            orgToAdd2.setAddress("22 Jump Street");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();
            orgToAdd3.setName("The Cats");
            orgToAdd3.setDescription("Feline");
            orgToAdd3.setAddress("123 Haley Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded2.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded3.getId(), addedSupe1.getId());

            toTest.deleteSupeOrganizationByOrganizationId(orgAdded.getId());

            List<Organization> toCheck = toTest.getOrganizationsBySupe(addedSupe1.getId());

            assertEquals(2, toCheck.size());

            Organization secondOrg = toCheck.get(0);
            Organization thirdOrg = toCheck.get(1);

            assertEquals(orgAdded2.getId(), secondOrg.getId());
            assertEquals("Old Kids on the Block", secondOrg.getName());
            assertEquals("Old Kids", secondOrg.getDescription());
            assertEquals("22 Jump Street", secondOrg.getAddress());

            assertEquals(orgAdded3.getId(), thirdOrg.getId());
            assertEquals("The Cats", thirdOrg.getName());
            assertEquals("Feline", thirdOrg.getDescription());
            assertEquals("123 Haley Ave.", thirdOrg.getAddress());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }
    
    @Test
    public void deleteSupeOrganizationByInvalidOrganizationIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Rainbow Gal");
            supeToAdd3.setDescription("Pretty colors!");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Organization orgToAdd = new Organization();
            orgToAdd.setName("New Kids on the Block");
            orgToAdd.setDescription("New Kids");
            orgToAdd.setAddress("21 Jump Street");
            Organization orgAdded = toTest.addOrganization(orgToAdd);

            Organization orgToAdd2 = new Organization();
            orgToAdd2.setName("Old Kids on the Block");
            orgToAdd2.setDescription("Old Kids");
            orgToAdd2.setAddress("22 Jump Street");
            Organization orgAdded2 = toTest.addOrganization(orgToAdd2);

            Organization orgToAdd3 = new Organization();
            orgToAdd3.setName("The Cats");
            orgToAdd3.setDescription("Feline");
            orgToAdd3.setAddress("123 Haley Ave.");
            Organization orgAdded3 = toTest.addOrganization(orgToAdd3);

            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded2.getId(), addedSupe1.getId());
            toTest.addSupeOrganization(orgAdded3.getId(), addedSupe1.getId());

            toTest.deleteSupeOrganizationByOrganizationId(8339);

            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

//    @Test
//    public void testDeleteSupeOrganizationByInvalidOrgId() {
//
//        try {
//            Power powerToAdd = new Power();
//            powerToAdd.setName("Earth");
//            Power addedPower = toTest.addPower(powerToAdd);
//
//            Supe supeToAdd = new Supe();
//            supeToAdd.setName("Rocky");
//            supeToAdd.setDescription("Rock in your face!");
//            supeToAdd.setPower(addedPower);
//            Supe addedSupe1 = toTest.addSupe(supeToAdd);
//
//            Supe supeToAdd2 = new Supe();
//            supeToAdd2.setName("Sandy");
//            supeToAdd2.setDescription("Throws Sand - Ow!");
//            supeToAdd2.setPower(addedPower);
//            Supe addedSupe2 = toTest.addSupe(supeToAdd2);
//
//            Supe supeToAdd3 = new Supe();
//            supeToAdd3.setName("Rainbow Gal");
//            supeToAdd3.setDescription("Pretty colors!");
//            supeToAdd3.setPower(addedPower);
//            Supe addedSupe3 = toTest.addSupe(supeToAdd3);
//
//            Organization orgToAdd = new Organization();
//            orgToAdd.setName("New Kids on the Block");
//            orgToAdd.setDescription("New Kids");
//            orgToAdd.setAddress("21 Jump Street");
//            Organization orgAdded = toTest.addOrganization(orgToAdd);
//
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());
//
//            toTest.deleteSupeOrganization(2477, addedSupe1.getId());
//            fail();
//        } catch (SuperheroSightingsDaoException ex) {
//            fail();
//        } catch (InvalidIdException ex) {
//        }
//    }
//
//    @Test
//    public void testDeleteSupeOrganizationByInvalidSupeId() {
//
//        try {
//            Power powerToAdd = new Power();
//            powerToAdd.setName("Earth");
//            Power addedPower = toTest.addPower(powerToAdd);
//
//            Supe supeToAdd = new Supe();
//            supeToAdd.setName("Rocky");
//            supeToAdd.setDescription("Rock in your face!");
//            supeToAdd.setPower(addedPower);
//            Supe addedSupe1 = toTest.addSupe(supeToAdd);
//
//            Supe supeToAdd2 = new Supe();
//            supeToAdd2.setName("Sandy");
//            supeToAdd2.setDescription("Throws Sand - Ow!");
//            supeToAdd2.setPower(addedPower);
//            Supe addedSupe2 = toTest.addSupe(supeToAdd2);
//
//            Supe supeToAdd3 = new Supe();
//            supeToAdd3.setName("Rainbow Gal");
//            supeToAdd3.setDescription("Pretty colors!");
//            supeToAdd3.setPower(addedPower);
//            Supe addedSupe3 = toTest.addSupe(supeToAdd3);
//
//            Organization orgToAdd = new Organization();
//            orgToAdd.setName("New Kids on the Block");
//            orgToAdd.setDescription("New Kids");
//            orgToAdd.setAddress("21 Jump Street");
//            Organization orgAdded = toTest.addOrganization(orgToAdd);
//
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe1.getId());
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe2.getId());
//            toTest.addSupeOrganization(orgAdded.getId(), addedSupe3.getId());
//
//            toTest.deleteSupeOrganization(orgAdded.getId(), 7332);
//            fail();
//        } catch (SuperheroSightingsDaoException ex) {
//            fail();
//        } catch (InvalidIdException ex) {
//        }
//    }

    //testing bad deletion if rows affected can be caught
    /**
     * Test of getAllLocations method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetAllLocationsGoldenPath() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            List<Location> allLocations = toTest.getAllLocations();

            assertEquals(3, allLocations.size());

            Location firstLoc = allLocations.get(0);
            assertEquals(locAdded1.getId(), firstLoc.getId());
            assertEquals("Local Lamp Post", firstLoc.getName());
            assertEquals("1 Clark Ave.", firstLoc.getAddress());
            assertEquals(new BigDecimal("-26.640281"), firstLoc.getLatitude());
            assertEquals(new BigDecimal("83.469807"), firstLoc.getLongitude());

            Location secondLoc = allLocations.get(1);
            assertEquals(locAdded2.getId(), secondLoc.getId());
            assertEquals("Clear Blue Pond", secondLoc.getName());
            assertEquals("2 Creek Street", secondLoc.getAddress());
            assertEquals(new BigDecimal("-8.386259"), secondLoc.getLatitude());
            assertEquals(new BigDecimal("-70.484343"), secondLoc.getLongitude());

            Location thirdLoc = allLocations.get(2);
            assertEquals(locAdded3.getId(), thirdLoc.getId());
            assertEquals("Sally Statue", thirdLoc.getName());
            assertEquals("4 Selby Lane", thirdLoc.getAddress());
            assertEquals(new BigDecimal("13.487871"), thirdLoc.getLatitude());
            assertEquals(new BigDecimal("58.255566"), thirdLoc.getLongitude());

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    /**
     * Test of getLocationById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetLocationByIdGoldenPath() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location firstLoc = toTest.getLocationById(locAdded1.getId());
            assertEquals(locAdded1.getId(), firstLoc.getId());
            assertEquals("Local Lamp Post", firstLoc.getName());
            assertEquals("1 Clark Ave.", firstLoc.getAddress());
            assertEquals(new BigDecimal("-26.640281"), firstLoc.getLatitude());
            assertEquals(new BigDecimal("83.469807"), firstLoc.getLongitude());

            Location secondLoc = toTest.getLocationById(locAdded2.getId());
            assertEquals(locAdded2.getId(), secondLoc.getId());
            assertEquals("Clear Blue Pond", secondLoc.getName());
            assertEquals("2 Creek Street", secondLoc.getAddress());
            assertEquals(new BigDecimal("-8.386259"), secondLoc.getLatitude());
            assertEquals(new BigDecimal("-70.484343"), secondLoc.getLongitude());

            Location thirdLoc = toTest.getLocationById(locAdded3.getId());
            assertEquals(locAdded3.getId(), thirdLoc.getId());
            assertEquals("Sally Statue", thirdLoc.getName());
            assertEquals("4 Selby Lane", thirdLoc.getAddress());
            assertEquals(new BigDecimal("13.487871"), thirdLoc.getLatitude());
            assertEquals(new BigDecimal("58.255566"), thirdLoc.getLongitude());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetLocationByInvalidId() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location firstLoc = toTest.getLocationById(1584);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addLocation method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddLocationGoldenPath() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location firstLoc = toTest.getLocationById(locAdded1.getId());
            assertEquals(locAdded1.getId(), firstLoc.getId());
            assertEquals("Local Lamp Post", firstLoc.getName());
            assertEquals("1 Clark Ave.", firstLoc.getAddress());
            assertEquals(new BigDecimal("-26.640281"), firstLoc.getLatitude());
            assertEquals(new BigDecimal("83.469807"), firstLoc.getLongitude());

            Location secondLoc = toTest.getLocationById(locAdded2.getId());
            assertEquals(locAdded2.getId(), secondLoc.getId());
            assertEquals("Clear Blue Pond", secondLoc.getName());
            assertEquals("2 Creek Street", secondLoc.getAddress());
            assertEquals(new BigDecimal("-8.386259"), secondLoc.getLatitude());
            assertEquals(new BigDecimal("-70.484343"), secondLoc.getLongitude());

            Location thirdLoc = toTest.getLocationById(locAdded3.getId());
            assertEquals(locAdded3.getId(), thirdLoc.getId());
            assertEquals("Sally Statue", thirdLoc.getName());
            assertEquals("4 Selby Lane", thirdLoc.getAddress());
            assertEquals(new BigDecimal("13.487871"), thirdLoc.getLatitude());
            assertEquals(new BigDecimal("58.255566"), thirdLoc.getLongitude());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddNullLocation() {

        try {
            Location locToAdd1 = null;
            Location locAdded1 = toTest.addLocation(locToAdd1);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    /**
     * Test of updateLocation method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testUpdateLocationGoldenPath() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location updatedLoc = new Location();
            updatedLoc.setId(locAdded3.getId());
            updatedLoc.setName("Zoo");
            updatedLoc.setAddress("9 Pine Street");
            updatedLoc.setLatitude(new BigDecimal("13.978816"));
            updatedLoc.setLongitude(new BigDecimal("53.981198"));
            toTest.updateLocation(updatedLoc);

            Location toCheck = toTest.getLocationById(locAdded3.getId());

            assertEquals(locAdded3.getId(), toCheck.getId());
            assertEquals("Zoo", toCheck.getName());
            assertEquals("9 Pine Street", toCheck.getAddress());
            assertEquals(new BigDecimal("13.978816"), toCheck.getLatitude());
            assertEquals(new BigDecimal("53.981198"), toCheck.getLongitude());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullLocation() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location updatedLoc = null;
            toTest.updateLocation(updatedLoc);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateInvalidLocationId() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            Location updatedLoc = new Location();
            updatedLoc.setId(2388);
            updatedLoc.setName("Zoo");
            updatedLoc.setAddress("9 Pine Street");
            updatedLoc.setLatitude(new BigDecimal("13.978816"));
            updatedLoc.setLongitude(new BigDecimal("53.981198"));
            toTest.updateLocation(updatedLoc);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteLocationById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeleteLocationByIdGoldenPath() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            toTest.deleteLocationById(locAdded2.getId());

            List<Location> allLocations = toTest.getAllLocations();
            assertEquals(2, allLocations.size());

            Location firstLoc = allLocations.get(0);
            assertEquals(locAdded1.getId(), firstLoc.getId());
            assertEquals("Local Lamp Post", firstLoc.getName());
            assertEquals("1 Clark Ave.", firstLoc.getAddress());
            assertEquals(new BigDecimal("-26.640281"), firstLoc.getLatitude());
            assertEquals(new BigDecimal("83.469807"), firstLoc.getLongitude());

            Location thirdLoc = allLocations.get(1);
            assertEquals(locAdded3.getId(), thirdLoc.getId());
            assertEquals("Sally Statue", thirdLoc.getName());
            assertEquals("4 Selby Lane", thirdLoc.getAddress());
            assertEquals(new BigDecimal("13.487871"), thirdLoc.getLatitude());
            assertEquals(new BigDecimal("58.255566"), thirdLoc.getLongitude());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteLocationByInvalidId() {

        try {
            Location locToAdd1 = new Location();
            locToAdd1.setName("Local Lamp Post");
            locToAdd1.setAddress("1 Clark Ave.");
            locToAdd1.setLatitude(new BigDecimal("-26.640281"));
            locToAdd1.setLongitude(new BigDecimal("83.469807"));
            Location locAdded1 = toTest.addLocation(locToAdd1);

            Location locToAdd2 = new Location();
            locToAdd2.setName("Clear Blue Pond");
            locToAdd2.setAddress("2 Creek Street");
            locToAdd2.setLatitude(new BigDecimal("-8.386259"));
            locToAdd2.setLongitude(new BigDecimal("-70.484343"));
            Location locAdded2 = toTest.addLocation(locToAdd2);

            Location locToAdd3 = new Location();
            locToAdd3.setName("Sally Statue");
            locToAdd3.setAddress("4 Selby Lane");
            locToAdd3.setLatitude(new BigDecimal("13.487871"));
            locToAdd3.setLongitude(new BigDecimal("58.255566"));
            Location locAdded3 = toTest.addLocation(locToAdd3);

            toTest.deleteLocationById(7345);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of getAllSightings method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetAllSightingsGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            List<Sighting> allSightings = toTest.getAllSightings();
            assertEquals(3, allSightings.size());

            Sighting firstSighting = allSightings.get(0);
            assertEquals(sightingAdded1.getId(), firstSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 13), firstSighting.getDate());
            assertEquals(locAdded.getId(), firstSighting.getLocation().getId());
            assertEquals(addedSupe1.getId(), firstSighting.getSupe().getId());

            Sighting secondSighting = allSightings.get(1);
            assertEquals(sightingAdded2.getId(), secondSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 14), secondSighting.getDate());
            assertEquals(locAdded.getId(), secondSighting.getLocation().getId());
            assertEquals(addedSupe2.getId(), secondSighting.getSupe().getId());

            Sighting thirdSighting = allSightings.get(2);
            assertEquals(sightingAdded3.getId(), thirdSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 15), thirdSighting.getDate());
            assertEquals(locAdded.getId(), thirdSighting.getLocation().getId());
            assertEquals(addedSupe3.getId(), thirdSighting.getSupe().getId());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    /**
     * Test of getSightingById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testGetSightingByIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting firstSighting = toTest.getSightingById(sightingAdded1.getId());
            assertEquals(sightingAdded1.getId(), firstSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 13), firstSighting.getDate());
            assertEquals(locAdded.getId(), firstSighting.getLocation().getId());
            assertEquals(addedSupe1.getId(), firstSighting.getSupe().getId());

            Sighting secondSighting = toTest.getSightingById(sightingAdded2.getId());
            assertEquals(sightingAdded2.getId(), secondSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 14), secondSighting.getDate());
            assertEquals(locAdded.getId(), secondSighting.getLocation().getId());
            assertEquals(addedSupe2.getId(), secondSighting.getSupe().getId());

            Sighting thirdSighting = toTest.getSightingById(sightingAdded3.getId());
            assertEquals(sightingAdded3.getId(), thirdSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 15), thirdSighting.getDate());
            assertEquals(locAdded.getId(), thirdSighting.getLocation().getId());
            assertEquals(addedSupe3.getId(), thirdSighting.getSupe().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testGetSightingByInvalidId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting firstSighting = toTest.getSightingById(7446);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addSighting method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testAddSightingGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting firstSighting = toTest.getSightingById(sightingAdded1.getId());
            assertEquals(sightingAdded1.getId(), firstSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 13), firstSighting.getDate());
            assertEquals(locAdded.getId(), firstSighting.getLocation().getId());
            assertEquals(addedSupe1.getId(), firstSighting.getSupe().getId());

            Sighting secondSighting = toTest.getSightingById(sightingAdded2.getId());
            assertEquals(sightingAdded2.getId(), secondSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 14), secondSighting.getDate());
            assertEquals(locAdded.getId(), secondSighting.getLocation().getId());
            assertEquals(addedSupe2.getId(), secondSighting.getSupe().getId());

            Sighting thirdSighting = toTest.getSightingById(sightingAdded3.getId());
            assertEquals(sightingAdded3.getId(), thirdSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 15), thirdSighting.getDate());
            assertEquals(locAdded.getId(), thirdSighting.getLocation().getId());
            assertEquals(addedSupe3.getId(), thirdSighting.getSupe().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testAddNullSighting() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = null;
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testAddSightingInvalidLocationId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locNotYetAdded = new Location();
            locNotYetAdded.setId(9388);
            locNotYetAdded.setName("Sewers");
            locNotYetAdded.setAddress("1 Twill Street");
            locNotYetAdded.setLatitude(new BigDecimal("-54.398272"));
            locNotYetAdded.setLongitude(new BigDecimal("134.922347"));

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locNotYetAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testAddSightingNullLocation() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locNotYetAdded = null;

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locNotYetAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testAddSightingInvalidSupeId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeNotYetAdded = new Supe();
            supeNotYetAdded.setId(3255);
            supeNotYetAdded.setName("Petals Z");
            supeNotYetAdded.setDescription("Petals for everyone");
            supeNotYetAdded.setPower(addedPower);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(supeNotYetAdded);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testAddSightingNullSupe() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeNotYetAdded = null;

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(supeNotYetAdded);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    //test for null location / supe objects in sightings??? 
    /**
     * Test of updateSighting method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testUpdateSightingGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(sightingAdded1.getId());
            updatedSighting.setDate(LocalDate.of(2019, 5, 6));
            updatedSighting.setLocation(locAdded);
            updatedSighting.setSupe(addedSupe2);

            toTest.updateSighting(updatedSighting);

            Sighting toCheck = toTest.getSightingById(sightingAdded1.getId());

            assertEquals(sightingAdded1.getId(), toCheck.getId());
            assertEquals(LocalDate.of(2019, 5, 6), toCheck.getDate());
            assertEquals(locAdded.getId(), toCheck.getLocation().getId());
            assertEquals(addedSupe2.getId(), toCheck.getSupe().getId());

        } catch (InvalidIdException | SuperheroSightingsDaoException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateNullSighting() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = null;

            toTest.updateSighting(updatedSighting);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateSightingInvalidLocationId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Location locNotYetAdded = new Location();
            locNotYetAdded.setId(2388);
            locNotYetAdded.setName("Forest");
            locNotYetAdded.setAddress("1231 Willis Street");
            locNotYetAdded.setLatitude(new BigDecimal("-32.345672"));
            locNotYetAdded.setLongitude(new BigDecimal("96.234547"));

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(sightingAdded1.getId());
            updatedSighting.setDate(LocalDate.of(2019, 5, 6));
            updatedSighting.setLocation(locNotYetAdded);
            updatedSighting.setSupe(addedSupe2);

            toTest.updateSighting(updatedSighting);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateSightingNullLocation() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Location locNotYetAdded = null;

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(sightingAdded1.getId());
            updatedSighting.setDate(LocalDate.of(2019, 5, 6));
            updatedSighting.setLocation(locNotYetAdded);
            updatedSighting.setSupe(addedSupe2);

            toTest.updateSighting(updatedSighting);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    @Test
    public void testUpdateSightingInvalidSupeId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeNotYetAdded = new Supe();
            supeNotYetAdded.setId(1267);
            supeNotYetAdded.setName("Petals Z");
            supeNotYetAdded.setDescription("Petals for everyone");
            supeNotYetAdded.setPower(addedPower);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe2);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(sightingAdded1.getId());
            updatedSighting.setDate(LocalDate.of(2019, 5, 6));
            updatedSighting.setLocation(locAdded);
            updatedSighting.setSupe(supeNotYetAdded);

            toTest.updateSighting(updatedSighting);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateSightingNullSupe() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeNotYetAdded = null;

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe2);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            Sighting updatedSighting = new Sighting();
            updatedSighting.setId(sightingAdded1.getId());
            updatedSighting.setDate(LocalDate.of(2019, 5, 6));
            updatedSighting.setLocation(locAdded);
            updatedSighting.setSupe(supeNotYetAdded);

            toTest.updateSighting(updatedSighting);
            fail();

        } catch (InvalidIdException ex) {
            fail();
        } catch (SuperheroSightingsDaoException ex) {
        }
    }

    /**
     * Test of deleteSightingById method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeleteSightingByIdGoldenPath() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            toTest.deleteSightingById(sightingAdded3.getId());

            List<Sighting> allSightings = toTest.getAllSightings();

            assertEquals(2, allSightings.size());

            Sighting firstSighting = allSightings.get(0);
            assertEquals(sightingAdded1.getId(), firstSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 13), firstSighting.getDate());
            assertEquals(locAdded.getId(), firstSighting.getLocation().getId());
            assertEquals(addedSupe1.getId(), firstSighting.getSupe().getId());

            Sighting secondSighting = allSightings.get(1);
            assertEquals(sightingAdded2.getId(), secondSighting.getId());
            assertEquals(LocalDate.of(2019, 3, 14), secondSighting.getDate());
            assertEquals(locAdded.getId(), secondSighting.getLocation().getId());
            assertEquals(addedSupe2.getId(), secondSighting.getSupe().getId());

        } catch (SuperheroSightingsDaoException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteSightingByInvalidId() {

        try {
            Power powerToAdd = new Power();
            powerToAdd.setName("Earth");
            Power addedPower = toTest.addPower(powerToAdd);

            Supe supeToAdd = new Supe();
            supeToAdd.setName("Rocky");
            supeToAdd.setDescription("Rock in your face!");
            supeToAdd.setPower(addedPower);
            Supe addedSupe1 = toTest.addSupe(supeToAdd);

            Supe supeToAdd2 = new Supe();
            supeToAdd2.setName("Sandy");
            supeToAdd2.setDescription("Throws Sand - Ow!");
            supeToAdd2.setPower(addedPower);
            Supe addedSupe2 = toTest.addSupe(supeToAdd2);

            Supe supeToAdd3 = new Supe();
            supeToAdd3.setName("Petals Z");
            supeToAdd3.setDescription("Petals for everyone");
            supeToAdd3.setPower(addedPower);
            Supe addedSupe3 = toTest.addSupe(supeToAdd3);

            Location locToAdd = new Location();
            locToAdd.setName("Sewers");
            locToAdd.setAddress("1 Twill Street");
            locToAdd.setLatitude(new BigDecimal("-54.398272"));
            locToAdd.setLongitude(new BigDecimal("134.922347"));
            Location locAdded = toTest.addLocation(locToAdd);

            Sighting sightingToAdd = new Sighting();
            sightingToAdd.setDate(LocalDate.of(2019, 3, 13));
            sightingToAdd.setLocation(locAdded);
            sightingToAdd.setSupe(addedSupe1);
            Sighting sightingAdded1 = toTest.addSighting(sightingToAdd);

            Sighting sightingToAdd2 = new Sighting();
            sightingToAdd2.setDate(LocalDate.of(2019, 3, 14));
            sightingToAdd2.setLocation(locAdded);
            sightingToAdd2.setSupe(addedSupe2);
            Sighting sightingAdded2 = toTest.addSighting(sightingToAdd2);

            Sighting sightingToAdd3 = new Sighting();
            sightingToAdd3.setDate(LocalDate.of(2019, 3, 15));
            sightingToAdd3.setLocation(locAdded);
            sightingToAdd3.setSupe(addedSupe3);
            Sighting sightingAdded3 = toTest.addSighting(sightingToAdd3);

            toTest.deleteSightingById(2366);
            fail();

        } catch (SuperheroSightingsDaoException ex) {
            fail();
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteAll method, of class SuperheroSightingsDaoDB.
     */
    @Test
    public void testDeleteAllGoldenPath() {
        //not required to test, used for testing set up
    }

}
