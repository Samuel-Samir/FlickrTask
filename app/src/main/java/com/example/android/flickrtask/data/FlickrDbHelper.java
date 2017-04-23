package com.example.android.flickrtask.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.flickrtask.data.FlickrContract.PhotoEntry;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickrDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="flickrPhotos.dp";
    private static final int DATABASE_VERSION = 1;

    public FlickrDbHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " + PhotoEntry.TABLE_NAME + " ( "+
                PhotoEntry.COLUMN_PHOTO_ID +" TEXT NOT NULL, "+
                PhotoEntry.COLUMN_PHOPO_BLOB +" BLOB NOT NULL "+ ");";

        db.execSQL(SQL_CREATE_PHOTO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PhotoEntry.TABLE_NAME );
        onCreate(db);
    }
}
