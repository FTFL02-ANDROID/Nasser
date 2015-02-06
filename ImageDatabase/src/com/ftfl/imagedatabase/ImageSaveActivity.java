package com.ftfl.imagedatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.imagedatabase.database.ImageDatabaseDataSource;
import com.ftfl.imagedatabase.util.ImageDatabaseConstants;
import com.ftfl.imagedatabase.util.ImageInformation;

public class ImageSaveActivity extends ActionBarActivity {

	String mImagePath = null;
	ImageView mIvShowImage = null;
	TextView mTvLatitude = null;
	TextView mTvLongitude = null;
	EditText mEtRemark = null;
	Calendar mCalendar = Calendar.getInstance();
	Integer mHour = 0;
	Integer mMinute = 0;
	Integer mYear = 0;
	Integer mDay = 0;
	Integer mMonth = 0;
	String mDate = "";
	String mTime = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_save);

		mYear = mCalendar.get(Calendar.YEAR);
		mMonth = mCalendar.get(Calendar.MONTH);
		mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

		mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		mMinute = mCalendar.get(Calendar.MINUTE);

		mDate = mDay.toString() + "/" + mMonth.toString() + "/"
				+ mYear.toString();
		mTime = mHour.toString() + ":" + mMinute.toString();

		mIvShowImage = (ImageView) findViewById(R.id.imageviewShowImage);
		mTvLatitude = (TextView) findViewById(R.id.tvLatitude);
		mTvLongitude = (TextView) findViewById(R.id.tvLongitude);
		mEtRemark = (EditText) findViewById(R.id.etRemark);

		Intent mActivityIntent = getIntent();
		mImagePath = mActivityIntent
				.getStringExtra(ImageDatabaseConstants.IMAGE_PATH);

		if (mImagePath != null) {

			Bitmap correctBmp = null;

			try {
				File f = new File(mImagePath);
				ExifInterface exif = new ExifInterface(f.getPath());
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				int angle = 0;

				if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
					angle = 90;
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
					angle = 180;
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
					angle = 270;
				}

				Matrix mat = new Matrix();
				mat.postRotate(angle);

				Bitmap bmp1 = BitmapFactory.decodeStream(
						new FileInputStream(f), null, null);
				correctBmp = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(),
						bmp1.getHeight(), mat, true);
			} catch (IOException e) {
				Log.w("TAG", "-- Error in setting image");
			} catch (OutOfMemoryError oom) {
				Log.w("TAG", "-- OOM Error in setting image");
			}
			mIvShowImage.setImageBitmap(correctBmp);
		}

		try {
			LocationManager locMgr = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String locProvider = locMgr.getBestProvider(criteria, false);
			Location location = locMgr.getLastKnownLocation(locProvider);

			// getting GPS status
			boolean isGPSEnabled = locMgr
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			boolean isNWEnabled = locMgr
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (isGPSEnabled && isNWEnabled)

			{
				if (isNWEnabled)
					if (locMgr != null)
						location = locMgr
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (isGPSEnabled)
					if (location == null)
						if (locMgr != null)
							location = locMgr
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}

			// Getting latitude of the current location
			Double latitude = location.getLatitude();

			// Getting longitude of the current location
			Double longitude = location.getLongitude();

			mTvLatitude.setText(latitude.toString());
			mTvLongitude.setText(longitude.toString());

		} catch (NullPointerException ne) {
			Log.e("Current Location", "Current Lat Lng is Null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_save, menu);
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

	public void performSave(View v) {

		ImageDatabaseDataSource imageDS = new ImageDatabaseDataSource(this);
		String description = mEtRemark.getText().toString();
		String latitude = mTvLatitude.getText().toString();
		String longitude = mTvLongitude.getText().toString();

		ImageInformation imageInsert = new ImageInformation();

		imageInsert.setLatitude(latitude);
		imageInsert.setLongitude(longitude);
		imageInsert.setDescription(description);
		imageInsert.setFileName(mImagePath);
		imageInsert.setDate(mDate);
		imageInsert.setTime(mTime);

		if (imageDS.insert(imageInsert) == true) {
			Toast.makeText(this, "Successfully Saved.", Toast.LENGTH_LONG)
					.show();

			startActivity(new Intent(ImageSaveActivity.this,
					ImageActivity.class));
			finish();
		} else {
			Toast.makeText(this, "Not Saved.", Toast.LENGTH_LONG).show();

		}

	}
}
