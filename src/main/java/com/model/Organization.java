package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
/**
 * Object for organization with their respective fields
 * @author benat
 *
 */
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String description;
    private List<Superhero> members;

}
