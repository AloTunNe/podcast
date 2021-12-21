package com.example.podcast.Service;

import com.example.podcast.Model.CatogoryOnMainBanner;
import com.example.podcast.Model.Chanel;
import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.Playlist;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("getUser.php")
    Call<List<User>> getDataUser();

    @GET("RandomEpisodeMainBanner.php")
    Call<List<EpisodeOnMainBanner>> getDataEpisodeMainBanner();

    @GET("RandomPlaylistOnMainBanner.php")
    Call<List<PlaylistOnMainBanner>> getDataPlaylistMainBanner();

    @GET("RandomChanelOnMainBanner.php")
    Call<List<ChanelOnMainBanner>> getDataChanelMainBanner();

    @GET("RandomCatogoryMainBanner.php")
    Call<List<CatogoryOnMainBanner>> getDataCatagoryMainBanner();

    @GET("GetAllEpisode.php")
    Call<List<Episode>> getAllDataEpisode();

    @FormUrlEncoded
    @POST("SearchEpisodeWithIdPlaylist.php")
    Call<List<Episode>> SearchEpisodeWithIdPlaylist(@Field("KeyWord") String idPlaylist);

    @FormUrlEncoded
    @POST("SearchEpisode.php")
    Call<List<Episode>> SearchEpisode(@Field("KeyWord") String Keyword);

    @FormUrlEncoded
    @POST("SearchEpisodeWithId.php")
    Call<List<Episode>> SearchEpisodeWithId(@Field("KeyWord") String Keyword);

    @FormUrlEncoded
    @POST ("UpadateEpisodeLikes.php")
    Call<String> UpdateEpisodeLikes(@Field("Likes") String Like, @Field("IdEpisode") String IdEpisode);

    @FormUrlEncoded
    @POST ("UpadateEpisodeListens.php")
    Call<String> UpdateEpisodeViews(@Field("Views") String View, @Field("IdEpisode") String IdEpisode);

    @FormUrlEncoded
    @POST ("SearchChanel.php")
    Call<List<Chanel>> SearchChanelData(@Field("KeyWord") String Keyword);

    @FormUrlEncoded
    @POST ("SearchPlaylist.php")
    Call<List<Playlist>> SearchPlaylistData(@Field("KeyWord") String keyword);

    @FormUrlEncoded
    @POST ("SearchPlaylistWithChanelId.php")
    Call<List<Playlist>> SearchPlaylistWithChanelId(@Field("KeyWord") String keyword);

    @FormUrlEncoded
    @POST ("getChanelById.php")
    Call<List<Chanel>> SearchChanelDataById(@Field("KeyWord") String keyword);

}
