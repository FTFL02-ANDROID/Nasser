package com.ftfl.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
					sleep(2 * 1000);

					if (mDataSource.isEmpty()) {
						Intent i = new Intent(ICareSplashActivity.this,
								ICareProfileCreateActivity.class);
						startActivity(i);
					} else {
						Intent i = new Intent(ICareSplashActivity.this,
								ICareDietListActivity.class);
						startActivity(i);
					}
					finish();
				} catch (Exception e) {
				}
			}
		};
		background.start();
	}
}
