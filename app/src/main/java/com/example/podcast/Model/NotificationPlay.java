package com.example.podcast.Model;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.podcast.R;
import com.example.podcast.Service.NotificationActionService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationPlay {

    public static final String CHANNEL_ID = "channel";

    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";

    public static Notification notification;

    public static void createNotification(Context context, Episode ep, int playButton, int pos, int size) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Bitmap icon = getBitmapFromURL(ep.getPicEpisode());

        PendingIntent pendingIntentPrevious;
        int drw_previous;
        if (pos == 0) {
            pendingIntentPrevious = null;
            drw_previous = 0;
        }
        else {
            Intent intentPrevious = new Intent(context, NotificationActionService.class).setAction(ACTION_PREVIOUS);
            pendingIntentPrevious = PendingIntent.getBroadcast(context, 0, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_previous = R.drawable.ic_baseline_skip_previous_24;
        }
        Intent intentPlay = new Intent(context, NotificationActionService.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentNext;
        int drw_next;
        if (pos == size) {
            pendingIntentNext = null;
            drw_next = 0;
        }
        else {
            Intent intentNext = new Intent(context, NotificationActionService.class).setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_next = R.drawable.ic_baseline_skip_next_24;
        }

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_podcast)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle(ep.getNameEpisode())
                .setContentText(ep.getAuthorEpisode())
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)
                .setShowWhen(true)
                .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", pendingIntentPrevious)
                .addAction(R.drawable.ic_play, "Play", pendingIntentPlay)
                .addAction(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        notificationManagerCompat.notify(123, notification);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
