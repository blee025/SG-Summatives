/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.controllers;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sg.superherosightings.models.Location;
import sg.superherosightings.models.Organization;
import sg.superherosightings.models.Power;
import sg.superherosightings.models.Sighting;
import sg.superherosightings.models.Supe;
import sg.superherosightings.services.SuperheroSightingsService;
import sg.superherosightings.services.SuperheroSightingsServiceException;

/**
 *
 * @author blee0
 */
@Controller
public class SupeController {

    @Autowired
    SuperheroSightingsService service;

    @GetMapping("/")
    public String displayHomepage(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "home";
    }

    @GetMapping("/supes")
    public String displaySupes(Model model) {
        List<Supe> supes = service.getAllSupes();
        model.addAttribute("supes", supes);
        return "supes";
    }

    @GetMapping("/powers")
    public String displayPowers(Model model) {
        List<Power> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }

    @PostMapping("/addPower")
    public String addPower(HttpServletRequest request) throws SuperheroSightingsServiceException {
        String name = request.getParameter("name");

        Power newPower = new Power();
        newPower.setName(name);

        service.addPower(newPower);

        return "redirect:/powers";
    }

    @PostMapping("/addLocation")
    public String addLocation(HttpServletRequest request) throws SuperheroSightingsServiceException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        
        Location newLocation = new Location();
        newLocation.setName(name);
        newLocation.setAddress(address);
        newLocation.setLatitude(new BigDecimal(latitude));
        newLocation.setLongitude(new BigDecimal(longitude));
        
        service.addLocation(newLocation);
        
        return "redirect:/locations";
    }
    
    @PostMapping("/addOrganization")
    public String addOrganization(HttpServletRequest request)  throws SuperheroSightingsServiceException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        //String supes = request.getParameter("supes");
        
        Organization newOrganization = new Organization();
        newOrganization.setName(name);
        newOrganization.setDescription(description);
        newOrganization.setAddress(address);
        //newOrganization.setSupes(supes);
        
        service.addOrganization(newOrganization);
        
        return "redirect:/organizations";
        
    }
    
};
