package com.ftfl.imagedatabase.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ftfl.imagedatabase.util.ImageInformation;

public class ImageDatabaseDataSource {

	// Database fields
	private SQLiteDatabase mImageDatabase;
	private ImageDatabaseSQLiteHelper mImageDatabaseDbHelper;
	List<ImageInformation> mImageList = new ArrayList<ImageInformation>();

	public ImageDatabaseDataSource(Context context) {
		mImageDatabaseDbHelper = new ImageDatabaseSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mImageDatabase = mImageDatabaseDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mImageDatabaseDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(ImageInformation eInsertObject) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ImageDatabaseSQLiteHelper.COL_LATTITUDE,
				eInsertObject.getLatitude());
		cv.put(ImageDatabaseSQLiteHelper.COL_LONGITUDE,
				eInsertObject.getLongitude());
		cv.put(ImageDatabaseSQLiteHelper.COL_DATE, eInsertObject.getDate());
		cv.put(ImageDatabaseSQLiteHelper.COL_TIME, eInsertObject.getTime());
		cv.put(ImageDatabaseSQLiteHelper.COL_DESCRIPTION,
				eInsertObject.getDescription());
		cv.put(ImageDatabaseSQLiteHelper.COL_IMAGE_PATH,
				eInsertObject.getFileName());

		long check = mImageDatabase.insert(
				ImageDatabaseSQLiteHelper.TABLE_IMAGE_INFO, null, cv);
		mImageDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<ImageInformation> imageInformationData() {
		this.open();

		Cursor mCursor = mImageDatabase.query(
				ImageDatabaseSQLiteHelper.TABLE_IMAGE_INFO, new String[] {
						ImageDatabaseSQLiteHelper.COL_ID,

						ImageDatabaseSQLiteHelper.COL_LATTITUDE,
						ImageDatabaseSQLiteHelper.COL_LONGITUDE,
						ImageDatabaseSQLiteHelper.COL_DATE,
						ImageDatabaseSQLiteHelper.COL_TIME,
						ImageDatabaseSQLiteHelper.COL_DESCRIPTION,
						ImageDatabaseSQLiteHelper.COL_IMAGE_PATH }, null, null,
				null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String id = mCursor.getString(mCursor
							.getColumnIndex(ImageDatabaseSQLiteHelper.COL_ID));
					String latitude = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_LATTITUDE));
					String longitude = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_LONGITUDE));
					String date = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_DATE));
					String time = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_TIME));
					String description = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_DESCRIPTION));
					String imagePath = mCursor
							.getString(mCursor
									.getColumnIndex(ImageDatabaseSQLiteHelper.COL_IMAGE_PATH));

					mImageList.add(new ImageInformation(id, latitude,
							longitude, description, imagePath, date, time));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mImageList;
	}

}
