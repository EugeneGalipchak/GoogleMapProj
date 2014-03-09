package com.example.place_its;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.place_its.AddItemActivity.UpdateSpinnerTask;
import com.example.place_its.ShowItemsActivity.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class ShowItemsActivity extends Activity {
	
	public static final String TAG = "ShowItemActivity";

	ProgressDialog dialog;
	ArrayAdapter<String> stringArrayAdapter;
	ListView listview;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// create showitems layout first.
		// Click on layout, New->Android XML File.
		// ResourceType should be Layout.
		// Add a EditText field in it showitems.xml
		// setContentView(R.layout.showitems);
		
		// Now create a method which get query your Google App Engine via HTTP GET.
		// Make sure that method gets the data via a new thread
		// or use async task.
		setContentView (R.layout.return_item);
		listview = (ListView) findViewById(R.id.mainListView);
		listview.setAdapter(stringArrayAdapter);
		dialog = ProgressDialog.show(this, "Loading data...", "Please wait :) ", false);
	    dialog.show();
		new data().execute(MainActivity.ITEM_URI);
		
	}
	public class data extends AsyncTask<String, Void, List<String>> {
		 @Override
	     protected List<String> doInBackground(String... url) {

					HttpClient client = new DefaultHttpClient();
					HttpGet request = new HttpGet(MainActivity.ITEM_URI);
					List<String> list = new ArrayList<String>();
					try {
						HttpResponse response = client.execute(request);
						HttpEntity entity = response.getEntity();
						String data = EntityUtils.toString(entity);
						Log.d(TAG, data);
						JSONObject myjson;

						try {
							myjson = new JSONObject(data);
							JSONArray array = myjson.getJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								JSONObject obj = array.getJSONObject(i);
								list.add(obj.get("name").toString());
							}

						} catch (JSONException e) {

					    	Log.d(TAG, "Error in parsing JSON");
						}

					} catch (ClientProtocolException e) {

				    	Log.d(TAG, "ClientProtocolException while trying to connect to GAE");
					} catch (IOException e) {

						Log.d(TAG, "IOException while trying to connect to GAE");
					}
	         return list;
	     }

	     protected void onPostExecute(List<String> list) {
				ArrayAdapter<String> dataAdapter = new
						ArrayAdapter<String>(getApplicationContext(),
						android.R.layout.simple_expandable_list_item_1, list);
				listview.setAdapter(dataAdapter);
				dialog.dismiss();

	     }

	 }

}