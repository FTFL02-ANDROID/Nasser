package com.ftfl.icare.fragment;//FragmentNewLunch

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareDailyDietDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ICareDailyDietChart;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class FragmentNewLunch extends Fragment implements
		TimePickerDialog.OnTimeSetListener {

	EditText mEtTime = null;
	EditText mEtFoodItem = null;

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

	public FragmentNewLunch() {

	}

	private void checkFieldsForEmptyValues() {

		if (!mEtTime.getText().toString().equals("")) {
			mEtTime.setError(null);
		}

		if (!mEtFoodItem.getText().toString().equals("")) {
			mEtFoodItem.setError(null);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_new_breakfast,
				container, false);

		mEtTime = (EditText) view.findViewById(R.id.et_new_daily_diet_time);
		// etTime.setOnFocusChangeListener(this);
		mEtFoodItem = (EditText) view
				.findViewById(R.id.et_new_daily_diet_food_item);
		mEtTime.addTextChangedListener(textWatcher);
		mEtFoodItem.addTextChangedListener(textWatcher);
		// View TimePicker
		final Calendar calendar = Calendar.getInstance();
		final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				this, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), false, false);
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

		Button btnSave = (Button) view
				.findViewById(R.id.button_create_daily_diet_menu);

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (!ICareConstants.SELECTED_DIET_DATE.equals("")) {
					String time = mEtTime.getText().toString();

					String foodItem = mEtFoodItem.getText().toString();
					if (!(time.equals("") || foodItem.equals(""))) {
						ICareDailyDietDataSource dietDS = new ICareDailyDietDataSource(
								getActivity());

						if (!dietDS.isExists(ICareConstants.LUNCH_TAG)) {

							ICareDailyDietChart insertDiet = new ICareDailyDietChart(
									ICareConstants.SELECTED_DIET_DATE, time,
									foodItem, ICareConstants.LUNCH_TAG, "",
									ICareConstants.SELECTED_PROFILE_ID);

							if (dietDS.insert(insertDiet)) {

								Toast.makeText(getActivity(),
										getString(R.string.successfully_saved),
										Toast.LENGTH_SHORT).show();

								if (dietDS.eventNumber() == 5) {
									((HomeActivity) getActivity())
											.SelectItem(6);
								}

							}

							else {
								Toast.makeText(getActivity(),
										getString(R.string.not_saved),
										Toast.LENGTH_SHORT).show();
							}
						} else {
							new AlertDialog.Builder(getActivity())
									.setTitle(R.string.duplicate_data)
									.setMessage(R.string.duplicate_data_message)
									.setNegativeButton(
											R.string.alert_ok,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
													// do nothing
												}
											}).setIcon(R.drawable.alert).show();
						}

					} else {
						if (time.equals(""))
							mEtTime.setError(getString(R.string.error_time));
						if (foodItem.equals(""))
							mEtFoodItem
									.setError(getString(R.string.error_food_item));
					}
				}

				else {
					new AlertDialog.Builder(getActivity())
							.setTitle("Date Not Selected")
							.setMessage(
									"Please touch calender icon and select date.")
							.setNegativeButton(R.string.alert_ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// do nothing
										}
									}).setIcon(R.drawable.alert).show();
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
}
