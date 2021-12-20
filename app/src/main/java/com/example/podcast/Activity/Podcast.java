package com.example.podcast.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.PodcastPlaylistAdapter;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Podcast extends AppCompatActivity {
    ImageButton btnPlayPodcast,  btnPrev, btnNext;

    TextView tvTitlePodcast, tvTotalTime, tvCurrentTime, tvAuthor, tvdiscription;
    ImageView imgPodcastBack;

    RecyclerView recyclerView;

    ShapeableImageView simgPodcastLayoutTopBackground;
    PodcastPlaylistAdapter podcastPlaylistAdapter;

    Context context;

    ArrayList<Episode> episodeArrayList;
    LinearLayoutManager linearLayoutManager;

    SeekBar skbar;
    Episode episode;
    EpisodeOnMainBanner episodeOnMainBanner;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);

        Init();
        Log.d("bbb: ", episodeOnMainBanner.getIdEpisode());

        getDataEpisodeId(episodeOnMainBanner.getIdEpisode());
        Log.d("bbb: ", episode.getIdEpisode());
        Log.d("bbb: ", episode.getAuthorEpisode());



       // getDataEpisodePlaylist(episode.getIdPlaylistEpisode());

        //podcastPlaylistAdapter = new PodcastPlaylistAdapter(context, episodeArrayList);
        //recyclerView.setAdapter(podcastPlaylistAdapter);



      //  Log.d("bbb: ", episodeArrayList.get(0).getAuthorEpisode());

        //SetUI();
        /*try {
            InitEpisode(episode.getLinkEpisode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SetTotalTime();
        UpdateTime();

        imgPodcastBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Podcast.this, Main.class);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        btnPlayPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTotalTime();
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnPlayPodcast.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    mediaPlayer.start();
                    btnPlayPodcast.setImageResource(R.drawable.ic_pause);

                }

                UpdateTime();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > listEpisode.size() - 1)
                {
                    position = 0;
                }
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                try {
                    InitEpisode(episode.getLinkEpisode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                btnPlayPodcast.setImageResource(R.drawable.ic_pause);
                SetTotalTime();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0)
                {
                    position = listEpisode.size() - 1;
                }
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                try {
                    InitEpisode(episode.getLinkEpisode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                btnPlayPodcast.setImageResource(R.drawable.ic_pause);
                SetTotalTime();
            }

        });

        skbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skbar.getProgress());
            }
        });*/
    }


    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                tvCurrentTime.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                skbar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);

                if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration())
                {
                    btnPlayPodcast.setImageResource(R.drawable.ic_play);
                    position++;
                    //InitEpisode();
                    mediaPlayer.start();
                    btnPlayPodcast.setImageResource(R.drawable.ic_pause);
                }
                if (mediaPlayer.isPlaying())
                {
                    btnPlayPodcast.setImageResource(R.drawable.ic_pause);
                }
                else
                {
                    btnPlayPodcast.setImageResource(R.drawable.ic_play);
                }

            }
        }, 1000);
    }

    private void SetTotalTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        tvTotalTime.setText(timeFormat.format(mediaPlayer.getDuration()));
        skbar.setMax(mediaPlayer.getDuration());

    }

/*<<<<<<< HEAD
    private void InitEpisode(String link) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.start();
            }
        });
        mediaPlayer.setDataSource(link);
        mediaPlayer.prepare();
=======
    private void InitEpisode() {
        mediaPlayer = MediaPlayer.create(Podcast.this, listEpisode.get(position).getFile());
        tvTitlePodcast.setText(listEpisode.get(position).getTitleEpisode());
    }

    private void AddEpisode() {
        listEpisode = new ArrayList<>();
        listEpisode.add(new Episode("NASCast | 'Reviewer' ăn tiền, không có chuyên môn ", R.raw.nascast));
        listEpisode.add(new Episode("Wingman | Giao tiếp hướng cho người hướng nội", R.raw.wingman2));
        listEpisode.add(new Episode("Tự Tình Lúc 0h | Còn thương người cũ", R.raw.tutinhluc0h));
        listEpisode.add(new Episode("3D Easy | Trường học hay tự học", R.raw.truonghochaytuhoc));

>>>>>>> 9299bec8b3878edf16a7483b667cbbe768f313d3
    }*/

    private void Init() {
        btnPlayPodcast = (ImageButton) findViewById(R.id.btn_play_pcast);
        btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        btnNext = (ImageButton) findViewById(R.id.btn_next);

        tvTitlePodcast = (TextView) findViewById(R.id.tv_Episode);
        tvTotalTime = (TextView) findViewById(R.id.tv_totalTime);
        tvCurrentTime = (TextView) findViewById(R.id.tv_currentTime);
        tvAuthor = (TextView) findViewById(R.id.tv_AuthorName);
        tvdiscription = (TextView) findViewById(R.id.tv_Discription);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_Episode_Podcast);

        episodeArrayList = new ArrayList<>();

        skbar = (SeekBar) findViewById(R.id.skbar);

        imgPodcastBack = (ImageView) findViewById(R.id.img_Podcast_Back);

        simgPodcastLayoutTopBackground = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        episodeOnMainBanner = (EpisodeOnMainBanner) getIntent().getParcelableExtra("Episode");

        context = this;
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);

    }
    private void SetUI() {

        tvAuthor.setText(episodeArrayList.get(0).getAuthorEpisode());
        tvdiscription.setText("Discription: " + episodeArrayList.get(0).getDiscriptionEpisode());

        Picasso.with(this).load(episodeOnMainBanner.getAvatar()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                simgPodcastLayoutTopBackground.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
    private void getDataEpisodeId(String IdEpisode) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.GetEpisodeById(IdEpisode);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                ArrayList<Episode> episodes = (ArrayList<Episode>) response.body();
                Log.d("bbb: ", String.valueOf(episodes.size()));
                /*for(int i = 0; i < episodes.size(); i++) {*/
                int i = 0;
                   episode = new Episode(episodes.get(i).getIdEpisode(), episodes.get(i).getChanelId(), episodes.get(i).getNameEpisode(), episodes.get(i).getDiscriptionEpisode(), episodes.get(i).getLinkEpisode(), episodes.get(i).getPicEpisode(), episodes.get(i).getLikesEpisode(), episodes.get(i).getViewsEpisode(), episodes.get(i).getIdPlaylistEpisode(), episodes.get(i).getAuthorEpisode());

               // }
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
    private void getDataEpisodePlaylist(String IdPlaylist) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.GetEpisodePlaylist(IdPlaylist);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                ArrayList<Episode> episodes = (ArrayList<Episode>) response.body();
                Log.d("bbb: ", String.valueOf(episodes.size()));
                for(int i = 0; i <episodes.size(); i++) {
                    episodeArrayList.add(new Episode(episodes.get(i).getIdEpisode(), episodes.get(i).getChanelId(), episodes.get(i).getNameEpisode(), episodes.get(i).getDiscriptionEpisode(), episodes.get(i).getLinkEpisode(), episodes.get(i).getPicEpisode(), episodes.get(i).getLikesEpisode(), episodes.get(i).getViewsEpisode(), episodes.get(i).getIdPlaylistEpisode(), episodes.get(i).getAuthorEpisode()));
                    Log.d("bbb: ", episodeArrayList.get(i).getIdEpisode());
                }
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
}
