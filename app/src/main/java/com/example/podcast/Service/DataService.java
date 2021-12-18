package com.example.podcast.Service;

import com.example.podcast.Model.CatogoryOnMainBanner;
import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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
}
