package com.ftfl.icare.fragment;

import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.ftfl.icare.R;
import com.ftfl.icare.util.ICareConstants;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class FragmentNewDailyDiet extends Fragment implements
		OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	LinearLayout mNewDate = null;
	TextView mTvShowDate = null;

	public FragmentNewDailyDiet() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(
				R.layout.fragment_layout_new_daily_diet_list, container, false);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				this, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), false);

		ICareConstants.SELECTED_DIET_DATE = "";

		mNewDate = (LinearLayout) view
				.findViewById(R.id.layout_new_daily_diet_date);
		mTvShowDate = (TextView) view.findViewById(R.id.tv_select_date);
		mNewDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.setCloseOnSingleTapDay(false);
				datePickerDialog.show(
						getActivity().getSupportFragmentManager(),
						ICareConstants.DATEPICKER_TAG);
			}
		});

		if (savedInstanceState != null) {
			DatePickerDialog dpd = (DatePickerDialog) getActivity()
					.getSupportFragmentManager().findFragmentByTag(
							ICareConstants.DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getActivity()
					.getSupportFragmentManager().findFragmentByTag(
							ICareConstants.TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) view.findViewById(R.id.pagerDailyDiet);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		return view;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager tabFragment) {
			super(tabFragment);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;

			switch (position) {
			case 0:
				fragment = new FragmentNewBreakfast();
				break;

			case 1:
				fragment = new FragmentNewSnacksMorning();
				break;
			case 2:
				fragment = new FragmentNewLunch();
				break;

			case 3:
				fragment = new FragmentNewSnacksAfternoon();
				break;
			case 4:
				fragment = new FragmentNewDinner();
				break;

			}
			return fragment;

		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_breakfast).toUpperCase(l);
			case 1:
				return getString(R.string.title_snacks_morning).toUpperCase(l);
			case 2:
				return getString(R.string.title_lunch).toUpperCase(l);
			case 3:
				return getString(R.string.title_snacks_afternoon)
						.toUpperCase(l);
			case 4:
				return getString(R.string.title_dinner).toUpperCase(l);

			}
			return null;
		}

	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {

		/*
		 * mTvShowDate.setText(new StringBuilder() // Month is 0 based so add 1
		 * .append(year).append("/").append(month + 1)
		 * .append("/").append(day));
		 */

		if (day < 10) {
			mTvShowDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(year).append("/").append(month + 1).append("/")
					.append("0" + day));
		} else
			mTvShowDate.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(year).append("/").append(month + 1).append("/")
					.append(day));

		ICareConstants.SELECTED_DIET_DATE = mTvShowDate.getText().toString();

	}
}
