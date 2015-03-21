package com.ftfl.icare.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.util.DoctorProfile;

public class DoctorCustomAdapter extends BaseAdapter {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private List<DoctorProfile> data = new ArrayList<DoctorProfile>();
	private static LayoutInflater inflater = null;
	public Resources res;
	Location mCurrentLocation = null;
	Location mImageLocation = null;

	/************* CustomAdapter Constructor *****************/
	public DoctorCustomAdapter(Activity eActivity, List<DoctorProfile> eData,
			Resources resLocal) {

		/********** Take passed values **********/
		activity = eActivity;
		data = eData;
		res = resLocal;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView name;
		public TextView specialization;
	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			view = inflater.inflate(R.layout.list_row, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.tvListDoctorName);
			holder.specialization = (TextView) view
					.findViewById(R.id.tvListSpecialization);

			/************ Set holder with LayoutInflater ************/
			view.setTag(holder);
		} else
			holder = (ViewHolder) view.getTag();

		if (data.size() <= 0) {

		} else {
			/***** Get each Model object from Arraylist ********/
			DoctorProfile doctor = (DoctorProfile) data.get(position);
			holder.name.setText("" + doctor.getName());
			holder.specialization.setText("" + doctor.getSpecialization());
		}
		return view;
	}
}
