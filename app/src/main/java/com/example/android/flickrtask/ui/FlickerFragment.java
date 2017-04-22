package com.example.android.flickrtask.ui;


import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.data.FlickrDbHelper;
import com.example.android.flickrtask.data.PhotoInfo;
import com.example.android.flickrtask.remote.FlickrAsyncTask;
import com.example.android.flickrtask.utilities.FunctionsUtilities;

import java.util.List;


public class FlickerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
   // private ApiResponse apiResponse ;
    private RecyclerView myRecyclerView ;
    private FlickerAdapter flickerAdapter ;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flicker, container, false);
        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        flickerAdapter = new FlickerAdapter();
        myRecyclerView.setAdapter(flickerAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
        flickerAdapter.setOnReachingLastPhoto(new FlickerAdapter.onReachingLastPhoto()
        {
            @Override
            public void onReachingLast(int newPage) {
                FlickrAsyncTask flickrAsyncTask =new FlickrAsyncTask(getActivity());
                flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
                    @Override
                    public void onPostExecute(ApiResponse apiRespons) {
                        flickerAdapter.addNewPagePhotos(apiRespons);
                    }
                });
                flickrAsyncTask.execute(String.valueOf(newPage));
            }
        });


        return rootView;
    }

    @Override
    public void onRefresh() {

        FlickrAsyncTask flickrAsyncTask =new FlickrAsyncTask(getActivity());
        flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
            @Override
            public void onPostExecute(ApiResponse apiRespons) {
                // apiResponse = apiRespons ;
                flickerAdapter.setApiResponse(apiRespons , getActivity());
                FunctionsUtilities.clearDb(getActivity());
                addPhotosToDB (apiRespons.getPhotos().getPhoto());

            }
        });
        flickrAsyncTask.execute("1");
        swipeRefreshLayout.setRefreshing(false);
    }

    public void addPhotosToDB(List<PhotoInfo> photo)
    {
        new AsyncTask<List<PhotoInfo>, Void, Void>() {
            @Override
            protected Void doInBackground(List<PhotoInfo>... params) {
                if (params.length!=0)
                {
                    List<PhotoInfo> photo = (List<PhotoInfo>) params[0];
                    for (int i=0 ;i<photo.size() ;i++)
                    {
                        long index= FunctionsUtilities.addNewPhoto( photo.get(i) , getActivity());
                        Log.i("index" ,String.valueOf(index));
                    }
                }
                return null;
            }
        }.execute(photo);

    }



}
