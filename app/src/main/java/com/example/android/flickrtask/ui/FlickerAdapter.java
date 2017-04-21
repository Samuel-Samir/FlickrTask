package com.example.android.flickrtask.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.data.ApiResponse;
import com.example.android.flickrtask.data.PhotoInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickerAdapter extends RecyclerView.Adapter<FlickerAdapter.RecyclerViewAdapterHolder>{

    private ApiResponse apiResponse ;
    private Activity myActivity;

    public void setApiResponse (ApiResponse apiResponse ,   Activity myActivity)
    {
        this.apiResponse =apiResponse;
        this.myActivity =myActivity;
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
    public void onBindViewHolder(RecyclerViewAdapterHolder holder, int position) {
        PhotoInfo photoInfo = apiResponse.getPhotos().getPhoto().get(position);
        String size="_m";
        String photoUrl = "https://farm%s.staticflickr.com/%s/%s_%s%s.jpg";
        photoUrl= String.format(photoUrl , photoInfo.getFarm() ,photoInfo.getServer() ,photoInfo.getId() ,photoInfo.getSecret(),size ) ;
        Picasso.with(myActivity)
                .load(photoUrl)
                .into(holder.imageView);
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
}
