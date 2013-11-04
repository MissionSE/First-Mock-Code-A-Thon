package com.missionse.securityhelper.database;

import java.util.ArrayList;
import java.util.List;

import org.orman.mapper.Model;

import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.database.model.Person;

public class PersonListFragment extends ListFragment {

	//private List<Person> persons = new LinkedList<Person>();
	private EditText searchBox;
	private int textlength=0;
	private ArrayList<Person> array_sort= new ArrayList<Person>();
	private List<Person> array_pre;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View lRootView = inflater.inflate(R.layout.fragment_person_list,
				container, false);

		initComponents(lRootView);

		updatePersonList(Model.fetchAll(Person.class));
		
		return lRootView;
		

	}

	private void updatePersonList(List<Person> persons) {

		array_pre = persons;
		
		ListAdapter l = new PersonAdapter(
				getActivity().getApplicationContext(),
				R.layout.fragment_person_list, R.id.person_list_entry_name,
				persons);
		setListAdapter(l);

	}

	private void initComponents(View tRoot) {
		searchBox = (EditText)tRoot.findViewById(R.id.person_list_searchtextbox);
		searchBox.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = searchBox.getText().length();
				array_sort.clear();
				for (int i = 0; i < array_pre.size(); i++) {
					if (textlength <= array_pre.get(i).toString().length()) {
						if (searchBox.getText()
								.toString()
								.equalsIgnoreCase(
										(String) array_pre.get(i).toString().subSequence(
												0, textlength))) {
							array_sort.add(array_pre.get(i));
						}
					}
				}
				updatePersonList(array_sort);
			}
		});
	}
	
	
	
	

}
