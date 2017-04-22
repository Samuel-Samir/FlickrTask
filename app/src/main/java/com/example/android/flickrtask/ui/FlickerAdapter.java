package com.example.android.flickrtask.ui;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.data.PhotoInfo;
import com.example.android.flickrtask.utilities.FunctionsUtilities;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickerAdapter extends RecyclerView.Adapter<FlickerAdapter.RecyclerViewAdapterHolder>{
    public final static String PHOTOS_LIST ="photos_list";
    public final static String PODITION ="position";

    private ApiResponse apiResponse ;
    private Activity myActivity;

    private  onReachingLastPhoto reachingLastPhoto ;

    public void setApiResponse (ApiResponse apiResponse ,   Activity myActivity)
    {
        this.apiResponse =apiResponse;
        this.myActivity =myActivity;
        notifyDataSetChanged();
    }

    public void addNewPagePhotos (ApiResponse newApiResponse)
    {
        apiResponse= FunctionsUtilities.addTwoApiResponse(apiResponse ,newApiResponse);
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
    public void onBindViewHolder(RecyclerViewAdapterHolder holder, final int position) {
        checkReachLast (position);
        PhotoInfo photoInfo = apiResponse.getPhotos().getPhoto().get(position);
        String size="_n";
        String photoUrl = "https://farm%s.staticflickr.com/%s/%s_%s%s.jpg";
        photoUrl= String.format(photoUrl , photoInfo.getFarm() ,photoInfo.getServer() ,photoInfo.getId() ,photoInfo.getSecret(),size ) ;
        Picasso.with(myActivity)
                .load(photoUrl)
                .placeholder(R.drawable.noposter)
                .error(R.drawable.noposter)
                .into(holder.imageView);

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
            int pageNum = FunctionsUtilities.getPageNum(apiResponse);
            pageNum+=1;
            int availablePages = FunctionsUtilities.getNumofAvailablePages(apiResponse);
            if (pageNum <= availablePages)
            {
                reachingLastPhoto.onReachingLast(pageNum);
                Toast.makeText(myActivity ,myActivity.getString(R.string.new_page),Toast.LENGTH_SHORT).show();
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
