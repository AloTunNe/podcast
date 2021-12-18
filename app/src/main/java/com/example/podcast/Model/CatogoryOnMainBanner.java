package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatogoryOnMainBanner implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Picture")
    @Expose
    private String picture;

    protected CatogoryOnMainBanner(Parcel in) {
        id = in.readString();
        name = in.readString();
        picture = in.readString();
    }

    public CatogoryOnMainBanner(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public static final Creator<CatogoryOnMainBanner> CREATOR = new Creator<CatogoryOnMainBanner>() {
        @Override
        public CatogoryOnMainBanner createFromParcel(Parcel in) {
            return new CatogoryOnMainBanner(in);
        }

        @Override
        public CatogoryOnMainBanner[] newArray(int size) {
            return new CatogoryOnMainBanner[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(picture);
    }
}