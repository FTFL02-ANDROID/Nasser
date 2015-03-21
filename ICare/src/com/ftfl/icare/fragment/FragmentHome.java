package com.ftfl.icare.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;

public class FragmentHome extends Fragment {

	TextView mTvDietChart;
	TextView mTvDoctorInfo;
	TextView mTvVaccinationChart;
	TextView mTvGallery;
	TextView mTvImportantNotes;
	TextView mTvMedicalHistory;

	public FragmentHome() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.home, container, false);
		((HomeActivity) getActivity()).disableMenu();

		mTvDietChart = (TextView) view.findViewById(R.id.tv_home_diet_chart);

		mTvDietChart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(6);
			}
		});

		mTvDoctorInfo = (TextView) view.findViewById(R.id.tv_home_doctor);

		mTvDoctorInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(7);
			}
		});

		mTvVaccinationChart = (TextView) view
				.findViewById(R.id.tv_home_vaccine);

		mTvVaccinationChart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(8);
			}
		});

		mTvGallery = (TextView) view.findViewById(R.id.tv_home_gallery);

		mTvGallery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(9);
			}
		});

		mTvImportantNotes = (TextView) view.findViewById(R.id.tv_home_notes);

		mTvImportantNotes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(10);
			}
		});

		mTvMedicalHistory = (TextView) view
				.findViewById(R.id.tv_home_medical_history);

		mTvMedicalHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(11);
			}
		});

		return view;
	}
}
