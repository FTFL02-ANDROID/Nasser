package com.ftfl.icare.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.util.ICareProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CustomSpinnerAdapter extends ArrayAdapter<ICareProfile>{

	public CustomSpinnerAdapter(Context context, int layoutResourceID,
			int textViewResourceId, List<ICareProfile> spinnerDataList) {
		super(context, layoutResourceID, textViewResourceId, spinnerDataList);
		
		this.context=context;
		this.layoutResID=layoutResourceID;
		this.spinnerData=spinnerDataList;
	}

	Context context;
	int layoutResID;
	List<ICareProfile> spinnerData;
	
	public CustomSpinnerAdapter(Context context, int layoutResourceID,
			List<ICareProfile> spinnerDataList) {
		super(context, layoutResourceID, spinnerDataList);
		
		this.context=context;
		this.layoutResID=layoutResourceID;
		this.spinnerData=spinnerDataList;
	
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		View row=convertView;
		SpinnerHolder holder;
		if(row==null)
		{
			LayoutInflater inflater=((Activity)context).getLayoutInflater();
			
			row=inflater.inflate(layoutResID, parent, false);
			holder=new SpinnerHolder();
			
			holder.userImage=(ImageView)row.findViewById(R.id.left_pic);
			holder.name=(TextView)row.findViewById(R.id.text_main_name);
			row.setTag(holder);
		}
		else
		{
			holder=(SpinnerHolder)row.getTag();
		}
		ICareProfile spinnerItem=spinnerData.get(position);
		String imagePath = spinnerItem.getImage();
	      DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(50)).cacheInMemory(true).cacheOnDisk(true).build();
			ImageLoader.getInstance().displayImage("file:///"+imagePath,holder.userImage , options);
		holder.name.setText(spinnerItem.getName());
		return row;
	}
	
	private static class SpinnerHolder
	{
		ImageView userImage;
		TextView  name;
	}
}
