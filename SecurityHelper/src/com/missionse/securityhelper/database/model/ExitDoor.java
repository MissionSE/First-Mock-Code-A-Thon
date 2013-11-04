package com.missionse.securityhelper.database.model;

import org.orman.mapper.EntityList;
import org.orman.mapper.Model;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.ManyToOne;
import org.orman.mapper.annotation.OneToMany;
import org.orman.mapper.annotation.PrimaryKey;

@Entity
public class ExitDoor extends Model<ExitDoor> {
	
	@PrimaryKey(autoIncrement=true)
	public int id;
	
	@ManyToOne
	public BuildingLocation location;
	
	@OneToMany( toType = History.class, onField = "door")
	public EntityList<ExitDoor,History> history = new EntityList<ExitDoor,History>(ExitDoor.class,History.class,this);
	
	public String name;

}
