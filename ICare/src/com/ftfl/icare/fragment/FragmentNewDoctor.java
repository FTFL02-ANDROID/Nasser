package com.ftfl.icare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.DoctorProfileDataSource;
import com.ftfl.icare.util.DoctorProfile;
import com.ftfl.icare.util.ICareConstants;

public class FragmentNewDoctor extends Fragment {
	EditText etName = null;
	EditText etSpecialization = null;
	EditText etPhone = null;
	EditText etEmail = null;
	EditText etAddress = null;

	Button btnSave = null;

	String doctorId = null;

	public FragmentNewDoctor() {

	}

	public void viewPreviousData() {
		int lId = Integer.parseInt(doctorId);
		DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
				getActivity());
		DoctorProfile viewProfile = doctorDS.singleProfileData(lId);
		etName.setText(viewProfile.getName());
		etSpecialization.setText(viewProfile.getSpecialization());
		etPhone.setText(viewProfile.getPhone());
		etEmail.setText(viewProfile.getEmail());
		etAddress.setText(viewProfile.getAddress());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_new_doctor,
				container, false);

		((HomeActivity) getActivity()).disableMenu();

		etName = (EditText) view.findViewById(R.id.et_new_doctor_name);
		etSpecialization = (EditText) view
				.findViewById(R.id.et_new_doctor_specialization);
		etPhone = (EditText) view.findViewById(R.id.et_new_doctor_phone);
		etEmail = (EditText) view.findViewById(R.id.et_new_doctor_email);
		etAddress = (EditText) view.findViewById(R.id.et_new_doctor_address);
		btnSave = (Button) view.findViewById(R.id.button_create_doctor_profile);

		doctorId = getArguments().getString(ICareConstants.DOCTOR_ID);
		if (doctorId != null) {
			viewPreviousData();
		}

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String name = etName.getText().toString();
				String specialization = etSpecialization.getText().toString();
				String phone = etPhone.getText().toString();
				String email = etEmail.getText().toString();
				String address = etAddress.getText().toString();

				if (!(name.equals("") || specialization.equals(""))) {

					DoctorProfile insertDoctorProfile = new DoctorProfile(name,
							specialization, phone, email, address,
							ICareConstants.SELECTED_PROFILE_ID);

					DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
							getActivity());

					if (doctorId != null) {
						int lId = Integer.parseInt(doctorId);
						if (doctorDS.updateData(lId, insertDoctorProfile)) {

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentDoctor fragmentNewDoctor = new FragmentDoctor();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentNewDoctor);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						}

						else {
							Toast.makeText(getActivity(), "not Saved",
									Toast.LENGTH_SHORT).show();
						}
					}

					else {
						if (doctorDS.insert(insertDoctorProfile)) {
							// ((HomeActivity)getActivity()).SelectItem(7);

							Toast.makeText(getActivity(), "Successfully Saved",
									Toast.LENGTH_SHORT).show();

							FragmentDoctor fragmentNewDoctor = new FragmentDoctor();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentNewDoctor);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						}

						else {
							Toast.makeText(getActivity(), "not Saved",
									Toast.LENGTH_SHORT).show();
						}
					}

				}
			}
		});

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {

					((HomeActivity) getActivity()).SelectItem(5);
					return true;
				} else {
					return false;
				}
			}
		});

		return view;
	}
}
