package com.example.podcast.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
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

public class PlayEpisode extends AppCompatActivity {

    Context context;

    ShapeableImageView simgTopBackgroundEpisode;

    TextView tvTopEpisodeName;
    TextView tvTopAuthorName;
    TextView tvTopDiscriotion;

    ImageView imgButtonBack;

    EpisodeOnMainBanner episodeOnMainBanner;
    Episode episode;

    ArrayList<Episode> episodeArrayList;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    PodcastPlaylistAdapter podcastPlaylistAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);

        Init();
        getDataEpisodeId(episodeOnMainBanner.getIdEpisode());
        /*getDataEpisodePlaylist(episode.getIdPlaylistEpisode());*/
        setUI();
    }

    private void setUI() {
        tvTopAuthorName.setText(episodeOnMainBanner.getAuthor());
        tvTopEpisodeName.setText(episodeOnMainBanner.getName());
        Picasso.with(context).load(episodeOnMainBanner.getAvatar()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
               recyclerView.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        /*podcastPlaylistAdapter = new PodcastPlaylistAdapter(context, episodeArrayList);
         recyclerView.setLayoutManager(linearLayoutManager);
        podcastPlaylistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(podcastPlaylistAdapter);*/
    }

    private void Init() {
        context = this;
        episodeOnMainBanner = (EpisodeOnMainBanner) getIntent().getParcelableExtra("Episode");

        simgTopBackgroundEpisode = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        tvTopEpisodeName = (TextView) findViewById(R.id.tv_Episode);
        tvTopAuthorName = (TextView) findViewById(R.id.tv_AuthorName);
        tvTopDiscriotion = (TextView)findViewById(R.id.tv_Discription);

        imgButtonBack = (ImageView) findViewById(R.id.img_Podcast_Back);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Podcast);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        episodeArrayList = new ArrayList<>();

    }
    private void getDataEpisodeId(String Id) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.GetEpisodeById(Id);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                ArrayList<Episode> episodes = (ArrayList<Episode>) response.body();
                for(int i = 0; i<episodes.size(); i++) {
                    episode = new Episode(episodes.get(i).getIdEpisode(), episodes.get(i).getChanelId(), episodes.get(i).getNameEpisode(),
                            episodes.get(i).getDiscriptionEpisode(), episodes.get(i).getLinkEpisode(), episodes.get(i).getPicEpisode(), episodes.get(i).getLikesEpisode(),
                            episodes.get(i).getViewsEpisode(), episodes.get(i).getIdPlaylistEpisode(), episodes.get(i).getAuthorEpisode());
                    Log.d("bbb: ", episode.getIdPlaylistEpisode());
                }
            }
            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
    private void getDataEpisodePlaylist(String Id) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.GetEpisodePlaylist(Id);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                ArrayList<Episode> episodes = (ArrayList<Episode>) response.body();
                for(int i = 0; i<episodes.size(); i++) {
                    episodeArrayList.add(new Episode(episodes.get(i).getIdEpisode(), episodes.get(i).getChanelId(), episodes.get(i).getNameEpisode(),
                            episodes.get(i).getDiscriptionEpisode(), episodes.get(i).getLinkEpisode(), episodes.get(i).getPicEpisode(), episodes.get(i).getLikesEpisode(),
                            episodes.get(i).getViewsEpisode(), episodes.get(i).getIdPlaylistEpisode(), episodes.get(i).getAuthorEpisode()));
                }
            }
            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
}
