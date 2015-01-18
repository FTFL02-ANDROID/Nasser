package com.ftfl.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.utill.ICareProfile;


public class ICareProfileCreateActivity extends Activity implements OnClickListener{
	Button save_btn = null;
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
	
	

	ICareProfileDataSource icareDS = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icare);
        
        

		// set the text field id to the variable.
		et_name = (EditText) findViewById(R.id.createUserName);
		et_age = (EditText) findViewById(R.id.createAge);
		etBloodGroup = (EditText) findViewById(R.id.createBloodGroup);
		et_weight = (EditText) findViewById(R.id.createWeight);
		et_height = (EditText) findViewById(R.id.createHeight);
		et_dateOfBirth = (EditText) findViewById(R.id.createDateOfBirth);
		et_specialNotes = (EditText) findViewById(R.id.createSpecialComment);
		save_btn = (Button) findViewById(R.id.Save);
		save_btn.setOnClickListener(this);
        
    }
    
    



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.icare, menu);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {

		case R.id.Save:
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
						/*
						 * if update is needed then update otherwise submit
						 */
			
							icareDS = new ICareProfileDataSource(this);
							if (icareDS.insert(profileDataInsert) == true) {
								toast = Toast.makeText(this, "Successfully Submitted.",
										Toast.LENGTH_LONG);
								toast.show();
								startActivity(new Intent(ICareProfileCreateActivity.this,
										ICareCreateDietChartActivity.class));
							} else {
								toast = Toast.makeText(this, "Not Submitted.",
										Toast.LENGTH_LONG);
								toast.show();
							}

						break;
		}
		
	}
}
