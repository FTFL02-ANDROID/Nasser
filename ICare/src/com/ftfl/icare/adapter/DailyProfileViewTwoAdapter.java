package com.ftfl.icare.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.utill.ICareActivityChart;

public class DailyProfileViewTwoAdapter extends ArrayAdapter<ICareActivityChart>{
	
	TextView tvEvent, tvTime, tvManu;
	ImageView imageAlarm;
	
	private final Activity context;
	List<ICareActivityChart> allDailyDietChart;
	
	public DailyProfileViewTwoAdapter(Activity context, List<ICareActivityChart> allDailyDietChart) {
		super(context, R.layout.activity_list_view,allDailyDietChart);
		this.context = context;
		this.allDailyDietChart = allDailyDietChart;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ICareActivityChart mDietChart;
		mDietChart = allDailyDietChart.get(position);
		
		LayoutInflater inflater = context.getLayoutInflater();
		
		View rowView = inflater.inflate(R.layout.activity_list_view, null, true);
		
		tvEvent = (TextView) rowView.findViewById(R.id.viewEvent);
		tvEvent.setText(mDietChart.getFoodMenu().toString());
		
		tvTime = (TextView) rowView.findViewById(R.id.viewTime);
		tvTime.setText(mDietChart.getTime().toString());
		
		tvManu = (TextView) rowView.findViewById(R.id.viewManu);
		tvManu.setText(mDietChart.getTime().toString());
		
		String mAlarm = mDietChart.getAlarm();
		if(mAlarm!=" "){
		
		imageAlarm = (ImageView) rowView.findViewById(R.id.imageAlarm);
	
		}
		
		return rowView;
	}
	
	

}
