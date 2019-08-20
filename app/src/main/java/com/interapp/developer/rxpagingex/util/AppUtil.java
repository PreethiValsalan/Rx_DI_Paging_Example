package com.interapp.developer.rxpagingex.util;

import android.content.res.Configuration;
import android.view.Display;

/**
 * Created by Preethi Valsalan on 2019-08-19
 */
public class AppUtil {

    public static final String ENDPOINT_URL = "https://api.flickr.com";
    public static final int PAGE_SIZE = 100;
    public static final String API_KEY = "1c6c955d7c1ed7e6c7a3fba2cac2cce0";

    public static final String THUMBNAIL_PHOTO_API = "https://farm%1$d.staticflickr.com/%2$s/%3$s_%4$s_q.jpg";

    public static int getScreenOrientation(Display screenOrientation)  {

        int orientation = Configuration.ORIENTATION_LANDSCAPE;
        if(screenOrientation.getWidth() < screenOrientation.getHeight()){
            orientation = Configuration.ORIENTATION_PORTRAIT;

        }
        return orientation;
    }
}
