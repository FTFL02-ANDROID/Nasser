package com.ftfl.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.utill.ICareProfile;

public class ICareProfilePreviewAcitvity extends Activity {

	Button update_btn = null;
	EditText et_name = null;
	EditText et_age = null;
	EditText etBloodGroup = null;
	EditText et_weight = null;
	EditText et_height = null;
	EditText et_dateOfBirth = null;
	EditText et_specialNotes = null;
	Toast toast = null;

	String mName = "";
	String mAge = "";
	String mBloodGroup = "";
	String mWeight = "";
	String mHeight = "";
	String mDateOfBirth = "";
	String mSpecialNotes = "";

	ICareProfile mUpdateProfile = null;
	ICareProfileDataSource mDataSource = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_profile_preview_acitvity);

		// set the textfield id to the variable.
		et_name = (EditText) findViewById(R.id.viewName);
		et_age = (EditText) findViewById(R.id.viewAge);
		etBloodGroup = (EditText) findViewById(R.id.viewBloodGroup);
		et_weight = (EditText) findViewById(R.id.viewWeight);
		et_height = (EditText) findViewById(R.id.viewHeight);
		et_dateOfBirth = (EditText) findViewById(R.id.viewDOB);
		et_specialNotes = (EditText) findViewById(R.id.viewSpComment);
		update_btn = (Button) findViewById(R.id.update);

		/*
		 * get the profile which include all data from database according
		 * profileId of the clicked item.
		 */
		mDataSource = new ICareProfileDataSource(this);
		mUpdateProfile = mDataSource.singleProfileData();

		String mName = mUpdateProfile.getName();
		String mAge = mUpdateProfile.getAge();
		String mEyeColor = mUpdateProfile.getBlooGroup();
		String mWeight = mUpdateProfile.getWeight();
		String mHeight = mUpdateProfile.getHeight();
		String mDateOfBirth = mUpdateProfile.getDateOfBirth();
		String mSpecialNotes = mUpdateProfile.getSpecialNotes();

		// set the value of database to the text field.
		et_name.setText(mName);
		et_age.setText(mAge);
		etBloodGroup.setText(mEyeColor);
		et_weight.setText(mWeight);
		et_height.setText(mHeight);
		et_dateOfBirth.setText(mDateOfBirth);
		et_specialNotes.setText(mSpecialNotes);
	}

	public void updateProfile(View v) {

		mName = et_name.getText().toString();
		mAge = et_age.getText().toString();
		mBloodGroup = etBloodGroup.getText().toString();
		mWeight = et_weight.getText().toString();
		mHeight = et_height.getText().toString();
		mDateOfBirth = et_dateOfBirth.getText().toString();
		mSpecialNotes = et_specialNotes.getText().toString();

		// Assign values in the ICareProfile
		ICareProfile profileDataInsert = new ICareProfile();
		profileDataInsert.setName(mName);
		profileDataInsert.setAge(mAge);
		profileDataInsert.setBloodGroup(mBloodGroup);
		profileDataInsert.setWeight(mWeight);
		profileDataInsert.setHeight(mHeight);
		profileDataInsert.setDateOfBirth(mDateOfBirth);
		profileDataInsert.setSpecialNotes(mSpecialNotes);

		mDataSource = new ICareProfileDataSource(this);

		if (mDataSource.updateData(1, profileDataInsert) == true) {
			toast = Toast.makeText(this,
					getString(R.string.successfullyUpdated), Toast.LENGTH_LONG);
			toast.show();
			Intent intent = new Intent(ICareProfilePreviewAcitvity.this,
					ICareProfilePreviewAcitvity.class);
			finish();
		} else {
			toast = Toast.makeText(this, getString(R.string.notUpdated),
					Toast.LENGTH_LONG);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icare_profile_preview_acitvity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.deleteProfile:
			mDataSource.deleteData(1);
			startActivity(new Intent(ICareProfilePreviewAcitvity.this,
					ICareProfileCreateActivity.class));
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
