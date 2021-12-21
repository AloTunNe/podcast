package com.example.podcast.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.podcast.Activity.Main;
import com.example.podcast.Model.Episode;
import com.example.podcast.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class PodcastService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer player;
    private ArrayList<Episode> episodeArrayList;
    private int epPos;
    private final IBinder podcastBind = new MusicBinder();
    private String songTitle = "";
    private static final int NOTIFY_ID = 1;
    private boolean shuffle = false;
    private Random rand;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return podcastBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        epPos = 0;
        // Khởi tạo một bộ phát đa phương tiện mới.
        player = new MediaPlayer();
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
    }

    public void setList(ArrayList<Episode> theSongs) {
        episodeArrayList = theSongs;
    }

    public class MusicBinder extends Binder {
        public PodcastService getService() {
            return PodcastService.this;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            try {
                playNext();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Intent notificationIntent = new Intent(this, Main.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification.Builder nBuilder = new Notification.Builder(this);
        nBuilder.setContentIntent(pendingIntent)
                .setTicker(songTitle)
                .setSmallIcon(R.drawable.ic_play)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification notif = nBuilder.getNotification();
        startForeground(NOTIFY_ID, notif);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }
    public void playSong() throws MalformedURLException {
        player.reset();
        Episode playSong = episodeArrayList.get(epPos);
        songTitle = playSong.getNameEpisode();
        //Long currentSong = playSong.get
        URL trackURL = new URL(playSong.getLinkEpisode());
        //Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentSong);
        try {
            player.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            player.setDataSource(String.valueOf(trackURL));
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error starting data source", e);
        }
        player.prepareAsync();
    }
    public void setEp(int epIndex) {
        epPos = epIndex;
    }
    public int getEpPos() {
        return player.getCurrentPosition();
    }
    public int getDur() {
        return player.getDuration();
    }
    public boolean isPlaying() {
        return player.isPlaying();
    }
    public void pausePlayer() {
        player.pause();
    }
    public void seek(int pos) {
        player.seekTo(pos);
    }
    public void go() {
        player.start();
    }
    public void playPrev() throws MalformedURLException {
        epPos--;
        if (epPos < 0) epPos = episodeArrayList.size() - 1;
        playSong();
    }
    public void playNext() throws MalformedURLException {
        if (shuffle) {
            int newSongPos = epPos;
            while (newSongPos == epPos) {
                newSongPos = rand.nextInt(episodeArrayList.size());
            }
            epPos = newSongPos;
        } else {
            epPos++;
            if (epPos >= episodeArrayList.size()) epPos = 0;
        }
        playSong();
    }
    @Override
    public void onDestroy() {
        stopForeground(true);
    }
    public void setShuffle() {
        if (shuffle) shuffle = false;
        else shuffle = true;
    }
}
