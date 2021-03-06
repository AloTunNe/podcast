package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.Playlist;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity implements PodcastPlaylistAdapter.OnPodcastPlaylistClick {
    ShapeableImageView simgPlaylistBackground;

    TextView tvPlaylistName;
    TextView tvDiscription;
    TextView tvPodcastNuber;

    ImageView imgButtonBack;

    RecyclerView recyclerView;
    PodcastPlaylistAdapter podcastPlaylistAdapter;

    Playlist playlist;
    ArrayList<Episode> episodeArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_layout);

        Init();
        SetUI();
        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
    }

    private void Init() {
        playlist = (Playlist) getIntent().getParcelableExtra("Playlist");

        episodeArrayList = new ArrayList<>();

        simgPlaylistBackground = (ShapeableImageView) findViewById(R.id.simg_playlist_background);

        tvPlaylistName = (TextView) findViewById(R.id.tv_playlist_name);
        tvDiscription = (TextView)  findViewById(R.id.tv_playlist_of_channel);
        tvPodcastNuber = (TextView) findViewById(R.id.tv_pcastnuber);

        imgButtonBack = (ImageView) findViewById(R.id.img_icon_back);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_playlistbanner);


    }
    private void SetUI() {
        episodeArrayList = new ArrayList<>();

        Picasso.with(PlaylistActivity.this).load(playlist.getPicPlaylist()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                simgPlaylistBackground.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        tvPlaylistName.setText(playlist.getNamePlaylist());
        getEpisodeByIdPlaylist(playlist.getIdPlaylist());
    }
    private void getEpisodeByIdPlaylist(String id) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.SearchEpisodeWithIdPlaylist(id);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                ArrayList<Episode> episodes = (ArrayList<Episode>) response.body();
                try {
                    episodeArrayList.clear();
                    if (episodes.size() != 0) {
                        for (int i = 0; i < episodes.size(); i++) {
                            episodeArrayList.add(episodes.get(i));
                        }
                        podcastPlaylistAdapter = new PodcastPlaylistAdapter(PlaylistActivity.this, episodeArrayList, PlaylistActivity.this);
                        podcastPlaylistAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(PlaylistActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(podcastPlaylistAdapter);
                        tvPodcastNuber.setText("Podcasts: " + episodeArrayList.size());
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getEpisodeByIdPlaylist(id);
                }



            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onPodcastPlaylistClick(int pos) {
        Episode episode = episodeArrayList.get(pos);
        Intent iNewActivity = new Intent(PlaylistActivity.this, PlayEpisode.class);
        iNewActivity.putExtra("Episode", episode);
        startActivity(iNewActivity);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
