package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserEmail")
    @Expose
    private String userEmail;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;
    @SerializedName("UserAvatar")
    @Expose
    private String userAvatar;
    @SerializedName("UserOwner")
    @Expose
    private String userOwner;

    protected User(Parcel in) {
        id = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
        userAvatar = in.readString();
        userOwner = in.readString();
    }

    public User(String id, String userName, String userEmail, String userPassword, String userAvatar, String userOwner) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAvatar = userAvatar;
        this.userOwner = userOwner;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userPassword);
        parcel.writeString(userAvatar);
        parcel.writeString(userOwner);
    }
}