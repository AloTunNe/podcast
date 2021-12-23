package com.example.podcast.Activity;

import static com.example.podcast.Activity.Main.mediaPlayer;

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
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayEpisode extends AppCompatActivity implements Playable, PodcastPlaylistAdapter.OnPodcastPlaylistClick {

    Context context;

    ImageView imgButtonBack;
    ImageButton ibPlay, ibPrev, ibNext, ibFastRewind, ibFastForward;
    ImageButton imgbtnLike;
    ShapeableImageView simgBackgroundpisode;

    TextView tvEpisodeName, tvTotalTime, tvTimeCurrent;
    TextView tvAuthorName;
    TextView tvDiscription;
    SeekBar seekBar;
    Episode episodeOnMainBanner;

    int like;

    PodcastPlaylistAdapter podcastPlaylistAdapter;
    ArrayList<Episode> episodeArrayList;

    RecyclerView recyclerView;

    int position = 0;
    boolean isPlaying = false;

    public NotificationManager notificationManager;
    //private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);
        try {
            Init();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("bbb: ", imgbtnLike.getBackground().toString());
        upDateViews("1", episodeOnMainBanner.getIdEpisode());
        getDataEpisodeById(episodeOnMainBanner.getIdEpisode());
        imgbtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like == 0) {
                    upDateLike("1", episodeOnMainBanner.getIdEpisode());
                    imgbtnLike.setImageResource(R.drawable.ic_likeon);
                    like = 1;
                } else {
                    upDateLike("-1", episodeOnMainBanner.getIdEpisode());
                    imgbtnLike.setImageResource(R.drawable.ic_likeoff);
                    like  = 0;
                }

            }
        });
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
            startService(new Intent(context, OnClearFromRecentService.class));
        }
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Main.isGlobalPlaying && mediaPlayer!=null) {
                    mediaPlayer.release();
                    mediaPlayer = null;}
                if (isPlaying) {
                    Main.isGlobalPlaying = true;
                    mediaPlayer.pause();
                    onTrackPause();
                }
                else {
                    mediaPlayer.start();
                    onTrackPlay();

                }
                setTotalTime();
                UpdateTime();
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrackNext();
                mediaPlayer.release();
                onTrackPlay();
            }
        });
        ibPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrackPrevious();
                onTrackPlay();
            }
        });
        ibFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeFastForward();

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


    }

    private void TimeFastForward()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");

                tvTimeCurrent.setText(timeFormat.format(mediaPlayer.getCurrentPosition() + 10000));
                seekBar.setProgress(mediaPlayer.getCurrentPosition() + 10000);
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                tvTimeCurrent.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
        }, 100);
    }


    private void setTotalTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        tvTotalTime.setText(timeFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }


    private void Init() throws MalformedURLException {
        context = this;

        like = 0;

        ibPlay = (ImageButton) findViewById(R.id.btn_play_pcast);
        episodeOnMainBanner = (Episode) getIntent().getParcelableExtra("Episode");
        imgButtonBack = (ImageView) findViewById(R.id.img_icon_back);
        imgbtnLike = (ImageButton) findViewById(R.id.imgBtn_Like);
        ibNext = (ImageButton) findViewById(R.id.btn_next);
        ibPrev = (ImageButton) findViewById(R.id.btn_prev);
        ibFastForward = (ImageButton) findViewById(R.id.btn_fastForward);
        ibFastRewind = (ImageButton) findViewById(R.id.btn_fastRewind);


        simgBackgroundpisode = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        tvEpisodeName = (TextView) findViewById(R.id.tv_Episode);
        tvAuthorName = (TextView) findViewById(R.id.tv_AuthorName);
        tvDiscription = (TextView) findViewById(R.id.tv_Discription);
        tvTotalTime = (TextView) findViewById(R.id.tv_totalTime);
        tvTimeCurrent = (TextView) findViewById(R.id.tv_currentTime);
        seekBar = (SeekBar) findViewById(R.id.skbar);
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
                    }
                    podcastPlaylistAdapter = new PodcastPlaylistAdapter(PlayEpisode.this, episodeArrayList, PlayEpisode.this);
                    podcastPlaylistAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(PlayEpisode.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(podcastPlaylistAdapter);
                }

                @Override
                public void onFailure(Call<List<Episode>> call, Throwable t) {

                }
            });
    }
    private void upDateLike(String likes, String episodeId) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.UpdateEpisodeLikes(likes, episodeId);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = (String) response.body();
                if (res.compareTo("Success") == 0) Toast.makeText(PlayEpisode.this, "Success", Toast.LENGTH_SHORT).show();
                else upDateLike(likes, episodeId);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void upDateViews(String views, String episodeId) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.UpdateEpisodeViews(views, episodeId);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = (String) response.body();
                if (res.compareTo("Success") == 0) Toast.makeText(PlayEpisode.this, "Success", Toast.LENGTH_SHORT).show();
                else upDateViews(views, episodeId);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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
                        mediaPlayer.pause();
                    } else {
                        onTrackPlay();
                        mediaPlayer.start();
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
        NotificationPlay.createNotification(context, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);

    }

    @Override
    public void onTrackPlay() {
        NotificationPlay.createNotification(context, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
        ibPlay.setImageResource(R.drawable.ic_pause);
        isPlaying = true;
    }

    @Override
    public void onTrackPause() {
        NotificationPlay.createNotification(context, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
        ibPlay.setImageResource(R.drawable.ic_play);
        isPlaying = false;

    }

    @Override
    public void onTrackNext() {
        position++;
        NotificationPlay.createNotification(context, episodeOnMainBanner, R.drawable.ic_podcast, position, episodeArrayList.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Main.isGlobalPlaying = false;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }*/

        //unregisterReceiver(broadcastReceiver);
        //mediaPlayer.release();
        //mediaPlayer = null;
    }

    @Override
    public void onPodcastPlaylistClick(int pos) {
        Main.isGlobalPlaying = false;
        mediaPlayer.release();
        mediaPlayer = null;
        Episode ep = episodeArrayList.get(pos);
        Intent iNewActivity = new Intent(PlayEpisode.this, PlayEpisode.class);
        iNewActivity.putExtra("Episode", ep);
        startActivity(iNewActivity);
    }
}
