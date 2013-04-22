package com.example.norserun;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

public class GPSDataContentProvider extends ContentProvider {

    private static final String TAG = "GPSDataContentProvider";

    private static final String DATABASE_NAME = "gpsdata.db";
    private static final int DATABASE_VERSION = 2;
    private static final String POINT_TABLE_NAME = "gpspoints";

    public static final String AUTHORITY = "com.example.norserun.gpsdatacontentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/gpspoint");
    
    private static String getDateTime(){
    	String currentDateTimeString = new SimpleDateFormat("HHmmss").format(new Date());
    	Log.d(TAG, "getDateTime returned" + currentDateTimeString);
    	return currentDateTimeString;
    }
    
    /**
     * This class helps open, create, and upgrade the database file.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
         try {
         Log.i(TAG, "Creating table " + POINT_TABLE_NAME);    
            db.execSQL("CREATE TABLE " + POINT_TABLE_NAME + " ("
                     + GPSData.GPSPoint._ID + " INTEGER PRIMARY KEY,"
                     + GPSData.GPSPoint.LATITUDE + " REAL,"
                     + GPSData.GPSPoint.LONGITUDE + " REAL,"
                     + GPSData.GPSPoint.TIME + " INTEGER"                    
                     + ");");
         } catch (SQLiteException e) {
          Log.e(TAG, e.toString());
         }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + POINT_TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper mOpenHelper;

    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext(),DATABASE_NAME);
        return true;
    }


 @Override
 public int delete(Uri arg0, String arg1, String[] arg2) {
  // TODO Auto-generated method stub
  return 0;
 }


 @Override
 public String getType(Uri uri) {
  Log.i(TAG, "getting type for " + uri.toString());
  // TODO Auto-generated method stub
  return null;
 }


 @Override
 public Uri insert(Uri uri, ContentValues values) {
  Log.e(TAG, "inserting value " + values.toString());
  
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
     long rowId = db.insert(POINT_TABLE_NAME, "", values);
         if (rowId > 0) {
             Uri noteUri = ContentUris.withAppendedId(GPSDataContentProvider.CONTENT_URI, rowId);
             getContext().getContentResolver().notifyChange(noteUri, null);
             return noteUri;
         }

         throw new SQLException("Failed to insert row into " + uri);
 }


 @Override
 public Cursor query(Uri uri, String[] projection, String selection,
   String[] selectionArgs, String sortOrder) {
  // TODO Auto-generated method stub
  return null;
 }


 @Override
 public int update(Uri uri, ContentValues values, String selection,
   String[] selectionArgs) {
  // TODO Auto-generated method stub
  return 0;
 }
}