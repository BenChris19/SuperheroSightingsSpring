package com.dao;

import java.util.List;

import com.model.Organization;
import com.model.Superhero;

/**
 * Organization dao interface
 * @author benat
 *
 */
public interface OrganizationDao {

    Organization getOrganizationById(int organizationid);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganizationById(int id);
    List<Organization> getOrganizationsForSuperhero(Superhero superhero);

}
