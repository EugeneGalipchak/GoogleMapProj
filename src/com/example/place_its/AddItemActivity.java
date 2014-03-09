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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddItemActivity extends Activity {
	//public static final String TAG = "AddItemActivity";
	//private Spinner spinner;
	private EditText item_name;
	//private EditText item_desc;
	private EditText item_price;
	ProgressDialog dialog;
	 double lng1; 
	 double lat1; 
	 public static boolean check;
	 
	 public static boolean checkMe ()
	 {
		 return check;
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		lng1 = extras.getDouble("long1");
		lat1 = extras.getDouble("lat1");

		setContentView(R.layout.additem);
		check = false;

		Button registerItem = (Button) findViewById(R.id.register_item);
		Log.d ("long", "");
		item_name = (EditText) findViewById(R.id.item_name);
		Log.d ("long", "");
		//item_desc = (EditText) findViewById(R.id.item_desc);
		item_price = (EditText) findViewById(R.id.item_price);
		//spinner = (Spinner) findViewById(R.id.item_spinner);
		Log.d ("long", "" + lng1);
		Log.d ("lat", " " + lat1);
		
		registerItem.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				check = true;
				postdata();
				Intent myIntent = new Intent(AddItemActivity.this, NewRemindersActivity.class);
				startActivity(myIntent);
				//AddItemActivity.this.onBackPressed();
			}
		});
	   
		dialog = ProgressDialog.show(this, "Loading data...", "Please wait :) ", false);
	    
		dialog.dismiss();
		//new UpdateSpinnerTask().execute("http://task2abcdefg.appspot.com/product");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void postdata() {
		final ProgressDialog dialog = ProgressDialog.show(this,
				"Posting Data...", "Please wait...", false);
		Thread t = new Thread() {

			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(MainActivity.ITEM_URI);
 
			    try {
			      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			      nameValuePairs.add(new BasicNameValuePair("name",
			    		  				Double.toString(lat1)));
			      //nameValuePairs.add(new BasicNameValuePair("description",
			    		  //item_desc.getText().toString()));
			      nameValuePairs.add(new BasicNameValuePair("price",
			    		  Double.toString(lng1)));
			      nameValuePairs.add(new BasicNameValuePair("product",
			    		  "tuan"));
			      nameValuePairs.add(new BasicNameValuePair("action",
				          "put"));
			      Log.d ("HERE OR NO", "YES");
			      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			  
			      HttpResponse response = client.execute(post);
			      
			      /*
			      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			      String line = "";
			      while ((line = rd.readLine()) != null) {
			       Log.d("", line);
			      }
				  */
			      
			    } catch (IOException e) {
			    	Log.d("", "IOException while trying to conect to GAE");
			    }
				dialog.dismiss();
			}
		};

		t.start();
		dialog.show();
	}
	
	/*
	 public class UpdateSpinnerTask extends AsyncTask<String, Void, List<String>> {
		 @Override
	     protected List<String> doInBackground(String... url) {
			 
	    	 HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(url[0]);
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
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
						android.R.layout.simple_spinner_item, list);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(dataAdapter);
				dialog.dismiss();
				
	     }

	 } */

}
