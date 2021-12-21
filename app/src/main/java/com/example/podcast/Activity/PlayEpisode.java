package com.example.podcast.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.NotificationPlay;
import com.example.podcast.Playable;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.example.podcast.Service.OnClearFromRecentService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayEpisode extends AppCompatActivity implements Playable {

    Context context;

    ImageView imgButtonBack;
    ImageButton ibPlay;

    ShapeableImageView simgBackgroundpisode;

    TextView tvEpisodeName;
    TextView tvAuthorName;
    TextView tvDiscription;
    ArrayList<Episode> episodeArrayList ;
    Episode episodeOnMainBanner;

    PodcastPlaylistAdapter podcastPlaylistAdapter;

    RecyclerView recyclerView;

    int position = 0;
    boolean isPlaying = false;

    public NotificationManager notificationManager;
    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);


        try {
            Init();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //getDataEpisodeById(episodeOnMainBanner.getIdEpisode());

        //Toast.makeText(context, episodeArrayList.get(0).getNameEpisode(), Toast.LENGTH_LONG);

        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACK_TRACK"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    onTrackPause();
                }
                else {
                    mediaPlayer.start();
                    onTrackPlay();

                }
            }
        });

    }


    private void Init() throws MalformedURLException {
        context = this;
        ibPlay = (ImageButton) findViewById(R.id.btn_play_pcast);
        episodeOnMainBanner = (Episode) getIntent().getParcelableExtra("Episode");
        imgButtonBack = (ImageView) findViewById(R.id.img_icon_back);

        simgBackgroundpisode = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        tvEpisodeName = (TextView) findViewById(R.id.tv_Episode);
        tvAuthorName = (TextView) findViewById(R.id.tv_AuthorName);
        tvDiscription = (TextView) findViewById(R.id.tv_Discription);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Podcast);
        episodeArrayList = new ArrayList<>();
        episodeArrayList.add(episodeOnMainBanner);
        URL url = new URL(episodeOnMainBanner.getLinkEpisode());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(String.valueOf(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                Call<List<Episode>> callback = dataService.GetEpisodeById(keyword);
                callback.enqueue(new Callback<List<Episode>>() {
                    @Override
                    public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                        episodeArrayList = (ArrayList<Episode>) response.body();
                        for (int i = 0; i < episodeArrayList.size(); i++) {
                            Log.d("bbb: ", episodeArrayList.get(i).getNameEpisode());
                        }
                        SetUi(episodeArrayList.get(0));
                        getDataEpisodeByPlaylist(episodeArrayList.get(0).getIdPlaylistEpisode());
                    }

                    @Override
                    public void onFailure(Call<List<Episode>> call, Throwable t) {

                    }
                });

    }

    private void getDataEpisodeByPlaylist(String keyword) {
            DataService dataService = APIService.getService();
            Call<List<Episode>> callback = dataService.GetEpisodePlaylist(keyword);
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
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(NotificationPlay.CHANNEL_ID, "Our project", NotificationManager.IMPORTANCE_HIGH);

        notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionName");

            switch (action) {
                case NotificationPlay.ACTION_PREVIOUS:
                    onTrackPrevious();
                    break;
                case NotificationPlay.ACTION_PLAY:
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case NotificationPlay.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {
        position--;
        NotificationPlay.createNotification(this, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);

    }

    @Override
    public void onTrackPlay() {
        NotificationPlay.createNotification(this, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
        ibPlay.setImageResource(R.drawable.ic_pause);
        isPlaying = true;
    }

    @Override
    public void onTrackPause() {
        NotificationPlay.createNotification(this, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
        ibPlay.setImageResource(R.drawable.ic_play);
        isPlaying = false;

    }

    @Override
    public void onTrackNext() {
        position++;
        NotificationPlay.createNotification(this, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
