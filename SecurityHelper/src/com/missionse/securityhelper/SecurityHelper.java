package com.missionse.securityhelper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.missionse.modelviewer.ModelViewerFragment;
import com.missionse.modelviewer.ModelViewerFragmentFactory;
import com.missionse.modelviewer.ObjectLoadedListener;
import com.missionse.securityhelper.database.ExitDetailFragment;
import com.missionse.securityhelper.database.LocationDetailFragment;
import com.missionse.securityhelper.database.PersonDetailFragment;
import com.missionse.securityhelper.database.PersonListFragment;
import com.missionse.securityhelper.database.model.Person;
import com.missionse.securityhelper.map.MapFragment;

public class SecurityHelper extends Activity implements ObjectLoadedListener {

	private PersonListFragment personListFragment;
	private PersonDetailFragment personDetailFragment;
	private LocationDetailFragment locationDetailFragment;
	private ExitDetailFragment exitDetailFragment;
	private MapFragment mapFragment;
	private ModelViewerFragment modelFragment;

	private SlidingMenu leftMenu;
	private SlidingMenu rightMenu;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_helper);

		personListFragment = new PersonListFragment();
		personDetailFragment = new PersonDetailFragment();
		locationDetailFragment = new LocationDetailFragment();
		exitDetailFragment = new ExitDetailFragment();

		mapFragment = new MapFragment();

		modelFragment = ModelViewerFragmentFactory.createObjModelFragment(R.raw.lobby_obj);
		modelFragment.registerObjectLoadedListener(this);

		leftMenu = new SlidingMenu(this);
		leftMenu.setMode(SlidingMenu.LEFT);
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		leftMenu.setShadowWidthRes(R.dimen.drawer_shadow_width);
		leftMenu.setShadowDrawable(R.drawable.shadow);
		leftMenu.setBehindWidthRes(R.dimen.drawer_width);
		leftMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		leftMenu.setMenu(R.layout.nav_drawer);

		rightMenu = new SlidingMenu(this);
		rightMenu.setMode(SlidingMenu.RIGHT);
		rightMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		rightMenu.setShadowWidthRes(R.dimen.drawer_shadow_width);
		rightMenu.setShadowDrawable(R.drawable.shadow);
		rightMenu.setBehindWidthRes(R.dimen.drawer_width);
		rightMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		rightMenu.setMenu(R.layout.nav_drawer_right);

		FragmentTransaction databaseTransaction = getFragmentManager().beginTransaction();
		databaseTransaction.replace(R.id.left_content, personListFragment);
		databaseTransaction.commit();

		FragmentTransaction mapTransaction = getFragmentManager().beginTransaction();
		mapTransaction.replace(R.id.right_content, mapFragment);
		mapTransaction.commit();

		Fragment leftDrawerFragment;
		if (savedInstanceState == null) {
			FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
			leftDrawerFragment = new SecurityHelperLeftDrawerFragment();
			transaction.replace(R.id.menu_frame, leftDrawerFragment);
			transaction.commit();
		}

		Fragment drawerFragment;
		if (savedInstanceState == null) {
			FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
			drawerFragment = new SecurityHelperRightDrawerFragment();
			transaction.replace(R.id.menu_frame_right, drawerFragment);
			transaction.commit();
		}
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

	public void showPersonList() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, personListFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	public void showPersonDetail(final Person person) {
		personDetailFragment.setPerson(person);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, personDetailFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();

		personDetailFragment.refresh();
	}

	public void showPersonDetail(final String person) {
		personDetailFragment.setPerson(person);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, personDetailFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();

		personDetailFragment.refresh();
	}

	public void showLocation(final String location) {
		locationDetailFragment.setLocation(location);

		FragmentTransaction leftTransaction = getFragmentManager().beginTransaction();
		leftTransaction.replace(R.id.left_content, locationDetailFragment);
		leftTransaction.commit();

		FragmentTransaction rightTransaction = getFragmentManager().beginTransaction();
		rightTransaction.replace(R.id.right_content, modelFragment);
		rightTransaction.commit();

		getFragmentManager().executePendingTransactions();

		locationDetailFragment.refresh();
	}

	public void showExitDetail(final String exit) {
		exitDetailFragment.setExit(exit);

		FragmentTransaction leftTransaction = getFragmentManager().beginTransaction();
		leftTransaction.replace(R.id.left_content, exitDetailFragment);
		leftTransaction.commit();

		getFragmentManager().executePendingTransactions();

		exitDetailFragment.refresh();
	}

	public void showMap() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction leftTransaction = getFragmentManager().beginTransaction();
		leftTransaction.replace(R.id.right_content, mapFragment);
		leftTransaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	@Override
	public void onObjectLoaded() {
		if (modelFragment.getAnimator() != null) {
			modelFragment.getAnimator().scaleTo(0.045f, 250);
			modelFragment.getAnimator().rotateTo(-45f, 225f, 0f, 250);
		}
	}

	@Override
	public void onBackPressed() {
		showPersonList();
	}
}
