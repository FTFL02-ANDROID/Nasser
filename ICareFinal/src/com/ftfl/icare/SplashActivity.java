package com.ftfl.icare;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ftfl.icare.database.ICareProfileDataSource;

public class SplashActivity extends Activity {
	ProgressBar mProgressBar = null;
	ICareProfileDataSource mDataSource = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressBarSplash);
		mDataSource = new ICareProfileDataSource(this);
		
		new Timer().schedule(new TimerTask() {
			public void run() {
				SplashActivity.this.runOnUiThread(new Runnable() {
					public void run() {		
						
						if(mDataSource.profileNumber()==0)
						{
							startActivity(new Intent(SplashActivity.this,
									CreateProfileActivity.class));
							finish();
						}
						
						else{
							startActivity(new Intent(SplashActivity.this,
									HomeActivity.class));
							finish();
						}
					}
				});
			}
		}, 3000);
		
		  final Thread t = new Thread(){
			  final int totalProgressTime = 100;
			   @Override
			   public void run(){
			 
			      int jumpTime = 0;
			      while(jumpTime < totalProgressTime){
			         try {
			            sleep(30);
			            jumpTime += 1;
			            mProgressBar.setProgress(jumpTime);
			         } catch (InterruptedException e) {
			           // TODO Auto-generated catch block
			           e.printStackTrace();
			         }
			      }	  
			   }
			   };
			   t.start();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
