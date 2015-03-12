package com.ftfl.icare.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ftfl.icare.utill.ICareActivityChart;

public class ICareDietDataSource {

	// Database fields
	private SQLiteDatabase iCareDacDatabase;
	private ICareSQLiteHelper iCareDacDbHelper;
	List<ICareActivityChart> dailyDietChart = new ArrayList<ICareActivityChart>();
	List<ICareActivityChart> todayDietChart = new ArrayList<ICareActivityChart>();
	List<String> upcomingDates = new ArrayList<String>();
	List<String> allDates = new ArrayList<String>();
	List<String> weeklyDates = new ArrayList<String>();
	List<String> monthlyDates = new ArrayList<String>();
	String mCurrentDate = "";

	public ICareDietDataSource(Context context) {
		iCareDacDbHelper = new ICareSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		iCareDacDatabase = iCareDacDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		iCareDacDbHelper.close();
	}

	/*
	 * providing the current date
	 */
	public void cDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy",
				Locale.getDefault());
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(ICareActivityChart eActivityChart) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(ICareSQLiteHelper.COL_ICARE_DIET_DATE, eActivityChart.getDate());
		cv.put(ICareSQLiteHelper.COL_ICARE_DIET_TIME, eActivityChart.getTime());
		cv.put(ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU,
				eActivityChart.getFoodMenu());
		cv.put(ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME,
				eActivityChart.getEventName());
		cv.put(ICareSQLiteHelper.COL_ICARE_DIET_ALARM,
				eActivityChart.getAlarm());

		long check = iCareDacDatabase.insert(
				ICareSQLiteHelper.ICARE_DIET_CHART, null, cv);
		this.close();
		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eActivityId,
			ICareActivityChart eActivityChart) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_DIET_DATE,
				eActivityChart.getDate());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_DIET_TIME,
				eActivityChart.getTime());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU,
				eActivityChart.getFoodMenu());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME,
				eActivityChart.getEventName());
		cvUpdate.put(ICareSQLiteHelper.COL_ICARE_DIET_ALARM,
				eActivityChart.getAlarm());

		int check = iCareDacDatabase.update(ICareSQLiteHelper.ICARE_DIET_CHART,
				cvUpdate, ICareSQLiteHelper.COL_ICARE_DIET_ID + "="
						+ eActivityId, null);
		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eActivityId) {
		this.open();
		iCareDacDatabase.delete(ICareSQLiteHelper.ICARE_DIET_CHART,
				ICareSQLiteHelper.COL_ICARE_DIET_ID + "=" + eActivityId, null);
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<ICareActivityChart> getDailyDietChart(String eDate) {
		this.open();
		this.cDate();

		Cursor mCursor = iCareDacDatabase.query(
				ICareSQLiteHelper.ICARE_DIET_CHART, new String[] {
						ICareSQLiteHelper.COL_ICARE_DIET_ID,
						ICareSQLiteHelper.COL_ICARE_DIET_DATE,
						ICareSQLiteHelper.COL_ICARE_DIET_TIME,
						ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU,
						ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME,
						ICareSQLiteHelper.COL_ICARE_DIET_ALARM, },
				ICareSQLiteHelper.COL_ICARE_DIET_DATE + "= '" + eDate + "' ",
				null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String mActivityId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ID));
					String mActivityDate = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
					String mActivityTime = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_TIME));
					String mDietFoodMenu = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU));
					String mDietEventName = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME));
					String mSetAlarm = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ALARM));

					dailyDietChart.add(new ICareActivityChart(mActivityId,
							mActivityDate, mActivityTime, mDietFoodMenu,
							mDietEventName, mSetAlarm));
				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return dailyDietChart;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<ICareActivityChart> getTodayDietChart() {
		this.open();
		this.cDate();

		Cursor mCursor = iCareDacDatabase.query(
				ICareSQLiteHelper.ICARE_DIET_CHART, new String[] {
						ICareSQLiteHelper.COL_ICARE_DIET_ID,
						ICareSQLiteHelper.COL_ICARE_DIET_DATE,
						ICareSQLiteHelper.COL_ICARE_DIET_TIME,
						ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU,
						ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME,
						ICareSQLiteHelper.COL_ICARE_DIET_ALARM,

				}, ICareSQLiteHelper.COL_ICARE_DIET_DATE + "= '" + mCurrentDate
						+ "' ", null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mActivityId = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ID));
					String mActivityDate = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
					String mActivityTime = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_TIME));
					String mDietFoodMenu = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU));
					String mDietEventName = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME));
					String mSetAlarm = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ALARM));

					todayDietChart.add(new ICareActivityChart(mActivityId,
							mActivityDate, mActivityTime, mDietFoodMenu,
							mDietEventName, mSetAlarm));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return todayDietChart;
	}

	/*
	 * using cursor for display upcoming data from database.
	 */
	public List<String> getUpcomingDates() {
		this.open();
		this.cDate();

		Cursor mCursor = iCareDacDatabase.rawQuery("SELECT DISTINCT "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + " FROM "
				+ ICareSQLiteHelper.ICARE_DIET_CHART + "  WHERE "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + " > '" + mCurrentDate
				+ "' ORDER BY " + ICareSQLiteHelper.COL_ICARE_DIET_DATE
				+ " ASC", null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
					String mActivityDate = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
					upcomingDates.add(mActivityDate);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return upcomingDates;
	}

	/*
	 * using cursor for display weekly data from database.
	 */
	public List<String> getPreviousDates(int eLimit) {
		this.open();
		this.cDate();

		Calendar weekly = Calendar.getInstance();

		weekly.add(Calendar.DATE, eLimit);

		StringBuilder week = new StringBuilder()
				.append(weekly.get(Calendar.DATE)).append("/")
				.append((weekly.get(Calendar.MONTH)) + 1).append("/")
				.append(weekly.get(Calendar.YEAR));
		String mWeek = week.toString();
		Cursor mCursor = iCareDacDatabase.rawQuery("SELECT DISTINCT "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + " FROM "
				+ ICareSQLiteHelper.ICARE_DIET_CHART + "  WHERE "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + " BETWEEN '"
				+ mCurrentDate + "' AND '" + mWeek + "' ORDER BY "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + "", null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
					String mActivityDate = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
					weeklyDates.add(mActivityDate);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return weeklyDates;
	}

	/*
	 * using cursor for display all data from database.
	 */
	public List<String> getAllDates() {
		this.open();
		this.cDate();

		Cursor mCursor = iCareDacDatabase.rawQuery("SELECT DISTINCT "
				+ ICareSQLiteHelper.COL_ICARE_DIET_DATE + " FROM "
				+ ICareSQLiteHelper.ICARE_DIET_CHART + "", null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
					String mActivityDate = mCursor
							.getString(mCursor
									.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
					allDates.add(mActivityDate);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return allDates;
	}

	/*
	 * create a Diet chart of ICareProfile. Here the data of the database
	 * according to the given id is set to the Diet chart and return a Diet
	 * chart object.
	 */

	public ICareActivityChart updateActivityData(long eActivityId) {
		this.open();
		ICareActivityChart iCareUpdateActivity;

		Cursor mUpdateCursor = iCareDacDatabase.query(
				ICareSQLiteHelper.ICARE_DIET_CHART, new String[] {
						ICareSQLiteHelper.COL_ICARE_DIET_ID,
						ICareSQLiteHelper.COL_ICARE_DIET_DATE,
						ICareSQLiteHelper.COL_ICARE_DIET_TIME,
						ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU,
						ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME,
						ICareSQLiteHelper.COL_ICARE_DIET_ALARM,

				}, ICareSQLiteHelper.COL_ICARE_DIET_ID + "=" + eActivityId,
				null, null, null, null);

		mUpdateCursor.moveToFirst();

		String mActivityId = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ID));
		String mActivityDate = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_DATE));
		String mActivityTime = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_TIME));
		String mDietFoodMenu = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_FOOD_MENU));
		String mDietEventName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_EVENT_NAME));
		String mSetAlarm = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(ICareSQLiteHelper.COL_ICARE_DIET_ALARM));

		mUpdateCursor.close();
		iCareUpdateActivity = new ICareActivityChart(mActivityId,
				mActivityDate, mActivityTime, mDietFoodMenu, mDietEventName,
				mSetAlarm);
		this.close();
		return iCareUpdateActivity;
	}

}
