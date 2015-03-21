package com.ftfl.icare.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.DoctorProfileDataSource;
import com.ftfl.icare.database.MedicalHistoryDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.MedicalHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentNewMedicalHistory extends Fragment implements
		OnItemSelectedListener, OnDateSetListener {

	ImageView mIvPrescription = null;
	EditText mEtDate = null;
	Spinner mSpinnerDoctorName = null;
	EditText mEtPurpose = null;
	EditText mEtSuggestion = null;

	Button mBtnCapturePrescription = null;
	Button mBtnSave = null;
	static final int CAMERA_REQUEST = 11;
	static final int CROP_REQUEST = 12;
	File image = null;

	String mPrescriptionImagePath = "";
	String id = null;
	String mDoctorName = "";
	List<String> mDoctorNameList = new ArrayList<String>();

	public FragmentNewMedicalHistory() {

	}

	public void viewPreviousData() {
		int lId = Integer.parseInt(id);

		MedicalHistoryDataSource medicalHistoryDS = new MedicalHistoryDataSource(
				getActivity());
		MedicalHistory viewMedicalHistory = medicalHistoryDS
				.singleMedicalHistoryData(lId);

		mEtDate.setText(viewMedicalHistory.getDate());
		mEtPurpose.setText(viewMedicalHistory.getPurpose());
		mEtSuggestion.setText(viewMedicalHistory.getSuggestion());

		String doctorName = viewMedicalHistory.getDoctorName();

		int position = 0;
		for (int i = 0; i < mDoctorNameList.size(); i++) {
			if (mDoctorNameList.get(i).equals(doctorName)) {
				position = i;
				break;
			}
		}

		mSpinnerDoctorName.setSelection(position);

		mPrescriptionImagePath = viewMedicalHistory.getPrescription();

		if (!mPrescriptionImagePath.equals("")) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage(
					"file:///" + mPrescriptionImagePath, mIvPrescription,
					options);
		}

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

		mPrescriptionImagePath = image.getAbsolutePath();

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
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
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

	/**
	 * On activity result
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST) {
			performCrop(mPrescriptionImagePath);

		}

		else if (requestCode == CROP_REQUEST) {

			if (mPrescriptionImagePath != null) {

				mBtnCapturePrescription.setText("Change Image");

				Bitmap correctBmp = null;

				try {
					File f = new File(mPrescriptionImagePath);
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
				mIvPrescription.setImageBitmap(correctBmp);
			}

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(
				R.layout.fragment_layout_new_medical_history, container, false);
		((HomeActivity) getActivity()).disableMenu();

		mIvPrescription = (ImageView) view
				.findViewById(R.id.imageviewNewPrescription);
		mEtDate = (EditText) view
				.findViewById(R.id.et_new_medical_history_date);
		mSpinnerDoctorName = (Spinner) view
				.findViewById(R.id.spinner_new_medical_history_doctor_name);
		mEtPurpose = (EditText) view
				.findViewById(R.id.et_new_medical_history_purpose);
		mEtSuggestion = (EditText) view
				.findViewById(R.id.et_new_medical_history_suggestion);

		mBtnCapturePrescription = (Button) view
				.findViewById(R.id.button_capture_prescription);
		mBtnSave = (Button) view.findViewById(R.id.button_new_medical_history);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				this, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), false);
		mEtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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

		mEtDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.setCloseOnSingleTapDay(false);
				datePickerDialog.show(
						getActivity().getSupportFragmentManager(),
						ICareConstants.DATEPICKER_TAG);

			}
		});

		DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
				getActivity());

		mDoctorNameList = doctorDS.doctorNameList();
		mSpinnerDoctorName.setOnItemSelectedListener(this);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_item, mDoctorNameList);
		// Specify the layout to use when the list of choices appears
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mSpinnerDoctorName.setAdapter(dataAdapter);

		mBtnCapturePrescription.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				callCamera();
			}
		});

		id = getArguments().getString(ICareConstants.MEDICAL_HISTORY_ID);
		if (id != null) {
			viewPreviousData();
		}

		mBtnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String date = mEtDate.getText().toString();
				String purpose = mEtPurpose.getText().toString();
				String suggestion = mEtSuggestion.getText().toString();

				if (!(date.equals("") || purpose.equals("") || suggestion
						.equals(""))) {

					MedicalHistory insertMedicalHistory = new MedicalHistory(
							date, mDoctorName, purpose, suggestion,
							mPrescriptionImagePath,
							ICareConstants.SELECTED_PROFILE_ID);

					MedicalHistoryDataSource medicalHistoryDS = new MedicalHistoryDataSource(
							getActivity());

					if (id != null) {
						int lId = Integer.parseInt(id);
						if (medicalHistoryDS.updateData(lId,
								insertMedicalHistory)) {

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentMedicalHistory fragmentMedicalHistory = new FragmentMedicalHistory();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentMedicalHistory);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						}

						else {
							Toast.makeText(getActivity(), "not Saved",
									Toast.LENGTH_SHORT).show();
						}
					}

					else {

						if (medicalHistoryDS.insert(insertMedicalHistory)) {
							// ((HomeActivity)getActivity()).SelectItem(7);

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentMedicalHistory fragmentMedicalHistory = new FragmentMedicalHistory();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentMedicalHistory);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						}

						else {
							Toast.makeText(getActivity(), "not Saved",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		mDoctorName = arg0.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		mDoctorName = arg0.toString();
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		// TODO Auto-generated method stub

		mEtDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(day).append("/").append(month + 1).append("/")
				.append(year));

	}

}
