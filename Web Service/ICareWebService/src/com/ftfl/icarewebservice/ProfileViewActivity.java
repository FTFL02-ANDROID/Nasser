package com.ftfl.icarewebservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProfileViewActivity extends ActionBarActivity {
	ProgressDialog pDialog;
	
	List<String> nameList = new ArrayList<String>();
	ListView listView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_view);
		listView = (ListView) findViewById(R.id.list);
		new LoadAllProfiles().execute();
	}
	
	public void setData()
	{
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		          android.R.layout.simple_list_item_1, android.R.id.text1, nameList);


		        // Assign adapter to ListView
		        listView.setAdapter(adapter); 
	}
	
	

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProfiles extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileViewActivity.this);
            pDialog.setMessage("Loading profiles. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
        	
    		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
    		HttpPost httppost = new HttpPost("http://10.106.8.73/icare/showallprofile.php");
    		// Depends on your web service
    		httppost.setHeader("Content-type", "application/json");
    		String work = null;

    		InputStream inputStream = null;
    		String result = null;
    		try {
    		    HttpResponse response = httpclient.execute(httppost);           
    		    HttpEntity entity = response.getEntity();

    		    inputStream = entity.getContent();
    		    // json is UTF-8 by default
    		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
    		    StringBuilder sb = new StringBuilder();

    		    String line = null;
    		    while ((line = reader.readLine()) != null)
    		    {
    		        sb.append(line + "\n");
    		    }
    		    result = sb.toString();
    		} catch (Exception e) { 
    		    // Oops
    		}
    		finally {
    		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
    		}
    		
    		try {
    			JSONObject jObject = new JSONObject(result);
    			JSONArray jArray = jObject.getJSONArray("profiles");
    			for (int i=0; i < jArray.length(); i++)
    			{
    				try {
                		JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String oneObjectsItem = oneObject.getString("name");
                        nameList.add(oneObjectsItem);
                        
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			
    			work = "yes";
    			
    			
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			work = null;
    		}
          
            return work;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String work) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(work!=null)
            {
            	
            	setData(); 
                   // Toast.makeText(getApplicationContext(), nameList.get(2), Toast.LENGTH_LONG).show();

            }
            else
            	Toast.makeText(getApplicationContext(), "sorry", Toast.LENGTH_LONG).show();
           
        }
 
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_view, menu);
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
