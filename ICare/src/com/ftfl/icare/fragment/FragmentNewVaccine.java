package com.ftfl.icare.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.VaccineDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.Vaccine;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class FragmentNewVaccine extends Fragment implements OnDateSetListener,
		TimePickerDialog.OnTimeSetListener {

	EditText mEtName = null;
	EditText mEtDate = null;
	EditText mEtTime = null;

	CheckBox mCbStatus = null;
	Button btnSave = null;

	String mStatus = "";
	String id = null;

	public FragmentNewVaccine() {

	}

	public void viewPreviousData() {
		int lId = Integer.parseInt(id);
		VaccineDataSource vaccineDS = new VaccineDataSource(getActivity());
		Vaccine viewVaccine = vaccineDS.singleVaccineData(lId);
		mEtName.setText(viewVaccine.getName());
		mEtDate.setText(viewVaccine.getDate());
		mEtTime.setText(viewVaccine.getTime());

		String status = viewVaccine.getStatus();

		if (status.equals("Given")) {
			mCbStatus.setChecked(true);
		}

		else {
			mCbStatus.setChecked(false);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_new_vaccine,
				container, false);
		((HomeActivity) getActivity()).disableMenu();

		mEtName = (EditText) view.findViewById(R.id.et_new_vaccine_name);
		mEtDate = (EditText) view.findViewById(R.id.et_new_vaccine_date);
		mEtTime = (EditText) view.findViewById(R.id.et_new_vaccine_time);
		mCbStatus = (CheckBox) view.findViewById(R.id.cb_new_vaccine_status);
		btnSave = (Button) view.findViewById(R.id.button_new_vaccine);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				this, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), false);
		final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				this, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), false, false);

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

		mEtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					timePickerDialog.setCloseOnSingleTapMinute(false);
					timePickerDialog.show(getActivity()
							.getSupportFragmentManager(),
							ICareConstants.TIMEPICKER_TAG);
				}
			}
		});

		mEtTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				timePickerDialog.setCloseOnSingleTapMinute(false);
				timePickerDialog.show(
						getActivity().getSupportFragmentManager(),
						ICareConstants.TIMEPICKER_TAG);
			}
		});

		id = getArguments().getString(ICareConstants.VACCINE_ID);
		if (id != null) {
			viewPreviousData();
		}

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String name = mEtName.getText().toString();
				String date = mEtDate.getText().toString();
				String time = mEtTime.getText().toString();

				if (mCbStatus.isChecked()) {
					mStatus = "Given";
				} else {
					mStatus = "Not Given";
				}
				if (!(name.equals("") || date.equals("") || time.equals(""))) {

					Vaccine insertVaccine = new Vaccine(name, date, time,
							mStatus, ICareConstants.SELECTED_PROFILE_ID);

					VaccineDataSource vaccineDS = new VaccineDataSource(
							getActivity());

					if (id != null) {
						int lId = Integer.parseInt(id);
						if (vaccineDS.updateData(lId, insertVaccine)) {

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentVaccine fragmentVaccine = new FragmentVaccine();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentVaccine);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						}

						else {
							Toast.makeText(getActivity(), "not Saved",
									Toast.LENGTH_SHORT).show();
						}
					}

					else {
						if (vaccineDS.insert(insertVaccine)) {
							// ((HomeActivity)getActivity()).SelectItem(7);

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentVaccine fragmentVaccine = new FragmentVaccine();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentVaccine);
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
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

		int hour = 0;
		String st = "";
		if (hourOfDay > 12) {
			hour = hourOfDay - 12;
			st = getString(R.string.pm);
		}

		else if (hourOfDay == 12) {
			hour = hourOfDay;
			st = getString(R.string.pm);
		}

		else if (hourOfDay == 0) {
			hour = hourOfDay + 12;
			st = getString(R.string.am);
		} else {
			hour = hourOfDay;
			st = getString(R.string.am);
		}
		mEtTime.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		// TODO Auto-generated method stub

		mEtDate.setText(new StringBuilder()

		.append(day).append("/").append(month + 1).append("/").append(year));
	}
}
