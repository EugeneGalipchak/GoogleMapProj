package com.example.place_its;

/*
 * TEAM 32
 * Isabella Do, Anh Dang, Duy Le, Chao Li
 * Derek Nguyen ,Yevgeniy Galipchak
 */

/*
 * The majority of this code was copied from our main texbook.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Public class DBAaptor stores all the necessary components for our
 * usable database.
 */
public class DBAdapter {
    //Initialized static variables
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL ="email";
    private static final String TAG = "DBAdapter";  
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "contacts";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE =
        "create table contacts (_id integer primary key autoincrement, "
        + "name text not null, email text not null);"; 
    private final Context context;    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        //Creation of new databasehelper
        DBHelper = new DatabaseHelper(context);
    }
        
    /*
     * Implementation of a SQL database in order to store our necessary data
     * with regards to the reminders being displayed.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //onCreate method which initilizes and opens the database
        @Override
        public void onCreate(SQLiteDatabase db) {
            //try catch block to the SQL database
        	try {
        		db.execSQL(DATABASE_CREATE);	
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        
        //onUpgrade function which takes case of upgrading the database version
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }    

    //Opens the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Closes the databse   
    public void close() {
        DBHelper.close();
    }
    
    //Inserting a reminder into the database
    public long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put( KEY_NAME, name  );
        initialValues.put(KEY_EMAIL, email);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //Deletes a specified reminder from the database
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //Retrieves all of the reminders
    public Cursor getAllContacts()  
    {
        //returning the db.query
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL}, null, null, null, null, null);
    	
    }

    //Method which revrieves a particular reminder
    public Cursor getContact(long rowId) throws SQLException {
        //creation of cursor
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        //check to see if the mCursor is null, in order to call moveToFirst
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Method to update the reminder
    public boolean updateContact(long rowId, String name, String email) {
        //Creation of new args fro ContentValues
        ContentValues args = new ContentValues();
        //calling args onto put
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
