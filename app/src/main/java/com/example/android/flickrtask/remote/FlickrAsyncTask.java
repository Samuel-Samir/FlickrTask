package com.example.android.flickrtask.remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.android.flickrtask.models.ApiResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

/**
 * Created by samuel on 4/21/2017.
 */

public class FlickrAsyncTask extends AsyncTask <String ,Void,ApiResponse> {
    private final String LOG_TAG = FlickrAsyncTask.class.getName();
    private ProgressDialog dialog;
    private Activity activity;
    private FlickrAsyncTaskCallBack flickrAsyncTaskCallBack ;

    public  FlickrAsyncTask (Activity activity)
    {
        dialog =new ProgressDialog(activity);
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("loading...");
        this.dialog.show();
        super.onPreExecute();
    }

    @Override
    protected ApiResponse doInBackground(String... params) {
        String jasonResponse;
        if (params.length==0)
        {
            return null;
        }
        URL flickrUrlRequest = NetworkUtils.buildUrl(params[0], activity );
        try {

            jasonResponse = NetworkUtils.getResponseFromAPI(flickrUrlRequest);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        ApiResponse photosResponse;
        photosResponse = gson.fromJson(jasonResponse, ApiResponse.class);
        return photosResponse;
    }

    @Override
    protected void onPostExecute(ApiResponse apiResponse) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(apiResponse!=null)
        {
            flickrAsyncTaskCallBack.onPostExecute(apiResponse);
        }
    }

    public  interface FlickrAsyncTaskCallBack
    {
        void onPostExecute (ApiResponse apiResponse);
    }

    public void setFlickrAsyncTaskCallBack (FlickrAsyncTaskCallBack flickrAsyncTaskCallBack )
    {
        this.flickrAsyncTaskCallBack =flickrAsyncTaskCallBack;

    }

}
