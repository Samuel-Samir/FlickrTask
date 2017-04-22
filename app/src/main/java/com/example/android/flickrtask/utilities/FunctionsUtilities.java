package com.example.android.flickrtask.utilities;

import com.example.android.flickrtask.data.ApiResponse;

/**
 * Created by samuel on 4/22/2017.
 */

public class FunctionsUtilities {

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
}
