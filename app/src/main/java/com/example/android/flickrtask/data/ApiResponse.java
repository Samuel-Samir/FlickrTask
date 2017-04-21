package com.example.android.flickrtask.data;

/**
 * Created by samuel on 4/21/2017.
 */

public class ApiResponse {
    private Photos photos= new Photos();
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
