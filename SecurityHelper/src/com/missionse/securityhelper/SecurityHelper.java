package com.missionse.securityhelper;

import gl.MarkerObject;
import gl.scenegraph.MeshComponent;
import system.ArActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.missionse.augmented.setups.BasicMultiSetup;
import com.missionse.modelviewer.ModelViewerFragment;
import com.missionse.modelviewer.ModelViewerFragmentFactory;
import com.missionse.modelviewer.ObjectLoadedListener;
import com.missionse.securityhelper.augmented.GenerateMeshComponents;
import com.missionse.securityhelper.database.ExitDetailFragment;
import com.missionse.securityhelper.database.LocationDetailFragment;
import com.missionse.securityhelper.database.PersonDetailFragment;
import com.missionse.securityhelper.database.PersonListFragment;
import com.missionse.securityhelper.map.MapFragment;
import com.missionse.securityhelper.picture.PictureFragment;
import com.missionse.securityhelper.reference.ReferenceManualFragment;
import com.missionse.securityhelper.reference.SingleManualFragment;
import com.missionse.securityhelper.video.VideoFragment;

public class SecurityHelper extends Activity implements ObjectLoadedListener {

	static final int TAKE_SECURITY_PICTURE = 1234;

	private boolean flashlightOn = false;
	private boolean showingPersonIcon = false;
	private boolean mapFragmentShowing = false;

	private PersonListFragment personListFragment;
	private PersonDetailFragment personDetailFragment;
	private LocationDetailFragment locationDetailFragment;
	private ExitDetailFragment exitDetailFragment;
	private MapFragment mapFragment;
	private ModelViewerFragment modelFragment;
	private VideoFragment videoFragment;
	private ReferenceManualFragment refManualFragment;
	private SingleManualFragment singleManualFragment;
	private PictureFragment pictureFragment;

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
		refManualFragment = new ReferenceManualFragment();
		singleManualFragment = new SingleManualFragment();
		pictureFragment = new PictureFragment();

		videoFragment = new VideoFragment();

		mapFragment = new MapFragment();

		modelFragment = ModelViewerFragmentFactory.createObjModelFragment(R.raw.lobby_obj);
		modelFragment.registerObjectLoadedListener(this);

		leftMenu = new SlidingMenu(this);
		leftMenu.setMode(SlidingMenu.LEFT);
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		leftMenu.setShadowWidthRes(R.dimen.drawer_shadow_width);
		leftMenu.setShadowDrawable(R.drawable.shadow_left);
		leftMenu.setBehindWidthRes(R.dimen.drawer_width);
		leftMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		leftMenu.setMenu(R.layout.nav_drawer);

		rightMenu = new SlidingMenu(this);
		rightMenu.setMode(SlidingMenu.RIGHT);
		rightMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		rightMenu.setShadowWidthRes(R.dimen.drawer_shadow_width);
		rightMenu.setShadowDrawable(R.drawable.shadow_right);
		rightMenu.setBehindWidthRes(R.dimen.drawer_width);
		rightMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		rightMenu.setMenu(R.layout.nav_drawer_right);

		FragmentTransaction databaseTransaction = getFragmentManager().beginTransaction();
		databaseTransaction.replace(R.id.left_content, personListFragment);
		databaseTransaction.commit();

		FragmentTransaction mapTransaction = getFragmentManager().beginTransaction();
		mapTransaction.replace(R.id.right_content, mapFragment);
		mapTransaction.commit();
		mapFragmentShowing = true;

		Fragment leftDrawerFragment;
		if (savedInstanceState == null) {
			FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
			leftDrawerFragment = new SecurityHelperLeftDrawerFragment();
			transaction.replace(R.id.menu_frame, leftDrawerFragment);
			transaction.commit();
		}

		Fragment rightDrawerFragment;
		if (savedInstanceState == null) {
			FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
			rightDrawerFragment = new SecurityHelperRightDrawerFragment();
			transaction.replace(R.id.menu_frame_right, rightDrawerFragment);
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

	public void showPersonDetail() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, personDetailFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	public void showSecurityVideo() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.right_content, videoFragment).addToBackStack("video");
		transaction.commit();

		mapFragmentShowing = false;

		getFragmentManager().executePendingTransactions();
	}

	public void showLocation() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction leftTransaction = getFragmentManager().beginTransaction();
		leftTransaction.replace(R.id.left_content, locationDetailFragment);
		leftTransaction.commit();

		FragmentTransaction rightTransaction = getFragmentManager().beginTransaction();
		rightTransaction.replace(R.id.right_content, modelFragment).addToBackStack("model");
		rightTransaction.commit();

		mapFragmentShowing = false;

		getFragmentManager().executePendingTransactions();
	}

	public void showLocationOnly() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction leftTransaction = getFragmentManager().beginTransaction();
		leftTransaction.replace(R.id.left_content, locationDetailFragment);
		leftTransaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	public void showExitDetail() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, exitDetailFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	public void showMap() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.right_content, mapFragment);
		transaction.commit();

		mapFragmentShowing = false;

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
		super.onBackPressed();
	}

	public void showReferenceManuals() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.left_content, refManualFragment);
		transaction.commit();

		getFragmentManager().executePendingTransactions();
	}

	public void showCamera() {
		leftMenu.showContent();
		rightMenu.showContent();

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, TAKE_SECURITY_PICTURE);
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		// Check which request we're responding to
		if (requestCode == TAKE_SECURITY_PICTURE) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				Bundle extras = data.getExtras();
				Bitmap mImageBitmap = (Bitmap) extras.get("data");

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.right_content, pictureFragment).addToBackStack("singleman");
				transaction.commit();

				mapFragmentShowing = false;

				getFragmentManager().executePendingTransactions();

				pictureFragment.setImageBitmap(mImageBitmap);
			}
		}
	}

	public void showFlashlight() {
		leftMenu.showContent();
		rightMenu.showContent();
		Camera cam;
		if (flashlightOn) {
			// cam = Camera.open();
			// cam.stopPreview();
			// cam.release();
			flashlightOn = false;
		} else {

			// cam = Camera.open();
			// Parameters cameraParameters = cam.getParameters();
			// cameraParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
			// cam.setParameters(cameraParameters);
			// cam.startPreview();
			flashlightOn = true;
		}
	}

	public void showNextLocationFinder() {
		leftMenu.showContent();
		rightMenu.showContent();

		BasicMultiSetup setup = new BasicMultiSetup(false);
		for (MeshComponent m : GenerateMeshComponents.getNextLocation(setup)) {
			setup.addObject(m);
		}
		ArActivity.startWithSetup(this, setup);
	}

	public void showVisualAssetStatus() {
		leftMenu.showContent();
		rightMenu.showContent();

		BasicMultiSetup setup = new BasicMultiSetup(false);
		for (MeshComponent m : GenerateMeshComponents.getRandomDots(setup)) {
			setup.addObject(m);
		}
		ArActivity.startWithSetup(this, setup);
	}

	public void showServerStatus() {
		leftMenu.showContent();
		rightMenu.showContent();

		BasicMultiSetup setup = new BasicMultiSetup(false);
		for (MarkerObject m : GenerateMeshComponents.getServerInfo(setup)) {
			setup.addMarker(m);
		}
		ArActivity.startWithSetup(this, setup);
	}

	public void showSingleManual() {
		leftMenu.showContent();
		rightMenu.showContent();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.right_content, singleManualFragment).addToBackStack("singleman");
		transaction.commit();

		mapFragmentShowing = false;

		getFragmentManager().executePendingTransactions();
	}

	public void togglePersonIcon() {
		if (mapFragmentShowing) {
			if (showingPersonIcon) {
				mapFragment.hidePerson();
				showingPersonIcon = false;
			} else {
				mapFragment.showPerson();
				showingPersonIcon = true;
			}
		}

	}

	public void showEvacZones() {
		leftMenu.showContent();
		rightMenu.showContent();
	}
}
