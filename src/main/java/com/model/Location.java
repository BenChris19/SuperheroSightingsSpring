package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Object for location, with its respective fields
 * @author benat
 *
 */
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private double latitude;

    private double longitude;

    private String description;
}
