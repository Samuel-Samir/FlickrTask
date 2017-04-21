package com.example.android.flickrtask.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.data.PhotoInfo;
import com.example.android.flickrtask.remote.FlickrAsyncTask;


public class FlickerFragment extends Fragment {
    private ApiResponse apiResponse ;
    private RecyclerView myRecyclerView ;
    private FlickerAdapter flickerAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flicker, container, false);
        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        flickerAdapter = new FlickerAdapter();
        myRecyclerView.setAdapter(flickerAdapter);



        FlickrAsyncTask flickrAsyncTask =new FlickrAsyncTask(getActivity());
        flickrAsyncTask.setFlickrAsyncTaskCallBack(new FlickrAsyncTask.FlickrAsyncTaskCallBack() {
            @Override
            public void onPostExecute(ApiResponse apiRespons) {
                apiResponse = apiRespons ;
                flickerAdapter.setApiResponse(apiResponse , getActivity());

            }
        });
        flickrAsyncTask.execute("1");

        return rootView;
    }

}
