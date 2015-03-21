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

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;

public class FragmentCareCenter extends Fragment {

	Button mBtnCareCenterOne = null;
	Button mBtnCareCenterTwo = null;
	Button mBtnCareCenterThree = null;
	Button mBtnCareCenterFour = null;
	Button mBtnCareCenterFive = null;
	Bundle bundle = new Bundle();

	public FragmentCareCenter() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_care_center,
				container, false);

		mBtnCareCenterOne = (Button) view.findViewById(R.id.buttonHCOne);
		mBtnCareCenterTwo = (Button) view.findViewById(R.id.buttonHCTwo);
		mBtnCareCenterThree = (Button) view.findViewById(R.id.buttonHCThree);
		mBtnCareCenterFour = (Button) view.findViewById(R.id.buttonHCFour);
		mBtnCareCenterFive = (Button) view.findViewById(R.id.buttonHCFive);

		mBtnCareCenterOne.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentGoogleMap fragmentViewCareCenter = new FragmentGoogleMap();
				bundle.putString("address",
						"Plot # 15, Road # 71, Gulshan-2, Dhaka");
				bundle.putString("phone", "+8802-8836000");
				bundle.putString("lat", "23.779885");
				bundle.putString("lan", "90.413218");
				bundle.putString("name", "United Hospital");
				fragmentViewCareCenter.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewCareCenter);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		mBtnCareCenterTwo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentGoogleMap fragmentViewCareCenter = new FragmentGoogleMap();
				bundle.putString("address",
						"Plot # 81, Block # E, Bashudhara R/A, Dhaka");
				bundle.putString("phone", "+8802-8401661");
				bundle.putString("lat", "23.700922");
				bundle.putString("lan", "90.428220");
				bundle.putString("name", "Appolo Hospital");
				fragmentViewCareCenter.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewCareCenter);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		mBtnCareCenterThree.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentGoogleMap fragmentViewCareCenter = new FragmentGoogleMap();
				bundle.putString("address",
						"House#71/A, Road#5/A, Dhanmondi R/A, Dhaka");
				bundle.putString("phone", "+8802-9144280");
				bundle.putString("lat", "23.742016");
				bundle.putString("lan", "90.379455");
				bundle.putString("name", "MediNova Medical");
				fragmentViewCareCenter.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewCareCenter);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		mBtnCareCenterFour.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentGoogleMap fragmentViewCareCenter = new FragmentGoogleMap();
				bundle.putString("address",
						"(10th floor), 18, Kamal Ataturk Avenue, Banani, Dhaka");
				bundle.putString("phone", "+8802-9893528");
				bundle.putString("lat", "23.793964");
				bundle.putString("lan", "90.403907");
				bundle.putString("name", "Armi Hospital");
				fragmentViewCareCenter.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewCareCenter);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		mBtnCareCenterFive.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentGoogleMap fragmentViewCareCenter = new FragmentGoogleMap();
				bundle.putString("address",
						"122, Kazi Nazrul Islam Avenue, Shahbag, Dhaka");
				bundle.putString("phone", "+8802-9661551");
				bundle.putString("lat", "23.810332");
				bundle.putString("lan", "90.412518");
				bundle.putString("name", "BIRDEM Hospital");
				fragmentViewCareCenter.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewCareCenter);
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
