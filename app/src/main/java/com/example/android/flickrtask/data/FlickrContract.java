package com.example.android.flickrtask.data;

import android.provider.BaseColumns;

/**
 * Created by samuel on 4/22/2017.
 */

public class FlickrContract {

    public static final class PhotoEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "photo";
        public static final String COLUMN_SECRET="secret";
        public static final String COLUMN_SERVER="server";
        public static final String COLUMN_FARM="farm";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_PHOPO_BLOB="image";



    }
}
