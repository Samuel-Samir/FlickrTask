package com.example.android.flickrtask.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samuel on 4/21/2017.
 */

public class ApiResponse implements Parcelable{
    private Photos photos= new Photos();
    private String stat;

    protected ApiResponse(Parcel in) {
        photos = in.readParcelable(Photos.class.getClassLoader());
        stat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(photos, flags);
        dest.writeString(stat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiResponse> CREATOR = new Creator<ApiResponse>() {
        @Override
        public ApiResponse createFromParcel(Parcel in) {
            return new ApiResponse(in);
        }

        @Override
        public ApiResponse[] newArray(int size) {
            return new ApiResponse[size];
        }
    };

    public Photos getPhotos() {
        return photos;
    }
}
