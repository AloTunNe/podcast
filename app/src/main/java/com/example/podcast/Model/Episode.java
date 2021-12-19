package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable {

    @SerializedName("IdEpisode")
    @Expose
    private String idEpisode;
    @SerializedName("ChanelId")
    @Expose
    private String chanelId;
    @SerializedName("NameEpisode")
    @Expose
    private String nameEpisode;
    @SerializedName("DiscriptionEpisode")
    @Expose
    private String discriptionEpisode;
    @SerializedName("LinkEpisode")
    @Expose
    private String linkEpisode;
    @SerializedName("PicEpisode")
    @Expose
    private String picEpisode;
    @SerializedName("LikesEpisode")
    @Expose
    private String likesEpisode;
    @SerializedName("ViewsEpisode")
    @Expose
    private String viewsEpisode;
    @SerializedName("IdPlaylistEpisode")
    @Expose
    private String idPlaylistEpisode;
    @SerializedName("AuthorEpisode")
    @Expose
    private String authorEpisode;

    public Episode(String idEpisode, String chanelId, String nameEpisode, String discriptionEpisode, String linkEpisode, String picEpisode, String likesEpisode, String viewsEpisode, String idPlaylistEpisode, String authorEpisode) {
        this.idEpisode = idEpisode;
        this.chanelId = chanelId;
        this.nameEpisode = nameEpisode;
        this.discriptionEpisode = discriptionEpisode;
        this.linkEpisode = linkEpisode;
        this.picEpisode = picEpisode;
        this.likesEpisode = likesEpisode;
        this.viewsEpisode = viewsEpisode;
        this.idPlaylistEpisode = idPlaylistEpisode;
        this.authorEpisode = authorEpisode;
    }

    protected Episode(Parcel in) {
        idEpisode = in.readString();
        chanelId = in.readString();
        nameEpisode = in.readString();
        discriptionEpisode = in.readString();
        linkEpisode = in.readString();
        picEpisode = in.readString();
        likesEpisode = in.readString();
        viewsEpisode = in.readString();
        idPlaylistEpisode = in.readString();
        authorEpisode = in.readString();
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getIdEpisode() {
        return idEpisode;
    }

    public void setIdEpisode(String idEpisode) {
        this.idEpisode = idEpisode;
    }

    public String getChanelId() {
        return chanelId;
    }

    public void setChanelId(String chanelId) {
        this.chanelId = chanelId;
    }

    public String getNameEpisode() {
        return nameEpisode;
    }

    public void setNameEpisode(String nameEpisode) {
        this.nameEpisode = nameEpisode;
    }

    public String getDiscriptionEpisode() {
        return discriptionEpisode;
    }

    public void setDiscriptionEpisode(String discriptionEpisode) {
        this.discriptionEpisode = discriptionEpisode;
    }

    public String getLinkEpisode() {
        return linkEpisode;
    }

    public void setLinkEpisode(String linkEpisode) {
        this.linkEpisode = linkEpisode;
    }

    public String getPicEpisode() {
        return picEpisode;
    }

    public void setPicEpisode(String picEpisode) {
        this.picEpisode = picEpisode;
    }

    public String getLikesEpisode() {
        return likesEpisode;
    }

    public void setLikesEpisode(String likesEpisode) {
        this.likesEpisode = likesEpisode;
    }

    public String getViewsEpisode() {
        return viewsEpisode;
    }

    public void setViewsEpisode(String viewsEpisode) {
        this.viewsEpisode = viewsEpisode;
    }

    public String getIdPlaylistEpisode() {
        return idPlaylistEpisode;
    }

    public void setIdPlaylistEpisode(String idPlaylistEpisode) {
        this.idPlaylistEpisode = idPlaylistEpisode;
    }

    public String getAuthorEpisode() {
        return authorEpisode;
    }

    public void setAuthorEpisode(String authorEpisode) {
        this.authorEpisode = authorEpisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idEpisode);
        parcel.writeString(chanelId);
        parcel.writeString(nameEpisode);
        parcel.writeString(discriptionEpisode);
        parcel.writeString(linkEpisode);
        parcel.writeString(picEpisode);
        parcel.writeString(likesEpisode);
        parcel.writeString(viewsEpisode);
        parcel.writeString(idPlaylistEpisode);
        parcel.writeString(authorEpisode);
    }
}
