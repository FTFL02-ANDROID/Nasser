package com.ftfl.icare.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ICareProfile;

public class FragmentViewProfile extends Fragment {

	TextView mTvName = null;
	TextView mTvDateOfBirth = null;
	TextView mTvGender = null;
	ImageView mShowImage = null;

	TextView mTvHeight = null;
	TextView mTvWeight = null;
	TextView mTvBloodGroup = null;
	ICareProfile mUpdateProfile = null;
	ICareProfileDataSource mDataSource = null;
	int mSelectedProfileId = 0;

	public FragmentViewProfile() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_profile,
				container, false);
		((HomeActivity) getActivity()).enableMenu();

		((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_PROFILE_TAG;
		mTvName = (TextView) view.findViewById(R.id.tvViewProfileName);
		mTvDateOfBirth = (TextView) view.findViewById(R.id.tvViewDateOfBirth);
		mTvGender = (TextView) view.findViewById(R.id.tvViewGender);
		mTvHeight = (TextView) view.findViewById(R.id.tvViewHeight);
		mTvWeight = (TextView) view.findViewById(R.id.tvViewWeight);
		mTvBloodGroup = (TextView) view.findViewById(R.id.tvViewBloodGroup);

		mShowImage = (ImageView) view.findViewById(R.id.viewProfileImage);

		/*
		 * get the profile which include all data from database according
		 * profileId of the clicked item.
		 */
		mDataSource = new ICareProfileDataSource(getActivity());

		mSelectedProfileId = Integer
				.parseInt(ICareConstants.SELECTED_PROFILE_ID);

		mUpdateProfile = mDataSource.singleProfileData(mSelectedProfileId);
		String mName = mUpdateProfile.getName();
		mTvName.setText(mName);
		mTvDateOfBirth.setText(mUpdateProfile.getDateOfBirth());
		mTvGender.setText(mUpdateProfile.getGender());
		mTvHeight.setText(mUpdateProfile.getHeight());
		mTvWeight.setText(mUpdateProfile.getWeight());
		mTvBloodGroup.setText(mUpdateProfile.getBlooGroup());
		String imagePath = mUpdateProfile.getImage();

		if (imagePath != "") {

			Bitmap correctBmp = null;

			try {
				File f = new File(imagePath);
				ExifInterface exif = new ExifInterface(f.getPath());
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				int angle = 0;

				if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
					angle = 90;
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
					angle = 180;
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
					angle = 270;
				}

				Matrix mat = new Matrix();
				mat.postRotate(angle);
				BitmapFactory.Options optionBitmap = new BitmapFactory.Options();
				optionBitmap.inSampleSize = 1;
				Bitmap bmp1 = BitmapFactory.decodeStream(
						new FileInputStream(f), null, optionBitmap);
				correctBmp = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(),
						bmp1.getHeight(), mat, true);
			} catch (IOException e) {
				Log.w("TAG", "-- Error in setting image");
			} catch (OutOfMemoryError oom) {
				Log.w("TAG", "-- OOM Error in setting image");
			}
			mShowImage.setImageBitmap(correctBmp);
		}

		return view;
	}
}
