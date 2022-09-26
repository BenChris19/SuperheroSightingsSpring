package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import java.time.LocalDate;

@Getter
/**
 * Sightings object with respective fields
 * @author benat
 *
 */
public class Sighting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private Superhero superhero;
    private Location location;

    private LocalDate date;
    
    //I added setter methods for this class, as I had errors when using lombok setter methods, when I received data from the user from the html file.
	public void setId(int id) {
		this.id = id;
	}

	public void setSuperhero(Superhero superhero) {
		this.superhero = superhero;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}


}
