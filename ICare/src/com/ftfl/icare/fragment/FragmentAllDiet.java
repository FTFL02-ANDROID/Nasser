package com.ftfl.icare.fragment;//FragmentAllDiet

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

public class FragmentAllDiet extends Fragment {

	List<String> allDietDateList = new ArrayList<String>();

	public FragmentAllDiet() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_all_diet_list,
				container, false);

		allDietDateList.clear();

		ICareDailyDietDataSource mDietDS = new ICareDailyDietDataSource(
				getActivity());
		allDietDateList = mDietDS.getAllDates();

		ListView listview = (ListView) view.findViewById(R.id.list_all_diet);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_layout_note, R.id.label, allDietDateList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				ICareConstants.SELECTED_DIET_DATE = allDietDateList
						.get(position);

				((HomeActivity) getActivity()).SelectItem(35);

			}
		});

		return view;
	}
}