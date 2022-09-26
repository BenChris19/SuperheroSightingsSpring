


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.App;
import com.dao.LocationDao;
import com.dao.OrganizationDao;
import com.dao.SightingDao;
import com.dao.SuperheroDao;
import com.model.Location;
import com.model.Organization;
import com.model.Sighting;
import com.model.Superhero;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=App.class)
@ComponentScan({"com"})
@EntityScan("com")
@EnableJpaRepositories("com")
public class LocationDaoTest {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoTest(){
    }

    @BeforeEach
    public void setUp(){
        List<Superhero> heroes = superheroDao.getAllHeroes();
        for(Superhero superhero : heroes){
            superheroDao.deleteHeroById(superhero.getId());
        }

        List<Organization> orgs = organizationDao.getAllOrganizations();
        for(Organization org : orgs){
            organizationDao.deleteOrganizationById(org.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocationById(location.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings){
            sightingDao.deleteSightingById(sighting.getId());
        }
    }

    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location.getId(), fromDao.getId());
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("The Pyramids");
        location2.setAddress("Egypt");
        location2.setDescription("Sandy");
        location2.setLatitude(4004);
        location2.setLongitude(9827);
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertEquals(locations.get(0).getId(),location.getId());
        assertEquals(locations.get(1).getId(),location2.getId());
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        location.setName("The Louvre");
        locationDao.updateLocation(location);

        assertNotEquals(location.getName(), fromDao.getName());

        location.setAddress("Japan");
        locationDao.updateLocation(location);
        
        assertNotEquals(location.getAddress(), fromDao.getAddress());
        
        location.setDescription("Small");
        locationDao.updateLocation(location);
        
        assertNotEquals(location.getDescription(), fromDao.getDescription());
        
        location.setLongitude(-23);
        locationDao.updateLocation(location);
        
        assertNotEquals(location.getLongitude(), fromDao.getLongitude());
        
        location.setLatitude(543);
        locationDao.updateLocation(location);
        
        assertNotEquals(location.getLatitude(), fromDao.getLatitude());
    }

    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location.getId(), fromDao.getId());

        locationDao.deleteLocationById(location.getId());

        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }


}
