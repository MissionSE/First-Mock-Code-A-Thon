package com.missionse.securityhelper;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecurityHelperRightDrawerFragment extends ListFragment {
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.drawer_list, null);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		createMenu();
	}

	private void createMenu() {
		final List<String> menuItems = new ArrayList<String>();
		menuItems.add("Map");
		menuItems.add("Camera");
		menuItems.add("Camera Feed");
		menuItems.add("Flashlight");
		menuItems.add("Next Location Finder");
		menuItems.add("Visual Asset Status");
		menuItems.add("Visual Server Status");

		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_list_item, R.id.drawer_entry, menuItems));
	}

	@Override
	public void onListItemClick(final ListView listView, final View view, final int position, final long id) {
		SecurityHelper activity = (SecurityHelper) getActivity();

		String selectedItem = (String) listView.getAdapter().getItem(position);
		if (selectedItem.equals("Map")) {
			activity.showMap();
		} else if (selectedItem.equals("Camera")) {
			activity.showCamera();
		} else if (selectedItem.equals("Camera Feed")) {
			activity.showSecurityVideo();
		} else if (selectedItem.equals("Flashlight")) {
			activity.showFlashlight();
		} else if (selectedItem.equals("Next Location Finder")) {
			activity.showNextLocationFinder();
		} else if (selectedItem.equals("Visual Asset Status")) {
			activity.showVisualAssetStatus();
		} else if (selectedItem.equals("Visual Server Status")) {
			activity.showServerStatus();
		}
	}
}
