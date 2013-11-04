package com.missionse.securityhelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SecurityHelper extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_helper);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.security_helper, menu);
		return true;
	}

}
