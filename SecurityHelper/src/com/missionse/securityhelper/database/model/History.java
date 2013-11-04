package com.missionse.securityhelper.database.model;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.ManyToOne;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class History extends Model<History> {

	@PrimaryKey(autoIncrement = true)
	public int id;

	@ManyToOne
	public Person person;

	@ManyToOne
	public BuildingLocation location;

	@ManyToOne
	public ExitDoor door;
	
	public long timestamp; 
	
	public HistoryAction personAction; 
	
}
