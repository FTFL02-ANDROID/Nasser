package com.ftfl.icare.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.util.DrawerItem;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ICareProfile;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final DrawerItemHolder drawerHolder;
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view
					.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

			drawerHolder.spinner = (Spinner) view
					.findViewById(R.id.drawerSpinner);

			drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

			drawerHolder.headerLayout = (LinearLayout) view
					.findViewById(R.id.headerLayout);
			drawerHolder.itemLayout = (LinearLayout) view
					.findViewById(R.id.itemLayout);
			drawerHolder.spinnerLayout = (LinearLayout) view
					.findViewById(R.id.spinnerLayout);

			view.setTag(drawerHolder);

		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}

		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

		if (dItem.isSpinner()) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);

			ICareProfileDataSource mDataSource = new ICareProfileDataSource(
					this.context);

			List<ICareProfile> userList = mDataSource.iCareProfilesList();

			CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,
					R.layout.custom_spinner_item, userList);

			drawerHolder.spinner.setAdapter(adapter);
			drawerHolder.spinner.setSelection(ICareConstants.POSITION);

			drawerHolder.spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							ICareConstants.POSITION = arg2;

							savePosition(arg2);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

		} else if (dItem.getTitle() != null) {
			if (position < 3) {
				drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
				drawerHolder.itemLayout.setVisibility(LinearLayout.GONE);
				drawerHolder.spinnerLayout.setVisibility(LinearLayout.GONE);

				drawerHolder.title.setText("");
				Log.d("Getview", "Passed4");

			}

			else {
				drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
				drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
				drawerHolder.spinnerLayout
						.setVisibility(LinearLayout.INVISIBLE);
				drawerHolder.title.setText("");
				Log.d("Getview", "Passed4");
			}

		} else {

			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
			Log.d("Getview", "Passed5");
		}
		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName, title;
		ImageView icon;
		LinearLayout headerLayout, itemLayout, spinnerLayout;
		Spinner spinner;
	}

	/*
	 * This function is use for saving current position of the spinner selected
	 * by the user.
	 */

	public void savePosition(int position) {
		SharedPreferences sharedpreferences = this.context
				.getSharedPreferences(ICareConstants.MY_PREFERENCES,
						Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putInt(ICareConstants.POS, position);
		editor.commit();
		ICareProfileDataSource mDataSource = new ICareProfileDataSource(
				this.context);
		List<String> profileId = mDataSource.profilesId();

		ICareConstants.SELECTED_PROFILE_ID = profileId.get(position);
	}
}