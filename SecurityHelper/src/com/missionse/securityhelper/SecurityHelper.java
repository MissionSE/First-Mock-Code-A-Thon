package com.missionse.securityhelper;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.missionse.securityhelper.database.DatabaseFragment;
import com.missionse.securityhelper.map.MapFragment;

public class SecurityHelper extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_helper);

		ActionBar actionBar = this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		Tab tab = actionBar.newTab().setText(R.string.floorplan)
				.setTabListener(new SecurityHelperTabListener<MapFragment>(this, "map", MapFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.database)
				.setTabListener(
						new SecurityHelperTabListener<DatabaseFragment>(this, "database", DatabaseFragment.class));
		actionBar.addTab(tab);
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
				// TODO: handle settings
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
