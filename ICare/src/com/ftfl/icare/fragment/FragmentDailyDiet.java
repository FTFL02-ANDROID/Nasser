package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareDailyDietDataSource;
import com.ftfl.icare.util.ICareConstants;

public class FragmentDailyDiet extends Fragment {

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	List<String> mUpcomingDietIdList = new ArrayList<String>();
	List<String> mUpcomingDietDateList = new ArrayList<String>();

	public FragmentDailyDiet() {

	}

	public void setData() {
		ICareDailyDietDataSource mDietDS = new ICareDailyDietDataSource(
				getActivity());
		mUpcomingDietDateList = mDietDS.getUpcomingDates();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_daily_diet_list,
				container, false);

		((HomeActivity) getActivity()).disableMenu();

		ImageButton ibCreateDailyDiet = (ImageButton) view
				.findViewById(R.id.button_create_daily_diet);

		ibCreateDailyDiet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				((HomeActivity) getActivity()).SelectItem(34);
			}
		});

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) view.findViewById(R.id.pagerViewDailyDiet);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mUpcomingDietIdList.clear();
		mUpcomingDietDateList.clear();

		setData();
		ListView listview = (ListView) view
				.findViewById(R.id.list_upcoming_diet);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_layout_note, R.id.label, mUpcomingDietDateList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				ICareConstants.SELECTED_DIET_DATE = mUpcomingDietDateList
						.get(position);

				((HomeActivity) getActivity()).SelectItem(35);

			}
		});

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
				fragment = new FragmentViewBreakfast();
				break;

			case 1:
				fragment = new FragmentViewSnacksMorning();
				break;
			case 2:
				fragment = new FragmentViewLunch();
				break;

			case 3:
				fragment = new FragmentViewSnacksAfternoon();
				break;
			case 4:
				fragment = new FragmentViewDinner();
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
			String pageName = "";

			switch (position) {
			case 0:
				pageName = getString(R.string.title_breakfast).toUpperCase(l);
				SpannableStringBuilder sb = new SpannableStringBuilder("    "
						+ pageName); // space added before text for convenience

				Drawable drawable = getActivity().getResources().getDrawable(
						R.drawable.breakfast);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());

				ImageSpan span = new ImageSpan(drawable);
				sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				return sb;
				// return getString(R.string.title_breakfast).toUpperCase(l);
			case 1:
				pageName = getString(R.string.title_snacks_morning)
						.toUpperCase(l);
				SpannableStringBuilder sb1 = new SpannableStringBuilder("    "
						+ pageName); // space added before text for convenience

				Drawable drawable1 = getActivity().getResources().getDrawable(
						R.drawable.snacks_morning);
				drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(),
						drawable1.getIntrinsicHeight());
				ImageSpan span1 = new ImageSpan(drawable1);
				sb1.setSpan(span1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				return sb1;
				// return
				// getString(R.string.title_snacks_morning).toUpperCase(l);
			case 2:
				pageName = getString(R.string.title_lunch).toUpperCase(l);
				SpannableStringBuilder sb2 = new SpannableStringBuilder("    "
						+ pageName); // space added before text for convenience

				Drawable drawable11 = getActivity().getResources().getDrawable(
						R.drawable.lunch);
				drawable11.setBounds(0, 0, drawable11.getIntrinsicWidth(),
						drawable11.getIntrinsicHeight());
				ImageSpan span11 = new ImageSpan(drawable11);
				sb2.setSpan(span11, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				return sb2;
				// return getString(R.string.title_lunch).toUpperCase(l);
			case 3:
				pageName = getString(R.string.title_snacks_afternoon)
						.toUpperCase(l);
				SpannableStringBuilder sb3 = new SpannableStringBuilder("    "
						+ pageName); // space added before text for convenience

				Drawable drawable3 = getActivity().getResources().getDrawable(
						R.drawable.snacks_morning);
				drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(),
						drawable3.getIntrinsicHeight());
				ImageSpan span3 = new ImageSpan(drawable3);
				sb3.setSpan(span3, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				return sb3;
				// return
				// getString(R.string.title_snacks_afternoon).toUpperCase(l);
			case 4:
				pageName = getString(R.string.title_dinner).toUpperCase(l);

				SpannableStringBuilder sb4 = new SpannableStringBuilder("    "
						+ pageName); // space added before text for convenience

				Drawable drawable4 = getActivity().getResources().getDrawable(
						R.drawable.lunch);
				drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(),
						drawable4.getIntrinsicHeight());
				ImageSpan span4 = new ImageSpan(drawable4);
				sb4.setSpan(span4, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				return sb4;
				// return getString(R.string.title_dinner).toUpperCase(l);

			}
			return null;

		}

	}
}
