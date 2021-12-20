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
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class SearchChanelAdapter extends RecyclerView.Adapter<SearchChanelAdapter.ViewHolder>{
    private List<Chanel> reList;
    Context context;
    public SearchChanelAdapter(Context context, List<Chanel> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChanelName;
        TextView tvChanelAuthorName;
        ShapeableImageView simgAuthorPic;
        public ViewHolder(View view) {
            super(view);
            tvChanelName = (TextView) view.findViewById(R.id.tv_channel_name);
            tvChanelAuthorName = (TextView) view.findViewById(R.id.tv_channel_author_name);
            simgAuthorPic = (ShapeableImageView) view.findViewById(R.id.simg_author_pic);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Chanel cn = reList.get(position);
       holder.tvChanelName.setText(cn.getChanelName());
       holder.tvChanelAuthorName.setText(cn.getChanelAuthor());
       Picasso.with(context).load(cn.getChanelAuthorPic()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.simgAuthorPic.setBackground(new BitmapDrawable(bitmap));
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
