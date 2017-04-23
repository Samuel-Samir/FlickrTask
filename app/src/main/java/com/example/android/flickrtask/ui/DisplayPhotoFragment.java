package com.example.android.flickrtask.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.cache.ImageLoader;
import com.example.android.flickrtask.models.PhotoInfo;
import com.example.android.flickrtask.models.Photos;
import com.example.android.flickrtask.utilities.Utils;


public class DisplayPhotoFragment extends Fragment  {
    private int position ;
    private Photos photos ;
    private ImageLoader imgLoader;
    ImageView  imageView;
    TextView titleTextView;
    ImageView closeImage ;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_photo, container, false);
        imageView =(ImageView ) rootView.findViewById(R.id.photo);
        titleTextView = (TextView) rootView.findViewById(R.id.title_text);
        closeImage =(ImageView) rootView.findViewById(R.id.close_image);
        imgLoader = new ImageLoader(getActivity());

        Bundle arguments = getArguments();
        if(arguments!=null)
        {
            photos = (Photos) arguments.getParcelable(FlickerAdapter.PHOTOS_LIST) ;
            position = (int) arguments.getInt(FlickerAdapter.PODITION);
        }

        if(photos!=null && position >-1)
        {
            setPhotoDetails ();
        }

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });
        return rootView ;
    }

    public void setPhotoDetails ()
    {
        PhotoInfo photoInfo = photos.getPhoto().get(position);
        titleTextView.setText(photoInfo.getTitle());
        String photoUrl = Utils.getPhotoUri(photoInfo);
        imgLoader.displayImage(photoUrl, imageView);
    }

}
