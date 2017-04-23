package com.example.android.flickrtask.ui;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.remote.FlickrAsyncTask;
import com.example.android.flickrtask.utilities.Utils;


public class FlickerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private ApiResponse apiResponse ;
    private RecyclerView myRecyclerView ;
    private FlickerAdapter flickerAdapter ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean firstLaunsh =false ;
    TextView connectionTextView;
    public static final String SAVE_ARGUMENT ="response";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flicker, container, false);
        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        onOrientationChange(getResources().getConfiguration().orientation);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        connectionTextView =(TextView) rootView.findViewById(R.id.connection_error);
        if(savedInstanceState==null)
        {
            onRefresh();
        }
        else
        {
            apiResponse = (ApiResponse) savedInstanceState.getParcelable(SAVE_ARGUMENT);
            if(apiResponse!=null)
            {
                flickerAdapter.setApiResponse(apiResponse, getActivity());
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        flickerAdapter.setOnReachingLastPhoto(new FlickerAdapter.onReachingLastPhoto()
        {
            @Override
            public void onReachingLast(int newPage) {
                FlickrAsyncTask flickrAsyncTask =new FlickrAsyncTask(getActivity());
                flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
                    @Override
                    public void onPostExecute(ApiResponse apiRespons) {
                        apiResponse = Utils.addTwoApiResponse(apiResponse ,apiRespons);
                        flickerAdapter.addNewPagePhotos(apiRespons);
                    }
                });
                flickrAsyncTask.execute(String.valueOf(newPage));
            }
        });


        return rootView;
    }

    public void onOrientationChange(int orientation){
        int landScape=3;
        int portrait= 2;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        if (widthPixels>=1023 || heightPixels>=1023)
        {
            landScape=4;
            portrait=3;
        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),portrait));
            flickerAdapter = new FlickerAdapter();
            myRecyclerView.setAdapter(flickerAdapter);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),landScape));
            flickerAdapter = new FlickerAdapter();
            myRecyclerView.setAdapter(flickerAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_ARGUMENT ,apiResponse);

    }

    @Override
    public void onRefresh() {

        if (Utils.checkInternetConnection(getActivity())) {
            connectionTextView.setVisibility(View.GONE);
            FlickrAsyncTask flickrAsyncTask = new FlickrAsyncTask(getActivity());
            flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
                @Override
                public void onPostExecute(ApiResponse apiRespons) {
                    apiResponse = apiRespons ;
                    flickerAdapter.setApiResponse(apiRespons, getActivity());
                    if (firstLaunsh) {
                    }
                    firstLaunsh = true;
                }
            });
            flickrAsyncTask.execute("1");
        }
        else {
            connectionTextView.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);

    }
}
