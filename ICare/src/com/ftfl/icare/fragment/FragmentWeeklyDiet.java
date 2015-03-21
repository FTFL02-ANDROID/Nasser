package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareDailyDietDataSource;
import com.ftfl.icare.util.ICareConstants;

public class FragmentWeeklyDiet extends Fragment {

	List<String> weeklyDietDateList = new ArrayList<String>();

	public FragmentWeeklyDiet() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_weekly_diet_list,
				container, false);

		weeklyDietDateList.clear();

		ICareDailyDietDataSource mDietDS = new ICareDailyDietDataSource(
				getActivity());
		weeklyDietDateList = mDietDS.getWeeklyDates();

		ListView listview = (ListView) view.findViewById(R.id.list_weekly_diet);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_layout_note, R.id.label, weeklyDietDateList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				ICareConstants.SELECTED_DIET_DATE = weeklyDietDateList
						.get(position);

				((HomeActivity) getActivity()).SelectItem(35);

			}
		});

		return view;
	}
}
