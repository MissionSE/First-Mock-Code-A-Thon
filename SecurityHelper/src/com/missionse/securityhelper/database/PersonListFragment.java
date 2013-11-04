package com.missionse.securityhelper.database;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.missionse.securityhelper.R;


public class PersonListFragment extends ListFragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View lRootView = inflater.inflate(R.layout.person_list, container, false);
		
		initComponents(lRootView);
		
		updatePersonList();
				
		return lRootView;
		
	}
	
	private void updatePersonList(){
		
	}
	
	
	public void initComponents(View tRoot){
		
	}
	
	
	

}
