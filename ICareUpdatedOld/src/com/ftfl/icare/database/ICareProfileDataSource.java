package com.ftfl.icare.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.icare.utill.ICareProfile;

public class ICareProfileDataSource {

	// Database fields
	private SQLiteDatabase iCareDatabase;
	private ICareSQLiteHelper iCareDbHelper;
	List<ICareProfile> iCareProfilesList = new ArrayList<ICareProfile>();

	public ICareProfileDataSource(Context context) {
		iCareDbHelper = new ICareSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		iCareDatabase = iCareDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		iCareDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(ICareProfile eProfile) {

		this.open();

		ContentValues cvInsert = new ContentValues();

		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
				eProfile.getName());

		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_AGE, eProfile.getAge());
		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
				eProfile.getBlooGroup());
		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
				eProfile.getWeight());
		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
				eProfile.getHeight());
		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
				eProfile.getDateOfBirth());

		cvInsert.put(ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES,
				eProfile.getSpecialNotes());

		long check = iCareDatabase.insert(ICareSQLiteHelper.I_CARE_PROFILE,
				null, cvInsert);

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eProfileId, ICareProfile eProfile) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
				eProfile.getName());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_AGE, eProfile.getAge());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
				eProfile.getBlooGroup());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
				eProfile.getWeight());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
				eProfile.getHeight());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
				eProfile.getDateOfBirth());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES,
				eProfile.getSpecialNotes());

		int check = iCareDatabase.update(ICareSQLiteHelper.I_CARE_PROFILE,
				cvUpdate, ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "="
						+ eProfileId, null);

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eProfileId) {
		this.open();
		try {
			iCareDatabase.delete(ICareSQLiteHelper.I_CARE_PROFILE,
					ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "=" + eProfileId,
					null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<ICareProfile> iCareProfilesList() {
		this.open();

		Cursor mCursor = iCareDatabase.query(ICareSQLiteHelper.I_CARE_PROFILE,
				new String[] { ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_AGE,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES },
				null, null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_ID));
					String mName = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME));
					String mAge = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_AGE));
					String mBloodGroup = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP));
					String mWeight = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT));
					String mHeight = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT));
					String mDateOfBirth = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH));
					String mSpecialNotes = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES));
					// long mmId = Long.parseLong(mId);
					iCareProfilesList.add(new ICareProfile(mId, mName, mAge,
							mBloodGroup, mWeight, mHeight, mDateOfBirth,
							mSpecialNotes));
				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return iCareProfilesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given id is set to the profile and return a profile class object.
	 */
	public ICareProfile singleProfileData() {
		this.open();
		ICareProfile iCareUpdateProfile;
		String mId;
		String mName;
		int profileID = 1;
		String mAge;
		String mBloodGroup;
		String mWeight;
		String mHeight;
		String mDateOfBirth;
		String mSpecialNotes;

		Cursor mUpdateCursor = iCareDatabase.query(
				ICareSQLiteHelper.I_CARE_PROFILE, new String[] {
						ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_AGE,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES },
				ICareSQLiteHelper.COL_ICARE_PROFILE_ID + "=" + profileID, null,
				null, null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_ID));
		mName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_NAME));
		mAge = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_AGE));
		mBloodGroup = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP));
		mWeight = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT));
		mHeight = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT));
		mDateOfBirth = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH));
		mSpecialNotes = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES));
		mUpdateCursor.close();
		iCareUpdateProfile = new ICareProfile(mId, mName, mAge, mBloodGroup,
				mWeight, mHeight, mDateOfBirth, mSpecialNotes);
		this.close();
		return iCareUpdateProfile;
	}

	public boolean isEmpty() {
		this.open();
		Cursor mCursor = iCareDatabase.query(ICareSQLiteHelper.I_CARE_PROFILE,
				new String[] { ICareSQLiteHelper.COL_ICARE_PROFILE_ID,
						ICareSQLiteHelper.COL_ICARE_PROFILE_NAME,
						ICareSQLiteHelper.COL_ICARE_PROFILE_AGE,
						ICareSQLiteHelper.COL_ICARE_PROFILE_BLOOD_GROUP,
						ICareSQLiteHelper.COL_ICARE_PROFILE_WEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_HEIGHT,
						ICareSQLiteHelper.COL_ICARE_PROFILE_DATE_OF_BIRTH,
						ICareSQLiteHelper.COL_ICARE_PROFILE_SPECIAL_NOTES },
				null, null, null, null, null);
		if (mCursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}
}
