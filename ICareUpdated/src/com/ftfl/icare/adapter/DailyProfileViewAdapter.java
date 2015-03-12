package com.ftfl.icare.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.utill.ICareActivityChart;

public class DailyProfileViewAdapter extends ArrayAdapter<ICareActivityChart> {

	private static LayoutInflater inflater = null;

	List<ICareActivityChart> allDailyDietChart = new ArrayList<ICareActivityChart>();

	public DailyProfileViewAdapter(Activity context,
			List<ICareActivityChart> allDailyDietChart) 
	{
		super(context, R.layout.activity_list_view, allDailyDietChart);
		
		this.allDailyDietChart = allDailyDietChart;
		
		/*********** Layout inflator to call external xml layout () ***********/
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {
		public TextView id;
		public TextView event;
		public TextView time;
		public TextView foodMenu;
		public ImageView image;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.activity_list_view, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			
			holder.id = (TextView) vi.findViewById(R.id.viewId);
			
			holder.event = (TextView) vi.findViewById(R.id.viewEvent);
			
			holder.time = (TextView) vi.findViewById(R.id.viewTime);
			
			holder.foodMenu = (TextView) vi.findViewById(R.id.viewManu);
			
			holder.image = (ImageView) vi.findViewById(R.id.imageAlarm);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} 
		else
			holder = (ViewHolder) vi.getTag();

		ICareActivityChart mDietChart = allDailyDietChart.get(position);

		holder.id.setText(mDietChart.getId().toString());
		
		holder.event.setText(mDietChart.getEventName().toString());

		holder.time.setText(mDietChart.getTime().toString());

		holder.foodMenu.setText(mDietChart.getFoodMenu().toString());

		String mAlarm = mDietChart.getAlarm();
		
		if (mAlarm == "0") {
			holder.image.setVisibility(View.GONE);
		} else {
			holder.image.setVisibility(View.VISIBLE);
		}
		return vi;
	}
}
