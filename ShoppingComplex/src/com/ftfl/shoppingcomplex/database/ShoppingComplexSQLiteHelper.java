package com.ftfl.shoppingcomplex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingComplexSQLiteHelper extends SQLiteOpenHelper {
	// Shopping Complex information Table
	public static final String TABLE_SHOPPING_COMPLEX = "shoppingcomplexinformation";
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_ADDRESS = "address";
	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_CLOSE_DAY = "close_day";
	public static final String COL_OPEN_TIME = "open_time";
	public static final String COL_SERVICE_DESCRIPTION = "service_description";
	public static final String COL_IMAGE_PATH = "image_path";

	private static final String DATABASE_NAME = "ShoppingComplex.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_PROFILE = "create table "
			+ TABLE_SHOPPING_COMPLEX + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_ADDRESS + " text not null," + " "
			+ COL_LATTITUDE + " text not null," + " " + COL_LONGITUDE
			+ " text not null," + " " + COL_CLOSE_DAY + " text not null," + " "
			+ COL_OPEN_TIME + " text not null," + " " + COL_SERVICE_DESCRIPTION
			+ " text not null," + " " + COL_IMAGE_PATH + " text not null);";

	public ShoppingComplexSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_PROFILE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ShoppingComplexSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_COMPLEX);
		onCreate(db);
	}

}
