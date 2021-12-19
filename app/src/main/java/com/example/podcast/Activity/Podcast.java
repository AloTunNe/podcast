package com.example.podcast.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.podcast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Podcast extends AppCompatActivity {
    ImageButton btnPlayPodcast,  btnPrev, btnNext;
    TextView tvTitlePodcast, tvTotalTime, tvCurrentTime;
    SeekBar skbar;
    ArrayList<Episode> listEpisode;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast);

        Init();
        AddEpisode();
        InitEpisode();
        SetTotalTime();
        UpdateTime();
        btnPlayPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                SetTotalTime();
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
                handler.postDelayed(this, 500);
            }
        }, 100);
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
        listEpisode.add(new Episode("NASCast", R.raw.nascast));
        listEpisode.add(new Episode("Happy", R.raw.happy));
        listEpisode.add(new Episode("Mắt nai", R.raw.mat_nai_autotune));
        listEpisode.add(new Episode("Tell ur mom", R.raw.tell_ur_mom));

    }

    private void Init() {
        btnPlayPodcast = (ImageButton) findViewById(R.id.btn_play_pcast);
        btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        tvTitlePodcast = (TextView) findViewById(R.id.tv_Episode);
        tvTotalTime = (TextView) findViewById(R.id.tv_totalTime);
        tvCurrentTime = (TextView) findViewById(R.id.tv_currentTime);
        skbar = (SeekBar) findViewById(R.id.skbar);
    }
}
