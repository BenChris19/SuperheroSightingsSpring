package com.controller;

import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for location.html
 * @author benat
 *
 */
@Controller
public class LocationController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * See all of the locations in the sigtings
     * @param model
     * @return
     */
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    /**
     * Add a location to the database
     * @param name
     * @param address
     * @param description
     * @param latitude
     * @param longitude
     * @return
     */
    @PostMapping("addLocation")
    public String addLocation(String name, String address, String description, double latitude, double longitude) {
        Location location = new Location();
        location.setName(name);
        location.setAddress(address);
        location.setDescription(description);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    /**
     * Delete a location from the database
     * @param id
     * @return
     */
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

    /**
     * Edit location from the database. Will redirect the user to a edit location html page.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    /**
     * After submitting the change to a location to the database, redirect to the locations page
     * @param location
     * @param result
     * @return
     */
    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if(result.hasErrors()) {
            return "editLocation";
        }
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

}
