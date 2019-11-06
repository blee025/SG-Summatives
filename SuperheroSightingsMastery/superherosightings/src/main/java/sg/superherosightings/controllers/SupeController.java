/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sg.superherosightings.daos.InvalidIdException;
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

    Set<ConstraintViolation<Supe>> supeViolations = new HashSet<>();
    Set<ConstraintViolation<Power>> powerViolations = new HashSet<>();
    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();
    Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();
    Set<ConstraintViolation<Sighting>> sightingViolations = new HashSet<>();

    @GetMapping("/")
    public String displayHomepage(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "home";
    }

    @GetMapping("/supes")
    public String displaySupes(Model model) {
        Supe supe = new Supe();
        model.addAttribute("supe", supe);
        List<Supe> supes = service.getAllSupes();
        List<Power> powers = service.getAllPowers();
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("supes", supes);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", supeViolations);

        return "supes";
    }

    @GetMapping("/powers")
    public String displayPowers(Model model) {
        List<Power> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("errors", powerViolations);
        return "powers";
    }

    @GetMapping("/organizations")
    public String displayOrganizations(Model model) {
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        List<Organization> organizations = service.getAllOrganizations();
        List<Supe> supes = service.getAllSupes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("supes", supes);
        model.addAttribute("errors", organizationViolations);

        return "organizations";
    }

    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", locationViolations);
        return "locations";
    }

    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        List<Supe> supes = service.getAllSupes();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("supes", supes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", sightingViolations);
        return "sightings";
    }

    @PostMapping("/addPower")
    public String addPower(Power power, HttpServletRequest request) throws SuperheroSightingsServiceException {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        powerViolations = validate.validate(power);
        String name = request.getParameter("name");

        //Power newPower = new Power();
        power.setName(name);

        if (powerViolations.isEmpty()) {
            service.addPower(power);
        }

        return "redirect:/powers";
    }

    @PostMapping("/addLocation")
    public String addLocation(Location location, HttpServletRequest request) throws SuperheroSightingsServiceException {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        locationViolations = validate.validate(location);

//        String name = request.getParameter("name");
//        String address = request.getParameter("address");
//        String latitude = request.getParameter("latitude");
//        String longitude = request.getParameter("longitude");
//
//        //Location newLocation = new Location();
//        location.setName(name);
//        location.setAddress(address);
//        location.setLatitude(new BigDecimal(latitude));
//        location.setLongitude(new BigDecimal(longitude));
        if (locationViolations.isEmpty()) {
            service.addLocation(location);
        }

        return "redirect:/locations";
    }

    @PostMapping("/addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) throws SuperheroSightingsServiceException, InvalidIdException {
        String[] supeIds = request.getParameterValues("supeId");

        List<Supe> supes = new ArrayList<>();
        if (supeIds != null) {
            for (String supeId : supeIds) {
                supes.add(service.getSupeById(Integer.parseInt(supeId)));
            }
        } else {
            FieldError error = new FieldError("organization", "supes", "Must include one supe.");
            result.addError(error);
        }

        organization.setSupes(supes);

        if (result.hasErrors()) {
            model.addAttribute("supes", service.getAllSupes());
            model.addAttribute("organization", organization);

            return "organizations";
        }

        service.addOrganization(organization);

//        String name = request.getParameter("name");
//        String description = request.getParameter("description");
//        String address = request.getParameter("address");
//        //String supes = request.getParameter("supes");
//
//        Organization newOrganization = new Organization();
//        newOrganization.setName(name);
//        newOrganization.setDescription(description);
//        newOrganization.setAddress(address);
//        //newOrganization.setSupes(supes);
        return "redirect:/organizations";
    }

    @PostMapping("/addSupe")
    public String addSupe(@Valid Supe supe, BindingResult result, HttpServletRequest request, Model model) throws SuperheroSightingsServiceException, InvalidIdException {

//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        supeViolations = validate.validate(supe);
        String powerId = request.getParameter("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        supe.setPower(service.getPowerById(Integer.parseInt(powerId)));

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(service.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("supe", "organizations", "Must include one organization");
            result.addError(error);
        }

        supe.setOrganizations(organizations);

        if (result.hasErrors()) {
            model.addAttribute("powers", service.getAllPowers());
            model.addAttribute("organizations", service.getAllOrganizations());
            model.addAttribute("supe", supe);
            return "supes";
        }

        service.addSupe(supe);

        return "redirect:/supes";
    }

    @PostMapping("/addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) throws SuperheroSightingsServiceException, InvalidIdException {

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        sightingViolations = validate.validate(sighting);

//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
//        String date = request.getParameter("date");
//
//        LocalDate localDate = LocalDate.parse(date, formatter);
//
//        sighting.setDate(localDate);
        String supeId = request.getParameter("supeId");
        String locationId = request.getParameter("locationId");

        sighting.setSupe(service.getSupeById(Integer.parseInt(supeId)));
        sighting.setLocation(service.getLocationById(Integer.parseInt(locationId)));

        if (sightingViolations.isEmpty()) {
            service.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSupe")
    public String deleteSupe(HttpServletRequest request) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSupebyId(id);

        return "redirect:/supes";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrganizationById(id);

        return "redirect:/organizations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deletePowerById(id);

        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = service.getPowerById(id);

        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SuperheroSightingsServiceException {

        if (result.hasErrors()) {
            model.addAttribute("power", power);
            
            return "editPower";
        }
            
        service.updatePower(power);
        

        return "redirect:/powers";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) throws InvalidIdException {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = service.getLocationById(id);

        model.addAttribute("location", location);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SuperheroSightingsServiceException {

//        int id = Integer.parseInt(request.getParameter("id"));
//        Location location = service.getLocationById(id);
//
//        location.setName(request.getParameter("name"));
//        location.setAddress(request.getParameter("address"));
//        location.setLatitude(new BigDecimal(request.getParameter("latitude")));
//        location.setLongitude(new BigDecimal(request.getParameter("longitude")));
        if (result.hasErrors()) {
            model.addAttribute("location", location);
            
            return "editLocation";
        }

        service.updateLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("editSupe")
    public String editSupe(Integer id, Model model) throws InvalidIdException {
        Supe supe = service.getSupeById(id);
        List<Power> powers = service.getAllPowers();
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("supe", supe);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "editSupe";
    }

    @PostMapping("editSupe")
    public String performEditSupe(@Valid Supe supe, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SuperheroSightingsServiceException {

        String powerId = request.getParameter("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        supe.setPower(service.getPowerById(Integer.parseInt(powerId)));

        List<Organization> organizations = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                organizations.add(service.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("supe", "organizations", "Must include one organization");
            result.addError(error);
        }

        supe.setOrganizations(organizations);

        if (result.hasErrors()) {
            model.addAttribute("powers", service.getAllPowers());
            model.addAttribute("organizations", service.getAllOrganizations());
            model.addAttribute("supe", supe);

            return "editSupe";
        }

        service.updateSupe(supe);

        return "redirect:/supes";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) throws InvalidIdException {
        Organization organization = service.getOrganizationById(id);
        List<Supe> supes = service.getAllSupes();
        model.addAttribute("organization", organization);
        model.addAttribute("supes", supes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SuperheroSightingsServiceException {
        String[] supeIds = request.getParameterValues("supeId");

        List<Supe> supes = new ArrayList<>();
        if (supeIds != null) {
            for (String supeId : supeIds) {
                supes.add(service.getSupeById(Integer.parseInt(supeId)));
            }
        } else {
            FieldError error = new FieldError("organization", "supes", "Must include one supe.");
            result.addError(error);
        }

        organization.setSupes(supes);

        if (result.hasErrors()) {
            model.addAttribute("supes", service.getAllSupes());
            model.addAttribute("organization", organization);

            return "editOrganization";
        }

        service.updateOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) throws InvalidIdException {
        Sighting sighting = service.getSightingById(id);
        List<Location> locations = service.getAllLocations();
        List<Supe> supes = service.getAllSupes();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        model.addAttribute("supes", supes);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, SuperheroSightingsServiceException {

        String locationId = request.getParameter("locationId");
        String supeId = request.getParameter("supeId");

        sighting.setLocation(service.getLocationById(Integer.parseInt(locationId)));
        sighting.setSupe(service.getSupeById(Integer.parseInt(supeId)));

        if (result.hasErrors()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("locations", service.getAllLocations());
            model.addAttribute("supes", service.getAllSupes());

            return "editSighting";
        }

        service.updateSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("supeDetail")
    public String supeDetail(Integer id, Model model) throws InvalidIdException {
        Supe supe = service.getSupeById(id);
        model.addAttribute("supe", supe);
        return "supeDetail";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) throws InvalidIdException {
        Organization organization = service.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

};
