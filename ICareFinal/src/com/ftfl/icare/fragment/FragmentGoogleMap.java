package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.R;
import com.ftfl.icare.util.JSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class FragmentGoogleMap extends Fragment implements LocationListener {

	GoogleMap mGoogleMap;
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	String mPhone = "";
	String mName = "";
	TextView mTvPhone = null;
	TextView mTvName = null;
	static View view;

	public FragmentGoogleMap() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
		}
		try {
			view = inflater.inflate(R.layout.fragment_layout_google_map,
					container, false);

		} catch (InflateException e) {

		}

		mLatitude = getArguments().getString("lat");
		mLongitude = getArguments().getString("lan");
		mAddress = getArguments().getString("address");
		mPhone = getArguments().getString("phone");
		mName = getArguments().getString("name");

		mTvPhone = (TextView) view.findViewById(R.id.tvHCPhone);
		mTvName = (TextView) view.findViewById(R.id.tvHCName);

		mTvPhone.setText(mPhone);
		mTvName.setText(mName);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity().getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
					getActivity(), requestCode);
			dialog.show();

		} else { // Google Play Services are available

			try {
				// Loading map
				initilizeMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Enabling MyLocation Layer of Google Map
			mGoogleMap.setMyLocationEnabled(true);
			mGoogleMap.clear();
			try {
				LocationManager locMgr = (LocationManager) getActivity()
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
				// locMgr.requestLocationUpdates(provider, 20000, 0, this);
			} catch (NullPointerException ne) {
				Log.e("Current Location", "Current Lat Lng is Null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mTvPhone.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				performCall(mPhone);

			}
		});
		return view;
	}

	public void performCall(String pnoneNumber) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + pnoneNumber));
		getActivity().startActivity(callIntent);

	}

	@Override
	public void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (mGoogleMap == null) {
			/*
			 * mGoogleMap = ((SupportMapFragment)
			 * this.getFragmentManager().findFragmentById( R.id.map)).getMap();
			 */

			mGoogleMap = ((MapFragment) getActivity().getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null) {
				Toast.makeText(getActivity(), "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();
			}
		}
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

		Double dLatitude = Double.parseDouble(mLatitude);
		Double dLongitude = Double.parseDouble(mLongitude);

		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(dLatitude, dLongitude)).title(mAddress);
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		mGoogleMap.addMarker(marker);

		String url = makeURL(latitude, longitude, dLatitude, dLongitude);
		connectAsyncTask myTask = new connectAsyncTask(url);
		myTask.execute();

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	/*
	 * make URL for getting data which is use for draw path.
	 */

	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	/*
	 * Draw path from current location to destination.
	 */

	public void drawPath(String result) {

		try {
			// Transform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				@SuppressWarnings("unused")
				Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).geodesic(true));
			}

		} catch (JSONException e) {

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

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		connectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage(getString(R.string.fetchingRoute));
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
			if (result != null) {
				drawPath(result);
			}
		}
	}

	/*
	 * @Override public void onPause() { super.onDestroyView(); Fragment
	 * fragment = (getFragmentManager().findFragmentById(R.id.map));
	 * FragmentTransaction ft =
	 * getActivity().getSupportFragmentManager().beginTransaction();
	 * ft.remove(fragment); ft.commit(); }
	 */
}
