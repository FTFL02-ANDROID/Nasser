package com.ftfl.icare;

import java.util.List;

import com.ftfl.icare.database.ICareDietDataSource;
import com.ftfl.icare.database.ICareSQLiteHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ICareDietHistoryActivity extends Activity {
	ListView mListView;
	TextView tvDate;
	ICareDietDataSource mDietDataSource;
	ICareSQLiteHelper mDBHelper;
	
	Integer check = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_diet_history);
		
		mDBHelper = new ICareSQLiteHelper(this);	
		mDietDataSource = new ICareDietDataSource(this);
		
		if(check==0)
		{
			List<String> allDates = mDietDataSource.allDates();
			
			ArrayAdapter<String> mDatesAdapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_list_item_1, allDates);
			// adding it to the list view.
			mListView = (ListView) findViewById(R.id.lvHistoryDietChart);
			mListView.setAdapter(mDatesAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent,
						android.view.View view, int position, long id) {

					tvDate = (TextView) view.findViewById(android.R.id.text1);
					String dateValue = tvDate.getText().toString(); // get id
																		// from text
																		// view

					/*
					 * create an intent and send data
					 */
					Intent mPreviewIntent = new Intent(getApplicationContext(),
							ICareDailyDietChartViewActivity.class);
					mPreviewIntent.putExtra("cDate", dateValue);

					startActivity(mPreviewIntent);
				}
			});
		}
		
		else
		{
			
		}
		
	}
	
	public void viweAllData(View v){
		 if(check>0)
		 {
			 check--;
		 }
		 startActivity(new Intent(ICareDietHistoryActivity.this,
				 ICareDietHistoryActivity.class));
		
	}
	
	public void viewWeeklyData(View v){
		 if(check<1)
		 {
			 check++;
		 }
		 startActivity(new Intent(ICareDietHistoryActivity.this,
				 ICareDietHistoryActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icare_diet_history, menu);
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
