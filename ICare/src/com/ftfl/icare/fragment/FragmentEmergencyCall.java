package com.ftfl.icare.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.OnShakeListener;
import com.ftfl.icare.util.ShakeDetector;

public class FragmentEmergencyCall extends Fragment {
	EditText mEtPhoneNumber = null;
	Button mBtnSave = null;
	SharedPreferences sharedpreferences;
	String mPhoneNumber = "";

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	public FragmentEmergencyCall() {

	}

	public void performCall(String pnoneNumber) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + pnoneNumber));
		getActivity().startActivity(callIntent);

	}

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_emergency_call,
				container, false);
		((HomeActivity) getActivity()).disableMenu();

		sharedpreferences = getActivity().getSharedPreferences(
				ICareConstants.MY_PREFERENCES, Context.MODE_PRIVATE);

		mEtPhoneNumber = (EditText) view
				.findViewById(R.id.et_emergency_phone_number);
		mBtnSave = (Button) view.findViewById(R.id.create_emergency_number);

		if (sharedpreferences.contains(ICareConstants.EMERGENCY_NUMBER)) {
			mEtPhoneNumber.setVisibility(view.GONE);

			mBtnSave.setText("Call");
			mPhoneNumber = sharedpreferences.getString(
					ICareConstants.EMERGENCY_NUMBER, "");
		}

		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {
				performCall(mPhoneNumber);
			}
		});

		mBtnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!sharedpreferences
						.contains(ICareConstants.EMERGENCY_NUMBER)) {
					mPhoneNumber = mEtPhoneNumber.getText().toString();

					Editor editor = sharedpreferences.edit();
					editor.putString(ICareConstants.EMERGENCY_NUMBER,
							mPhoneNumber);
					editor.commit();
				}

				else {
					mPhoneNumber = sharedpreferences.getString(
							ICareConstants.EMERGENCY_NUMBER, "");

					performCall(mPhoneNumber);
				}

			}
		});

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {

					((HomeActivity) getActivity()).SelectItem(5);
					return true;
				} else {
					return false;
				}
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

}
