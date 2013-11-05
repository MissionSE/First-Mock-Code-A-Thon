package com.missionse.securityhelper.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;
import com.missionse.securityhelper.database.model.BuildingLocation;
import com.missionse.securityhelper.database.model.History;
import com.missionse.securityhelper.database.model.HistoryAction;

public class PersonDetailFragment extends Fragment {

	private View contentView;
	private String personDummy;

	public PersonDetailFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_person_detail, null);

		Random rand = new Random();
		int index = rand.nextInt(2);

		List<String> firstname = new ArrayList();
		firstname.add("John");
		firstname.add("Jane");
		firstname.add("Ralph");

		personDummy = firstname.get(index);
		TextView textView = (TextView) contentView.findViewById(R.id.person_detail_name);
		textView.setText(personDummy + " Smith");

		textView = (TextView) contentView.findViewById(R.id.person_detail_number);
		textView.setText("P: " + "856.345.3245");

		textView = (TextView) contentView.findViewById(R.id.person_detail_current_location);
		textView.setText("Current Location: " + "Lobby");
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				((SecurityHelper) getActivity()).togglePersonIcon();
			}

		});

		textView = (TextView) contentView.findViewById(R.id.person_detail_cube_name);
		textView.setText("Cube Number: " + "R-12B");

		textView = (TextView) contentView.findViewById(R.id.person_detail_cube_location);
		textView.setText("Cube Location: " + "Radar");

		List<History> history = new ArrayList<History>();
		History hist1 = new History();
		hist1.personAction = HistoryAction.ENTER_ROOM;
		hist1.timestamp = 12344L;
		BuildingLocation loc1 = new BuildingLocation();
		loc1.name = "CND";
		hist1.location = loc1;

		History hist2 = new History();
		hist2.personAction = HistoryAction.EXIT_ROOM;
		hist2.timestamp = 12345L;
		BuildingLocation loc2 = new BuildingLocation();
		loc2.name = "Radar";
		hist2.location = loc2;

		History hist3 = new History();
		hist3.personAction = HistoryAction.ENTER_ROOM;
		hist3.timestamp = 12346L;
		BuildingLocation loc3 = new BuildingLocation();
		loc3.name = "Lobby";
		hist3.location = loc3;

		history.add(hist1);
		history.add(hist2);
		history.add(hist3);

		ListView listView = (ListView) contentView.findViewById(R.id.person_detail_history);
		listView.setAdapter(new PersonHistoryAdapter(getActivity(), R.layout.person_history_entry, history));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
				((SecurityHelper) getActivity()).showLocationOnly();
			}

		});
		return contentView;
	}

	private class PersonHistoryAdapter extends ArrayAdapter<History> {
		private List<History> historyEntries;
		private int textViewResource;

		public PersonHistoryAdapter(final Context context, final int resource, final List<History> history) {
			super(context, resource, history);
			textViewResource = resource;
			historyEntries = history;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(textViewResource, null);
			}
			History historyEntry = historyEntries.get(position);
			if (historyEntry != null) {
				TextView action = (TextView) convertView.findViewById(R.id.person_history_action);
				action.setText(historyEntry.personAction.toString());
				TextView door = (TextView) convertView.findViewById(R.id.person_history_door);
				// door.setText(historyEntry.door.name);
				door.setText("Exit 2A");
				TextView location = (TextView) convertView.findViewById(R.id.person_history_location);
				// location.setText(historyEntry.location.name);
				location.setText("Location: MSE Cafe");

			}
			return convertView;
		}
	}

}
