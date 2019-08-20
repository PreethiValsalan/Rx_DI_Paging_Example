package com.interapp.developer.rxpagingex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Preethi Valsalan on 8/14/19
 */
public class PhotoListWrapper {


    @SerializedName("photos")
    @Expose
    private PhotoList photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotoList getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoList photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
