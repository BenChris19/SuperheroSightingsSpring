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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=App.class)
@ComponentScan({"com"})
@EntityScan("com")
@EnableJpaRepositories("com")
public class OrganizationDaoTest {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public OrganizationDaoTest(){
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
    public void testAddAndGetOrg() {
        Superhero hero = new Superhero();
        hero.setName("Frozone");
        hero.setPower("Ice Bender");
        hero = superheroDao.addHero(hero);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization org = new Organization();
        org.setName("The Incredibles");
        org.setAddress("1444 Incredible Way, Municiberg");
        org.setMembers(heroes);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org.getId(), fromDao.getId());
    }

    @Test
    public void testGetAllCourses() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization org = new Organization();
        org.setName("The Incredibles");
        org.setAddress("1444 Incredible Way, Municiberg");
        org.setMembers(heroes);
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Fake Org");
        org2.setAddress("Random Place");
        org2.setMembers(heroes);
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = organizationDao.getAllOrganizations();
        assertEquals(2, orgs.size());
    }

    @Test
    public void testUpdateCourse() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization org = new Organization();
        org.setName("The Incredibles");
        org.setAddress("1444 Incredible Way, Municiberg");
        org.setMembers(heroes);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org.getId(), fromDao.getId());


        fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org.getId(), fromDao.getId());
    }

    @Test
    public void testDeleteCourseById() {
        Superhero hero = new Superhero();
        hero.setName("Elastigirl");
        hero.setPower("Extreme Flexibility");
        hero = superheroDao.addHero(hero);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);

        Organization org = new Organization();
        org.setName("The Incredibles");
        org.setAddress("1444 Incredible Way, Municiberg");
        org.setMembers(heroes);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org.getId(), fromDao.getId());

        organizationDao.deleteOrganizationById(org.getId());

        fromDao = organizationDao.getOrganizationById(org.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetCoursesForStudent() {
        Superhero hero = new Superhero();
        hero.setName("Frozone");
        hero.setPower("Ice Bender");
        hero = superheroDao.addHero(hero);

        Superhero hero2 = new Superhero();
        hero2.setName("ElastiGirl");
        hero2.setPower("Extreme Flexibility");
        hero2 = superheroDao.addHero(hero2);

        List<Superhero> heroes = new ArrayList<>();
        heroes.add(hero);
        heroes.add(hero2);

        List<Superhero> heroes2 = new ArrayList<>();
        heroes2.add(hero2);

        Organization org = new Organization();
        org.setName("The Incredibles");
        org.setAddress("1444 Incredible Way, Municiberg");
        org.setMembers(heroes);
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("New Name");
        org2.setAddress("Nowhere");
        org2.setMembers(heroes2);
        org2 = organizationDao.addOrganization(org2);

        Organization org3 = new Organization();
        org3.setName("Toto");
        org3.setAddress("Africa");
        org3.setMembers(heroes);
        org3 = organizationDao.addOrganization(org3);

        List<Organization> orgs = organizationDao.getOrganizationsForSuperhero(hero);
        assertEquals(2, orgs.size());
    }

}
