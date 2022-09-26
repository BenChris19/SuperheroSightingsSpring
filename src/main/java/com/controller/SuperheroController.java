package com.controller;

import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for superhero.html file
 * @author benat
 *
 */
@Controller
public class SuperheroController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * View all of the heroes/villains in the database
     * @param model
     * @return
     */
    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> heroes = superheroDao.getAllHeroes();
        model.addAttribute("superheroes", heroes);
        return "superheroes";
    }

    /**
     * Add a superhero/supervillain to the database
     * @param name
     * @param power
     * @param description
     * @return
     */
    @PostMapping("addSuperhero")
    public String addSuperhero(String name, String power, String description) {
        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setPower(power);
        superhero.setDescription(description);
        superheroDao.addHero(superhero);

        return "redirect:/superheroes";
    }

    /**
     * Delete a superhero or a supervilain from the database
     * @param id
     * @return
     */
    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        superheroDao.deleteHeroById(id);
        return "redirect:/superheroes";
    }

    /**
     * Edit superhero from the database. Redirect to editHero.html page
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getHeroById(id);
        model.addAttribute("superhero", superhero);
        return "editSuperhero";
    }

    /**
     * After succesfully editing the superhero, redirect user to superhero.html page
     * @param superhero
     * @param result
     * @return
     */
    @PostMapping("editSuperhero")
    public String performEditSuperhero(@Valid Superhero superhero, BindingResult result) {
        if(result.hasErrors()) {
            return "editSuperhero";
        }
        superheroDao.updateHero(superhero);
        return "redirect:/superheroes";
    }

}
