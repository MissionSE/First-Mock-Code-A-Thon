package com.missionse.securityhelper;

import android.app.Application;

import com.missionse.database.GenericDatabase;
import com.missionse.securityhelper.database.model.BuildingLocation;
import com.missionse.securityhelper.database.model.CubeInfo;
import com.missionse.securityhelper.database.model.ExitDoor;
import com.missionse.securityhelper.database.model.History;
import com.missionse.securityhelper.database.model.Person;

public class SecurityHelperApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		GenericDatabase.initialize(
				getApplicationContext(), /* Context */ 
				"default.db",            /* Database name */
				1,                       /* Database version */
				new Class<?>[]{          /* Entity Classes  */ 
							BuildingLocation.class, 
			                CubeInfo.class,
			                ExitDoor.class,
			                History.class,
			                Person.class,
			                });
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
		

}
