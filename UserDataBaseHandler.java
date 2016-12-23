package database;

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
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.ViewDebug.ExportedProperty;
import android.widget.EditText;
import android.widget.Toast;

public class UserDataBaseHandler extends SQLiteOpenHelper
{
	public static int mversion=1;
	public static String mDatabaseName="AEM";
	public static String mTaskTable="UserTask";
	public static String mRegTable="UserRegistration";

	public static String DAS_ID="DAS_ID";
	public static String USER_NAME="USER_NAME";
	public static String PASSWORD="PASSWORD";
	public static String MOBILE_NO="MOBILE_NO";
	public static String EMAIL_ID="EMAIL_ID";
	public static String DOB="DOB";
	public static String ORG_UNIT="ORG_UNIT";
	public static String BASE_LOCATION="BASE_LOCATION";
	public static String FLAG="FLAG";


	public static String cTaskId="Task_id";
	public static String cDasId="Das_id";
	public static String cTaskName="Task_name";
	public static String cAssignedBy="Assigned_by";
	public static String cAssigneeName="Assignee_name";
	public static String cStartDate="Start_date";
	public static String cEndDate="End_date";
	public static String cDescription="Description";
	public static String cStatus="Status";
	public static String cUserComments="User_comments";

	public static Cursor c;
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
		 c.close();
	     database.close();
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
		
		ContentValues values=new ContentValues();
		
		values.put(PASSWORD,reg.getmPasword());
		values.put(MOBILE_NO,reg.getmMobileNo());
		values.put(EMAIL_ID, reg.getmEmailId());
		
		String dasId=reg.getmDasId();
		String password=reg.getmPasword();
		String mob=reg.getmMobileNo();
		String email=reg.getmEmailId();
		
		Log.d(dasId,"in changePass");
		Log.d(mdasId,"in changePass");
		Log.d(password,"in changePass");
		Log.d(mob,"in changePass");
	    Log.d(email,"in changePass");
	   
	     database.close();
		return database.update(mRegTable,values,"DAS_ID ="+" '"+mdasId+"' ",null);
		
		
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
	     c.close();
	     database.close();
	     return flag;
	     
		
	}
	public String[] getEmailAndMob(String dasId)
	{
		SQLiteDatabase database=this.getWritableDatabase();
		String info[]={"",""};
		Log.d(dasId,"in 1st if "+ dasId);
		String sql="select EMAIL_ID,MOBILE_NO from "+ mRegTable +" where DAS_ID='"+dasId +"'";
		Cursor c1=database.rawQuery(sql,null);
		while(c1.moveToNext())
		{
			//int index=c1.getColumnIndex(MOBI);
			info[0]=c1.getString(0);
			info[1]=c1.getString(1);
			Log.d(dasId,"in while "+ dasId);
			Log.d(dasId,"in curosr======"+c1.getCount()+" "+c1.getString(0)+" "+c1.getString(1));

		}
		c1.close();
		database.close();
		return info;
	
	}
	public int updateInfo(String strUser,String strDob,String strOU,String strBase,String dasId)
	{
		ContentValues values=new ContentValues();
		values.put("USER_NAME", strUser);
		values.put("DOB", strDob);
		values.put("ORG_UNIT", strOU);
		values.put("BASE_LOCATION", strBase);
		String[] whereArgs={dasId};
		SQLiteDatabase database=this.getWritableDatabase();
    	int id=database.update(mRegTable,values,DAS_ID+"=?",whereArgs);
    	return id;
		
	}
	public int updateTask(UserTask task,String dasId)
	{
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
		String []whereArgs={dasId};
		SQLiteDatabase database=this.getWritableDatabase();
		int id=database.update(mTaskTable, values, DAS_ID+"=?", whereArgs);
		return id;
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
		 }
		
		 return c;
		 
	}

	public boolean checkTaskRecord(String dasId)
	{
		boolean flag=true;
		String hi="hiiiii";
		SQLiteDatabase database=this.getReadableDatabase(); 
		
		String sql="Select * from " +  mTaskTable + " where DAS_ID = +  '"+dasId+"' ";
		c=database.rawQuery(sql,null);
		
		Log.d(hi,"dasId"  +dasId);
		
		if(c.getCount()>0)
		 {
			 Log.d(hi,"in c!=null" + dasId);
			 flag=false;
			 Log.d("In c!=null",Boolean.toString(flag));
		 }
		 
		 
		
		
		
		return flag;
	}


}


