package com.ftfl.icare.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.VaccineDataSource;
import com.ftfl.icare.util.Vaccine;

public class FragmentViewVaccine extends Fragment {

	TextView mTvName = null;
	TextView mTvDate = null;
	TextView mTvTime = null;
	TextView mTvStatus = null;

	public FragmentViewVaccine() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_vaccine,
				container, false);

		((HomeActivity) getActivity()).enableMenu();

		mTvName = (TextView) view.findViewById(R.id.tvViewVaccineName);
		mTvDate = (TextView) view.findViewById(R.id.tvViewVaccineDate);
		mTvTime = (TextView) view.findViewById(R.id.tvViewVaccineTime);
		mTvStatus = (TextView) view.findViewById(R.id.tvViewVaccineStatus);

		String vaccineId = getArguments().getString("vaccineid");
		int lVaccineId = Integer.parseInt(vaccineId);

		VaccineDataSource vaccineDS = new VaccineDataSource(getActivity());
		Vaccine viewVaccine = vaccineDS.singleVaccineData(lVaccineId);
		mTvName.setText(viewVaccine.getName());
		mTvDate.setText(viewVaccine.getDate());
		mTvTime.setText(viewVaccine.getTime());
		mTvStatus.setText(viewVaccine.getStatus());

		return view;
	}
}
