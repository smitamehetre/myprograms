package fragments;

import java.util.Calendar;

import model.UserTask;

import com.example.drawerpractice1.R;

import database.UserDataBaseHandler;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class UpdateTaskFragment extends DialogFragment implements android.view.View.OnClickListener {
	
	Button btnUpdate;
	EditText editFromDate,editToDate,editTask,editAssignedBy,editAssigneename,editDesc,editUserCom;
	String stringTask,stringAssignedBy,stringAssigneename,stringDesc,stringUserCom,stringStatus,stringStartD,stringEndD,stringItem;
	UserDataBaseHandler dbHandler;
	String strtext;
	Spinner itemSpinner;
	private int mYear, mMonth, mDay, mHour, mMinute;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.updatetask,container, false);
		ActionBar ab=getActivity().getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		editFromDate=(EditText)view.findViewById(R.id.editStartDate);
		editFromDate.setOnClickListener(this);
		editToDate=(EditText)view.findViewById(R.id.editEndDate);
		editToDate.setOnClickListener(this);
		btnUpdate=(Button)view.findViewById(R.id.btnAssignTask);
		btnUpdate.setOnClickListener(this);
		dbHandler=new UserDataBaseHandler(this.getActivity());
		itemSpinner=(Spinner)view.findViewById(R.id.spinner1);
		itemSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				stringStatus = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		editTask=(EditText)view.findViewById(R.id.editTask);
		editAssignedBy=(EditText)view.findViewById(R.id.editAssignedBy);
		editAssigneename=(EditText)view.findViewById(R.id.editAssigneeName);
		editDesc=(EditText)view.findViewById(R.id.editDesc);
		editUserCom=(EditText)view.findViewById(R.id.editUserComments);
		editFromDate=(EditText)view.findViewById(R.id.editFromDate);
		editToDate=(EditText)view.findViewById(R.id.editEndDate);
		strtext = getArguments().getString("DasId");
		Log.d(strtext,"In Add Task Fragment Das Id============="+strtext);
		// fetch records of selected das id and set it to email_id and mobil
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==editFromDate)
		{
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog datePickerDialog =new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener()
			{

				@Override
				public void onDateSet(android.widget.DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					editFromDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
				}

			}, mYear, mMonth+1, mDay);
			datePickerDialog.show();
		}
		if(v==editToDate)
		{
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog datePickerDialog =new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener()
			{

				@Override
				public void onDateSet(android.widget.DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					editToDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
				}

			}, mYear, mMonth, mDay);
			datePickerDialog.show();
		}
		if(v==btnUpdate)
		{
			stringTask=editTask.getText().toString();
			stringAssignedBy=editAssignedBy.getText().toString();
			stringAssigneename=editAssigneename.getText().toString();
			stringDesc=editDesc.getText().toString();
			stringUserCom=editUserCom.getText().toString();
			stringStartD=editFromDate.getText().toString();
			stringEndD=editToDate.getText().toString();
			Log.d(stringTask,stringTask+" "+stringAssignedBy+" "+stringAssigneename+" "+stringDesc+
					" "+stringUserCom+" "+stringStatus+" "+stringStartD+" "+stringEndD);
			if(stringTask.equals("")||stringAssignedBy.equals("") || stringAssigneename.equals("") ||
					stringDesc.equals("") || stringUserCom.equals("") ||
					stringStatus.equals("") || stringStartD.equals("") || stringEndD.equals(""))
			{
				Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				//if all field are not null then add into db and display ALERT
				UserTask task=new UserTask();
				task.setmDasId(strtext);
				task.setmTaskName(stringTask);
				task.setmAssignBy(stringAssignedBy);
				task.setmAssignName(stringAssigneename);
				task.setmStartDate(stringStartD);
				task.setmEndDate(stringEndD);
				task.setmDescription(stringDesc);
				task.setmStatus(stringStatus);
				task.setmUserComments(stringUserCom);
				dbHandler.updateTask(task,strtext);
			
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				// set dialog message
				alertDialogBuilder
				.setMessage("Task Has Been Added/Updated.")
				.setCancelable(false)
				.setPositiveButton("yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						Fragment fragment = new HomeScreenFragment();
						FragmentManager fragmentManager = getFragmentManager();
						FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						fragmentTransaction.replace(R.id.content_frame, fragment);
						fragmentTransaction.commit();

					}
				});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		}
	}
	

}
