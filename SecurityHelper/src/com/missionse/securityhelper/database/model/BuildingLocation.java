package com.missionse.securityhelper.database.model;

import org.orman.mapper.EntityList;
import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.OneToMany;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class BuildingLocation extends Model<BuildingLocation> {
	
	@PrimaryKey(autoIncrement=true)
	public int id;

	public long x;
	
	public long y;
	
	public String name;
	
	@OneToMany( toType = Person.class, onField = "currentLocation")
	public EntityList<BuildingLocation,Person> peopleInLocation = new EntityList<BuildingLocation,Person>(BuildingLocation.class,Person.class,this);
	
	@OneToMany(toType = History.class, onField = "location")
    public EntityList<BuildingLocation, History> history = new EntityList(Person.class, History.class, this);
	
	
	public BuildingLocation(){
		//empty constructor needed for library
	}

}
