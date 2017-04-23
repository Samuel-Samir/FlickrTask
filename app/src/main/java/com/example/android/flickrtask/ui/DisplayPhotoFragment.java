package com.example.android.flickrtask.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.data.PhotoInfo;
import com.example.android.flickrtask.data.Photos;
import com.squareup.picasso.Picasso;


public class DisplayPhotoFragment extends Fragment {
    private int position ;
    private Photos photos ;

    ImageView  imageView;
    TextView titleTextView;
    ImageView closeImage ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_photo, container, false);
        imageView =(ImageView ) rootView.findViewById(R.id.photo);
        titleTextView = (TextView) rootView.findViewById(R.id.title_text);
        closeImage =(ImageView) rootView.findViewById(R.id.close_image);
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
        String size="_n";
        String photoUrl = "https://farm%s.staticflickr.com/%s/%s_%s%s.jpg";
        photoUrl= String.format(photoUrl , photoInfo.getFarm() ,photoInfo.getServer() ,photoInfo.getId() ,photoInfo.getSecret(),size ) ;
        Picasso.with(getActivity())
                .load(photoUrl)
                .placeholder(R.drawable.noposter)
                .error(R.drawable.noposter)
                .into(imageView);
       titleTextView.setText(photoInfo.getTitle());

    }

}
