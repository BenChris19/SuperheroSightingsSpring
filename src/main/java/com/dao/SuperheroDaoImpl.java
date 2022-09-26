package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Superhero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Superherpo dao implementation
 * @author benat
 *
 */
@Repository
public class SuperheroDaoImpl implements SuperheroDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * Get a superhero/supervillan from id
     * @param superId
     */
    @Override
    public Superhero getHeroById(int superId) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM superherosupervillan WHERE superid = ?";
            return jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), superId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Get all superhero and supervillans from the database
     */
    @Override
    public List<Superhero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM superherosupervillan";
        return jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
    }

    /**
     * Add hero to the database
     * @param superhero
     */
    @Override
    @Transactional
    public Superhero addHero(Superhero superhero) {
        final String INSERT_HERO = "INSERT INTO superherosupervillan(superName, superdescription, superpower) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower());

        int newId = jdbc.queryForObject("SELECT LASTVAL()", Integer.class);
        superhero.setId(newId);
        return superhero;
    }

    /**
     * Update superhero supervillan to the database
     * @param superhero
     */
    @Override
    public void updateHero(Superhero superhero) {
        final String UPDATE_HERO = "UPDATE superherosupervillan SET superName = ?, superdescription = ?, superpower = ? "
                + "WHERE superId = ?";
        jdbc.update(UPDATE_HERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower(),
                superhero.getId());
    }

    /**
     * Deletes a superhero supervillan, finds it by id
     * @param superId
     */
    @Override
    @Transactional
    public void deleteHeroById(int superId) {
        final String DELETE_SUPER_ORG = "DELETE FROM organizationaffilition WHERE superId = ?";
        jdbc.update(DELETE_SUPER_ORG, superId);

        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE superId = ?";
        jdbc.update(DELETE_SIGHTING, superId);

        final String DELETE_HERO = "DELETE FROM superherosupervillan WHERE superId = ?";
        jdbc.update(DELETE_HERO, superId);
    }

    public static final class HeroMapper implements RowMapper<Superhero> {

    	/**
    	 * Map superhero supervilan
    	 * @param rs
    	 * @param index
    	 * @throws SQLException
    	 */
    	@Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero hero = new Superhero();
            hero.setId(rs.getInt("superId"));
            hero.setName(rs.getString("superName"));
            hero.setDescription(rs.getString("superdescription"));
            hero.setPower(rs.getString("superpower"));

            return hero;
        }
    }

}
