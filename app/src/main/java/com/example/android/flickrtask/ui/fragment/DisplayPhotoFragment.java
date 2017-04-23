package com.example.android.flickrtask.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.cache.ImageLoader;
import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.models.PhotoInfo;
import com.example.android.flickrtask.models.Photos;
import com.example.android.flickrtask.ui.OnSwipeTouchListener;
import com.example.android.flickrtask.ui.adapter.FlickerAdapter;
import com.example.android.flickrtask.utilities.Utils;

import static com.example.android.flickrtask.ui.fragment.FlickerFragment.SAVE_ARGUMENT;


public class DisplayPhotoFragment extends Fragment  {
    private ApiResponse apiResponse;
    private ImageLoader imgLoader;
    private int recevidPosition;
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

        if(savedInstanceState==null) {
            Bundle arguments = getArguments();
            if (arguments != null) {
                apiResponse = (ApiResponse) arguments.getParcelable(FlickerAdapter.PHOTOS_LIST);
                recevidPosition = (int) arguments.getInt(FlickerAdapter.PODITION);
            }
        }
        else {
            apiResponse = (ApiResponse) savedInstanceState.getParcelable(SAVE_ARGUMENT);
            recevidPosition =(int) savedInstanceState.getInt("position");

        }

        if(apiResponse!=null && recevidPosition >-1)
        {
            setPhotoDetails (recevidPosition);
        }

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });



        return rootView ;
    }

    public void setPhotoDetails (int position )
    {
        if(position>-1 && position<apiResponse.getPhotos().getPhoto().size()) {
            PhotoInfo photoInfo = apiResponse.getPhotos().getPhoto().get(position);
            titleTextView.setText(photoInfo.getTitle());
            String photoUrl = Utils.getPhotoUri(photoInfo);
            imgLoader.displayImage(photoUrl, imageView);
        }

        imageView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                setPhotoDetails (recevidPosition-1);
                recevidPosition-=1;
            }
            public void onSwipeLeft() {
                setPhotoDetails (recevidPosition+1);
                recevidPosition+=1;
            }
            public void onSwipeBottom() {
            }

        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_ARGUMENT ,apiResponse);
        outState.putInt("position",recevidPosition);

    }
}
