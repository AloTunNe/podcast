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
import com.example.podcast.Model.EpisodeOnMainBanner;
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
    TextView tvAuthorBrowse;
    ImageView img_Humburger_Menu;
    User user;
    ArrayList<EpisodeOnMainBanner> episodeOnMainBannerList;
    RecommendAdapter recommendAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        getDataEpisodeBanner();

        recommendAdapter = new RecommendAdapter(this, episodeOnMainBannerList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recommendAdapter);
        /*SetUI();*/

        tvAuthorBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Main.this, Browse_Authors.class);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
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
    }

    private void SetUI() {
    }

    private void Init() {
        tvAuthorBrowse = (TextView) findViewById(R.id.tvAuthors);
        img_Humburger_Menu = (ImageView) findViewById(R.id.img_Humbuger_Menu);
        episodeOnMainBannerList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Main_Banner);
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
                    episodeOnMainBannerList.add(new EpisodeOnMainBanner(episodes.get(i).getName(), episodes.get(i).getAvatar(), episodes.get(i).getLink(), episodes.get(i).getListens(), episodes.get(i).getAuthor()));
                }

            }

            @Override
            public void onFailure(Call<List<EpisodeOnMainBanner>> call, Throwable t) {

            }
        });
    }
}
