package com.example.podcast.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.SearchPodcastAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Browse_Podcast extends AppCompatActivity {

    Context context;

    EditText edtSearch;

    ImageView imgIconsearch;

    TextView tvPodcastNumber;

    SearchPodcastAdapter searchPodcastAdapter;

    ArrayList<Episode> episodeArrayList ;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_podcasts);

        Init();
        SetUi();

        imgIconsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSearch.getText() != null) {
                    getDataEpisodeSearch(edtSearch.getText().toString());
                    searchPodcastAdapter = new SearchPodcastAdapter(Browse_Podcast.this, episodeArrayList);
                    searchPodcastAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Podcast.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(searchPodcastAdapter);
                    searchPodcastAdapter.notifyDataSetChanged();
                   /* SetUi();*/
                }
                else Toast.makeText(context, "Please write Keyword to Search!", Toast.LENGTH_LONG).show();
            }
        });


    }
    private void Init() {
        context = this;

        edtSearch = (EditText) findViewById(R.id.edt_Search);

        imgIconsearch = (ImageView) findViewById(R.id.img_icon_search);

        tvPodcastNumber = (TextView) findViewById(R.id.tv_podcast_number);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_banner_search);

    }

    private void SetUi() {
        /*searchPodcastAdapter = new SearchPodcastAdapter(context, episodeArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchPodcastAdapter);
        searchPodcastAdapter.notifyDataSetChanged();*/
    }
    private void getDataEpisodeSearch(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.SearchEpisode(keyword);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
               episodeArrayList = (ArrayList<Episode>) response.body();
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }


}
