package com.ftfl.icare;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.DailyProfileViewAdapter;
import com.ftfl.icare.database.ICareDietDataSource;
import com.ftfl.icare.database.ICareSQLiteHelper;
import com.ftfl.icare.utill.FTFLConstants;
import com.ftfl.icare.utill.ICareActivityChart;

public class ICareDailyDietChartViewActivity extends Activity {
	ListView mListView = null;
	ICareDietDataSource mDietDataSource = null;
	ICareSQLiteHelper mDBHelper = null;
	ICareActivityChart dailyDietChart = null;
	int id_To_Update = 0;
	String mDate = "";
	TextView textDate;
	String mId = "";
	TextView mId_tv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_daily_diet_chart_view);

		final String[] option = new String[] { getString(R.string.editData),
				getString(R.string.deleteData) };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.selectData));
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					editData(mId);
				}
				if (which == 1) {
					deleteData(mId);
				}
			}
		});
		final AlertDialog dialog = builder.create();

		mDBHelper = new ICareSQLiteHelper(this);
		mDietDataSource = new ICareDietDataSource(this);
		textDate = (TextView) findViewById(R.id.textDailyDietChartDate);

		Intent mIntent = getIntent();
		mDate = mIntent.getStringExtra(FTFLConstants.ACTIVITYDATE);

		textDate.setText(mDate);
		List<ICareActivityChart> allDailyDietChart = mDietDataSource
				.getDailyDietChart(mDate);

		DailyProfileViewAdapter arrayAdapter = new DailyProfileViewAdapter(
				this, allDailyDietChart);

		// adding it to the list view.
		mListView = (ListView) findViewById(R.id.lvDailyDietChart);
		mListView.setAdapter(arrayAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mId_tv = (TextView) view.findViewById(R.id.viewId);
				mId = mId_tv.getText().toString(); // get id
				dialog.show();
			}
		});
	}

	public void editData(String eId) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				ICareCreateDietChartActivity.class);
		mEditIntent.putExtra("FTFLConstants.ACTIVITYID", eId);
		// startActivity(mEditIntent);
		startActivityForResult(mEditIntent, 2);
	}

	public void deleteData(String eId) {
		mDietDataSource = new ICareDietDataSource(this);
		Long lId = Long.parseLong(eId);
		mDietDataSource.deleteData(lId);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icare_daily_diet_chart_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
