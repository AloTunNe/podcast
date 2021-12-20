package com.example.podcast.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist implements Parcelable {

    @SerializedName("IdPlaylist")
    @Expose
    private String idPlaylist;
    @SerializedName("IdChanel")
    @Expose
    private String idChanel;
    @SerializedName("PicPlaylist")
    @Expose
    private String picPlaylist;
    @SerializedName("NamePlaylist")
    @Expose
    private String namePlaylist;
    @SerializedName("ChanelPlaylist")
    @Expose
    private String chanelPlaylist;

    protected Playlist(Parcel in) {
        idPlaylist = in.readString();
        idChanel = in.readString();
        picPlaylist = in.readString();
        namePlaylist = in.readString();
        chanelPlaylist = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public Playlist(String idPlaylist, String idChanel, String picPlaylist, String namePlaylist, String chanelPlaylist) {
        this.idPlaylist = idPlaylist;
        this.idChanel = idChanel;
        this.picPlaylist = picPlaylist;
        this.namePlaylist = namePlaylist;
        this.chanelPlaylist = chanelPlaylist;
    }
    public Playlist(Playlist p) {
        this.idPlaylist = p.getIdPlaylist();
        this.idChanel = p.getIdChanel();
        this.picPlaylist = p.getPicPlaylist();
        this.namePlaylist = p.getNamePlaylist();
        this.chanelPlaylist = p.getChanelPlaylist();
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getIdChanel() {
        return idChanel;
    }

    public void setIdChanel(String idChanel) {
        this.idChanel = idChanel;
    }

    public String getPicPlaylist() {
        return picPlaylist;
    }

    public void setPicPlaylist(String picPlaylist) {
        this.picPlaylist = picPlaylist;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

    public String getChanelPlaylist() {
        return chanelPlaylist;
    }

    public void setChanelPlaylist(String chanelPlaylist) {
        this.chanelPlaylist = chanelPlaylist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idPlaylist);
        parcel.writeString(idChanel);
        parcel.writeString(picPlaylist);
        parcel.writeString(namePlaylist);
        parcel.writeString(chanelPlaylist);
    }
}
