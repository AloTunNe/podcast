package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.ParameterizedType;

public class Chanel implements Parcelable {
    @SerializedName("ChanelId")
    @Expose
    private String chanelId;
    @SerializedName("CatogoryId")
    @Expose
    private String catogoryId;
    @SerializedName("ChanelName")
    @Expose
    private String chanelName;
    @SerializedName("ChanelBackground")
    @Expose
    private String chanelBackground;
    @SerializedName("ChanelDiscription")
    @Expose
    private String chanelDiscription;
    @SerializedName("ChanelFollows")
    @Expose
    private String chanelFollows;
    @SerializedName("ChanelAuthor")
    @Expose
    private String chanelAuthor;
    @SerializedName("ChanelAuthorPic")
    @Expose
    private String chanelAuthorPic;

    public Chanel(String chanelId, String catogoryId, String chanelName, String chanelBackground, String chanelDiscription, String chanelFollows, String chanelAuthor, String chanelAuthorPic) {
        this.chanelId = chanelId;
        this.catogoryId = catogoryId;
        this.chanelName = chanelName;
        this.chanelBackground = chanelBackground;
        this.chanelDiscription = chanelDiscription;
        this.chanelFollows = chanelFollows;
        this.chanelAuthor = chanelAuthor;
        this.chanelAuthorPic = chanelAuthorPic;
    }
    public Chanel(Chanel c) {
        this.chanelId = c.getChanelId();
        this.catogoryId = c.getCatogoryId();
        this.chanelName = c.getChanelName();
        this.chanelBackground = c.getChanelBackground();
        this.chanelDiscription = c.getChanelDiscription();
        this.chanelFollows = c.getChanelFollows();
        this.chanelAuthor = c.getChanelAuthor();
        this.chanelAuthorPic = c.getChanelAuthorPic();
    }


    protected Chanel(Parcel in) {
        chanelId = in.readString();
        catogoryId = in.readString();
        chanelName = in.readString();
        chanelBackground = in.readString();
        chanelDiscription = in.readString();
        chanelFollows = in.readString();
        chanelAuthor = in.readString();
        chanelAuthorPic = in.readString();
    }

    public static final Creator<Chanel> CREATOR = new Creator<Chanel>() {
        @Override
        public Chanel createFromParcel(Parcel in) {
            return new Chanel(in);
        }

        @Override
        public Chanel[] newArray(int size) {
            return new Chanel[size];
        }
    };

    public String getChanelId() {
        return chanelId;
    }

    public void setChanelId(String chanelId) {
        this.chanelId = chanelId;
    }

    public String getCatogoryId() {
        return catogoryId;
    }

    public void setCatogoryId(String catogoryId) {
        this.catogoryId = catogoryId;
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    public String getChanelBackground() {
        return chanelBackground;
    }

    public void setChanelBackground(String chanelBackground) {
        this.chanelBackground = chanelBackground;
    }

    public String getChanelDiscription() {
        return chanelDiscription;
    }

    public void setChanelDiscription(String chanelDiscription) {
        this.chanelDiscription = chanelDiscription;
    }

    public String getChanelFollows() {
        return chanelFollows;
    }

    public void setChanelFollows(String chanelFollows) {
        this.chanelFollows = chanelFollows;
    }

    public String getChanelAuthor() {
        return chanelAuthor;
    }

    public void setChanelAuthor(String chanelAuthor) {
        this.chanelAuthor = chanelAuthor;
    }

    public String getChanelAuthorPic() {
        return chanelAuthorPic;
    }

    public void setChanelAuthorPic(String chanelAuthorPic) {
        this.chanelAuthorPic = chanelAuthorPic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chanelId);
        parcel.writeString(catogoryId);
        parcel.writeString(chanelName);
        parcel.writeString(chanelBackground);
        parcel.writeString(chanelDiscription);
        parcel.writeString(chanelFollows);
        parcel.writeString(chanelAuthor);
        parcel.writeString(chanelAuthorPic);
    }
}
