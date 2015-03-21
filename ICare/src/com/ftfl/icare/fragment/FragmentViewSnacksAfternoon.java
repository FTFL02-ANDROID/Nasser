package com.ftfl.icare.fragment;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareDailyDietDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ICareDailyDietChart;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class FragmentViewSnacksAfternoon extends Fragment implements
		TimePickerDialog.OnTimeSetListener {

	TextView mTvTime = null;
	TextView mTvFoodItem = null;
	LinearLayout mLviewDiet = null;

	LinearLayout mLupdateDiet = null;
	LinearLayout mLdeleteDiet = null;
	EditText mEtTime = null;
	EditText mEtFoodItem = null;

	String mEventName = ICareConstants.SNACKS_AFTERNOON_TAG;

	String mId = "";

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

	public FragmentViewSnacksAfternoon() {

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
		View view = inflater.inflate(R.layout.fragment_layout_view_daily_diet,
				container, false);

		mTvTime = (TextView) view.findViewById(R.id.tvViewDailyDietTime);
		mTvFoodItem = (TextView) view
				.findViewById(R.id.tvViewDailyDietFoodMenu);

		mLviewDiet = (LinearLayout) view.findViewById(R.id.layoutViewDiet);
		mLupdateDiet = (LinearLayout) view.findViewById(R.id.layoutUpdateDiet);
		mLdeleteDiet = (LinearLayout) view.findViewById(R.id.layoutDeleteDiet);

		ICareDailyDietDataSource dietDS = new ICareDailyDietDataSource(
				getActivity());

		ICareDailyDietChart diet = new ICareDailyDietChart();

		if (ICareConstants.SELECTED_DIET_DATE.equals("")) {
			diet = dietDS.todayEvent(mEventName);
		} else {
			diet = dietDS.singleDietEvent(mEventName);

			mId = diet.getId();
		}

		mTvTime.setText(diet.getTime());
		mTvFoodItem.setText(diet.getFoodMenu());

		/*
		 * performing update work
		 */

		if (ICareConstants.DIET_UPDATE_STATE.equals("update")) {
			mLviewDiet.setVisibility(View.GONE);
			mLupdateDiet.setVisibility(View.VISIBLE);

			mEtTime = (EditText) view
					.findViewById(R.id.et_update_daily_diet_time);
			// etTime.setOnFocusChangeListener(this);
			mEtFoodItem = (EditText) view
					.findViewById(R.id.et_update_daily_diet_food_item);
			mEtTime.setText(diet.getTime());

			mEtFoodItem.setText(diet.getFoodMenu());
			mEtTime.addTextChangedListener(textWatcher);
			mEtFoodItem.addTextChangedListener(textWatcher);
			// View TimePicker
			final Calendar calendar = Calendar.getInstance();
			final TimePickerDialog timePickerDialog = TimePickerDialog
					.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY),
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
					timePickerDialog.show(getActivity()
							.getSupportFragmentManager(),
							ICareConstants.TIMEPICKER_TAG);
				}
			});

			Button btnSave = (Button) view
					.findViewById(R.id.button_update_daily_diet_menu);

			btnSave.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					if (!ICareConstants.SELECTED_DIET_DATE.equals("")) {
						String time = mEtTime.getText().toString();

						String foodItem = mEtFoodItem.getText().toString();
						if (!(time.equals("") || foodItem.equals(""))) {
							ICareDailyDietDataSource dietDS = new ICareDailyDietDataSource(
									getActivity());

							ICareDailyDietChart updateDiet = new ICareDailyDietChart(
									ICareConstants.SELECTED_DIET_DATE, time,
									foodItem, mEventName, "",
									ICareConstants.SELECTED_PROFILE_ID);
							if (!mId.equals("")) {
								int id = Integer.parseInt(mId);
								if (dietDS.updateData(id, updateDiet)) {

									Toast.makeText(
											getActivity(),
											getString(R.string.successfully_update),
											Toast.LENGTH_SHORT).show();

									// ((HomeActivity)getActivity()).SelectItem(6);

									ICareConstants.DIET_UPDATE_STATE = "";
									((HomeActivity) getActivity())
											.reloadCurrentFragment();

								}

								else {
									Toast.makeText(getActivity(),
											getString(R.string.not_update),
											Toast.LENGTH_SHORT).show();
								}
							} else {
								if (dietDS.insert(updateDiet)) {

									Toast.makeText(
											getActivity(),
											getString(R.string.successfully_update),
											Toast.LENGTH_SHORT).show();

									// ((HomeActivity)getActivity()).SelectItem(6);

									ICareConstants.DIET_UPDATE_STATE = "";
									((HomeActivity) getActivity())
											.reloadCurrentFragment();

								}

								else {
									Toast.makeText(getActivity(),
											getString(R.string.not_update),
											Toast.LENGTH_SHORT).show();
								}
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

		}

		else if (ICareConstants.DIET_UPDATE_STATE.equals("delete")
				&& ICareConstants.FRAGMANT_POSITION == 3) {
			new AlertDialog.Builder(getActivity())
					.setTitle("Delete " + mEventName)
					.setMessage("Are you sure you want to delete this entry?")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									deleteData();
								}
							})
					.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ICareConstants.DIET_UPDATE_STATE = "";
									((HomeActivity) getActivity())
											.reloadCurrentFragment();
								}
							}).setIcon(android.R.drawable.ic_dialog_alert)
					.show();
		}

		return view;
	}

	/*
	 * This function is use for delete selected data.
	 */

	public void deleteData() {
		mLviewDiet.setVisibility(View.GONE);
		mLupdateDiet.setVisibility(View.GONE);
		mLdeleteDiet.setVisibility(View.VISIBLE);

		ICareDailyDietDataSource dietDSTwo = new ICareDailyDietDataSource(
				getActivity());

		if (!mId.equals("")) {
			int id = Integer.parseInt(mId);
			dietDSTwo.deleteData(id);
		} else
			Toast.makeText(getActivity(), getString(R.string.no_data_delete),
					Toast.LENGTH_SHORT).show();

		ICareConstants.DIET_UPDATE_STATE = "";
		((HomeActivity) getActivity()).reloadCurrentFragment();
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
