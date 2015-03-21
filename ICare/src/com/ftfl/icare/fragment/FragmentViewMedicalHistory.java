package com.ftfl.icare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.MedicalHistoryDataSource;
import com.ftfl.icare.util.MedicalHistory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentViewMedicalHistory extends Fragment {

	public FragmentViewMedicalHistory() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_layout_view_medical_history,
						container, false);
		((HomeActivity) getActivity()).enableMenu();

		TextView tvDate = (TextView) view
				.findViewById(R.id.tvViewMedicalHistoryDate);
		TextView tvDoctorName = (TextView) view
				.findViewById(R.id.tvViewMedicalHistoryDoctorName);
		TextView tvPurpose = (TextView) view
				.findViewById(R.id.tvViewMedicalHistoryPurpose);
		TextView tvSuggestion = (TextView) view
				.findViewById(R.id.tvViewMedicalHistorySuggestion);
		ImageView ivPrescription = (ImageView) view
				.findViewById(R.id.imageviewViewPrescription);

		String id = getArguments().getString("medicalhistoryid");
		int lId = Integer.parseInt(id);

		MedicalHistoryDataSource medicalHistoryDS = new MedicalHistoryDataSource(
				getActivity());
		MedicalHistory viewMedicalHistory = medicalHistoryDS
				.singleMedicalHistoryData(lId);

		tvDate.setText(viewMedicalHistory.getDate());
		tvDoctorName.setText(viewMedicalHistory.getDoctorName());
		tvPurpose.setText(viewMedicalHistory.getPurpose());
		tvSuggestion.setText(viewMedicalHistory.getSuggestion());

		String prescriptionImagePath = viewMedicalHistory.getPrescription();

		if (!prescriptionImagePath.equals("")) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(0))
					.cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance()
					.displayImage("file:///" + prescriptionImagePath,
							ivPrescription, options);
		}

		return view;
	}
}
