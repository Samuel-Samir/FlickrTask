package com.example.android.flickrtask.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.cache.ImageLoader;
import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.models.PhotoInfo;
import com.example.android.flickrtask.utilities.Utils;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickerAdapter extends RecyclerView.Adapter<FlickerAdapter.RecyclerViewAdapterHolder>{
    public final static String PHOTOS_LIST ="photos_list";
    public final static String PODITION ="position";
    private ApiResponse apiResponse ;
    private Activity myActivity;
    private  onReachingLastPhoto reachingLastPhoto ;
    private ImageLoader imgLoader;


    public void setApiResponse (ApiResponse apiResponse ,   Activity myActivity)
    {
        this.apiResponse =apiResponse;
        this.myActivity =myActivity;
        imgLoader = new ImageLoader(myActivity);
        notifyDataSetChanged();
    }

    public void addNewPagePhotos (ApiResponse newApiResponse)
    {
        apiResponse= Utils.addTwoApiResponse(apiResponse ,newApiResponse);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        int layoutPhotoItem = R.layout.photo_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutPhotoItem ,parent ,shouldAttachToParentImmediately);
        return new RecyclerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterHolder holder, final int position) {
        checkReachLast (position);

        PhotoInfo photoInfo = apiResponse.getPhotos().getPhoto().get(position);
        String photoUrl = Utils.getPhotoUri(photoInfo);
        imgLoader.displayImage(photoUrl, holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayPhotoFragment displayPhotoFragment = new DisplayPhotoFragment();
                Bundle bundle =new Bundle();
                bundle.putParcelable(PHOTOS_LIST,apiResponse.getPhotos());
                bundle.putInt(PODITION,position);
                displayPhotoFragment.setArguments(bundle);
                ((FragmentActivity)myActivity).getSupportFragmentManager()
                        .beginTransaction().addToBackStack("displayPhotoFragment").replace(R.id.container ,displayPhotoFragment)
                        .commit();
            }
        });
    }

    public void checkReachLast(int position )
    {
        if(position== apiResponse.getPhotos().getPhoto().size()-2)
        {
            int pageNum = Utils.getPageNum(apiResponse);
            pageNum+=1;
            int availablePages = Utils.getNumofAvailablePages(apiResponse);
            if (pageNum <= availablePages)
            {
                reachingLastPhoto.onReachingLast(pageNum);
            }
            else {
                Toast.makeText(myActivity ,myActivity.getString(R.string.last_page),Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public int getItemCount() {
        if(apiResponse!=null)
        {
            return apiResponse.getPhotos().getPhoto().size();
        }
        return 0;
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView imageView ;
        public RecyclerViewAdapterHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

    public interface onReachingLastPhoto
    {
        void onReachingLast (int newPage) ;
    }
    public void setOnReachingLastPhoto (onReachingLastPhoto reachingLastPhoto)
    {
        this.reachingLastPhoto =reachingLastPhoto ;
    }
}
