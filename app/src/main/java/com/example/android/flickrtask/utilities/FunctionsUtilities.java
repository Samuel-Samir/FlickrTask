package com.example.android.flickrtask.utilities;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.data.FlickrDbHelper;
import com.example.android.flickrtask.data.PhotoInfo;
import com.example.android.flickrtask.data.FlickrContract.PhotoEntry;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by samuel on 4/22/2017.
 */

public class FunctionsUtilities {
    private Target mTarget;
    public static ApiResponse addTwoApiResponse(ApiResponse original , ApiResponse newResponse)
    {
        original.getPhotos().setPage( newResponse.getPhotos().getPage() ) ;
        original.getPhotos().getPhoto().addAll(newResponse.getPhotos().getPhoto());
        return original;
    }

    public static int getPageNum (ApiResponse apiResponse)
    {
        return  apiResponse.getPhotos().getPage();
    }
    public static int getNumofAvailablePages (ApiResponse apiResponse)
    {
        return  apiResponse.getPhotos().getPages();
    }

    public static long addNewPhoto( PhotoInfo photoInfo, Activity activity) {

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
            cv.put(PhotoEntry.COLUMN_SECRET, photoInfo.getSecret());
            cv.put(PhotoEntry.COLUMN_SERVER, photoInfo.getServer());
            cv.put(PhotoEntry.COLUMN_FARM, photoInfo.getFarm());
            cv.put(PhotoEntry.COLUMN_TITLE, photoInfo.getTitle());
            byte[] posterByteArr = convertBitmapToByteArr(theBitmap);
            cv.put(PhotoEntry.COLUMN_PHOPO_BLOB, posterByteArr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mDb.insert(PhotoEntry.TABLE_NAME, null, cv);
    }

    public static byte[] convertBitmapToByteArr(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static void clearDb ( Activity activity)
    {
        FlickrDbHelper dbHelper = new FlickrDbHelper(activity);
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();
        Boolean result= mDb.delete(PhotoEntry.TABLE_NAME, null, null) > 0;
        Log.i("Clear DB" , String.valueOf(result));

    }
}
