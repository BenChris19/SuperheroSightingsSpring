package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.Location;
import com.model.Sighting;
import com.model.Superhero;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implmenets sighting dao interface
 * @author benat
 *
 */
@Repository
public class SightingDaoImpl implements SightingDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * Get a sighting by id from the database
     * @param sightingId
     */
    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sightings WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightMapper(), sightingId);
            sighting.setSuperhero(getHeroForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    public List<Sighting> organizeByDate(){
        try {
            final String SELECT_ALL_SIGHTING_ORGANISED = "SELECT * FROM sightings ORDER BY sightdate DESC";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTING_ORGANISED, new SightMapper());
            int i = 0;
            List<Sighting> newestSightings = new ArrayList<>();
            
            while(i<sightings.size() && i<10) {
            	newestSightings.add(sightings.get(i));
            	i+=1;
            }
            addHeroAndLocationToSightings(newestSightings);
            return newestSightings;
        } catch(DataAccessException ex) {
            return null;
        }    	
    }

    /**
     * Get sighting from db
     * @param sighting
     */
    private Superhero getHeroForSighting(Sighting sighting) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT s.* FROM superherosupervillan s "
                + "JOIN sightings si ON s.superId = si.superId WHERE si.sightingId = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new SuperheroDaoImpl.HeroMapper(),
                sighting.getId());
    }

    private Location getLocationForSighting(Sighting sighting) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM \"LOCATION\" l "
                + "JOIN sightings si ON l.locationId = si.locationId WHERE si.sightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoImpl.LocationMapper(),
                sighting.getId());
    }

    /**
     * Get all sightings from the database
     */
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sightings";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightMapper());

        addHeroAndLocationToSightings(sightings);

        return sightings;
    }

    /**
     * Add a hero and a location to the sightings
     * @param sightings
     */
    private void addHeroAndLocationToSightings(List<Sighting> sightings) {
        for(Sighting sighting : sightings) {
            sighting.setSuperhero(getHeroForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
        }
    }

    /**
     * Add sighting to the database
     * @param sighting
     */
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sightings(sightDate, superId, locationId) VALUES(?,?,?)";
        System.out.print("\n\n*************\n\n");
        jdbc.update(INSERT_SIGHTING,
                Date.valueOf(sighting.getDate()),
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId());
        int newId = jdbc.queryForObject("SELECT LASTVAL()", Integer.class);
        sighting.setId(newId);

        return sighting;
    }

    /**
     * Update sighting from database
     * @param sighting
     */
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sightings "
                + "SET sightDate = ?, superId = ?, locationId = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getSuperhero().getId(),
                sighting.getLocation().getId(),
                sighting.getId());
    }

   /**
    * Delete sighting by id from database
    */
    public void deleteSightingById(int sightingId) {
        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, sightingId);
    }

    public static final class SightMapper implements RowMapper<Sighting> {

    	/**
    	 * Map sighting to database
    	 * @param rs
    	 * @param index
    	 * @throws SQLException
    	 */
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("sightDate").toString());

            return sighting;
        }
    }
}
