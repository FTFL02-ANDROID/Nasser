package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.adapter.DoctorCustomAdapter;
import com.ftfl.icare.database.DoctorProfileDataSource;
import com.ftfl.icare.util.DoctorProfile;
import com.ftfl.icare.util.ICareConstants;

public class FragmentDoctor extends Fragment {

	ImageButton mibCreateDoctor = null;

	List<DoctorProfile> profileList = new ArrayList<DoctorProfile>();

	List<String> doctorNameList = new ArrayList<String>();
	List<String> doctorIdList = new ArrayList<String>();

	ListView mListView = null;

	Bundle bundle = new Bundle();

	public FragmentDoctor() {

	}

	public void setData() {
		DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
				getActivity());

		profileList = doctorDS.doctorProfileList();

		for (int i = 0; i < profileList.size(); i++) {
			doctorNameList.add(profileList.get(i).getName());
			doctorIdList.add(profileList.get(i).getId());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_doctor,
				container, false);

		mibCreateDoctor = (ImageButton) view
				.findViewById(R.id.button_create_doctor);

		mibCreateDoctor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// ((HomeActivity)getActivity()).SelectItem(33);
				((HomeActivity) getActivity()).SelectItem(31);
				/*
				 * FragmentNewDoctor fragmentNewNote = new FragmentNewDoctor();
				 * 
				 * 
				 * FragmentManager fragmentManager = getFragmentManager();
				 * FragmentTransaction fragmentTransaction =
				 * fragmentManager.beginTransaction();
				 * fragmentTransaction.replace(R.id.content_frame,
				 * fragmentNewNote); fragmentTransaction.addToBackStack(null);
				 * fragmentTransaction.commit();
				 */
			}
		});

		mListView = (ListView) view.findViewById(R.id.list_doctor);

		profileList.clear();
		doctorNameList.clear();

		setData();

		Resources res = getResources();
		DoctorCustomAdapter adapter = new DoctorCustomAdapter(getActivity(),
				profileList, res);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_DOCTOR_PROFILE;
				((HomeActivity) getActivity()).mId = doctorIdList.get(position);

				FragmentViewDoctorProfile fragmentViewDoctor = new FragmentViewDoctorProfile();
				bundle.putString("doctorid", doctorIdList.get(position));
				fragmentViewDoctor.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewDoctor);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();

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
