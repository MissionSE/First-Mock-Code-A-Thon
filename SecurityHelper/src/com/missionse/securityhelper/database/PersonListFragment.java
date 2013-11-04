package com.missionse.securityhelper.database;

import java.util.LinkedList;
import java.util.List;

import org.orman.mapper.Model;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.database.model.Person;


public class PersonListFragment extends ListFragment {
	
	private List<Person> persons = new LinkedList<Person>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View lRootView = inflater.inflate(R.layout.fragment_person_list, container, false);
		
		initComponents(lRootView);
		
		updatePersonList();
				
		return lRootView;
		
	}
	
	private void updatePersonList(){
		persons = Model.fetchAll(Person.class);
		
		ListAdapter l = 
				new PersonAdapter(
						getActivity().getApplicationContext(),
						R.layout.fragment_person_list,
						R.id.person_list_entry_name,
						persons);
		setListAdapter(l);
		
	}
	
	
	public void initComponents(View tRoot){
		
	}
	
	
	

}
