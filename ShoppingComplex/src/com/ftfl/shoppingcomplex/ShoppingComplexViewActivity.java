package com.ftfl.shoppingcomplex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;
import com.ftfl.shoppingcomplex.util.ShoppingComplexConstants;

public class ShoppingComplexViewActivity extends Activity {

	TextView mTvName = null;
	TextView mTvAddress = null;
	TextView mTvLatitude = null;
	TextView mTvLongitude = null;
	TextView mTvCloseDay = null;
	TextView mTvOpenTime = null;
	TextView mTvDescription = null;
	ImageView mIvImage = null;

	ShoppingComplexDataSource mShoppingComplexDS = null;
	ShoppingComplex mUpdateShoppingComplex = null;

	String mID = "";
	long mLId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_complex_view);

		mTvName = (TextView) findViewById(R.id.viewName);
		mTvAddress = (TextView) findViewById(R.id.viewAddress);
		mTvLatitude = (TextView) findViewById(R.id.viewLatitude);
		mTvLongitude = (TextView) findViewById(R.id.viewLongitude);
		mTvCloseDay = (TextView) findViewById(R.id.viewCloseDay);
		mTvOpenTime = (TextView) findViewById(R.id.viewDailyOpenTime);
		mTvDescription = (TextView) findViewById(R.id.viewServiceDescription);
		mIvImage = (ImageView) findViewById(R.id.viewImage);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra(ShoppingComplexConstants.ID);

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mShoppingComplexDS = new ShoppingComplexDataSource(this);
			mUpdateShoppingComplex = mShoppingComplexDS
					.singleShoppingComplexData(mLId);

			String name = mUpdateShoppingComplex.getName();
			String address = mUpdateShoppingComplex.getAddress();
			String latitude = mUpdateShoppingComplex.getLatitude();
			String longitude = mUpdateShoppingComplex.getLongitude();
			String closeDay = mUpdateShoppingComplex.getCloseDay();
			String openTime = mUpdateShoppingComplex.getDailyOpenTime();
			String description = mUpdateShoppingComplex.getServiceDescription();
			String imagePath = mUpdateShoppingComplex.getImagePath();

			// set the value of database to the text field.

			mTvName.setText(name);
			mTvAddress.setText(address);
			mTvLatitude.setText(latitude);
			mTvLongitude.setText(longitude);
			mTvCloseDay.setText(closeDay);
			mTvOpenTime.setText(openTime);
			mTvDescription.setText(description);

			if (imagePath != null) {
				Bitmap bmImg = BitmapFactory.decodeFile(imagePath);
				mIvImage.setImageBitmap(bmImg);
			}
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
