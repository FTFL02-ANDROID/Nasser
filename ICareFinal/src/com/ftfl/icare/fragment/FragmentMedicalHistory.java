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
import com.ftfl.icare.adapter.MedicalHistoryCustomAdapter;
import com.ftfl.icare.database.MedicalHistoryDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.MedicalHistory;

public class FragmentMedicalHistory extends Fragment {

	List<MedicalHistory> mMedicalHistoryList = new ArrayList<MedicalHistory>();

	List<String> medicalHistoryDateList = new ArrayList<String>();
	List<String> medicalHistoryIdList = new ArrayList<String>();

	ListView mListView = null;

	Bundle bundle = new Bundle();

	public FragmentMedicalHistory() {

	}

	public void setData() {
		MedicalHistoryDataSource medicalHistoryDS = new MedicalHistoryDataSource(
				getActivity());

		mMedicalHistoryList = medicalHistoryDS.medicalHistoryList();

		for (int i = 0; i < mMedicalHistoryList.size(); i++) {
			medicalHistoryDateList.add(mMedicalHistoryList.get(i).getDate());
			medicalHistoryIdList.add(mMedicalHistoryList.get(i).getId());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_medical_history,
				container, false);
		((HomeActivity) getActivity()).disableMenu();

		ImageButton ibCreateMedicalHistory = (ImageButton) view
				.findViewById(R.id.button_create_medical_history);

		ibCreateMedicalHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				((HomeActivity) getActivity()).SelectItem(33);
			}
		});

		mListView = (ListView) view.findViewById(R.id.list_medical_history);

		mMedicalHistoryList.clear();
		medicalHistoryDateList.clear();

		setData();

		Resources res = getResources();
		MedicalHistoryCustomAdapter adapter = new MedicalHistoryCustomAdapter(
				getActivity(), mMedicalHistoryList, res);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_MEDICAL_HISTORY;
				((HomeActivity) getActivity()).mId = medicalHistoryIdList
						.get(position);

				FragmentViewMedicalHistory fragmentMedicalHistory = new FragmentViewMedicalHistory();
				bundle.putString("medicalhistoryid",
						medicalHistoryIdList.get(position));
				fragmentMedicalHistory.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentMedicalHistory);
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
