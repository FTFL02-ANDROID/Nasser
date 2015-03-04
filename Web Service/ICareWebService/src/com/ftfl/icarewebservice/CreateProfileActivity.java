package com.ftfl.icarewebservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icarewebservice.util.Profile;

public class CreateProfileActivity extends ActionBarActivity {

	EditText mEtName = null;
	EditText mEtDateOfBirth = null;
	EditText mEtHeight = null;
	EditText mEtWeight = null;
	EditText mEtGender = null;


	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_profile);
		mEtName = (EditText) findViewById(R.id.profileName);
		mEtDateOfBirth = (EditText) findViewById(R.id.profileDateOfBirth);
		mEtHeight = (EditText) findViewById(R.id.profileHeight);
		mEtWeight = (EditText) findViewById(R.id.profileWeight);
		mEtGender = (EditText) findViewById(R.id.profileGender);

	}

	public void createProfile(View v) {
		String name = mEtName.getText().toString();
		String dateOfBirth = mEtDateOfBirth.getText().toString();
		String height = mEtHeight.getText().toString();
		String weight = mEtWeight.getText().toString();
		String gender = mEtGender.getText().toString();

		String id = "20";

		Profile icareProfile = new Profile(id, name, dateOfBirth, height,
				weight, gender);

		try {
			JSONObject json = new JSONObject();

			json.put("id", icareProfile.getId());
			json.put("name", icareProfile.getName());
			json.put("date_of_birth", icareProfile.getDateOfBirth());
			json.put("height", icareProfile.getHeight());
			json.put("weight", icareProfile.getmWeight());
			json.put("gender", icareProfile.getGender());

			new JSONTransmitter().execute(new JSONObject[] { json });

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public class JSONTransmitter extends
			AsyncTask<JSONObject, JSONObject, HttpResponse> {

		String url = "http://10.106.8.73/icare/createprofile.php";

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateProfileActivity.this);
			pDialog.setMessage("Creating Product..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected HttpResponse doInBackground(JSONObject... data) {
			JSONObject json = data[0];
			HttpClient client = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(),
					100000);

			JSONObject jsonResponse = null;
			HttpPost post = new HttpPost(url);
			HttpResponse response = null;
			try {
				StringEntity se = new StringEntity("json=" + json.toString());
				post.addHeader("content-type",
						"application/x-www-form-urlencoded");
				post.setEntity(se);

				response = client.execute(post);
				String resFromServer = org.apache.http.util.EntityUtils
						.toString(response.getEntity());

				jsonResponse = new JSONObject(resFromServer);
				Log.i("Response from server", jsonResponse.getString("msg"));
			} catch (Exception e) {
				e.printStackTrace();

			}

			return response;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(HttpResponse response) {
			// dismiss the dialog once done
			pDialog.dismiss();

			if (response != null)
				Toast.makeText(getApplicationContext(), "Successfully Saved",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getApplicationContext(), "Not Saved",
						Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * 
	 * protected void sendJson(final String data) { Thread t = new Thread() {
	 * 
	 * public void run() { Looper.prepare(); //For Preparing Message Pool for
	 * the child Thread HttpClient client = new DefaultHttpClient();
	 * HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
	 * //Timeout Limit HttpResponse response; JSONObject json = new
	 * JSONObject();
	 * 
	 * try { HttpPost post = new
	 * HttpPost("http://192.168.0.101/icare/createprofile.php"); json.put("id",
	 * "10"); json.put("name", "nasser"); json.put("date_of_birth", "df");
	 * json.put("height", "wfwf"); json.put("weight", "sffsf");
	 * json.put("gender", "male"); StringEntity se = new StringEntity(
	 * json.toString()); // se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
	 * "application/json")); // post.setEntity(se);
	 * 
	 * List <NameValuePair> nvps = new ArrayList <NameValuePair>(); nvps.add(new
	 * BasicNameValuePair("data", se));
	 * 
	 * post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	 * 
	 * 
	 * response = client.execute(post);
	 * 
	 * Checking response if(response!=null){ InputStream in =
	 * response.getEntity().getContent(); //Get the data in the entity
	 * 
	 * Toast.makeText(getApplicationContext(), "successfully send",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * } catch(Exception e) { e.printStackTrace(); // createDialog("Error",
	 * "Cannot Estabilish Connection");
	 * 
	 * Toast.makeText(getApplicationContext(), "Cannot Establish Connection",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * Looper.loop(); //Loop in the message queue } };
	 * 
	 * t.start(); }
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_profile, menu);
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
