package com.missionse.securityhelper.database;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.database.model.BuildingLocation;
import com.missionse.securityhelper.database.model.ExitDoor;
import com.missionse.securityhelper.database.model.Person;

public class LocationDetailFragment extends Fragment {

	private View contentView;
	private BuildingLocation location;

	public LocationDetailFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_location_detail, container);
		return contentView;
	}

	public void setLocation(final BuildingLocation location) {
		this.location = location;
	}

	public void refresh() {
		TextView textView = (TextView) contentView.findViewById(R.id.location_detail_name);
		textView.setText(location.name);

		ListView listView = (ListView) contentView.findViewById(R.id.location_detail_exits);
		listView.setAdapter(new ExitsListAdapter(getActivity(), R.layout.location_detail_exit_entry, location.exits));

		listView = (ListView) contentView.findViewById(R.id.location_detail_people);
		listView.setAdapter(new PersonListAdapter(getActivity(), R.layout.location_detail_person_entry,
				location.peopleInLocation));
	}

	private class PersonListAdapter extends ArrayAdapter<Person> {
		private List<Person> people;
		private int textViewResource;

		public PersonListAdapter(final Context context, final int resource, final List<Person> people) {
			super(context, resource, people);
			textViewResource = resource;
			this.people = people;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(textViewResource, null);
			}
			Person person = people.get(position);
			if (person != null) {
				TextView action = (TextView) convertView.findViewById(R.id.location_detail_person_name);
				action.setText(person.firstName + " " + person.middleName + " " + person.lastName);
			}
			return convertView;
		}
	}

	private class ExitsListAdapter extends ArrayAdapter<ExitDoor> {
		private List<ExitDoor> exits;
		private int textViewResource;

		public ExitsListAdapter(final Context context, final int resource, final List<ExitDoor> exits) {
			super(context, resource, exits);
			textViewResource = resource;
			this.exits = exits;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(textViewResource, null);
			}
			ExitDoor exit = exits.get(position);
			if (exit != null) {
				TextView action = (TextView) convertView.findViewById(R.id.location_detail_exit_name);
				action.setText(exit.name);
			}
			return convertView;
		}
	}
}
