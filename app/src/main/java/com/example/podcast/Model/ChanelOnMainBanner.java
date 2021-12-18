package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChanelOnMainBanner implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("ChanelName")
    @Expose
    private String chanelName;
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserAvatar")
    @Expose
    private String userAvatar;

    protected ChanelOnMainBanner(Parcel in) {
        id = in.readString();
        chanelName = in.readString();
        picture = in.readString();
        userName = in.readString();
        userAvatar = in.readString();
    }

    public ChanelOnMainBanner(String id, String chanelName, String picture, String userName, String userAvatar) {
        this.id = id;
        this.chanelName = chanelName;
        this.picture = picture;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public static final Creator<ChanelOnMainBanner> CREATOR = new Creator<ChanelOnMainBanner>() {
        @Override
        public ChanelOnMainBanner createFromParcel(Parcel in) {
            return new ChanelOnMainBanner(in);
        }

        @Override
        public ChanelOnMainBanner[] newArray(int size) {
            return new ChanelOnMainBanner[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(chanelName);
        parcel.writeString(picture);
        parcel.writeString(userName);
        parcel.writeString(userAvatar);
    }
}
