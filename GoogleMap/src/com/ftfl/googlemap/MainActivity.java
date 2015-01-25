package com.ftfl.googlemap;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements LocationListener {
	// Google Map
	GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			try {
				// Loading map
				initilizeMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);

			try {
				LocationManager locMgr = (LocationManager) this
						.getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				String locProvider = locMgr.getBestProvider(criteria, false);
				Location location = locMgr.getLastKnownLocation(locProvider);

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
				onLocationChanged(location);
				//locMgr.requestLocationUpdates(provider, 20000, 0, this);
			} catch (NullPointerException ne) {
				Log.e("Current Location", "Current Lat Lng is Null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);


		
		// create marker
		MarkerOptions marker = new MarkerOptions().position(latLng).title("Your Current Location ");
		 
		// adding marker
		googleMap.addMarker(marker);
		
		MarkerOptions marker2 = new MarkerOptions().position(new LatLng(latitude-0.1, longitude-0.1)).title("Second Marker ");
		marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		googleMap.addMarker(marker2);
		
		String url = makeURL(latitude, longitude, latitude-0.1, longitude-0.1);
		connectAsyncTask myTask = new connectAsyncTask(url);
		myTask.execute();
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		

		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	
	 public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
	        StringBuilder urlString = new StringBuilder();
	        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	        urlString.append("?origin=");// from
	        urlString.append(Double.toString(sourcelat));
	        urlString.append(",");
	        urlString
	                .append(Double.toString( sourcelog));
	        urlString.append("&destination=");// to
	        urlString
	                .append(Double.toString( destlat));
	        urlString.append(",");
	        urlString.append(Double.toString( destlog));
	        urlString.append("&sensor=false&mode=driving&alternatives=true");
	        return urlString.toString();
	 }
	 
	 
	 public void drawPath(String  result) {

		    try {
		            //Transform the string into a json object
		           final JSONObject json = new JSONObject(result);
		           JSONArray routeArray = json.getJSONArray("routes");
		           JSONObject routes = routeArray.getJSONObject(0);
		           JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
		           String encodedString = overviewPolylines.getString("points");
		           List<LatLng> list = decodePoly(encodedString);

		           for(int z = 0; z<list.size()-1;z++){
		                LatLng src= list.get(z);
		                LatLng dest= list.get(z+1);
		                Polyline line = googleMap.addPolyline(new PolylineOptions()
		                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
		                .width(2)
		                .color(Color.BLUE).geodesic(true));
		            }

		    } 
		    catch (JSONException e) {

		    }
		} 
	 
	 private List<LatLng> decodePoly(String encoded) {

		    List<LatLng> poly = new ArrayList<LatLng>();
		    int index = 0, len = encoded.length();
		    int lat = 0, lng = 0;

		    while (index < len) {
		        int b, shift = 0, result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lat += dlat;

		        shift = 0;
		        result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lng += dlng;

		        LatLng p = new LatLng( (((double) lat / 1E5)),
		                 (((double) lng / 1E5) ));
		        poly.add(p);
		    }

		    return poly;
		}
	 
	 private class connectAsyncTask extends AsyncTask<Void, Void, String>{
		    private ProgressDialog progressDialog;
		    String url;
		    connectAsyncTask(String urlPass){
		        url = urlPass;
		    }
		    @Override
		    protected void onPreExecute() {
		        // TODO Auto-generated method stub
		        super.onPreExecute();
		        progressDialog = new ProgressDialog(MainActivity.this);
		        progressDialog.setMessage("Fetching route, Please wait...");
		        progressDialog.setIndeterminate(true);
		        progressDialog.show();
		    }
		    @Override
		    protected String doInBackground(Void... params) {
		        JSONParser jParser = new JSONParser();
		        String json = jParser.getJSONFromUrl(url);
		        return json;
		    }
		    @Override
		    protected void onPostExecute(String result) {
		        super.onPostExecute(result);   
		        progressDialog.hide();        
		        if(result!=null){
		            drawPath(result);
		        }
		    }
		}
}
