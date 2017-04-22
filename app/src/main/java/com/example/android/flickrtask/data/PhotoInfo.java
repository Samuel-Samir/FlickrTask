package com.example.android.flickrtask.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samuel on 4/21/2017.
 */

public class PhotoInfo implements Parcelable{
    private String id ;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;



    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }
    public String getSecret() {
        return secret;
    }
    public String getServer() {
        return server;
    }
    public int getFarm() {
        return farm;
    }
    public String getTitle() {
        return title;
    }

    protected PhotoInfo(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readInt();
        title = in.readString();
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeInt(farm);
        dest.writeString(title);
    }
}
