package com.example.podcast.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;

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

}