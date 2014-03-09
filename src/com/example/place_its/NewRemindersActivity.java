package com.example.place_its;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NewRemindersActivity extends Activity {
	public static final String TAG = "AddItemActivity";
	//public static final String PRODUCT_URI = "http://task2abcdefg.appspot.com/product";
	public static final String ITEM_URI = "http://task2abcdefg.appspot.com/item";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d ("back here again", "yeah");
		setContentView(R.layout.reminder_main);
		//Log.d ("back here again", "yeah1");
		//Button addItem = (Button) findViewById(R.id.addItem);
		Button addProduct = (Button) findViewById(R.id.addProduct);
		//Log.d ("back here again", "yeah2");
		//Button showItems = (Button) findViewById(R.id.showItems);
		Button showProducts = (Button) findViewById(R.id.showProducts);
		//Log.d ("back here again", "yeah3");
		Button buttonDone = (Button) findViewById(R.id.buttonDone);
		//Log.d ("back here again", "yeah456");
		//Log.d ("long", "");

		
		

		addProduct.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Bundle extras = getIntent().getExtras();
				Log.d ("back here again", "yeah456");

					final double lng = extras.getDouble("long");
					final double lat = extras.getDouble("lat");

					
				Intent myIntent = new Intent(NewRemindersActivity.this, AddItemActivity.class);
				myIntent.putExtra("long1", lng);
				myIntent.putExtra("lat1", lat);

				startActivity(myIntent);
				//NewRemindersActivity.this.onBackPressed();

			}
			
		});
		showProducts.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(NewRemindersActivity.this, ShowItemsActivity.class);
				startActivity(myIntent);

			}
			
		});
		buttonDone.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(NewRemindersActivity.this, MainActivity.class);
				startActivity(myIntent);

			}
			
		});
		

	}

	
}
