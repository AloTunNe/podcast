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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class RecommendChanelAdapter extends RecyclerView.Adapter<RecommendChanelAdapter.ViewHolder>{
    private List<ChanelOnMainBanner> reList;
    private Context context;
    public RecommendChanelAdapter(Context context, List<ChanelOnMainBanner> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChanelMainBannerName;
        TextView tvChanelMainBannerUser;
        ImageButton tvChanelMainBannerUserAvatar;
        RelativeLayout rcvChanelMainBanner;
        public ViewHolder(View view) {
            super(view);
            tvChanelMainBannerName = (TextView) view.findViewById(R.id.tv_Chanel_Main_Banner_Name);
            tvChanelMainBannerUser = (TextView) view.findViewById(R.id.tv_Chanel_Main_Banner_Author);
            tvChanelMainBannerUserAvatar = (ImageButton) view.findViewById(R.id.imb_Chanel_Main_Banner_Avatar);
            rcvChanelMainBanner = (RelativeLayout) view.findViewById(R.id.rcv_Chanel_Main_Banner);
            // Define click listener for the ViewHolder's View

        }


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chanel_main_banner_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChanelOnMainBanner cn = reList.get(position);
        holder.tvChanelMainBannerName.setText(cn.getChanelName());
        holder.tvChanelMainBannerUser.setText(cn.getUserName());
        Picasso.with(context).load(cn.getUserAvatar()).into(holder.tvChanelMainBannerUserAvatar);
        Picasso.with(context).load(cn.getPicture()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.rcvChanelMainBanner.setBackground(new BitmapDrawable(bitmap));
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
