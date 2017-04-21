package com.example.android.flickrtask.remote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;

import com.example.android.flickrtask.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by samuel on 4/21/2017.
 */


public final class NetworkUtils {

    private static final String TAG  = NetworkUtils.class.getName();
    private static final String Movies_BASE_URL ="https://api.flickr.com/services/rest/";
    private static final String METHOD = "method";
    private static final String API_KEY = "api_key";
    private static final String PER_PAGE = "per_page";
    private static final String PAGE = "page";
    private static final String FORMAT = "format";
    private static final String NO_JSON_CALL_BACK = "nojsoncallback";


    public static URL buildUrl (String pageNumber, Context context )
    {
        Uri buildUri = Uri.parse(Movies_BASE_URL).buildUpon()
                //.appendEncodedPath(moviesOrder)
                .appendQueryParameter(METHOD ,context.getString(R.string.METHOD))
                .appendQueryParameter(API_KEY ,context.getString(R.string.API_KEY))
                .appendQueryParameter(PER_PAGE ,"50")
                .appendQueryParameter(PAGE ,pageNumber)
                .appendQueryParameter(FORMAT ,context.getString(R.string.FORMAT))
                .appendQueryParameter(NO_JSON_CALL_BACK ,"1")
                .appendQueryParameter(API_KEY ,context.getString(R.string.API_KEY))
                .build();
        URL url = null ;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromAPI (URL url) throws IOException
    {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else
                return null;
        }
        finally {
            httpURLConnection.disconnect();
        }


    }



    public static boolean checkInternetConnection(Context context)
    {

        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}

