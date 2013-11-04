package com.missionse.securityhelper;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

public class SecurityHelperTabListener<T extends Fragment> implements TabListener {

	private Fragment fragment;
	private Context context;
	private Class<T> fragmentClassType;
	private String transactionTag;

	public SecurityHelperTabListener(final Context activity, final String tag, final Class<T> clazz) {
		context = activity;
		transactionTag = tag;
		fragmentClassType = clazz;
	}

	@Override
	public void onTabReselected(final Tab tab, final FragmentTransaction transaction) {
		// Nothing to do.
	}

	@Override
	public void onTabSelected(final Tab tab, final FragmentTransaction transaction) {
		if (fragment == null) {
			fragment = Fragment.instantiate(context, fragmentClassType.getName());
			transaction.add(R.id.content, fragment, transactionTag);
		} else {
			transaction.attach(fragment);
		}
	}

	@Override
	public void onTabUnselected(final Tab tab, final FragmentTransaction transaction) {
		if (fragment != null) {
			transaction.detach(fragment);
		}
	}
}
