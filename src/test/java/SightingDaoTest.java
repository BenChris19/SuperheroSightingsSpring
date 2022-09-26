

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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=App.class)
@ComponentScan({"com"})
@EntityScan("com")
@EnableJpaRepositories("com")
public class SightingDaoTest {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public SightingDaoTest(){
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
    public void testAddAndGetSighting() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().toString());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting.getId(), fromDao.getId());
    }

    @Test
    public void testGetAllSightings() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        Superhero hero2 = new Superhero();
        hero2.setName("Frozone");
        hero2.setPower("Ice Bender");
        hero2 = superheroDao.addHero(hero2);

        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().toString());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now().toString());
        sighting2.setSuperhero(hero2);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertEquals(2, sightings.size());
    }

    @Test
    public void testUpdateSighting() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().toString());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting.getId(), fromDao.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting.getId(), fromDao.getId());
    }

    @Test
    public void testDeleteSightingById() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        Location location = new Location();
        location.setName("Eiffel Tower");
        location.setAddress("Paris");
        location.setDescription("Big");
        location.setLatitude(3200);
        location.setLongitude(7682);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().toString());
        sighting.setSuperhero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting.getId(), fromDao.getId());

        sightingDao.deleteSightingById(sighting.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }

}
