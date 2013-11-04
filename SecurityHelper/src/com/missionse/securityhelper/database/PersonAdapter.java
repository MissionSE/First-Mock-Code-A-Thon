package com.missionse.securityhelper.database;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.missionse.securityhelper.database.model.Person;

public class PersonAdapter extends ArrayAdapter<Person> {
	
	private Context context;
    private int layoutResourceId;   
    private Person data[] = null;
    private int textViewResourceId;
    

	public PersonAdapter(Context context, int resource, int textViewResourceId,
			List<Person> objects) {
		super(context, resource, textViewResourceId, objects);
		
		layoutResourceId = resource;
		this.textViewResourceId = textViewResourceId;
	    this.context = context;
	    
	    data = new Person[objects.size()];
	    for(int x=0; x < objects.size(); x++){
	    	data[x] = objects.get(x);
	    }
	    
	}
	
	   @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        PersonHolder holder = null;
	       
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	           
	            holder = new PersonHolder();
	            holder.txtTitle = (TextView)row.findViewById(textViewResourceId);
	           
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PersonHolder)row.getTag();
	        }
	       
	        Person person = data[position];
	        holder.txtTitle.setText(person.toString());    
	       
	        return row;
	    }
	   
	    static class PersonHolder
	    {
	        TextView txtTitle;
	    }

}
