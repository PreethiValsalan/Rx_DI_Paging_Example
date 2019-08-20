package com.interapp.developer.rxpagingex.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.NonNull;

/**
 * Created by Preethi Valsalan on 8/14/19
 */
public class BasicPhoto implements Parcelable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private int farm;
    @SerializedName("title")
    @Expose
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public static DiffUtil.ItemCallback<BasicPhoto> DIFF_CALLBACK = new DiffUtil.ItemCallback<BasicPhoto>() {
        @Override
        public boolean areItemsTheSame(@NonNull BasicPhoto oldItem, @NonNull BasicPhoto newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BasicPhoto oldItem, @NonNull BasicPhoto newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        BasicPhoto article = (BasicPhoto) obj;
        return article.id == this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeInt(this.farm);
        dest.writeString(this.title);
    }

    public BasicPhoto() {
    }

    protected BasicPhoto(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<BasicPhoto> CREATOR = new Parcelable.Creator<BasicPhoto>() {
        @Override
        public BasicPhoto createFromParcel(Parcel source) {
            return new BasicPhoto(source);
        }

        @Override
        public BasicPhoto[] newArray(int size) {
            return new BasicPhoto[size];
        }
    };
}
