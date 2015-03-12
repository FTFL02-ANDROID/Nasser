package com.ftfl.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.utill.ICareProfile;

public class ICareSplashActivity extends Activity {
	ICareProfileDataSource mDataSource = null;
	ICareProfile mProfile = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_splash);
		
		mDataSource = new ICareProfileDataSource(this);
	
		
		
		
		
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 3 seconds
					sleep(2 * 1000);

					// After 2 seconds redirect to another intent
					// Intent i=new
					// Intent(getBaseContext(),FTFLICareProfileActivity.class);

					// Bellow code will do the same thing....
					
					if(mDataSource.isEmpty())
					{
						Intent i = new Intent(ICareSplashActivity.this,
								ICareProfileCreateActivity.class);
						startActivity(i);
					}
					else{
						Intent i = new Intent(ICareSplashActivity.this,
								ICareDietListActivity.class);
						startActivity(i);
					}
				
					
					// Remove activity
					finish(); // so that, it will not get back in the previous
								// file.

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icare_splash, menu);
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
