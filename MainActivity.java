package com.example.camera;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

	 ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button b=(Button)findViewById(R.id.button1);
        
        iv=(ImageView) findViewById(R.id.imageView1);
        
       b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			
			startActivityForResult(intent, 1);
		
		}
	});
    }
    
     @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(arg0, arg1, arg2);
    	
    	Bitmap bm=(Bitmap) arg2.getExtras().get("arg2");
    	iv.setImageBitmap(bm);
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
