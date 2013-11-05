package com.missionse.securityhelper.database;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;
import com.missionse.securityhelper.database.model.ExitDoor;
import com.missionse.securityhelper.database.model.Person;

public class LocationDetailFragment extends Fragment {

	private View contentView;

	public LocationDetailFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_location_detail, null);

		List<ExitDoor> exits = new ArrayList<ExitDoor>();
		ExitDoor door1 = new ExitDoor();
		door1.name = "N-E Exit";
		ExitDoor door2 = new ExitDoor();
		door2.name = "S-E Exit";
		exits.add(door1);
		exits.add(door2);

		List<Person> peopleInLocation = new ArrayList<Person>();

		Person person1 = new Person();
		person1.firstName = "Joe";
		person1.middleName = "M.";
		person1.lastName = "Smith";
		Person person2 = new Person();
		person2.firstName = "Jane";
		person2.middleName = "F.";
		person2.lastName = "Johnson";

		peopleInLocation.add(person1);
		peopleInLocation.add(person2);

		TextView textView = (TextView) contentView.findViewById(R.id.location_detail_name);
		textView.setText("C4 Exit");

		ListView exitsList = (ListView) contentView.findViewById(R.id.location_detail_exits);

		exitsList.setAdapter(new ExitsListAdapter(getActivity(), R.layout.location_detail_exit_entry, exits));
		exitsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
				((SecurityHelper) LocationDetailFragment.this.getActivity()).showExitDetail();
			}
		});

		ListView personList = (ListView) contentView.findViewById(R.id.location_detail_people);
		personList.setAdapter(new PersonListAdapter(getActivity(), R.layout.location_detail_person_entry,
				peopleInLocation));

		personList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
				((SecurityHelper) LocationDetailFragment.this.getActivity()).showPersonDetail();
			}

		});

		return contentView;
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
