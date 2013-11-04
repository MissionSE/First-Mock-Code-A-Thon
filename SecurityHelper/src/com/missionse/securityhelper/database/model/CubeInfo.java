package com.missionse.securityhelper.database.model;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class CubeInfo extends Model<CubeInfo> {
	
	@PrimaryKey(autoIncrement=true)
	public int id;

	public BuildingLocation buildingLocation;
	
	public String phoneNumber;
	
	public Person assignedPerson;
	
	public CubeInfo(){
		//empty constructor for library
	}

}
