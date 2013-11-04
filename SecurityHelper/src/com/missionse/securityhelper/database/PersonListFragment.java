package com.missionse.securityhelper.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;
import com.missionse.securityhelper.database.model.Person;

public class PersonListFragment extends ListFragment {

	// private List<Person> persons = new LinkedList<Person>();
	private EditText searchBox;
	private ListView listView;
	private int textlength = 0;
	private ArrayList<Person> array_sort = new ArrayList<Person>();
	private static List<Person> array_pre;
	private boolean isFiltered = false;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

		View lRootView = inflater.inflate(R.layout.fragment_person_list, container, false);

		initComponents(lRootView);

		// updatePersonList(Model.fetchAll(Person.class));
		updatePersonList(getPeople());

		return lRootView;

	}

	public static List<Person> getPeople() {
		String[] fn = new String[] { "Roberto", "James", "Kyle", "Ron", "Mike", "Eric" };
		List<Person> persons = new LinkedList<Person>();
		Random r = new Random();
		for (int x = 0; x < 20; x++) {
			Person p = new Person();
			p.firstName = fn[r.nextInt(fn.length)];
			p.lastName = fn[r.nextInt(fn.length)];
			persons.add(p);
		}
		array_pre = persons;
		return persons;
	}

	private void updatePersonList(final List<Person> persons) {

		ListAdapter l = new PersonAdapter(getActivity(), R.layout.person_list_entry, persons);
		setListAdapter(l);

	}

	private void initComponents(final View tRoot) {
		searchBox = (EditText) tRoot.findViewById(R.id.person_list_searchtextbox);
		searchBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(final Editable s) {
			}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
			}

			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
				textlength = searchBox.getText().length();
				array_sort.clear();
				for (int i = 0; i < array_pre.size(); i++) {
					if (textlength <= array_pre.get(i).toString().length()) {
						if (searchBox.getText().toString()
								.equalsIgnoreCase((String) array_pre.get(i).toString().subSequence(0, textlength))) {
							array_sort.add(array_pre.get(i));
						}
					}
				}
				if (textlength > 0) {
					isFiltered = true;
					updatePersonList(array_sort);
				} else {
					isFiltered = false;
					// updatePersonList(Model.fetchAll(Person.class));
					updatePersonList(getPeople());
				}
			}
		});
		listView = (ListView) tRoot.findViewById(android.R.id.list);
		// listView.setOnItemClickListener(this);
	}

	@Override
	public void onListItemClick(final ListView l, final View v, final int position, final long id) {
		if (!isFiltered) {
			Person p = array_pre.get(position);
			((SecurityHelper) this.getActivity()).showPersonDetail(p.firstName);
		} else {
			Person p = array_sort.get(position);
			((SecurityHelper) this.getActivity()).showPersonDetail(p.firstName);
		}

	}

	private class PersonAdapter extends ArrayAdapter<Person> {

		private List<Person> data = new LinkedList<Person>();
		private int layoutid;

		public PersonAdapter(final Context context, final int resource, final List<Person> objects) {
			super(context, resource, objects);
			layoutid = resource;
			data = objects;

		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(layoutid, null);
			}
			Person p = data.get(position);
			if (p != null) {
				TextView action = (TextView) convertView
						.findViewById(com.missionse.securityhelper.R.id.person_list_entry_name);
				action.setText(p.toString());
			}
			return convertView;
		}
	}

}
