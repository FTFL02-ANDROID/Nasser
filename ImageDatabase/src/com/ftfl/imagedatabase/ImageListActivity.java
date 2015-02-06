package com.ftfl.imagedatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ftfl.imagedatabase.adapter.CustomAdapter;
import com.ftfl.imagedatabase.database.ImageDatabaseDataSource;
import com.ftfl.imagedatabase.util.ImageInformation;

public class ImageListActivity extends ActionBarActivity {
	ImageDatabaseDataSource mImageDS = null;
	List<ImageInformation> mImageList = new ArrayList<ImageInformation>();
	ListView mListView = null;
	CustomAdapter adapter = null;
	Location location = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);

		try {
			LocationManager locMgr = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String locProvider = locMgr.getBestProvider(criteria, false);
			location = locMgr.getLastKnownLocation(locProvider);

			// getting GPS status
			boolean isGPSEnabled = locMgr
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			boolean isNWEnabled = locMgr
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (isGPSEnabled && isNWEnabled)

			{
				if (isNWEnabled)
					if (locMgr != null)
						location = locMgr
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (isGPSEnabled)
					if (location == null)
						if (locMgr != null)
							location = locMgr
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}

		} catch (NullPointerException ne) {
			Log.e("Current Location", "Current Lat Lng is Null");
		} catch (Exception e) {
			e.printStackTrace();
		}

		mImageDS = new ImageDatabaseDataSource(this);
		mImageList = mImageDS.imageInformationData();
		Resources res = getResources();
		mListView = (ListView) findViewById(R.id.imageList);
		adapter = new CustomAdapter(this, mImageList, res, location);
		mListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_list, menu);
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
