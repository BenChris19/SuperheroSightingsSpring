package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implements LocationDao interface. Give functionality to all of the methods.
 * @author benat
 *
 */
@Repository
public class LocationDaoImpl implements LocationDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * Gets the location by Id
     * @param locationId
     */
    @Override
    public Location getLocationById(int locationId) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM \"LOCATION\" WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), locationId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    /**
     * Get all locations from the database
     */
    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM \"LOCATION\"";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    /**
     * Add to the database
     * @param location
     */
    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO \"LOCATION\"(locationName, locationAddress, locationDescription, locationLatitude, locationLongitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LASTVAL()", Integer.class);
        location.setId(newId);
        return location;
    }

    /**
     * Update location from the database 
     * @param location
     */
    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE \"LOCATION\" SET locationName = ?, locationAddress = ?, locationDescription = ?, locationLatitude = ?, locationLongitude = ? "
                + "WHERE locationId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    /**
     * Finds a location by Id and deletes it from the database
     * @param locationId
     */
    @Override
    public void deleteLocationById(int locationId) {
        final String DELETE_LOCATION = "DELETE FROM \"LOCATION\" WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

    	/**
    	 * Map the location to the database.
    	 * @param rs
    	 * @param index
    	 * @throws SQLException
    	 */
    	@Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("locationId"));
            location.setName(rs.getString("locationName"));
            location.setAddress(rs.getString("locationAddress"));
            location.setDescription(rs.getString("locationDescription"));
            location.setLatitude(rs.getDouble("locationLatitude"));
            location.setLongitude(rs.getDouble("locationLongitude"));

            return location;
        }
    }
}
