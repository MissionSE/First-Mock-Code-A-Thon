package com.missionse.securityhelper.reference;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.missionse.securityhelper.R;

public class SingleManualFragment extends Fragment {

	private View contentView;

	public SingleManualFragment() {

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_single_manual, null);
		return contentView;
	}
}
