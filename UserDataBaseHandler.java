package database;

import java.util.ArrayList;
import java.util.List;

import fragments.HomeScreenFragment;
import model.UserReg;
import model.UserTask;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.ViewDebug.ExportedProperty;
import android.widget.EditText;
import android.widget.Toast;

public class UserDataBaseHandler extends SQLiteOpenHelper
{
	private static int mversion=1;
	private static String mDatabaseName="AEM";
	private String mTaskTable="UserTask";
	private String mRegTable="UserRegistration";

	private String DAS_ID="DAS_ID";
	private String USER_NAME="USER_NAME";
	private String PASSWORD="PASSWORD";
	private String MOBILE_NO="MOBILE_NO";
	private String EMAIL_ID="EMAIL_ID";
	private String DOB="DOB";
	private String ORG_UNIT="ORG_UNIT";
	private String BASE_LOCATION="BASE_LOCATION";
	private String FLAG="FLAG";


	private String cTaskId="Task_id";
	private String cDasId="Das_id";
	public static String cTaskName="Task_name";
	public static String cAssignedBy="Assigned_by";
	private String cAssigneeName="Assignee_name";
	public static String cStartDate="Start_date";
	private String cEndDate="End_date";
	public static String cDescription="Description";
	public static String cStatus="Status";
	private String cUserComments="User_comments";

	private Cursor c;
	public UserDataBaseHandler(Context context)
	{
		super(context, mDatabaseName, null, mversion);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String hi="hiiiii";
		Log.d(hi,"oncreate");
		String sql= " CREATE TABLE " + mRegTable  + " ( " + DAS_ID + " text," + USER_NAME + " text ," +
				PASSWORD + " text ," + MOBILE_NO + " text ," + EMAIL_ID +" text ," + DOB +
				" text ,"+ ORG_UNIT + " text ," + BASE_LOCATION + " text ," + FLAG + " text " +  " ) ;";
		db.execSQL(sql);
		String sql1="create table "+ mTaskTable + " ( "+cTaskId+" integer primary key autoincrement , " +
				cDasId+" text , " +cTaskName+" text, "+
				cAssignedBy+" text, "+cAssigneeName+" text, "+cStartDate + " text, "+cEndDate+" text, "+
				cDescription+" text, "+cStatus+" text, "+cUserComments+
				" text);";
		db.execSQL(sql1);

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		String q="drop table if exists "+mTaskTable;
		db.execSQL(q);
		onCreate(db);

	}
	/*public void add(EditText textDasId, EditText textUserName,
			EditText textPassword, EditText textConfirm, EditText textMobile,
			EditText textEmail)
	{
		 SQLiteDatabase database=this.getWritableDatabase();

		 ContentValues values=new ContentValues();

		 //values.put("DAS_ID");

	}
	 */

	public void add(UserReg userreg) 
	{
		SQLiteDatabase database=this.getWritableDatabase();

		ContentValues values=new ContentValues();

		values.put(DAS_ID, userreg.getmDasId());
		values.put(USER_NAME,userreg.getmUserName());
		values.put(PASSWORD,userreg.getmPasword());
		values.put(MOBILE_NO,userreg.getmMobileNo());
		values.put(EMAIL_ID,userreg.getmEmailId());

		database.insert(mRegTable,null, values);


	}



	public boolean checkPassword(String dasId, String password)
	{
		SQLiteDatabase database=this.getReadableDatabase();
		boolean flag=false;
		String hi="hiiiiiiiii";

		String sql="Select PASSWORD from "+ mRegTable + " where DAS_ID = + '"+dasId+"' " ;
		c=database.rawQuery(sql,null);

		if(c!=null)
		{
			if(c.moveToFirst());

			{
					String getPass=c.getString(0);
					
					if(password.equals(getPass))
					{
						Log.d(hi,"in if");
						flag=true;
					}
					
					
					Log.d(getPass,"password "+getPass);

					Log.d(dasId,"text dasid ");
					Log.d(password,"text password ");
				
				
			}
		}

		return flag;
	}
	public void addTask(UserTask task)
	{
		SQLiteDatabase database=this.getWritableDatabase();

		ContentValues values=new ContentValues();

		values.put(DAS_ID, task.getmDasId());
		values.put(cTaskName,task.getmTaskName());
		values.put(cAssignedBy,task.getmAssignBy());
		values.put(cAssigneeName,task.getmAssignName());
		values.put(cStartDate,task.getmStartDate());
		values.put(cEndDate,task.getmEndDate());
		values.put(cDescription, task.getmDescription());
		values.put(cStatus,task.getmStatus());
		values.put(cUserComments,task.getmUserComments());

		database.insert(mTaskTable,null, values);

	}
 
	public int changePassword(UserReg reg,String mdasId) 
	{
		SQLiteDatabase database=this.getWritableDatabase();
		String mobileno = null,emailid = null;
		int flag=1;
		
		/*ContentValues values=new ContentValues();
		
		values.put(PASSWORD,reg.getmPasword());
		values.put(MOBILE_NO,reg.getmMobileNo());
		values.put(EMAIL_ID, reg.getmEmailId());*/
		
		String sql="Select " + MOBILE_NO + "," + EMAIL_ID + " from " + mRegTable +  " where DAS_ID = + '"+mdasId+"' ";
		c=database.rawQuery(sql,null);
		
		if(c!=null)
		{
			if(c.moveToFirst())
			{
				mobileno=c.getString(0);
		        emailid=c.getString(1);
				
				Log.d(mobileno,"in cursor");
				Log.d(emailid,"in cursor");
			}
		}
		
		
		/*String sql="update" + mRegTable + "set" + PASSWORD + "=" + reg.getmPasword() +"," + MOBILE_NO + "=" + reg.getmMobileNo() +","+ EMAIL_ID + "=" +reg.getmEmailId() + "where" + DAS_ID + "=" +reg.getmDasId() ;  
		database.execSQL(sql);*/
		
		String dasId=reg.getmDasId();
		String password=reg.getmPasword();
		String mob=reg.getmMobileNo();
		String email=reg.getmEmailId();
		
		Log.d(dasId,"in changePass");
		Log.d(mdasId,"in changePass");
		Log.d(password,"in changePass");
		Log.d(mob,"in changePass");
	    Log.d(email,"in changePass");
	    
	    if(mob.equals(mobileno) && email.equals(emailid))
	    {
	    	changePasswordFinal(reg,mdasId);
	    	flag=0;
	     //return database.update(mRegTable,values,"DAS_ID ="+" '"+mdasId+"' ",null);
	    }
	    
	    return flag;
		
	    
	    
	    
	}



	private void changePasswordFinal(UserReg reg, String mdasId) 
	{
		SQLiteDatabase database=this.getWritableDatabase();
		
		
		ContentValues values=new ContentValues();
		
		values.put(PASSWORD,reg.getmPasword());
		
		
		database.update(mRegTable,values,"DAS_ID ="+" '"+mdasId+"' ",null);
		
	}



	
	public boolean checkDuplicateDasId(String dasId)
	{
	     SQLiteDatabase database=this.getReadableDatabase();
	     boolean flag=true;
	     
	     String sql="Select * From " + mRegTable + " where DAS_ID = + '"+dasId+"' " ;
	     c=database.rawQuery(sql,null);
	     
	     if(c!=null)
	     {
	    	   if(c.moveToFirst())
	    	   {
	    	 	flag=false;
	    	   }
	     }
	     
	     return flag;
	     
		
	}
	public Cursor fetchAllRecords(String dasId) 
	{
		
		 Log.d(dasId,"in fetchAllRecords");
		SQLiteDatabase database=this.getReadableDatabase(); 
		 String sql="Select rowid _id, "+ cTaskName + "," + cDescription + "," + cStartDate + "," + cAssignedBy + "," + cStatus +  " from " + mTaskTable +  " where DAS_ID = +  '"+dasId+"' ";
		 c=database.rawQuery(sql,null);
		 
		 //List<String> arraylist = new ArrayList<String>();
		 
		 
		 
		 if(c!=null)
		 {
			 c.moveToFirst();
			
			 do
			 {
				  int taskid=c.getInt(0);
				 String taskname=c.getString(1);
				 String desc=c.getString(2);
				 String startdate=c.getString(3);
				 String assignedby=c.getString(4);
				 String status=c.getString(5);
				 
			/*	 arraylist.add(taskname);
				 arraylist.add(desc);
				 arraylist.add(startdate);
				 arraylist.add(assignedby);
				 arraylist.add(status);
			*/	 
				 
				 
				 Log.d(Integer.toString(taskid),"taskid"+ Integer.toString(taskid));
				 Log.d(taskname,"task name "+taskname );
				 Log.d(desc,"description"+desc);
				 Log.d(assignedby,"assign by"+assignedby);
				 Log.d(status,"status"+status);
				 
				 
				 
			 }while(c.moveToNext());
			 
			 
			 
		 }
		 
		 c.moveToFirst();
		 return c;
		 
	}


}


