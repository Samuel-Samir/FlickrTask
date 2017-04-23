package com.example.android.flickrtask.data;

import android.provider.BaseColumns;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickrContract {

    public static final class PhotoEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "photo";
        public static final String COLUMN_PHOTO_ID="photoId";
        public static final String COLUMN_PHOPO_BLOB="image";
    }
}
