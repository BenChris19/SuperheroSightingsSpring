package com.dao;

import java.util.List;

import com.model.Sighting;

/**
 * Sightings dao interface
 * @author benat
 *
 */
public interface SightingDao {

    Sighting getSightingById(int sightingId);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    List<Sighting> organizeByDate();
    void deleteSightingById(int sightingid);

}
