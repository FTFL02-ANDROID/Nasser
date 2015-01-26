package com.ftfl.shoppingcomplex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;

public class AddShoppingComplexActivity extends ActionBarActivity implements
OnClickListener, OnTimeSetListener {
	Button btns_save = null;
	
	EditText etName = null;
	EditText etAddress = null;
	EditText etLatitude = null;
	EditText etLongitude = null;
	EditText etCloseDay = null;
	EditText etOpenTime = null;
	EditText etDescription = null;
	
	String mName = "";
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	String mCloseDay = "";
	String mOpenTime = "";
	String mDescription = "";
	
	
	TextView tvItem = null;

	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	String mStrProfileID = "";
	String mID = "";
	Long mLId ;
	ShoppingComplexDataSource mShoppingComplexDS = null;
	ShoppingComplex mUpdateShoppingComplex = null;
	
	String mActivityCurrentDate = "";
	String mActivityProfileId = "";
	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	String mCurrentDate = "";
	Calendar mCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_shopping_complex);
		
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);

		etName = (EditText) findViewById(R.id.addName);
		etAddress = (EditText) findViewById(R.id.addAddress);
		etLatitude = (EditText) findViewById(R.id.addLatitude);
		etLongitude = (EditText) findViewById(R.id.addLongitude);
		etCloseDay = (EditText) findViewById(R.id.addCloseDay);
		etOpenTime = (EditText) findViewById(R.id.addDailyOpenTime);
		etDescription = (EditText) findViewById(R.id.addServiceDescription);
		btns_save = (Button) findViewById(R.id.btnSave);

	
		etOpenTime.setOnClickListener(this);
		btns_save.setOnClickListener(this);

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
	
			etName.setText(mName);
			etAddress.setText(mAddress);
			etLatitude.setText(mLatitude);
			etLongitude.setText(mLongitude);
			etCloseDay.setText(mCloseDay);
			etOpenTime.setText(mOpenTime);
			etDescription.setText(mDescription);

			/*
			 * change button name
			 */
			btns_save.setText("Update");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_shopping_complex, menu);
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


	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

		mSetHour = hourOfDay;
		mSetMinute = minute;
		int hour = 0;
		String st = "";
		if (hourOfDay > 12) {
			hour = hourOfDay - 12;
			st = "PM";
		}

		else if (hourOfDay == 12) {
			hour = hourOfDay;
			st = "PM";
		}

		else if (hourOfDay == 0) {
			hour = hourOfDay + 12;
			st = "AM";
		} else {
			hour = hourOfDay;
			st = "AM";
		}
		etOpenTime.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast toast = null;
		switch (v.getId()) {

		case R.id.addDailyOpenTime:
			// Process to get Current Time

			mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
			mMinute = mCalendar.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this, this, mHour,
					mMinute, false);
			tpd.show();
			break;

		case R.id.btnSave:

		
		
		
			mName = etName.getText().toString();
			mAddress = etAddress.getText().toString();
			mLatitude = etLatitude.getText().toString();
			mLongitude = etLongitude.getText().toString();
			
			mCloseDay = etCloseDay.getText().toString();
			mOpenTime = etOpenTime.getText().toString();
			
			mDescription = etDescription.getText().toString();

			ShoppingComplex shoppingComplexInsert = new ShoppingComplex();
				shoppingComplexInsert.setName(mName);
				shoppingComplexInsert.setAddress(mAddress);
				shoppingComplexInsert.setLatitude(mLatitude);
				shoppingComplexInsert.setLongitude(mLongitude);
				shoppingComplexInsert.setCloseDay(mCloseDay);
				shoppingComplexInsert.setmDailyOpenTime(mOpenTime);
				shoppingComplexInsert.setServiceDescription(mDescription);
				

			/*
			 * if update is needed then update otherwise submit
			 */
			if (mID != null) {

				mLId = Long.parseLong(mID);

				mShoppingComplexDS = new ShoppingComplexDataSource(this);

				if (mShoppingComplexDS.updateData(mLId, shoppingComplexInsert) == true) {
					toast = Toast.makeText(this, "Successfully Updated.",
							Toast.LENGTH_LONG);
					toast.show();
					startActivity(new Intent(AddShoppingComplexActivity.this,
							ShoppingComplexListActivity.class));
					finish();
				} else {
					toast = Toast.makeText(this, "Not Updated.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			} else {
				mShoppingComplexDS = new ShoppingComplexDataSource(this);
				if (mShoppingComplexDS.insert(shoppingComplexInsert) == true) {
					toast = Toast.makeText(this, "Successfully Saved.",
							Toast.LENGTH_LONG);
					toast.show();

					startActivity(new Intent(AddShoppingComplexActivity.this,
							ShoppingComplexListActivity.class));

					//finish();
				} else {
					toast = Toast.makeText(this, "Not Saved.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
			break;
		}

	}
}
