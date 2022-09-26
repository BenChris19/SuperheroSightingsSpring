package com.dao;

import java.util.List;

import com.model.Superhero;

/**
 * Superhero dao interface
 * @author benat
 *
 */
public interface SuperheroDao {

    Superhero getHeroById(int superId);
    List<Superhero> getAllHeroes();
    Superhero addHero(Superhero superhero);
    void updateHero(Superhero superhero);
    void deleteHeroById(int superId);

}
