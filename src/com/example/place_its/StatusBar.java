package com.example.place_its;

/*
 * TEAM 32
 * Isabella Do, Anh Dang, Duy Le, Chao Li
 * Derek Nguyen ,Yevgeniy Galipchak
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//Public class StatusBar used in order to interacti with our status bar icon
public class StatusBar extends Activity implements OnClickListener {
	NotificationManager nm;
	static final int uniqueID = 12323;
	//onCeate method used in order to create nm on click listener
	protected void onCreate( Bundle b ) {
		super.onCreate( b );
		setContentView( R.layout.statusbar );
		Button stat = (Button)findViewById( R.id.b );
		stat.setOnClickListener( this );
		nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
		nm.cancel( uniqueID );
	}

    //onClick method used in order to initialize a new intent object and pass nm
	@Override
	public void onClick(View v) {
		Intent intent = new Intent( this, StatusBar.class);
		PendingIntent pi = PendingIntent.getActivity( this, 0, intent, 0);
		//initialized strings
		String body = "You have a reminder for this location";
		String title = "Place-its";
		//new obect n
		@SuppressWarnings("deprecation")
		Notification n = new Notification( R.drawable.ic_launcher, body, System.currentTimeMillis() );
		
		n.setLatestEventInfo( this, title, body, pi );
		n.defaults = Notification.DEFAULT_ALL;
		nm.notify(uniqueID, n );
		finish();
	}
}
