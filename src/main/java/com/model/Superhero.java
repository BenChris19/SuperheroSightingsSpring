package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Superhero object with respective fields.
 * @author benat
 *
 */
public class Superhero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String power;

}
