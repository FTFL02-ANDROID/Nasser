package com.ftfl.icare.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.adapter.ImageAdapter;
import com.ftfl.icare.util.ICareConstants;

public class FragmentGallery extends Fragment {
	List<String> mImagePathList = new ArrayList<String>();

	public FragmentGallery() {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				ICareConstants.IMAGE_DIRECTORY_NAME);
		for (File imageFile : mediaStorageDir.listFiles()) {
			if (imageFile.isFile()) {
				mImagePathList.add(imageFile.getAbsolutePath());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_gallery,
				container, false);

		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(getActivity(), mImagePathList));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showImage(position);

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

	public void showImage(int ePosition) {

		File imageFile = new File(mImagePathList.get(ePosition));
		Uri picUri = Uri.fromFile(imageFile);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(picUri, "image/*");
		startActivity(intent);
	}
}
