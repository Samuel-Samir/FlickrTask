package com.example.android.flickrtask.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 4/21/2017.
 */

public class Photos  implements Parcelable{
    private int page ;
    private int pages ;
    private int perpage ;
    private int total ;
    private List<PhotoInfo> photo = new ArrayList<>();

    public Photos ()
    {

    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {this.page = page;}

    public int getPages() {
        return pages;
    }

    public List<PhotoInfo> getPhoto() {
        return photo;
    }
    protected Photos(Parcel in) {
        page = in.readInt();
        pages = in.readInt();
        perpage = in.readInt();
        total = in.readInt();
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(pages);
        dest.writeInt(perpage);
        dest.writeInt(total);
    }
}
