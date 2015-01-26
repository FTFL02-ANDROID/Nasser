package com.ftfl.shoppingcomplex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;

public class ShoppingComplexViewActivity extends ActionBarActivity {
	
	TextView tvName = null;
	TextView tvAddress = null;
	TextView tvLatitude = null;
	TextView tvLongitude = null;
	TextView tvCloseDay = null;
	TextView tvOpenTime = null;
	TextView tvDescription = null;
	
	ShoppingComplexDataSource mShoppingComplexDS = null;
	ShoppingComplex mUpdateShoppingComplex = null;
	
	String mID = "";
	Long mLId ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_complex_view);
		
		tvName = (TextView) findViewById(R.id.viewName);
		tvAddress = (TextView) findViewById(R.id.viewAddress);
		tvLatitude = (TextView) findViewById(R.id.viewLatitude);
		tvLongitude = (TextView) findViewById(R.id.viewLongitude);
		tvCloseDay = (TextView) findViewById(R.id.viewCloseDay);
		tvOpenTime = (TextView) findViewById(R.id.viewDailyOpenTime);
		tvDescription = (TextView) findViewById(R.id.viewServiceDescription);
		
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("id");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mShoppingComplexDS = new ShoppingComplexDataSource(this);
			mUpdateShoppingComplex = mShoppingComplexDS
					.singleShoppingComplexData(mLId);

			String mName = mUpdateShoppingComplex.getName();
			String mAddress = mUpdateShoppingComplex.getAddress();
			String mLatitude = mUpdateShoppingComplex.getLatitude();
			String mLongitude = mUpdateShoppingComplex.getLongitude();
			String mCloseDay = mUpdateShoppingComplex.getCloseDay();
			String mOpenTime = mUpdateShoppingComplex.getDailyOpenTime();
			String mDescription = mUpdateShoppingComplex.getServiceDescription();
	

			// set the value of database to the text field.
	
			tvName.setText(mName);
			tvAddress.setText(mAddress);
			tvLatitude.setText(mLatitude);
			tvLongitude.setText(mLongitude);
			tvCloseDay.setText(mCloseDay);
			tvOpenTime.setText(mOpenTime);
			tvDescription.setText(mDescription);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_complex_view, menu);
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
