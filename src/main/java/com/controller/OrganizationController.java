package com.controller;

import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Organization;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for organization.html
 * @author benat
 *
 */
@Controller
public class OrganizationController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * View all of the organizations in the database
     * @param model
     * @return
     */
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("superheroes", superheroes);
        return "organizations";
    }

    /**
     * Add an organization to the database
     * @param organization
     * @param request
     * @return
     */
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] superheroIds = request.getParameterValues("superheroId");

        List<Superhero> superheroes = new ArrayList<>();
        for(String superheroId : superheroIds) {
            superheroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }
        organization.setMembers(superheroes);
        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    /**
     * Edit an organization from the database
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        model.addAttribute("organization", organization);
        model.addAttribute("superheroes", superheroes);
        return "editOrganization";
    }

    /**
     * After successfully editing the organization, redirect the user to the organization html page
     * @param organization
     * @param result
     * @param request
     * @param model
     * @return
     */
    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        String[] superheroIds = request.getParameterValues("superheroId");

        List<Superhero> superheroes = new ArrayList<>();
        if(superheroIds != null) {
            for(String superheroId : superheroIds) {
                superheroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
            }
        } else {
            FieldError error = new FieldError("organization", "superheroes", "Must include one superhero");
            result.addError(error);
        }

        organization.setMembers(superheroes);

        if(result.hasErrors()) {
            model.addAttribute("superheroes", superheroDao.getAllHeroes());
            model.addAttribute("organization", organization);
            return "editOrganization";
        }

        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }


}
