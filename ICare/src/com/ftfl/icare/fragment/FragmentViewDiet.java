package com.ftfl.icare.fragment;

import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.util.ICareConstants;

public class FragmentViewDiet extends Fragment {

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	TextView mTvShowDate = null;

	public FragmentViewDiet() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_diet,
				container, false);
		((HomeActivity) getActivity()).enableMenu();

		((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_DIET;

		mTvShowDate = (TextView) view.findViewById(R.id.tv_show_date);

		mTvShowDate.setText(ICareConstants.SELECTED_DIET_DATE);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getChildFragmentManager());

		mViewPager = (ViewPager) view.findViewById(R.id.pagerViewSingleDiet);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageSelected(int arg0) {

				ICareConstants.FRAGMANT_POSITION = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

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

			}
			return null;

		}

	}
}
