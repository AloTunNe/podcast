package com.example.podcast.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.RecommendAdapter;
import com.example.podcast.Adapter.RecommendChanelAdapter;
import com.example.podcast.Adapter.RecommendPlaylistAdapter;
import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity {
    TextView tvChanels;
    TextView tvPlaylists;
    ImageView img_Humburger_Menu;
    User user;
    Context context;
    ArrayList<EpisodeOnMainBanner> episodeOnMainBannerList;
    ArrayList<PlaylistOnMainBanner> playlistOnMainBannerArrayList;
    ArrayList<ChanelOnMainBanner> chanelOnMainBannerArrayList;

    RecommendAdapter recommendAdapter;
    RecommendPlaylistAdapter recommendPlaylistAdapter;
    RecommendChanelAdapter recommendChanelAdapter;

    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagerPlaylistMainBanner;
    LinearLayoutManager linearLayoutManagerChanelMainBanner;

    RecyclerView recyclerView;
    RecyclerView rcv_Multi_Main_Banner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        getDataEpisodeBanner();
        getDataPlaylistBanner();
        /*getDataChanelBanner();*/

        SetUI();

        /*tvChanels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataChanelBanner();
                recommendChanelAdapter = new RecommendChanelAdapter(context, chanelOnMainBannerArrayList);
                rcv_Multi_Main_Banner.setLayoutManager(linearLayoutManagerChanelMainBanner);
                rcv_Multi_Main_Banner.setAdapter(recommendChanelAdapter);
                recommendChanelAdapter.notifyDataSetChanged();


            }
        });*/
        /*tvPlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataPlaylistBanner();
                recommendPlaylistAdapter = new RecommendPlaylistAdapter(context, playlistOnMainBannerArrayList);
                rcv_Multi_Main_Banner.setLayoutManager(linearLayoutManagerPlaylistMainBanner);
                rcv_Multi_Main_Banner.setAdapter(recommendPlaylistAdapter);
            }
        });*/

        img_Humburger_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Main.this, Hamburger_Menu.class);
                iNewActivity.putExtra("User_Login", user);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

    private void SetUI() {
        recommendAdapter = new RecommendAdapter(this, episodeOnMainBannerList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recommendAdapter);

        recommendPlaylistAdapter = new RecommendPlaylistAdapter(this, playlistOnMainBannerArrayList);
        rcv_Multi_Main_Banner.setLayoutManager(linearLayoutManagerPlaylistMainBanner);
        rcv_Multi_Main_Banner.setAdapter(recommendPlaylistAdapter);

    }

    private void Init() {
        context = this;
        tvChanels = (TextView) findViewById(R.id.tvChanels);
        tvPlaylists = (TextView) findViewById(R.id.tvPlaylist);
        img_Humburger_Menu = (ImageView) findViewById(R.id.img_Humbuger_Menu);

        episodeOnMainBannerList = new ArrayList<>();
        playlistOnMainBannerArrayList = new ArrayList<>();
        chanelOnMainBannerArrayList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManagerPlaylistMainBanner = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Main_Banner);
        rcv_Multi_Main_Banner = (RecyclerView) findViewById(R.id.rcv_Multi_Main_Banner);

        user = (User) getIntent().getParcelableExtra("User_Login");

    }
    private void getDataEpisodeBanner() {
        DataService dataService = APIService.getService();
        Call<List<EpisodeOnMainBanner>> callback = dataService.getDataEpisodeMainBanner();
        callback.enqueue(new Callback<List<EpisodeOnMainBanner>>() {
            @Override
            public void onResponse(Call<List<EpisodeOnMainBanner>> call, Response<List<EpisodeOnMainBanner>> response) {
                ArrayList<EpisodeOnMainBanner> episodes = (ArrayList<EpisodeOnMainBanner>) response.body();
                for(int i = 0; i <episodes.size(); i++) {
                    episodeOnMainBannerList.add(new EpisodeOnMainBanner(episodes.get(i).getName(), episodes.get(i).getAvatar(), episodes.get(i).getLink(), episodes.get(i).getListens(), episodes.get(i).getAuthor(), episodes.get(i).getAuthorAVT()));
                }
                recommendAdapter.notifyDataSetChanged();
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
                for(int i = 0; i <playlists.size(); i++) {
                    playlistOnMainBannerArrayList.add(new PlaylistOnMainBanner(playlists.get(i).getId(), playlists.get(i).getChanel(), playlists.get(i).getPicture(), playlists.get(i).getName()));
                }
                recommendPlaylistAdapter.notifyDataSetChanged();
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
                for(int i = 0; i <chanels.size(); i++) {
                   chanelOnMainBannerArrayList.add(new ChanelOnMainBanner(chanels.get(i).getId(), chanels.get(i).getChanelName(), chanels.get(i).getPicture(), chanels.get(i).getUserName(), chanels.get(i).getUserAvatar()));
                }
                recommendChanelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ChanelOnMainBanner>> call, Throwable t) {

            }
        });
    }
}
