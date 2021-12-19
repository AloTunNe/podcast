package com.example.podcast.Service;

import com.example.podcast.Model.CatogoryOnMainBanner;
import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
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
    @POST("GetAllEpisode.php")
    Call<List<Episode>> GetEpisodePlaylist(@Field("IdPlaylist") String IdPlaylist);

    @FormUrlEncoded
    @POST("SearchEpisode.php")
    Call<List<Episode>> SearchEpisode(@Field("KeyWord") String KeyWord);

    @FormUrlEncoded
    @POST ("UpadateEpisodeLikes.php")
    Call<String> UpdateEpisodeLikes(@Field("Likes") String Like, @Field("IdEpisode") String IdEpisode);

    @FormUrlEncoded
    @POST ("UpadateEpisodeListens.php")
    Call<String> UpdateEpisodeViews(@Field("Views") String View, @Field("IdEpisode") String IdEpisode);
}
