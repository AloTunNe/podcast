package com.example.podcast.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Podcast extends AppCompatActivity {
    ImageButton btnPlayPodcast,  btnPrev, btnNext;

    TextView tvTitlePodcast, tvTotalTime, tvCurrentTime;
    ImageView imgPodcastBack;

    ShapeableImageView simgPodcastLayoutTopBackground;

    SeekBar skbar;
    ArrayList<Episode> listEpisode;
    EpisodeOnMainBanner episodeOnMainBanner;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);

        Init();
        SetUI();
        AddEpisode();
        InitEpisode();
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
                InitEpisode();
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
                InitEpisode();
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
        });
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
                    InitEpisode();
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

    }

    private void Init() {
        btnPlayPodcast = (ImageButton) findViewById(R.id.btn_play_pcast);
        btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        tvTitlePodcast = (TextView) findViewById(R.id.tv_Episode);
        tvTotalTime = (TextView) findViewById(R.id.tv_totalTime);
        tvCurrentTime = (TextView) findViewById(R.id.tv_currentTime);
        skbar = (SeekBar) findViewById(R.id.skbar);
        imgPodcastBack = (ImageView) findViewById(R.id.img_Podcast_Back);

        simgPodcastLayoutTopBackground = (ShapeableImageView) findViewById(R.id.simgTopBackground);

        episodeOnMainBanner = (EpisodeOnMainBanner) getIntent().getParcelableExtra("Episode");
    }
    private void SetUI() {
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
}
