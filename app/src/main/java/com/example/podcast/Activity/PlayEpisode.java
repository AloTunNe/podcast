package com.example.podcast.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Adapter.SearchPodcastAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayEpisode extends AppCompatActivity {
    Context context;

    ImageView imgButtonBack;

    ShapeableImageView simgBackgroundpisode;

    TextView tvEpisodeName;
    TextView tvAuthorName;
    TextView tvDiscription;

    Episode episodeOnMainBanner;

    PodcastPlaylistAdapter podcastPlaylistAdapter;
    ArrayList<Episode> episodeArrayList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);




        Init();
        getDataEpisodeById(episodeOnMainBanner.getIdEpisode());



        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

    }
    private void Init() {
        context = this;
        episodeOnMainBanner = (Episode) getIntent().getParcelableExtra("Episode");
        imgButtonBack = (ImageView) findViewById(R.id.img_icon_back);

        simgBackgroundpisode = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        tvEpisodeName = (TextView) findViewById(R.id.tv_Episode);
        tvAuthorName = (TextView) findViewById(R.id.tv_AuthorName);
        tvDiscription = (TextView) findViewById(R.id.tv_Discription);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Podcast);

    }

    private void SetUi(Episode ep) {
        tvEpisodeName.setText(ep.getNameEpisode());
        tvAuthorName.setText(ep.getAuthorEpisode());
        tvDiscription.setText(ep.getDiscriptionEpisode());
        Picasso.with(context).load(ep.getPicEpisode()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                simgBackgroundpisode.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
    private void getDataEpisodeById(String keyword) {
                DataService dataService = APIService.getService();
                Call<List<Episode>> callback = dataService.SearchEpisodeWithId(keyword);
                callback.enqueue(new Callback<List<Episode>>() {
                    @Override
                    public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                        episodeArrayList = (ArrayList<Episode>) response.body();
                        getDataEpisodeByPlaylist(episodeArrayList.get(0).getIdPlaylistEpisode());
                        SetUi(episodeArrayList.get(0));

                    }

                    @Override
                    public void onFailure(Call<List<Episode>> call, Throwable t) {

                    }
                });

    }

    private void getDataEpisodeByPlaylist(String keyword) {
            DataService dataService = APIService.getService();
            Call<List<Episode>> callback = dataService.SearchEpisodeWithIdPlaylist(keyword);
            callback.enqueue(new Callback<List<Episode>>() {
                @Override
                public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                    episodeArrayList = (ArrayList<Episode>) response.body();
                    for (int i = 0; i < episodeArrayList.size(); i++) {
                        Log.d("bbb: ", episodeArrayList.get(i).getNameEpisode());
                    }
                    podcastPlaylistAdapter = new PodcastPlaylistAdapter(PlayEpisode.this, episodeArrayList);
                    podcastPlaylistAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(PlayEpisode.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(podcastPlaylistAdapter);
                }

                @Override
                public void onFailure(Call<List<Episode>> call, Throwable t) {

                }
            });
        }



}
