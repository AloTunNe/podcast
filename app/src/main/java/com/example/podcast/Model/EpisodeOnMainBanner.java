package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeOnMainBanner implements Parcelable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Listens")
    @Expose
    private String listens;
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("AuthorAVT")
    @Expose
    private String authorAVT;

    public EpisodeOnMainBanner(String name, String avatar, String link, String listens, String author, String authorAVT) {
        this.name = name;
        this.avatar = avatar;
        this.link = link;
        this.listens = listens;
        this.author = author;
        this.authorAVT = authorAVT;
    }

    protected EpisodeOnMainBanner(Parcel in) {
        name = in.readString();
        avatar = in.readString();
        link = in.readString();
        listens = in.readString();
        author = in.readString();
        authorAVT = in.readString();
    }

    public static final Creator<EpisodeOnMainBanner> CREATOR = new Creator<EpisodeOnMainBanner>() {
        @Override
        public EpisodeOnMainBanner createFromParcel(Parcel in) {
            return new EpisodeOnMainBanner(in);
        }

        @Override
        public EpisodeOnMainBanner[] newArray(int size) {
            return new EpisodeOnMainBanner[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getListens() {
        return listens;
    }

    public void setListens(String listens) {
        this.listens = listens;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAVT() {
        return authorAVT;
    }

    public void setAuthorAVT(String authorAVT) {
        this.authorAVT = authorAVT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(avatar);
        parcel.writeString(link);
        parcel.writeString(listens);
        parcel.writeString(author);
        parcel.writeString(authorAVT);
    }
}