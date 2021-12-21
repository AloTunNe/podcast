package com.example.podcast.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Adapter.SearchPodcastAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.PodcastController;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.example.podcast.Service.PodcastService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayEpisode extends AppCompatActivity implements MediaController.MediaPlayerControl{

    private PodcastService podcastService;
    private Intent playIntent;
    private boolean podcastBound = false;
    private PodcastController controller;
    private boolean paused = false;
    private boolean playbackPaused = false;
    Context context;

    ImageView imgButtonBack;

    ShapeableImageView simgBackgroundpisode;

    TextView tvEpisodeName;
    TextView tvAuthorName;
    TextView tvDiscription;

    Episode episode;
    ArrayList<Episode> episodeArrayList ;
    EpisodeOnMainBanner episodeOnMainBanner;

    PodcastPlaylistAdapter podcastPlaylistAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, PodcastService.class);
            bindService(playIntent, podcastConnection, BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);



        Init();
        getDataEpisodeById(episodeOnMainBanner.getIdEpisode());
        Toast.makeText(context, episodeArrayList.get(0).getNameEpisode(), Toast.LENGTH_LONG);
        imgButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(PlayEpisode.this, Main.class);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        Collections.sort(episodeArrayList, new Comparator<Episode>() {
            @Override
            public int compare(Episode lhs, Episode rhs) {
                return lhs.getNameEpisode().compareTo(rhs.getNameEpisode());
            }
        });
        setController();

    }
    private ServiceConnection podcastConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PodcastService.MusicBinder binder = (PodcastService.MusicBinder) service;
            podcastService = binder.getService();
            podcastService.setList(episodeArrayList);
            podcastBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            podcastBound = false;
        }
    };
    public void songPicked(View v) throws MalformedURLException {
        podcastService.setEp(Integer.parseInt(v.getTag().toString()));
        podcastService.playSong();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }
    @Override
    protected void onDestroy() {
        stopService(playIntent);
        podcastService = null;
        super.onDestroy();
    }
    private void setController() {
        controller = new PodcastController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playNext();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playPrev();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.rcv_Episode_Podcast));
        controller.setEnabled(true);
    }
    private void playNext() throws MalformedURLException {
        podcastService.playNext();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }
    private void playPrev() throws MalformedURLException {
        podcastService.playPrev();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    private void Init() {
        context = this;
        episodeOnMainBanner = (EpisodeOnMainBanner) getIntent().getParcelableExtra("Episode");
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
            Call<List<Episode>> callback = dataService.GetEpisodeById(keyword);
            callback.enqueue(new Callback<List<Episode>>() {
                @Override
                public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                    episodeArrayList = (ArrayList<Episode>) response.body();
                    for (int i = 0; i < episodeArrayList.size(); i++) {
                        Log.d("bbb: ", episodeArrayList.get(i).getNameEpisode());
                    }
                    SetUi(episodeArrayList.get(0));
                    /*getDataEpisodeByPlaylist(episodeArrayList.get(0).getIdPlaylistEpisode());*/
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
                for(int i = 0; i < episodeArrayList.size(); i++) {
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

    @Override
    public void start() {
        podcastService.go();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        podcastService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (podcastService != null && podcastBound && podcastService.isPlaying()) {
            return podcastService.getDur();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        podcastService.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if (podcastService != null && podcastBound) {
            return podcastService.isPlaying();
        } else {
            return false;
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }


    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
            setController();
            paused = false;
        }
    }
    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }
}
