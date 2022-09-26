package com.controller;

import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Location;
import com.model.Sighting;
import com.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller for sightings.html
 * @author benat
 *
 */
@Controller
public class SightingController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * View all of the sightings in the database
     * @param model
     * @return
     */
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    /**
     * Add sigting to the database
     * @param sighting
     * @param request
     * @return
     */
    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        sighting.setSuperhero(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setDate(date);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    /**
     * Delete a sighting from the database
     * @param id
     * @return
     */
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    /**
     * Edit a sighting from the database, redirect to edit sighting html page.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    /**
     * After successfully editing a sighting, redirect user to sightings page.
     * @param sighting
     * @param result
     * @param request
     * @param model
     * @return
     */
    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        if(superheroId == null) {
            FieldError error = new FieldError("sighting", "superhero", "Must include a superhero");
            result.addError(error);
        }
        if(locationId == null){
            FieldError error = new FieldError("sighting", "location", "Must include a location");
            result.addError(error);
        }
        if(date == null){
            FieldError error = new FieldError("sighting", "date", "Must include a date");
            result.addError(error);
        }

        sighting.setSuperhero(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setDate(date);
        sightingDao.updateSighting(sighting);


        if(result.hasErrors()) {
            model.addAttribute("sighting", sighting);
            return "editSighting";
        }

        return "redirect:/sightings";
    }

}
