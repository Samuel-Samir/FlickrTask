package com.example.android.flickrtask.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.android.flickrtask.models.ApiResponse;
import com.example.android.flickrtask.models.PhotoInfo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by samuel on 4/22/2017.
 */

public class Utils {
    public static ApiResponse addTwoApiResponse(ApiResponse original , ApiResponse newResponse)
    {
        original.getPhotos().setPage( newResponse.getPhotos().getPage() ) ;
        original.getPhotos().getPhoto().addAll(newResponse.getPhotos().getPhoto());
        return original;
    }

    public static int getPageNum (ApiResponse apiResponse)
    {
        return  apiResponse.getPhotos().getPage();
    }
    public static int getNumofAvailablePages (ApiResponse apiResponse)
    {
        return  apiResponse.getPhotos().getPages();
    }

    public static boolean checkInternetConnection(Context context)
    {

        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPhotoUri(PhotoInfo photoInfo)
    {
        String size = "_n";
        String photoUrl = "https://farm%s.staticflickr.com/%s/%s_%s%s.jpg";
        photoUrl = String.format(photoUrl, photoInfo.getFarm(), photoInfo.getServer(), photoInfo.getId(), photoInfo.getSecret(), size);
        return  photoUrl;
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;

        try {
            byte[] bytes = new byte[buffer_size];
            while(true) {
                int count = is.read(bytes, 0, buffer_size);
                if(count == -1) {
                    break;
                }
                os.write(bytes, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
