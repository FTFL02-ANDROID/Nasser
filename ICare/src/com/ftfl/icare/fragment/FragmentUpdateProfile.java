package com.ftfl.icare.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ICareProfile;

public class FragmentUpdateProfile extends Fragment implements
		OnTimeSetListener, OnFocusChangeListener, OnDateSetListener {

	static final int CAMERA_REQUEST = 1;
	static final int CROP_REQUEST = 2;

	Button mBtnUpdate = null;
	Button mBtnTakePicture = null;

	EditText mEtName = null;
	EditText mEtDateOfBirth = null;
	EditText mEtGender = null;
	EditText mEtHeight = null;
	EditText mEtWeight = null;
	EditText mEtBloodGroup = null;

	String mName = "";
	String mDateOfBirth = "";
	String mGender = "";
	String mHeight = "";
	String mWeight = "";
	String mBloodGroup = "";
	String mImagePath = "";

	File image = null;
	ImageView mIvCapturedImage = null;

	ICareProfile mUpdateProfile = null;
	ICareProfileDataSource mDataSource = null;
	int mSelectedProfileId = 0;

	public FragmentUpdateProfile() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_update_profile,
				container, false);
		((HomeActivity) getActivity()).disableMenu();

		mEtName = (EditText) view.findViewById(R.id.update_name);
		mEtDateOfBirth = (EditText) view
				.findViewById(R.id.update_date_of_birth);
		mEtGender = (EditText) view.findViewById(R.id.update_gender);
		mEtHeight = (EditText) view.findViewById(R.id.update_height);
		mEtWeight = (EditText) view.findViewById(R.id.update_weight);
		mEtBloodGroup = (EditText) view.findViewById(R.id.update_blood_group);
		mIvCapturedImage = (ImageView) view
				.findViewById(R.id.imageviewShowImage);

		// mBtnUpdate = (Button) findViewById(R.id.btnSave);

		mBtnTakePicture = (Button) view.findViewById(R.id.updateImage);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				this, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), false);
		mEtDateOfBirth
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							datePickerDialog.setYearRange(1985, 2028);
							datePickerDialog.setCloseOnSingleTapDay(false);
							datePickerDialog.show(getActivity()
									.getSupportFragmentManager(),
									ICareConstants.DATEPICKER_TAG);
						}
					}
				});

		mEtDateOfBirth.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.setCloseOnSingleTapDay(false);
				datePickerDialog.show(
						getActivity().getSupportFragmentManager(),
						ICareConstants.DATEPICKER_TAG);

			}
		});

		mBtnTakePicture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				callCamera();
			}
		});

		/*
		 * get the profile which include all data from database according
		 * profileId of the clicked item.
		 */
		mDataSource = new ICareProfileDataSource(getActivity());

		mSelectedProfileId = Integer
				.parseInt(ICareConstants.SELECTED_PROFILE_ID);

		mUpdateProfile = mDataSource.singleProfileData(mSelectedProfileId);
		mName = mUpdateProfile.getName();
		mDateOfBirth = mUpdateProfile.getDateOfBirth();
		mGender = mUpdateProfile.getGender();
		mHeight = mUpdateProfile.getHeight();
		mWeight = mUpdateProfile.getWeight();
		mBloodGroup = mUpdateProfile.getBlooGroup();
		mImagePath = mUpdateProfile.getImage();

		// set the value of database to the text field.

		mEtName.setText(mName);
		mEtDateOfBirth.setText(mDateOfBirth);
		mEtGender.setText(mGender);
		mEtHeight.setText(mHeight);
		mEtWeight.setText(mWeight);
		mEtBloodGroup.setText(mBloodGroup);

		if (mImagePath != "") {

			mBtnTakePicture.setText("Change Image");

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
				BitmapFactory.Options optionBitmap = new BitmapFactory.Options();
				optionBitmap.inSampleSize = 1;
				Bitmap bmp1 = BitmapFactory.decodeStream(
						new FileInputStream(f), null, optionBitmap);
				correctBmp = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(),
						bmp1.getHeight(), mat, true);
			} catch (IOException e) {
				Log.w("TAG", "-- Error in setting image");
			} catch (OutOfMemoryError oom) {
				Log.w("TAG", "-- OOM Error in setting image");
			}
			mIvCapturedImage.setImageBitmap(correctBmp);
		}

		Button btnSave = (Button) view.findViewById(R.id.btnSave);

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast toast;
				mName = mEtName.getText().toString();
				mDateOfBirth = mEtDateOfBirth.getText().toString();
				mGender = mEtGender.getText().toString();
				mHeight = mEtHeight.getText().toString();
				mWeight = mEtWeight.getText().toString();
				mBloodGroup = mEtBloodGroup.getText().toString();

				if (!(mName.equals("") || mDateOfBirth.equals("")
						|| mGender.equals("") || mHeight.equals("")
						|| mWeight.equals("") || mBloodGroup.equals(""))) {
					ICareProfile updateProfile = new ICareProfile();

					updateProfile.setName(mName);
					updateProfile.setDateOfBirth(mDateOfBirth);
					updateProfile.setGender(mGender);
					updateProfile.setHeight(mHeight);
					updateProfile.setWeight(mWeight);
					updateProfile.setBloodGroup(mBloodGroup);
					updateProfile.setImage(mImagePath);

					mDataSource = new ICareProfileDataSource(getActivity());

					if (mDataSource.updateData(mSelectedProfileId,
							updateProfile) == true) {
						toast = Toast.makeText(getActivity(),
								getString(R.string.successfully_update),
								Toast.LENGTH_SHORT);
						toast.show();
						((HomeActivity) getActivity()).SelectItem(2);
					} else {
						toast = Toast.makeText(getActivity(),
								getString(R.string.not_update),
								Toast.LENGTH_SHORT);
						toast.show();
					}
				}
			}
		});

		return view;
	}

	/**
	 * open camera method
	 */
	public void callCamera() {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent
				.resolveActivity(getActivity().getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast.makeText(getActivity(), ex.getMessage(),
						Toast.LENGTH_SHORT).show();
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
	 * On activity result
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST) {
			performCrop(mImagePath);

		}

		else if (requestCode == CROP_REQUEST) {

			if (mImagePath != null) {

				mBtnTakePicture.setText("Change Image");

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
					BitmapFactory.Options option = new BitmapFactory.Options();
					option.inSampleSize = 1;
					Bitmap bmp1 = BitmapFactory.decodeStream(
							new FileInputStream(f), null, option);
					correctBmp = Bitmap.createBitmap(bmp1, 0, 0,
							bmp1.getWidth(), bmp1.getHeight(), mat, true);
				} catch (IOException e) {
					Log.w("TAG", "-- Error in setting image");
				} catch (OutOfMemoryError oom) {
					Log.w("TAG", "-- OOM Error in setting image");
				}
				mIvCapturedImage.setImageBitmap(correctBmp);
			}

		}

	}

	private File createImageFile() throws IOException {

		if (image == null) {
			// External SD card location
			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					ICareConstants.IMAGE_DIRECTORY_NAME);

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
			image = File.createTempFile(imageFileName, /* prefix */
					".jpg", /* suffix */
					mediaStorageDir /* directory */
			);
		}

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
			// startActivity(cropIntent);
			startActivityForResult(cropIntent, CROP_REQUEST);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = getString(R.string.not_croped);
			Toast toast = Toast.makeText(getActivity(), errorMessage,
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		// TODO Auto-generated method stub

		mEtDateOfBirth.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(day).append("/").append(month + 1).append("/")
				.append(year));

	}

}
