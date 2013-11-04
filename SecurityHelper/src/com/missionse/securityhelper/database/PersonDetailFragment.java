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
import com.missionse.securityhelper.database.model.History;
import com.missionse.securityhelper.database.model.Person;

public class PersonDetailFragment extends Fragment {

	private View contentView;
	private Person person;

	public PersonDetailFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_person_detail, container);
		return contentView;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	public void refresh() {
		TextView textView = (TextView) contentView.findViewById(R.id.person_detail_name);
		textView.setText(person.firstName + " " + person.middleName + " " + person.lastName);

		textView = (TextView) contentView.findViewById(R.id.person_detail_number);
		textView.setText("P: " + person.cubeInfo.phoneNumber);

		textView = (TextView) contentView.findViewById(R.id.person_detail_current_location);
		textView.setText("Location: " + person.currentLocation.name);

		textView = (TextView) contentView.findViewById(R.id.person_detail_cube_name);
		textView.setText("Cube Number: " + person.cubeInfo.name);

		textView = (TextView) contentView.findViewById(R.id.person_detail_cube_location);
		textView.setText("Cube Number: " + person.cubeInfo.buildingLocation.name);

		ListView listView = (ListView) contentView.findViewById(R.id.person_detail_history);
		listView.setAdapter(new PersonHistoryAdapter(getActivity(), R.layout.person_history_entry, person.history));
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
				door.setText(historyEntry.door.name);
				TextView location = (TextView) convertView.findViewById(R.id.person_history_location);
				location.setText(historyEntry.location.name);
			}
			return convertView;
		}
	}

}
