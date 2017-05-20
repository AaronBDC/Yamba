package com.example.yamba;

import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	static final String TAG = "StatusActivity";
	EditText editStatus;
	Button buttonUpdate;
	
	/** called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        //Debug.startMethodTracing("Yamba.trace");		// starts tracing - point A
        
        
        Log.d(TAG, "onCreated with Bundle: " + bundle);
        
        setContentView(R.layout.status);
        editStatus = (EditText) findViewById(R.id.edit_status);
        
        buttonUpdate = (Button)findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(this);
        
    }
    
    @Override
	protected void onStop() {
    	super.onStop();
    	//Debug.stopMethodTracing();
    	
	}

	public void onClick(View v) {        

    	try {
			new PostToTwitter().execute(editStatus.getText().toString());
			Log.d(TAG, "executed!!!! :) ");
		} catch (Exception e) {
			Log.e(TAG, "Died", e);
			e.printStackTrace();
		} 
    	
		//Log.d(TAG, "onClicked with text: " + statusText);
		
    }
    class PostToTwitter extends AsyncTask<String, Void, String> {
    	
    	/* New Thread */
		@Override
		protected String doInBackground(String... params) {
			try {
				//Twitter twitter = new Twitter("student", "password");
				//twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				//twitter.setStatus(params[0]);
				
				((YambaApp) getApplication()).getTwitter().setStatus(params[0]);
				
				Log.d(TAG, "Successfully posted: "+ params[0]);
				return "Successfully posted: " + params[0];
			} catch (TwitterException e) {
				Log.e(TAG, "Died", e);
				e.printStackTrace();
                return "Failed posting:  " + params[0];
				
			}
		}
		/* UI Thread */
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
				.show();
		}
    	
    }
    

    
    
}


