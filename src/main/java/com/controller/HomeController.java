package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Sighting;

/**
 * Controller for home.html
 * @author benat
 *
 */
@Controller
public class HomeController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;
    
    /**
     * Home page, when first starting the program it will redirect you to the main page
     * @param model
     * @return
     */
    @GetMapping({"/","home"})
    public String displayHome(Model model) {
        List<Sighting> sightings = sightingDao.organizeByDate();
        model.addAttribute("sightings", sightings);
        return "home";
    }
}
