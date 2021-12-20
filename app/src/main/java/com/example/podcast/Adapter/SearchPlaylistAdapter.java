package com.example.podcast.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Model.Chanel;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.Playlist;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaylistAdapter extends RecyclerView.Adapter<SearchPlaylistAdapter.ViewHolder>{
    private List<Playlist> reList;
    Context context;
    public SearchPlaylistAdapter(Context context, List<Playlist> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaylistName;
        TextView tvPlaylistChanelName;
        ShapeableImageView simPlaylistBackground;
        public ViewHolder(View view) {
            super(view);
            tvPlaylistName = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Name);
            tvPlaylistChanelName = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Chanel);
            simPlaylistBackground = (ShapeableImageView) view.findViewById(R.id.imgPlaylistBackgroundBanner);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_main_banner_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = reList.get(position);
        holder.tvPlaylistName.setText(playlist.getNamePlaylist());
        holder.tvPlaylistChanelName.setText(playlist.getChanelPlaylist());
        Picasso.with(context).load(playlist.getPicPlaylist()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.simPlaylistBackground.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return (reList != null ? reList.size():0);
    }

}
