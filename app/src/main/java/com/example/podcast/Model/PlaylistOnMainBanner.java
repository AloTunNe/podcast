package com.example.podcast.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistOnMainBanner implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Chanel")
    @Expose
    private String chanel;
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("Name")
    @Expose
    private String name;

    public PlaylistOnMainBanner(String id, String chanel, String picture, String name) {
        this.id = id;
        this.chanel = chanel;
        this.picture = picture;
        this.name = name;
    }

    protected PlaylistOnMainBanner(Parcel in) {
        id = in.readString();
        chanel = in.readString();
        picture = in.readString();
        name = in.readString();
    }

    public static final Creator<PlaylistOnMainBanner> CREATOR = new Creator<PlaylistOnMainBanner>() {
        @Override
        public PlaylistOnMainBanner createFromParcel(Parcel in) {
            return new PlaylistOnMainBanner(in);
        }

        @Override
        public PlaylistOnMainBanner[] newArray(int size) {
            return new PlaylistOnMainBanner[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(chanel);
        parcel.writeString(picture);
        parcel.writeString(name);
    }
}
