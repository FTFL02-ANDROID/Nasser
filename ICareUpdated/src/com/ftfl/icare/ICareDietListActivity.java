package com.ftfl.icare;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.adapter.DailyProfileViewAdapter;
import com.ftfl.icare.database.ICareDietDataSource;
import com.ftfl.icare.database.ICareSQLiteHelper;
import com.ftfl.icare.utill.FTFLConstants;
import com.ftfl.icare.utill.ICareActivityChart;

public class ICareDietListActivity extends ActionBarActivity {
	ListView mListViewOne = null;
	ListView mListViewTwo = null;
	ICareDietDataSource mDietDataSource = null;
	ICareSQLiteHelper mDBHelper = null;
	TextView tvDate = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_diet_list);

		mDBHelper = new ICareSQLiteHelper(this);
		mDietDataSource = new ICareDietDataSource(this);

		List<ICareActivityChart> allDailyDietChart = mDietDataSource
				.getTodayDietChart();

		DailyProfileViewAdapter arrayAdapterOne = new DailyProfileViewAdapter(
				this, allDailyDietChart);

		// adding it to the list view.
		mListViewOne = (ListView) findViewById(R.id.lvTodayDietChart);
		mListViewOne.setAdapter(arrayAdapterOne);

		List<String> upcomingDates = mDietDataSource.getUpcomingDates();
		// DailyProfileViewThreeAdapter arrayAdapterTwo = new
		// DailyProfileViewThreeAdapter(this, allDailyDietChart);
		ArrayAdapter<String> mDatesAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, upcomingDates);
		// adding it to the list view.
		mListViewTwo = (ListView) findViewById(R.id.lvUpComingDietChart);
		mListViewTwo.setAdapter(mDatesAdapter);

		mListViewTwo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				tvDate = (TextView) view.findViewById(android.R.id.text1);
				String dateValue = tvDate.getText().toString(); // get date

				Intent mPreviewIntent = new Intent(getApplicationContext(),
						ICareDailyDietChartViewActivity.class);
				mPreviewIntent.putExtra(FTFLConstants.ACTIVITYDATE, dateValue);

				startActivity(mPreviewIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icare_diet_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.history:
			startActivity(new Intent(ICareDietListActivity.this,
					ICareDietHistoryActivity.class));
			return true;
		case R.id.createDiet:
			startActivity(new Intent(ICareDietListActivity.this,
					ICareCreateDietChartActivity.class));
			return true;
		case R.id.myprofile:
			startActivity(new Intent(ICareDietListActivity.this,
					ICareProfilePreviewAcitvity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
