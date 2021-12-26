package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.RecommendPlaylistAdapter;
import com.example.podcast.Adapter.SearchPlaylistAdapter;
import com.example.podcast.Adapter.SearchPodcastAdapter;
import com.example.podcast.Model.Chanel;
import com.example.podcast.Model.Playlist;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelActivity extends AppCompatActivity implements SearchPlaylistAdapter.OnPlaylistSearchClick {

    ImageView imgIconBack;

    ShapeableImageView simgChanelBackground;
    RecyclerView recyclerView;

    TextView tvChannelName;
    TextView tvPodcastNumber;
    TextView tvFollows;
    TextView tvDiscription;
    TextView tvPlaylist;
    TextView tvPodcast;

    Context context;



    Chanel chanel;
    ArrayList<Chanel> chanelArrayList;
    ArrayList<Playlist> playlistArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chanel);

        Init();
        SearchChanelById(chanel.getChanelId( ));
        /*Log.d("bbb: ", chanel.getChanelDiscription());

        SetUi();*/
        imgIconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

    }

    private void SetUi() {

        tvChannelName.setText(chanel.getChanelName());
        tvDiscription.setText(chanel.getChanelDiscription());
        tvFollows.setText(chanel.getChanelFollows());
        tvPodcastNumber.setText("Podcasts: ");
        Picasso.with(ChannelActivity.this).load(chanel.getChanelBackground()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                simgChanelBackground.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void Init() {
        chanel = (Chanel) getIntent().getParcelableExtra("Chanel");

        context = ChannelActivity.this;

        playlistArrayList = new ArrayList<>();
        chanelArrayList = new ArrayList<>();

        tvChannelName = (TextView) findViewById(R.id.tv_channel_name);
        tvPodcastNumber = (TextView) findViewById(R.id.tv_pcastnuber);
        tvFollows = (TextView) findViewById(R.id.tv_follow);
        tvDiscription = (TextView) findViewById(R.id.tv_descriptionchanel);
        tvPlaylist = (TextView) findViewById(R.id.tv_playlist);
        tvPodcast = (TextView) findViewById(R.id.tv_podcast);

        imgIconBack = (ImageView) findViewById(R.id.img_icon_back);

        simgChanelBackground = (ShapeableImageView) findViewById(R.id.simg_channel_background);

        recyclerView = (RecyclerView)  findViewById(R.id.rcv_playlistbanner);

    }
    private void SearchChanelById(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Chanel>> callback = dataService.SearchChanelDataById(keyword);
        callback.enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {
                chanelArrayList = (ArrayList<Chanel>) response.body();
                try {
                    chanel = new Chanel(chanelArrayList.get(0));
                    SetUi();
                    SearchPlaylistByChanelId(chanel.getChanelId());
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    SearchChanelById(keyword);
                }

            }

            @Override
            public void onFailure(Call<List<Chanel>> call, Throwable t) {

            }
        });
    }
    private void SearchPlaylistByChanelId(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.SearchPlaylistWithChanelId(keyword);
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlists = (ArrayList<Playlist>) response.body();
                try {
                    if (playlists.size() != 0) {
                        playlistArrayList.clear();
                        for(int i = 0; i<playlists.size(); i++) {
                            playlistArrayList.add(playlists.get(i));
                        }
                        SearchPlaylistAdapter searchPlaylistAdapter = new SearchPlaylistAdapter(context, playlistArrayList, (SearchPlaylistAdapter.OnPlaylistSearchClick) context);
                        searchPlaylistAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChannelActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(searchPlaylistAdapter);
                    }

                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    SearchPlaylistByChanelId(keyword);
                }

            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }
    public void onPlaylistSearchClick(int position) {
        Playlist playlist = playlistArrayList.get(position);
        Intent iNewActivity = new Intent(ChannelActivity.this, PlaylistActivity.class);
        iNewActivity.putExtra("Playlist", playlist);
        startActivity(iNewActivity);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
