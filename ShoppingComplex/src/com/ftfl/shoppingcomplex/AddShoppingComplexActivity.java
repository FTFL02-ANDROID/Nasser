package com.ftfl.shoppingcomplex;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;
import com.ftfl.shoppingcomplex.util.ShoppingComplexConstants;

public class AddShoppingComplexActivity extends Activity implements
		OnClickListener, OnTimeSetListener, OnFocusChangeListener {
	static final int CAMERA_REQUEST = 1;

	Button mBtnSave = null;
	Button mBtnAddImage = null;

	EditText mEtName = null;
	EditText mEtAddress = null;
	EditText mEtLatitude = null;
	EditText mEtLongitude = null;
	EditText mEtCloseDay = null;
	EditText mEtOpenTime = null;
	EditText mEtDescription = null;

	String mName = "";
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	String mCloseDay = "";
	String mOpenTime = "";
	String mDescription = "";
	String mImagePath = "";

	String mStrProfileID = "";
	String mID = "";
	long mLID = 0;
	ShoppingComplexDataSource mShoppingComplexDS = null;
	ShoppingComplex mUpdateShoppingComplex = null;

	Calendar mCalendar = Calendar.getInstance();
	static final String IMAGE_DIRECTORY_NAME = "Shopping Complex Images";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_shopping_complex);

		/*
		 * Setting up dialog box
		 */

		final String[] option = new String[] {
				getString(R.string.takeFromCamera),
				getString(R.string.selectFromGallery) };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.selectOption));
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {
					callCamera();
				}
				if (which == 1) {
					callGallery();
				}
			}
		});
		final AlertDialog alertDialog = builder.create();

		mEtName = (EditText) findViewById(R.id.addName);
		mEtAddress = (EditText) findViewById(R.id.addAddress);
		mEtLatitude = (EditText) findViewById(R.id.addLatitude);
		mEtLongitude = (EditText) findViewById(R.id.addLongitude);
		mEtCloseDay = (EditText) findViewById(R.id.addCloseDay);
		mEtOpenTime = (EditText) findViewById(R.id.addDailyOpenTime);
		mEtDescription = (EditText) findViewById(R.id.addServiceDescription);
		mBtnSave = (Button) findViewById(R.id.btnSave);

		mBtnAddImage = (Button) findViewById(R.id.btnAddImage);

		mBtnAddImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				alertDialog.show();
			}
		});

		mEtOpenTime.setOnFocusChangeListener(this);
		mBtnSave.setOnClickListener(this);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra(ShoppingComplexConstants.ID);

		if (mID != null) {
			mLID = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mShoppingComplexDS = new ShoppingComplexDataSource(this);
			mUpdateShoppingComplex = mShoppingComplexDS
					.singleShoppingComplexData(mLID);

			String mName = mUpdateShoppingComplex.getName();
			String mAddress = mUpdateShoppingComplex.getAddress();
			String mLatitude = mUpdateShoppingComplex.getLatitude();
			String mLongitude = mUpdateShoppingComplex.getLongitude();
			String mCloseDay = mUpdateShoppingComplex.getCloseDay();
			String mOpenTime = mUpdateShoppingComplex.getDailyOpenTime();
			String mDescription = mUpdateShoppingComplex
					.getServiceDescription();

			// set the value of database to the text field.

			mEtName.setText(mName);
			mEtAddress.setText(mAddress);
			mEtLatitude.setText(mLatitude);
			mEtLongitude.setText(mLongitude);
			mEtCloseDay.setText(mCloseDay);
			mEtOpenTime.setText(mOpenTime);
			mEtDescription.setText(mDescription);

			/*
			 * change button name
			 */
			mBtnSave.setText(getString(R.string.update));
		}
	}

	/*
	 * Perform action after setting time.
	 */
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

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
		mEtOpenTime.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));

	}

	/*
	 * Perform action after button click.
	 */

	@Override
	public void onClick(View v) {

		Toast toast = null;

		mName = mEtName.getText().toString();
		mAddress = mEtAddress.getText().toString();
		mLatitude = mEtLatitude.getText().toString();
		mLongitude = mEtLongitude.getText().toString();

		mCloseDay = mEtCloseDay.getText().toString();
		mOpenTime = mEtOpenTime.getText().toString();

		mDescription = mEtDescription.getText().toString();

		ShoppingComplex shoppingComplexInsert = new ShoppingComplex();
		shoppingComplexInsert.setName(mName);
		shoppingComplexInsert.setAddress(mAddress);
		shoppingComplexInsert.setLatitude(mLatitude);
		shoppingComplexInsert.setLongitude(mLongitude);
		shoppingComplexInsert.setCloseDay(mCloseDay);
		shoppingComplexInsert.setmDailyOpenTime(mOpenTime);
		shoppingComplexInsert.setServiceDescription(mDescription);
		shoppingComplexInsert.setImagePath(mImagePath);

		/*
		 * if update is needed then update otherwise submit
		 */
		if (mID != null) {

			mLID = Long.parseLong(mID);

			mShoppingComplexDS = new ShoppingComplexDataSource(this);

			if (mShoppingComplexDS.updateData(mLID, shoppingComplexInsert) == true) {
				toast = Toast.makeText(this,
						getString(R.string.successfullyUpdated),
						Toast.LENGTH_SHORT);
				toast.show();
				startActivity(new Intent(AddShoppingComplexActivity.this,
						ShoppingComplexListActivity.class));
				finish();
			} else {
				toast = Toast.makeText(this, getString(R.string.notUpdated),
						Toast.LENGTH_SHORT);
				toast.show();
			}
		} else {
			mShoppingComplexDS = new ShoppingComplexDataSource(this);
			if (mShoppingComplexDS.insert(shoppingComplexInsert) == true) {
				toast = Toast.makeText(this,
						getString(R.string.successfullySaved),
						Toast.LENGTH_SHORT);
				toast.show();

				startActivity(new Intent(AddShoppingComplexActivity.this,
						ShoppingComplexListActivity.class));

				// finish();
			} else {
				toast = Toast.makeText(this, getString(R.string.notSaved),
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	/*
	 * Perform action after focusing a view
	 */

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (hasFocus) {
			// Process to get Current Time

			int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
			int minute = mCalendar.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog timePickerDialog = new TimePickerDialog(this,
					this, hour, minute, false);
			timePickerDialog.show();
		}
	}

	/**
	 * open camera method
	 */
	public void callCamera() {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT)
						.show();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, CAMERA_REQUEST);
			}
		}
	}

	/**
	 * open gallery method
	 */

	public void callGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", true);

		File photoFile = null;
		try {
			photoFile = createImageFile();
		} catch (IOException ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		// Continue only if the File was successfully created
		if (photoFile != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			startActivity(Intent.createChooser(intent, "Select Picture"));
		}
	}

	/**
	 * On activity result
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			performCrop(mImagePath);

		}
	}

	private File createImageFile() throws IOException {

		// External SD card location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				mediaStorageDir /* directory */
		);
		mImagePath = image.getAbsolutePath();
		return image;
	}

	/*
	 * Perform Crop action of the image.
	 */

	private void performCrop(String eFilePath) {
		try {

			File imageFile = new File(eFilePath);
			Uri picUri = Uri.fromFile(imageFile);

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 0);
			cropIntent.putExtra("aspectY", 0);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 150);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);

			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(imageFile));
			startActivity(cropIntent);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = getString(R.string.notCrop);
			Toast toast = Toast
					.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
