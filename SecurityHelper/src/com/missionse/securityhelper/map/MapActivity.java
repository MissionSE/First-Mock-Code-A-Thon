package com.missionse.securityhelper.map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.missionse.securityhelper.R;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		RelativeLayout mapView = (RelativeLayout) findViewById(R.id.map_view);
		mapView.addView(new MapView(this));
	}

}
