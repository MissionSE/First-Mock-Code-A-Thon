package com.missionse.securityhelper.database;

import android.app.Fragment;

import com.missionse.securityhelper.database.model.BuildingLocation;

public class LocationDetailFragment extends Fragment {

	private BuildingLocation location;

	public void setLocation(final BuildingLocation location) {
		this.location = location;
	}

	public void refresh() {

	}

}
