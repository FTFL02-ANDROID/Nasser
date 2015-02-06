package com.ftfl.imagedatabase;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Timer().schedule(new TimerTask() {
			public void run() {
				SplashActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						startActivity(new Intent(SplashActivity.this,
								ImageActivity.class));
						finish();
					}
				});
			}
		}, 2000);
	}

}
