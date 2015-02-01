package com.ftfl.shoppingcomplex;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;

public class SplashActivity extends Activity {
	ShoppingComplexDataSource mDataSource = null;
	ShoppingComplex mShoppingComplex = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mDataSource = new ShoppingComplexDataSource(this);

		new Timer().schedule(new TimerTask() {
			public void run() {
				SplashActivity.this.runOnUiThread(new Runnable() {
					public void run() {

						if (mDataSource.isEmpty()) {
							startActivity(new Intent(SplashActivity.this,
									AddShoppingComplexActivity.class));
						} else {
							startActivity(new Intent(SplashActivity.this,
									ShoppingComplexListActivity.class));

						}

						finish();
					}
				});
			}
		}, 2000);
	}
}
