package fragments;

import java.util.ArrayList;

import com.example.drawerpractice1.R;

import database.UserDataBaseHandler;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;


import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class HomeScreenFragment extends Fragment
{
	private UserDataBaseHandler db;
	SimpleCursorAdapter cusoradapter;
	String dasId,hi="hiiiiii";
    Cursor c;
    ListView listview;
    
    String taskname[];
    String assignedby[];
    String descriptition[];
    String startdate[];
    String status[];
    
   
    int [] to= new int[]{R.id.textTaskName,R.id.textDescription,R.id.textduration,R.id.textAssignedBy,R.id.textStatus};
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		/*Context context=getActivity();
		UserDataBaseHandler db=new UserDataBaseHandler(context);*/
		
		
		
		View view=inflater.inflate(R.layout.homescreenfragment,container,false);
		
		Context context=getActivity();
		UserDataBaseHandler db=new UserDataBaseHandler(context);
			
		dasId = getArguments().getString("DasId");
		
		listview=(ListView) view.findViewById(R.id.listView1);
		/*
		 ArrayList<String> names = new ArrayList<String>();
		 
		 Log.d(hi,"in fragment");
		 
		 try 
		 {
			 names=(ArrayList<String>) db.fetchAllRecords(dasId);
			 int listSize = names.size();

			 for (int i = 0; i<listSize; i++){
			     Log.i("Member name: ", names.get(i));
			 }
             
         }
		 catch (SQLException e) 
		 {
             e.printStackTrace();
         }
		 
		 
		 listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_view,R.id.textTaskName,R.id.textDescription,R.id.textduration,R.id.textAssignedBy,R.id.textStatus,names);

         // set the adapter
         listview.setAdapter(listAdapter);*/
		
		 c=db.fetchAllRecords(dasId);
		
		 String taskname="hiiii";
		
		Log.d(taskname,"task name in adapter");
		
		
		do
		{
		String taskname1=c.getString(0);
		String desc=c.getString(1);
		String startdate=c.getString(2);
		String assignedby=c.getString(3);
		String status=c.getString(4);
		
		Log.d(taskname1,"task name in adapter");
		Log.d(desc,"desc in adapter");
		Log.d(startdate,"date in adapter");
		Log.d(assignedby,"assign by in adapter");
		Log.d(status,"status in adapter");
		}while(c.moveToNext());
		
		 String[] columns = new String[] {UserDataBaseHandler.cTaskName,UserDataBaseHandler.cDescription,UserDataBaseHandler.cStartDate,UserDataBaseHandler.cAssignedBy,UserDataBaseHandler.cStatus};
		 
		 int [] to= new int[]{R.id.textTaskName,R.id.textDescription,R.id.textduration,R.id.textAssignedBy,R.id.textStatus};
		 
		 try
		 {
		  cusoradapter=new SimpleCursorAdapter(getActivity(),R.layout.list_view,c,columns,to);
		  
		  Log.d(hi,"in try");
		  
		  listview=(ListView) view.findViewById(R.id.listView1);
		  
		  listview.setAdapter(cusoradapter);
		 }
		 catch(Exception E)
		 {
			// Log.v("Error",E.getMessage());
		 }
		  
		
				
				
		return view;
		
	}
	
	/*public void displayListView()
	{
		Context context=getActivity();
		UserDataBaseHandler db=new UserDataBaseHandler(context);
			
		dasId = getArguments().getString("DasId");
		c=db.fetchAllRecords(dasId);
		
		 String[] columns = new String[] {db.cTaskName,db.cDescription,db.cAssignedBy,db.cStatus,db.cStartDate};
		 
		 int [] to= new int[]{R.id.textTaskName,R.id.textDescription,R.id.textduration,R.id.textAssignedBy,R.id.textStatus};
		 
		 
		  cusoradapter=new SimpleCursorAdapter(getActivity(),R.layout.homescreenfragment,c,columns,to);
		  
		  
		  // Assign adapter to ListView
		  listView.setAdapter(cusoradapter);
				  
	}
*/
	
	
	
}
