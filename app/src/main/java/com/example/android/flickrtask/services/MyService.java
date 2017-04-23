package com.example.android.flickrtask.services;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.remote.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by samuel on 4/23/2017.
 */

public class MyService extends JobService {
    private static onServiceFinishCallBack onServiceFinishCall ;
    public static boolean appIsKilled = false ;

    @Override
    public boolean onStartJob(JobParameters params) {
        //Toast.makeText(this ,"update from service" ,Toast.LENGTH_SHORT).show();
        internalAsyncTask internalAsyncTask = new internalAsyncTask();
        internalAsyncTask.execute("1");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return appIsKilled;
    }


    class internalAsyncTask  extends AsyncTask <String ,Void,ApiResponse> {


        @Override
        protected ApiResponse doInBackground(String... params) {
            String jasonResponse;
            if (params.length==0)
            {
                return null;
            }
            URL flickrUrlRequest = null;
            try {
                flickrUrlRequest = new URL("https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=862ada56405ee6b5cfa990459350f328&per_page=30&page=1&format=json&nojsoncallback=1&api_key=862ada56405ee6b5cfa990459350f328");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                jasonResponse = NetworkUtils.getResponseFromAPI(flickrUrlRequest);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            Gson gson = new Gson();
            ApiResponse photosResponse;
            photosResponse = gson.fromJson(jasonResponse, ApiResponse.class);
            Log.d("sasa" ,photosResponse.getPhotos().getPhoto().get(0).getId());
            return photosResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse apiResponse) {
            if(apiResponse!=null)
            {
                onServiceFinishCall.onServiceFinishAsyncTask(apiResponse);
            }
        }
    }

    public interface onServiceFinishCallBack
    {
        void onServiceFinishAsyncTask (ApiResponse apiResponse) ;
    }
    public static void setOnServiceFinish(onServiceFinishCallBack onServiceFinish)
    {
        onServiceFinishCall=onServiceFinish;
    }

}
