package com.missionse.securityhelper.database.model;

import org.orman.mapper.EntityList;
import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.ManyToOne;
import org.orman.mapper.annotation.OneToMany;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class Person extends Model<Person> {
	
	@PrimaryKey(autoIncrement=true)
	public int id;
	
	public String firstName;
	
	public String lastName;
	
	public String middleName;
	
	public boolean handicap;
	
	public CubeInfo cubeInfo;
	
	@OneToMany(toType = History.class, onField = "person")
    public EntityList<Person, History> history = new EntityList(Person.class, History.class, this);
	
	@ManyToOne
	public BuildingLocation currentLocation;
	
	
	
	public Person(){
		//emtpy constructor needed for library
	}
	

}
