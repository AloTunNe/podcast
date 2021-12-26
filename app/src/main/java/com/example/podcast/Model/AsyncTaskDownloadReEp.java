package com.example.podcast.Model;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import com.example.podcast.Adapter.RecommendAdapter;
import com.example.podcast.Adapter.RecommendCatogoryAdapter;
import com.example.podcast.Adapter.RecommendChanelAdapter;
import com.example.podcast.Adapter.RecommendPlaylistAdapter;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AsyncTaskDownloadReEp extends AsyncTask<String , Void, Boolean> {
    ArrayList<EpisodeOnMainBanner> episodeOnMainBannerList;
    ArrayList<PlaylistOnMainBanner> playlistOnMainBannerArrayList;
    ArrayList<ChanelOnMainBanner> chanelOnMainBannerArrayList;
    ArrayList<CatogoryOnMainBanner> catogoryOnMainBannerArrayList;

    RecommendAdapter recommendAdapter;
    RecommendPlaylistAdapter recommendPlaylistAdapter;
    RecommendChanelAdapter recommendChanelAdapter;
    RecommendCatogoryAdapter recommendCatogoryAdapter;

    public AsyncTaskDownloadReEp(ArrayList<EpisodeOnMainBanner> episodeOnMainBannerList, ArrayList<PlaylistOnMainBanner> playlistOnMainBannerArrayList, ArrayList<ChanelOnMainBanner> chanelOnMainBannerArrayList, ArrayList<CatogoryOnMainBanner> catogoryOnMainBannerArrayList, RecommendAdapter recommendAdapter, RecommendPlaylistAdapter recommendPlaylistAdapter, RecommendChanelAdapter recommendChanelAdapter, RecommendCatogoryAdapter recommendCatogoryAdapter) {
        this.episodeOnMainBannerList = episodeOnMainBannerList;
        this.playlistOnMainBannerArrayList = playlistOnMainBannerArrayList;
        this.chanelOnMainBannerArrayList = chanelOnMainBannerArrayList;
        this.catogoryOnMainBannerArrayList = catogoryOnMainBannerArrayList;
        this.recommendAdapter = recommendAdapter;
        this.recommendPlaylistAdapter = recommendPlaylistAdapter;
        this.recommendChanelAdapter = recommendChanelAdapter;
        this.recommendCatogoryAdapter = recommendCatogoryAdapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
            getDataEpisodeBanner();
            getDataPlaylistBanner();
            getDataChanelBanner();
            getDataCatogoryBanner();

        return null;
    }
    private void getDataEpisodeBanner() {
        DataService dataService = APIService.getService();
        Call<List<EpisodeOnMainBanner>> callback = dataService.getDataEpisodeMainBanner();
        callback.enqueue(new Callback<List<EpisodeOnMainBanner>>() {
            @Override
            public void onResponse(Call<List<EpisodeOnMainBanner>> call, Response<List<EpisodeOnMainBanner>> response) {
                ArrayList<EpisodeOnMainBanner> episodes = (ArrayList<EpisodeOnMainBanner>) response.body();
                try {
                    episodeOnMainBannerList.clear();
                    for (int i = 0; i < episodes.size(); i++) {
                        episodeOnMainBannerList.add(new EpisodeOnMainBanner(episodes.get(i).getIdEpisode(), episodes.get(i).getName(), episodes.get(i).getAvatar(), episodes.get(i).getLink(), episodes.get(i).getListens(), episodes.get(i).getAuthor(), episodes.get(i).getAuthorAVT()));
                    }
                    recommendAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getDataEpisodeBanner();
                }

            }

            @Override
            public void onFailure(Call<List<EpisodeOnMainBanner>> call, Throwable t) {

            }
        });
    }
    private void getDataPlaylistBanner() {
        DataService dataService = APIService.getService();
        Call<List<PlaylistOnMainBanner>> callback = dataService.getDataPlaylistMainBanner();
        callback.enqueue(new Callback<List<PlaylistOnMainBanner>>() {
            @Override
            public void onResponse(Call<List<PlaylistOnMainBanner>> call, Response<List<PlaylistOnMainBanner>> response) {
                ArrayList<PlaylistOnMainBanner> playlists = (ArrayList<PlaylistOnMainBanner>) response.body();
                try {
                    playlistOnMainBannerArrayList.clear();
                    for (int i = 0; i < playlists.size(); i++) {
                        playlistOnMainBannerArrayList.add(new PlaylistOnMainBanner(playlists.get(i).getId(), playlists.get(i).getChanel(), playlists.get(i).getPicture(), playlists.get(i).getName()));
                    }
                    recommendPlaylistAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getDataPlaylistBanner();
                }

            }

            @Override
            public void onFailure(Call<List<PlaylistOnMainBanner>> call, Throwable t) {

            }
        });
    }
    private void getDataChanelBanner() {
        DataService dataService = APIService.getService();
        Call<List<ChanelOnMainBanner>> callback = dataService.getDataChanelMainBanner();
        callback.enqueue(new Callback<List<ChanelOnMainBanner>>() {
            @Override
            public void onResponse(Call<List<ChanelOnMainBanner>> call, Response<List<ChanelOnMainBanner>> response) {
                ArrayList<ChanelOnMainBanner> chanels = (ArrayList<ChanelOnMainBanner>) response.body();
                try {
                    chanelOnMainBannerArrayList.clear();
                    for (int i = 0; i < chanels.size(); i++) {
                        chanelOnMainBannerArrayList.add(new ChanelOnMainBanner(chanels.get(i).getId(), chanels.get(i).getChanelName(), chanels.get(i).getPicture(), chanels.get(i).getUserName(), chanels.get(i).getUserAvatar()));
                        recommendChanelAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getDataChanelBanner();
                }

            }

            @Override
            public void onFailure(Call<List<ChanelOnMainBanner>> call, Throwable t) {

            }
        });
    }
    private void getDataCatogoryBanner() {
        DataService dataService = APIService.getService();
        Call<List<CatogoryOnMainBanner>> callback = dataService.getDataCatagoryMainBanner();
        callback.enqueue(new Callback<List<CatogoryOnMainBanner>>() {
            @Override
            public void onResponse(Call<List<CatogoryOnMainBanner>> call, Response<List<CatogoryOnMainBanner>> response) {
                ArrayList<CatogoryOnMainBanner> catogories = (ArrayList<CatogoryOnMainBanner>) response.body();
                try {
                    catogoryOnMainBannerArrayList.clear();
                    for (int i = 0; i < catogories.size(); i++) {
                        catogoryOnMainBannerArrayList.add(new CatogoryOnMainBanner(catogories.get(i).getId(), catogories.get(i).getName(), catogories.get(i).getPicture()));
                    }
                    recommendChanelAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getDataCatogoryBanner();
                }

                }

            @Override
            public void onFailure(Call<List<CatogoryOnMainBanner>> call, Throwable t) {

            }
        });
    }
}
