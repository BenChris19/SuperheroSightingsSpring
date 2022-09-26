package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Organization;
import com.model.Superhero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Organization implementation implements the organization dao interface
 * @author benat
 *
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * Get organization by Id
     * @param organizationId
     */
    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrgMapper(), organizationId);
            org.setMembers(getHeroesForOrg(organizationId));
            return org;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    /**
     * Gets a superhero or supervillan from an organization
     * @param id
     * @return
     */
    private List<Superhero> getHeroesForOrg(int superId) {
        final String SELECT_HEROES_FOR_ORG = "SELECT s.* FROM superherosupervillan s "
                + "JOIN organizationaffilition so ON so.superId = s.superId WHERE so.organizationId = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORG, new SuperheroDaoImpl.HeroMapper(), superId);
    }

    /**
     * Get all of the organizations from the database
     */
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGS = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS, new OrgMapper());
        associateHeroes(orgs);
        return orgs;
    }

    /**
     * Associate heroes with the organizations
     * @param orgs
     */
    private void associateHeroes(List<Organization> orgs) {
        for (Organization org : orgs) {
            org.setMembers(getHeroesForOrg(org.getId()));
        }
    }

    /**
     * Add organization to the database.
     * @param organization
     */
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORG = "INSERT INTO organization(organizationName, organizationDescription, organizationAddress) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress());

        int newId = jdbc.queryForObject("SELECT LASTVAL()", Integer.class);
        organization.setId(newId);
        insertAffOrg(organization);
        return organization;
    }

    /**
     * Insert organization and affiliate it to superhero/supervillan
     * @param organization
     */
    private void insertAffOrg(Organization organization) {
        final String INSERT_SUPER_ORG = "INSERT INTO "
                + "organizationaffilition(organizationId, superId) VALUES(?,?)";
        for(Superhero hero : organization.getMembers()) {
            jdbc.update(INSERT_SUPER_ORG,
                    organization.getId(),
                    hero.getId());
        }
    }

    /**
     * Update organization from the environment
     */
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORG = "UPDATE organization SET organizationName = ?, organizationDescription = ?, organizationAddress= ? WHERE organizationId = ?";
        jdbc.update(UPDATE_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getId());

        final String DELETE_SUPER_ORG = "DELETE FROM organizationaffilition WHERE organizationId = ?";
        jdbc.update(DELETE_SUPER_ORG, organization.getId());
        insertAffOrg(organization);
    }

    /**
     * Find organization id and delete it from the database
     * @param organizationId
     */
    @Transactional
    public void deleteOrganizationById(int organizationId) {
        final String DELETE_SUPER_ORG = "DELETE FROM organizationaffilition WHERE organizationId = ?";
        jdbc.update(DELETE_SUPER_ORG, organizationId);

        final String DELETE_ORG = "DELETE FROM organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORG, organizationId);
    }

    /**
     * Get an organization for a certain superhero superVillain
     */
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero) {
        final String SELECT_ORGS_FOR_HERO = "SELECT o.* FROM organization o JOIN "
                + "organizationaffilition so ON so.organizationId = o.organizationId WHERE so.superId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_HERO,
                new OrgMapper(), superhero.getId());
        associateHeroes(orgs);
        return orgs;
    }

    public static final class OrgMapper implements RowMapper<Organization> {

    	/**
    	 * Map organization
    	 * @param rs
    	 * @param index
    	 * @throws SQLException
    	 */
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("organizationId"));
            org.setName(rs.getString("organizationName"));
            org.setDescription(rs.getString("organizationDescription"));
            org.setAddress(rs.getString("organizationAddress"));
            return org;
        }
    }
}
