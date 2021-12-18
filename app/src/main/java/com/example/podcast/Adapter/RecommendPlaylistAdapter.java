package com.example.podcast.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class RecommendPlaylistAdapter extends RecyclerView.Adapter<RecommendPlaylistAdapter.ViewHolder>{
    private List<PlaylistOnMainBanner> reList;
    private Context context;
    public RecommendPlaylistAdapter(Context context, List<PlaylistOnMainBanner> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaylistMainBannerName;
        TextView tvPlaylistMainBannerChanel;
        RelativeLayout rcvPlaylistMainBanner;
        public ViewHolder(View view) {
            super(view);
            tvPlaylistMainBannerName = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Name);
            tvPlaylistMainBannerChanel = (TextView) view.findViewById(R.id.tv_Playlist_Main_Banner_Chanel);
            rcvPlaylistMainBanner = (RelativeLayout) view.findViewById(R.id.rcv_Playlist_Main_Banner);
            // Define click listener for the ViewHolder's View

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
        PlaylistOnMainBanner pl = reList.get(position);
        holder.tvPlaylistMainBannerName.setText(pl.getName());
        holder.tvPlaylistMainBannerChanel.setText(pl.getChanel());
        Picasso.with(context).load(pl.getPicture()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.rcvPlaylistMainBanner.setBackground(new BitmapDrawable(bitmap));
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