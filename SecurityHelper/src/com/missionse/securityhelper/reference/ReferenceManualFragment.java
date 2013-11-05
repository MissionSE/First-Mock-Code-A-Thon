package com.missionse.securityhelper.reference;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;

public class ReferenceManualFragment extends Fragment {

	private View contentView;

	public ReferenceManualFragment() {

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_reference_manual, null);

		ListView list = (ListView) contentView.findViewById(R.id.ref_manual_list);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
				((SecurityHelper) getActivity()).showSingleManual();
			}

		});
		return contentView;
	}
}
