package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareDailyDietDataSource;

public class FragmentViewDailyDiet extends Fragment {

	List<String> upcomingDietIdList = new ArrayList<String>();
	List<String> upcomingDietDateList = new ArrayList<String>();

	public FragmentViewDailyDiet() {

	}

	public void setData() {
		ICareDailyDietDataSource mDietDS = new ICareDailyDietDataSource(
				getActivity());
		upcomingDietDateList = mDietDS.getUpcomingDates();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_daily_diet_list,
				container, false);
		upcomingDietIdList.clear();
		upcomingDietDateList.clear();

		setData();
		ListView listview = (ListView) view
				.findViewById(R.id.list_upcoming_diet);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_layout_note, R.id.label, upcomingDietDateList);

		listview.setAdapter(adapter);
		return view;
	}
}
