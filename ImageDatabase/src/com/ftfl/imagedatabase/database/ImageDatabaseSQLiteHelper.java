package com.ftfl.imagedatabase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImageDatabaseSQLiteHelper extends SQLiteOpenHelper {
	// Shopping Complex information Table
	public static final String TABLE_IMAGE_INFO = "image_information";
	public static final String COL_ID = "id";

	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_DATE = "date";
	public static final String COL_TIME = "time";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_IMAGE_PATH = "image_path";

	private static final String DATABASE_NAME = "imagedatabase.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_PROFILE = "create table "
			+ TABLE_IMAGE_INFO + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_LATTITUDE
			+ " text not null," + " " + COL_LONGITUDE + " text not null," + " "
			+ COL_DATE + " text not null," + " " + COL_TIME + " text not null,"
			+ " " + COL_DESCRIPTION + " text not null," + " " + COL_IMAGE_PATH
			+ " text not null);";

	public ImageDatabaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_PROFILE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ImageDatabaseSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_PROFILE);
		onCreate(db);
	}

}
