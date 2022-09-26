package com.dao;

import java.util.List;

import com.model.Location;

/**
 * Interface for Location dao
 * @author benat
 *
 */
public interface LocationDao {

    Location getLocationById(int locationId);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int locationId);
}
