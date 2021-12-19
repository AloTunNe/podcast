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

import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.PlaylistOnMainBanner;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class RecommendChanelAdapter extends RecyclerView.Adapter<RecommendChanelAdapter.ViewHolder>{
    private List<ChanelOnMainBanner> reList;
    private Context context;
    private OnReChannelClick mOnReChannelClick;
    public RecommendChanelAdapter(Context context, List<ChanelOnMainBanner> reList, OnReChannelClick onReChannelClick) {
        this.context = context;
        this.reList = reList;
        this.mOnReChannelClick = onReChannelClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvChanelMainBannerName;
        TextView tvChanelMainBannerUser;
        ShapeableImageView saiChanelMainBannerUserAvatar;
        ShapeableImageView saiChanelMainBannerBackground;
        RelativeLayout rcvChanelMainBanner;
        OnReChannelClick onReChannelClick;
        public ViewHolder(View view, OnReChannelClick onReChannelClick) {
            super(view);
            tvChanelMainBannerName = (TextView) view.findViewById(R.id.tv_Chanel_Main_Banner_Name);
            tvChanelMainBannerUser = (TextView) view.findViewById(R.id.tv_Chanel_Main_Banner_Author);
            saiChanelMainBannerUserAvatar = (ShapeableImageView) view.findViewById(R.id.imb_Chanel_Main_Banner_Avatar);
            saiChanelMainBannerBackground = (ShapeableImageView) view.findViewById(R.id.imgChanelBackgroundBanner);
            rcvChanelMainBanner = (RelativeLayout) view.findViewById(R.id.rcv_Chanel_Main_Banner);
            // Define click listener for the ViewHolder's View
            this.onReChannelClick = onReChannelClick;
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onReChannelClick.onReChannelClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chanel_main_banner_layout, parent, false);
        return new ViewHolder(view, mOnReChannelClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChanelOnMainBanner cn = reList.get(position);
        holder.tvChanelMainBannerName.setText(cn.getChanelName());
        holder.tvChanelMainBannerUser.setText(cn.getUserName());
        Picasso.with(context).load(cn.getUserAvatar()).into(holder.saiChanelMainBannerUserAvatar);
        Picasso.with(context).load(cn.getPicture()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.saiChanelMainBannerBackground.setBackground(new BitmapDrawable(bitmap));
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

    public interface OnReChannelClick{
        void onReChannelClick(int position);
    }
}
