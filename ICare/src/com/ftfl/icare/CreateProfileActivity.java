package com.ftfl.icare;

import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.util.ICareProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateProfileActivity extends Activity implements
		OnItemSelectedListener {

	String mGender = "";
	EditText mEtName = null;
	String mName = "";
	Toast toast = null;
	
	// TextWatcher
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i2,
				int i3) {
						}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2,
				int i3) {
		}

		@Override
		public void afterTextChanged(Editable editable) {

			checkFieldsForEmptyValues();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_profile);
		mEtName = (EditText) findViewById(R.id.profile_name_start);
		mEtName.addTextChangedListener(textWatcher);
		Spinner spinnerGender = (Spinner) findViewById(R.id.profile_gender_start);
		spinnerGender.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.gender_array,
				R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerGender.setAdapter(adapter);
	}
	
	/*
	 * In this function if EditText remain empty it will set error otherwise remove error
	 */
	private void checkFieldsForEmptyValues() {

		if (!mEtName.getText().toString().equals("")) {
			mEtName.setError(null);
		}
	}

	/*
	 * If user click create profile then this function do all activity for creating profile. 
	 */
	
	public void createProfile(View v) {
		
		mName = mEtName.getText().toString();
		
		if(!mName.equals(""))
		{
			ICareProfile profileDataInsert = new ICareProfile();
			profileDataInsert.setName(mName);
			profileDataInsert.setGender(mGender);
			ICareProfileDataSource icareDS = new ICareProfileDataSource(this);
			if (icareDS.insert(profileDataInsert) == true) {
				toast = Toast.makeText(this, getString(R.string.successfully_saved),
						Toast.LENGTH_LONG);//Successfully Submitted.
				toast.show();
				startActivity(new Intent(CreateProfileActivity.this,
						HomeActivity.class));
			} else {
				toast = Toast.makeText(this,  getString(R.string.not_saved),
						Toast.LENGTH_LONG);
				toast.show();
			}
			finish();
		}
		
		else
		{
			mEtName.setError(Html.fromHtml("<font color='red'>Enter Name</font>"));
		}			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_profile, menu);
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		mGender = parent.getItemAtPosition(position).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		mGender = parent.toString();

	}
}
