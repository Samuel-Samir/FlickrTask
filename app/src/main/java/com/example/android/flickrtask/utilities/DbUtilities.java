package com.example.android.flickrtask.utilities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.android.flickrtask.data.FlickrContract;
import com.example.android.flickrtask.data.FlickrDbHelper;
import com.example.android.flickrtask.data.PhotoInfo;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by samuel on 4/23/2017.
 */

public class DbUtilities {
    private Target mTarget;


    public static long addNewPhotoToDb(PhotoInfo photoInfo, Activity activity) {

        FlickrDbHelper dbHelper = new FlickrDbHelper(activity);
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        String size="_n";
        String photoUrl = "https://farm%s.staticflickr.com/%s/%s_%s%s.jpg";
        photoUrl= String.format(photoUrl , photoInfo.getFarm() ,photoInfo.getServer() ,photoInfo.getId() ,photoInfo.getSecret(),size ) ;
        ContentValues cv = new ContentValues();

        try {
            Bitmap theBitmap = Glide.
                    with(activity).
                    load(photoUrl).
                    asBitmap().
                    into(100, 100).
                    get();
            byte[] posterByteArr = convertBitmapToByteArr(theBitmap);
            cv.put(FlickrContract.PhotoEntry.COLUMN_PHOTO_ID, photoInfo.getId());
            cv.put(FlickrContract.PhotoEntry.COLUMN_PHOPO_BLOB, posterByteArr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mDb.insert(FlickrContract.PhotoEntry.TABLE_NAME, null, cv);
    }

    public static byte[] convertBitmapToByteArr(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrToBitmap(byte[] bytes) {
        if (bytes == null)
            return null;

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void clearDb ( Activity activity)
    {
        FlickrDbHelper dbHelper = new FlickrDbHelper(activity);
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        Boolean result= mDb.delete(FlickrContract.PhotoEntry.TABLE_NAME, null, null) > 0;
        Log.i("Clear DB" , String.valueOf(result));
    }

    public static Cursor getPhotoFromDb (String id , Activity activity )
    {
        FlickrDbHelper dbHelper = new FlickrDbHelper(activity);
        SQLiteDatabase mDb = dbHelper.getReadableDatabase();
        String selection = FlickrContract.PhotoEntry.COLUMN_PHOTO_ID+"=?";
        String[] selectionArgs = {id};
        return mDb.query(
                FlickrContract.PhotoEntry.TABLE_NAME,
                null,
                selection ,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
