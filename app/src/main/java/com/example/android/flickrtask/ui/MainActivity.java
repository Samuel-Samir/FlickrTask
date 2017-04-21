package com.example.android.flickrtask.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.remote.FlickrAsyncTask;
import com.example.android.flickrtask.remote.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ApiResponse apiResponse ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlickrAsyncTask flickrAsyncTask =new FlickrAsyncTask(this);
        flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
            @Override
            public void onPostExecute(ApiResponse apiRespons) {
                apiResponse = apiRespons ;
            }
        });
        flickrAsyncTask.execute("1");

    }
}
