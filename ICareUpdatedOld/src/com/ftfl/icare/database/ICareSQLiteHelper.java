package com.ftfl.icare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ICareSQLiteHelper extends SQLiteOpenHelper {
	// ICare Profile Table
	public static final String I_CARE_PROFILE = "icareprofiles";
	public static final String COL_ICARE_PROFILE_ID = "id";
	public static final String COL_ICARE_PROFILE_NAME = "name";
	public static final String COL_ICARE_PROFILE_AGE = "age";
	public static final String COL_ICARE_PROFILE_BLOOD_GROUP = "blood_group";
	public static final String COL_ICARE_PROFILE_WEIGHT = "weight";
	public static final String COL_ICARE_PROFILE_HEIGHT = "height";
	public static final String COL_ICARE_PROFILE_DATE_OF_BIRTH = "dateofbirth";
	public static final String COL_ICARE_PROFILE_SPECIAL_NOTES = "special_notes";

	// ICare Activity Chart Table
	public static final String ICARE_DIET_CHART = "icaredietchart";
	public static final String COL_ICARE_DIET_ID = "diet_id";
	public static final String COL_ICARE_DIET_DATE = "date";
	public static final String COL_ICARE_DIET_TIME = "time";
	public static final String COL_ICARE_DIET_FOOD_MENU = "food_menu";
	public static final String COL_ICARE_DIET_EVENT_NAME = "event_name";
	public static final String COL_ICARE_DIET_ALARM = "set_alarm";

	private static final String DATABASE_NAME = "iCare.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_PROFILE = "create table "
			+ I_CARE_PROFILE + "( " + COL_ICARE_PROFILE_ID
			+ " integer primary key autoincrement, " + " "
			+ COL_ICARE_PROFILE_NAME + " text not null," + " "
			+ COL_ICARE_PROFILE_AGE + " text not null," + " "
			+ COL_ICARE_PROFILE_BLOOD_GROUP + " text not null," + " "
			+ COL_ICARE_PROFILE_WEIGHT + " text not null," + " "
			+ COL_ICARE_PROFILE_HEIGHT + " text not null," + " "
			+ COL_ICARE_PROFILE_DATE_OF_BIRTH + " text not null," + " "
			+ COL_ICARE_PROFILE_SPECIAL_NOTES + " text not null);";

	private static final String DATABASE_CREATE_DACTIVITY = "create table "
			+ ICARE_DIET_CHART + "(" + COL_ICARE_DIET_ID
			+ " integer primary key autoincrement, " + COL_ICARE_DIET_DATE
			+ " text not null," + COL_ICARE_DIET_TIME + " text not null,"
			+ COL_ICARE_DIET_FOOD_MENU + " text not null,"
			+ COL_ICARE_DIET_EVENT_NAME + " text not null,"
			+ COL_ICARE_DIET_ALARM + " text not null);";

	public ICareSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_PROFILE);
		database.execSQL(DATABASE_CREATE_DACTIVITY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ICareSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + I_CARE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + ICARE_DIET_CHART);
		onCreate(db);
	}

}
