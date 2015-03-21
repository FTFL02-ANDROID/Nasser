package com.ftfl.icare.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.DoctorProfileDataSource;
import com.ftfl.icare.util.DoctorProfile;

public class FragmentViewDoctorProfile extends Fragment {

	TextView mTvName = null;
	TextView mTvSpeciality = null;
	TextView mTvPhone = null;
	TextView mTvEmail = null;
	TextView mTvAddress = null;
	String mMobile = "";
	String mEmailAddress = "";

	public FragmentViewDoctorProfile() {

	}

	public void performCall(String pnoneNumber) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + pnoneNumber));
		getActivity().startActivity(callIntent);

	}

	public void performEmail(String emailAddress) {
		Intent email = new Intent(Intent.ACTION_SEND);

		email.putExtra(Intent.EXTRA_EMAIL, emailAddress);

		// Use email client only
		email.setType("message/rfc822");
		getActivity().startActivity(
				Intent.createChooser(email, "Choose an email client"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_doctor,
				container, false);
		((HomeActivity) getActivity()).enableMenu();

		mTvName = (TextView) view.findViewById(R.id.tvViewDoctorName);
		mTvSpeciality = (TextView) view
				.findViewById(R.id.tvViewDoctorSpecialization);
		mTvPhone = (TextView) view.findViewById(R.id.tvViewDoctorPhone);
		mTvEmail = (TextView) view.findViewById(R.id.tvViewDoctorEmail);
		mTvAddress = (TextView) view.findViewById(R.id.tvViewDoctorAddress);

		String profileId = getArguments().getString("doctorid");
		int lProfileId = Integer.parseInt(profileId);

		DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
				getActivity());
		DoctorProfile viewProfile = doctorDS.singleProfileData(lProfileId);
		mTvName.setText(viewProfile.getName());
		mTvSpeciality.setText(viewProfile.getSpecialization());
		mTvPhone.setText(viewProfile.getPhone());
		mTvEmail.setText(viewProfile.getEmail());
		mTvAddress.setText(viewProfile.getAddress());

		mMobile = viewProfile.getPhone();
		mEmailAddress = viewProfile.getEmail();

		mTvPhone.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				performCall(mMobile);

			}
		});

		mTvEmail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				performEmail(mEmailAddress);

			}
		});
		return view;
	}
}
