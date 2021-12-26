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

import com.example.podcast.Model.Playlist;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.util.List;

public class SearchPlaylistAdapter extends RecyclerView.Adapter<SearchPlaylistAdapter.ViewHolder>{
    private final List<Playlist> reList;
    Context context;
    private final OnPlaylistSearchClick mOnPlaylistSearchClick;
    public SearchPlaylistAdapter(Context context, List<Playlist> reList, OnPlaylistSearchClick onPlaylistSearchClick) {
        this.context = context;
        this.reList = reList;
        this.mOnPlaylistSearchClick = onPlaylistSearchClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPlaylistName;
        TextView tvPlaylistChanelName;
        ShapeableImageView simPlaylistBackground;
        OnPlaylistSearchClick onPlaylistSearchClick;
        public ViewHolder(View view, OnPlaylistSearchClick onPlaylistSearchClick) {
            super(view);
            tvPlaylistName = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Name);
            tvPlaylistChanelName = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Chanel);
            simPlaylistBackground = (ShapeableImageView) view.findViewById(R.id.imgPlaylistBackgroundBanner);

            this.onPlaylistSearchClick = onPlaylistSearchClick;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPlaylistSearchClick.onPlaylistSearchClick(getAdapterPosition());

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_main_banner_layout, parent, false);
        return new ViewHolder(view, mOnPlaylistSearchClick);
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

    public interface OnPlaylistSearchClick{
        void onPlaylistSearchClick(int position);
    }

}
