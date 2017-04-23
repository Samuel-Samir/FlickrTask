package com.example.android.flickrtask.ui.fragment;


import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.cache.ImageLoader;
import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.remote.FlickrAsyncTask;
import com.example.android.flickrtask.services.MyService;
import com.example.android.flickrtask.ui.adapter.FlickerAdapter;
import com.example.android.flickrtask.utilities.Utils;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;


public class FlickerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    public static final String SAVE_ARGUMENT ="response";
    private static final long POLL_FREQUENCY = 60000;
    private static final int JOB_ID = 100;
    private ApiResponse apiResponse ;
    private RecyclerView myRecyclerView ;
    private FlickerAdapter flickerAdapter ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView connectionTextView;
    private JobScheduler mJobScheduler;
    private ImageLoader imgLoader;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flicker, container, false);
        mJobScheduler= JobScheduler.getInstance(getActivity());
        constructJob();
        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        onOrientationChange(getResources().getConfiguration().orientation);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        connectionTextView =(TextView) rootView.findViewById(R.id.connection_error);
        imgLoader = new ImageLoader(getActivity());

        setHasOptionsMenu(true);
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

        //this is listener called when reach last photo to load the next page
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

        //this is listener called when the Service load the data to notifay UI
        MyService.setOnServiceFinish(new MyService.onServiceFinishCallBack() {
                                         @Override
                                         public void onServiceFinishAsyncTask(ApiResponse apiRespons) {
                                             apiResponse = apiRespons ;
                                             flickerAdapter.setApiResponse(apiRespons, getActivity());
                                         }
                                     }
        );
        return rootView;
    }

    /*
     *this function check if the device phone  or tablet Then decide how many columns
     */
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
                }
            });
            flickrAsyncTask.execute("1");
        }
        else {
            connectionTextView.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);

    }
    private void constructJob(){

        JobInfo.Builder builder=new JobInfo.Builder(JOB_ID,new ComponentName(getActivity(), MyService.class));
        builder.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id==R.id.action_refresh)
        {
            imgLoader.clearCahce();
            onRefresh();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
