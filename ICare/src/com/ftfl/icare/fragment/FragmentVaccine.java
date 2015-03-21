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
import com.ftfl.icare.adapter.VaccinationCustomAdapter;
import com.ftfl.icare.database.VaccineDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.Vaccine;

public class FragmentVaccine extends Fragment {

	List<Vaccine> vaccineList = new ArrayList<Vaccine>();

	List<String> vaccineNameList = new ArrayList<String>();
	List<String> vaccineIdList = new ArrayList<String>();

	ListView mListView = null;

	Bundle bundle = new Bundle();

	public FragmentVaccine() {

	}

	public void setData() {
		VaccineDataSource vaccineDS = new VaccineDataSource(getActivity());

		vaccineList = vaccineDS.vaccineList();

		for (int i = 0; i < vaccineList.size(); i++) {
			vaccineNameList.add(vaccineList.get(i).getName());
			vaccineIdList.add(vaccineList.get(i).getId());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_vaccine,
				container, false);
		((HomeActivity) getActivity()).disableMenu();

		ImageButton ibCreateVaccine = (ImageButton) view
				.findViewById(R.id.button_create_vaccine);

		ibCreateVaccine.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				((HomeActivity) getActivity()).SelectItem(32);
			}
		});

		mListView = (ListView) view.findViewById(R.id.list_vaccine);

		vaccineList.clear();
		vaccineNameList.clear();

		setData();

		Resources res = getResources();
		VaccinationCustomAdapter adapter = new VaccinationCustomAdapter(
				getActivity(), vaccineList, res);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_VACCINE;
				((HomeActivity) getActivity()).mId = vaccineIdList
						.get(position);

				FragmentViewVaccine fragmentViewVaccine = new FragmentViewVaccine();
				bundle.putString("vaccineid", vaccineIdList.get(position));
				fragmentViewVaccine.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewVaccine);
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
