package com.ftfl.shoppingcomplex.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.shoppingcomplex.util.ShoppingComplex;

public class ShoppingComplexDataSource {

	// Database fields
	private SQLiteDatabase shoppingComplexDatabase;
	private ShoppingComplexSQLiteHelper shoppingComplexDbHelper;
	List<ShoppingComplex> shoppingComplexList = new ArrayList<ShoppingComplex>();

	public ShoppingComplexDataSource(Context context) {
		shoppingComplexDbHelper = new ShoppingComplexSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		shoppingComplexDatabase = shoppingComplexDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		shoppingComplexDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(ShoppingComplex eInsertObject) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ShoppingComplexSQLiteHelper.COL_NAME, eInsertObject.getName());
		cv.put(ShoppingComplexSQLiteHelper.COL_ADDRESS,
				eInsertObject.getAddress());
		cv.put(ShoppingComplexSQLiteHelper.COL_LATTITUDE,
				eInsertObject.getLatitude());
		cv.put(ShoppingComplexSQLiteHelper.COL_LONGITUDE,
				eInsertObject.getLongitude());
		cv.put(ShoppingComplexSQLiteHelper.COL_CLOSE_DAY,
				eInsertObject.getCloseDay());
		cv.put(ShoppingComplexSQLiteHelper.COL_OPEN_TIME,
				eInsertObject.getDailyOpenTime());
		cv.put(ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION,
				eInsertObject.getServiceDescription());
		cv.put(ShoppingComplexSQLiteHelper.COL_IMAGE_PATH,
				eInsertObject.getImagePath());

		long check = shoppingComplexDatabase.insert(
				ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX, null, cv);
		shoppingComplexDatabase.close();

		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eId, ShoppingComplex eUpdateObject) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_NAME,
				eUpdateObject.getName());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_ADDRESS,
				eUpdateObject.getAddress());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_LATTITUDE,
				eUpdateObject.getLatitude());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_LONGITUDE,
				eUpdateObject.getLongitude());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_CLOSE_DAY,
				eUpdateObject.getCloseDay());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_OPEN_TIME,
				eUpdateObject.getDailyOpenTime());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION,
				eUpdateObject.getServiceDescription());
		cvUpdate.put(ShoppingComplexSQLiteHelper.COL_IMAGE_PATH,
				eUpdateObject.getImagePath());

		int check = shoppingComplexDatabase.update(
				ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX, cvUpdate,
				ShoppingComplexSQLiteHelper.COL_ID + "=" + eId, null);
		shoppingComplexDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eId) {
		this.open();
		try {
			shoppingComplexDatabase.delete(
					ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX,
					ShoppingComplexSQLiteHelper.COL_ID + "=" + eId, null);
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
	public List<ShoppingComplex> shoppingComplexData() {
		this.open();

		Cursor mCursor = shoppingComplexDatabase.query(
				ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX,
				new String[] { ShoppingComplexSQLiteHelper.COL_ID,
						ShoppingComplexSQLiteHelper.COL_NAME,
						ShoppingComplexSQLiteHelper.COL_ADDRESS,
						ShoppingComplexSQLiteHelper.COL_LATTITUDE,
						ShoppingComplexSQLiteHelper.COL_LONGITUDE,
						ShoppingComplexSQLiteHelper.COL_CLOSE_DAY,
						ShoppingComplexSQLiteHelper.COL_OPEN_TIME,
						ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION,
						ShoppingComplexSQLiteHelper.COL_IMAGE_PATH }, null,
				null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mId = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_ID));
					String mName = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_NAME));
					String mAddress = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_ADDRESS));
					String mLatitude = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_LATTITUDE));
					String mLongitude = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_LONGITUDE));
					String mCloseDay = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_CLOSE_DAY));
					String mOpenTime = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_OPEN_TIME));
					String mDescription = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION));
					String mImagePath = mCursor
							.getString(mCursor
									.getColumnIndex(ShoppingComplexSQLiteHelper.COL_IMAGE_PATH));

					shoppingComplexList.add(new ShoppingComplex(mId, mName,
							mAddress, mLatitude, mLongitude, mCloseDay,
							mOpenTime, mDescription, mImagePath));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return shoppingComplexList;
	}

	/*
	 * Provide data of the Shopping complex According to the shopping complex id
	 */
	public ShoppingComplex singleShoppingComplexData(long eShoppingComplexId) {
		this.open();
		ShoppingComplex informationObject;
		String mId;
		String mName;
		String mAddress;
		String mLatitude;
		String mLongitude;
		String mCloseDay;
		String mOpenTime;

		String mDescription;
		String mImagePath;

		Cursor mUpdateCursor = shoppingComplexDatabase.query(
				ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX,
				new String[] { ShoppingComplexSQLiteHelper.COL_ID,
						ShoppingComplexSQLiteHelper.COL_NAME,
						ShoppingComplexSQLiteHelper.COL_ADDRESS,
						ShoppingComplexSQLiteHelper.COL_LATTITUDE,
						ShoppingComplexSQLiteHelper.COL_LONGITUDE,
						ShoppingComplexSQLiteHelper.COL_CLOSE_DAY,
						ShoppingComplexSQLiteHelper.COL_OPEN_TIME,
						ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION,
						ShoppingComplexSQLiteHelper.COL_IMAGE_PATH },
				ShoppingComplexSQLiteHelper.COL_ID + "=" + eShoppingComplexId,
				null, null, null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_ID));
		mName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_NAME));
		mAddress = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_ADDRESS));
		mLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_LATTITUDE));
		mLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_LONGITUDE));
		mCloseDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_CLOSE_DAY));
		mOpenTime = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_OPEN_TIME));
		mDescription = mUpdateCursor
				.getString(mUpdateCursor
						.getColumnIndex(ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION));
		mImagePath = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ShoppingComplexSQLiteHelper.COL_IMAGE_PATH));
		mUpdateCursor.close();
		informationObject = new ShoppingComplex(mId, mName, mAddress,
				mLatitude, mLongitude, mCloseDay, mOpenTime, mDescription,
				mImagePath);
		this.close();
		return informationObject;
	}

	/*
	 * Check is the database empty or not
	 */

	public boolean isEmpty() {
		this.open();
		Cursor mCursor = shoppingComplexDatabase.query(
				ShoppingComplexSQLiteHelper.TABLE_SHOPPING_COMPLEX,
				new String[] { ShoppingComplexSQLiteHelper.COL_ID,
						ShoppingComplexSQLiteHelper.COL_NAME,
						ShoppingComplexSQLiteHelper.COL_ADDRESS,
						ShoppingComplexSQLiteHelper.COL_LATTITUDE,
						ShoppingComplexSQLiteHelper.COL_LONGITUDE,
						ShoppingComplexSQLiteHelper.COL_CLOSE_DAY,
						ShoppingComplexSQLiteHelper.COL_OPEN_TIME,
						ShoppingComplexSQLiteHelper.COL_SERVICE_DESCRIPTION },
				null, null, null, null, null);
		if (mCursor.getCount() == 0) {
			this.close();
			return true;
		}

		else {
			this.close();
			return false;
		}
	}

}
