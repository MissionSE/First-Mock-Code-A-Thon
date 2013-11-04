package com.missionse.securityhelper.database;

import java.util.ArrayList;
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

public class ExitDetailFragment extends Fragment {

	private View contentView;

	private class DoorHistory {
		public String person;
		public String action;
		public String time;
	}

	public ExitDetailFragment() {

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_exit_detail, null);
		return contentView;
	}

	public void setExit(final String exit) {

	}

	public void refresh() {
		TextView textView = (TextView) contentView.findViewById(R.id.exit_detail_name);
		textView.setText("C4 Door");

		DoorHistory hist1 = new DoorHistory();
		hist1.person = "Roberto V.";
		hist1.action = "ENTER";
		hist1.time = "Nov 12 2013 12:13:12";

		DoorHistory hist2 = new DoorHistory();
		hist2.person = "James S.";
		hist2.action = "EXIT";
		hist2.time = "Nov 23 2013 13:14:15";

		DoorHistory hist3 = new DoorHistory();
		hist3.person = "Mike T.";
		hist3.action = "EXIT";
		hist3.time = "Dec 12 2013 11:09:45";

		List<DoorHistory> history = new ArrayList<DoorHistory>();
		history.add(hist1);
		history.add(hist2);
		history.add(hist3);

		ListView historyList = (ListView) contentView.findViewById(R.id.exit_detail_history);
		historyList.setAdapter(new ExitHistoryAdapter(getActivity(), R.layout.exit_history_entry, history));

	}

	private class ExitHistoryAdapter extends ArrayAdapter<DoorHistory> {
		private List<DoorHistory> historyEntries;
		private int textViewResource;

		public ExitHistoryAdapter(final Context context, final int resource, final List<DoorHistory> history) {
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
			DoorHistory historyEntry = historyEntries.get(position);
			if (historyEntry != null) {
				TextView action = (TextView) convertView.findViewById(R.id.exit_history_person);
				action.setText(historyEntry.person);
				TextView door = (TextView) convertView.findViewById(R.id.exit_history_action);
				door.setText(historyEntry.action);
				TextView location = (TextView) convertView.findViewById(R.id.exit_history_time);
				location.setText(historyEntry.time);
			}
			return convertView;
		}
	}
}
