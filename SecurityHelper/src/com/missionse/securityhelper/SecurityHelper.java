package com.missionse.securityhelper;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.missionse.securityhelper.database.DatabaseFragment;
import com.missionse.securityhelper.map.MapFragment;

public class SecurityHelper extends Activity {

	private DatabaseFragment databaseFragment;
	private MapFragment mapFragment;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_helper);

		ActionBar actionBar = this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		databaseFragment = new DatabaseFragment();
		mapFragment = new MapFragment();

		SecurityHelperTabListener databaseTabListener = new SecurityHelperTabListener(this, R.id.left_content);
		SecurityHelperTabListener mapTabListener = new SecurityHelperTabListener(this, R.id.right_content);

		Tab databaseTab = actionBar.newTab().setText(R.string.database).setTabListener(databaseTabListener);
		Tab mapTab = actionBar.newTab().setText(R.string.floorplan).setTabListener(mapTabListener);

		actionBar.addTab(databaseTab);
		actionBar.addTab(mapTab);

		FragmentTransaction databaseTransaction = this.getFragmentManager().beginTransaction();
		databaseTransaction.replace(R.id.left_content, databaseFragment);
		databaseTransaction.commit();

		FragmentTransaction mapTransaction = this.getFragmentManager().beginTransaction();
		mapTransaction.replace(R.id.right_content, mapFragment);
		mapTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.security_helper, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
