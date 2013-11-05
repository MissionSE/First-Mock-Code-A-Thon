package com.missionse.securityhelper.map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.missionse.securityhelper.R;

public class MapFragment extends Fragment {

	private MapView theView;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		RelativeLayout mapView = (RelativeLayout) view.findViewById(R.id.map_view);
		theView = new MapView(getActivity());
		mapView.addView(theView);
		return mapView;
	}

	public void hidePerson() {
		theView.showPerson(false);
	}

	public void showPerson() {
		theView.showPerson(true);
	}

}
