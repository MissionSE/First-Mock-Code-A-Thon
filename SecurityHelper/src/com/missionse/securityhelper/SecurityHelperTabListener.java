package com.missionse.securityhelper;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.View;

public class SecurityHelperTabListener implements TabListener {

	private Activity activity;
	private int layout;

	public SecurityHelperTabListener(final Activity activity, final int id) {
		this.activity = activity;
		layout = id;
	}

	@Override
	public void onTabReselected(final Tab tab, final FragmentTransaction transaction) {
		View otherContent;
		if (layout == R.id.left_content) {
			otherContent = activity.findViewById(R.id.right_content);
			showHideOtherContent(otherContent);
		} else if (layout == R.id.right_content) {
			otherContent = activity.findViewById(R.id.left_content);
			showHideOtherContent(otherContent);
		}
	}

	private void showHideOtherContent(final View otherContent) {
		if (otherContent.getVisibility() == View.VISIBLE) {
			otherContent.setVisibility(View.GONE);
		} else if (otherContent.getVisibility() == View.GONE) {
			otherContent.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onTabSelected(final Tab tab, final FragmentTransaction transaction) {
	}

	@Override
	public void onTabUnselected(final Tab tab, final FragmentTransaction transaction) {
	}
}
