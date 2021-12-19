package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.RecommendAdapter;
import com.example.podcast.Adapter.RecommendCatogoryAdapter;
import com.example.podcast.Adapter.RecommendChanelAdapter;
import com.example.podcast.Adapter.RecommendPlaylistAdapter;
import com.example.podcast.Model.AsyncTaskDownloadReEp;
import com.example.podcast.Model.CatogoryOnMainBanner;
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

public class Main extends AppCompatActivity implements RecommendAdapter.OnRecommendEpClick, RecommendCatogoryAdapter.OnCategoryClick, RecommendChanelAdapter.OnReChannelClick, RecommendPlaylistAdapter.OnRePlaylistClick{
    TextView tvChanels;
    TextView tvPlaylists;
    TextView tvCatogories;
    ImageView img_Humburger_Menu;
    ImageView imgIconSearch;
    User user;
    Context context;
    ArrayList<EpisodeOnMainBanner> episodeOnMainBannerList;
    ArrayList<PlaylistOnMainBanner> playlistOnMainBannerArrayList;
    ArrayList<ChanelOnMainBanner> chanelOnMainBannerArrayList;
    ArrayList<CatogoryOnMainBanner> catogoryOnMainBannerArrayList;

    RecommendAdapter recommendAdapter;
    RecommendPlaylistAdapter recommendPlaylistAdapter;
    RecommendChanelAdapter recommendChanelAdapter;
    RecommendCatogoryAdapter recommendCatogoryAdapter;

    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagerPlaylistMainBanner;

    RecyclerView recyclerView;
    RecyclerView rcv_Multi_Main_Banner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        SetUI();
        new AsyncTaskDownloadReEp(episodeOnMainBannerList, playlistOnMainBannerArrayList, chanelOnMainBannerArrayList, catogoryOnMainBannerArrayList, recommendAdapter, recommendPlaylistAdapter,recommendChanelAdapter,  recommendCatogoryAdapter).execute();


        tvChanels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommendChanelAdapter.notifyDataSetChanged();
                rcv_Multi_Main_Banner.setAdapter(recommendChanelAdapter);
            }
        });

        tvPlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommendPlaylistAdapter.notifyDataSetChanged();
                rcv_Multi_Main_Banner.setAdapter(recommendPlaylistAdapter);
            }
        });

        tvCatogories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommendCatogoryAdapter.notifyDataSetChanged();
                rcv_Multi_Main_Banner.setAdapter(recommendCatogoryAdapter);
            }
        });

        img_Humburger_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Main.this, Hamburger_Menu.class);
                iNewActivity.putExtra("User_Login", user);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
        imgIconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Main.this, Browse_Podcast.class);
                iNewActivity.putExtra("User_Login", user);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

    private void SetUI() {
        recommendAdapter = new RecommendAdapter(this, episodeOnMainBannerList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recommendAdapter);
        recommendAdapter.notifyDataSetChanged();
        recommendPlaylistAdapter = new RecommendPlaylistAdapter(this, playlistOnMainBannerArrayList, this);
        recommendChanelAdapter = new RecommendChanelAdapter(context, chanelOnMainBannerArrayList, this);
        recommendCatogoryAdapter = new RecommendCatogoryAdapter(context, catogoryOnMainBannerArrayList, this);

       /* final int speedScroll = 15000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count < recommendAdapter.getItemCount()){
                    recyclerView.scrollToPosition(count++);
                    handler.postDelayed(this,speedScroll);
                }


            }
        };
        handler.postDelayed(runnable,speedScroll);*/

        rcv_Multi_Main_Banner.setLayoutManager(linearLayoutManagerPlaylistMainBanner);

        rcv_Multi_Main_Banner.setAdapter(recommendPlaylistAdapter);


    }

    private void Init() {
        context = this;
        tvChanels = (TextView) findViewById(R.id.tvChanels);
        tvPlaylists = (TextView) findViewById(R.id.tvPlaylist);
        tvCatogories = (TextView) findViewById(R.id.tvCatogory);
        img_Humburger_Menu = (ImageView) findViewById(R.id.img_Humbuger_Menu);
        imgIconSearch = (ImageView) findViewById(R.id.imgsearch);

        episodeOnMainBannerList = new ArrayList<>();
        playlistOnMainBannerArrayList = new ArrayList<>();
        chanelOnMainBannerArrayList = new ArrayList<>();
        catogoryOnMainBannerArrayList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManagerPlaylistMainBanner = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Main_Banner);
        rcv_Multi_Main_Banner = (RecyclerView) findViewById(R.id.rcv_Multi_Main_Banner);

        user = (User) getIntent().getParcelableExtra("User_Login");

    }

    @Override
    public void onRecommendEpClick(int position) {
        Log.d(TAG, "onRecommendEpClick: click on item " + position);
        Toast.makeText(this, "onRecommendEpClick: click on item " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(int position) {
        Log.d(TAG, "onCategoryClick: click on item " + position);
        Toast.makeText(this, "onCategoryClick: click on item " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReChannelClick(int position) {
        Log.d(TAG, "onReChannelClick: click on item " + position);
        Toast.makeText(this, "onReChannelClick: click on item " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRePlaylistClick(int position) {
        Toast.makeText(this, "onRePlaylistClick: click on item" + position, Toast.LENGTH_SHORT).show();
    }
}
