package com.ftfl.icare;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.icare.database.ICareDietDataSource;
import com.ftfl.icare.database.ICareSQLiteHelper;
import com.ftfl.icare.utill.FTFLConstants;

public class ICareDietHistoryActivity extends Activity {
	ListView mListView = null;
	TextView tvDate  = null;
	ICareDietDataSource mDietDataSource  = null;
	ICareSQLiteHelper mDBHelper  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icare_diet_history);
		
		mDBHelper = new ICareSQLiteHelper(this);	
		mDietDataSource = new ICareDietDataSource(this);
		mListView = (ListView) findViewById(R.id.lvDietChartHistory);
		
		List<String> allDates = null;
		if(FTFLConstants.RADIOBTN == FTFLConstants.ZERO)
		{
			allDates = mDietDataSource.getAllDates();
		}
		
		else if(FTFLConstants.RADIOBTN == FTFLConstants.TWO)
		{	
			allDates = mDietDataSource.getPreviousDates(-7);
		}
		
		else
		{
			allDates = mDietDataSource.getPreviousDates(-30);
		}
		
		ArrayAdapter<String> mDatesAdapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, allDates);
		// adding it to the list view.
		mListView.setAdapter(mDatesAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				tvDate = (TextView) view.findViewById(android.R.id.text1);
				String dateValue = tvDate.getText().toString(); 
				Intent mPreviewIntent = new Intent(getApplicationContext(),
						ICareDailyDietChartViewActivity.class);
				mPreviewIntent.putExtra("cDate", dateValue);

				startActivity(mPreviewIntent);
			}
		});
	}
	
	public void viweAllData(View v){
		FTFLConstants.RADIOBTN = FTFLConstants.ZERO;
		 startActivity(new Intent(ICareDietHistoryActivity.this,
				 ICareDietHistoryActivity.class));
	}
	
	public void viewWeeklyData(View v){
		FTFLConstants.RADIOBTN = FTFLConstants.ONE;
		 
		 startActivity(new Intent(ICareDietHistoryActivity.this,
				 ICareDietHistoryActivity.class));
	}
	
	public void viewMonthlyData(View v){
		FTFLConstants.RADIOBTN = FTFLConstants.TWO;
		 
		 startActivity(new Intent(ICareDietHistoryActivity.this,
				 ICareDietHistoryActivity.class));
	}
}
