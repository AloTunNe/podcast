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

import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{
    private List<EpisodeOnMainBanner> reList;
    private Context context;
    private OnRecommendEpClick mOnRecommendEpClick;
    public RecommendAdapter(Context context, List<EpisodeOnMainBanner> reList, OnRecommendEpClick onRecommendEpClick) {
        this.context = context;
        this.reList = reList;
        this.mOnRecommendEpClick = onRecommendEpClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView tvEpisodeMainBannerName;
         TextView tvEpisodeMainBannerAuthor;
         TextView tvEpisodeMainBannerViews;
         ShapeableImageView imgEpisodeMainBannerAvatar;
         ShapeableImageView saiEpisodeBackgroundMainBanner;
         RelativeLayout rcvMainBanner;
         OnRecommendEpClick onRecommendEpClick;
        public ViewHolder(View view, OnRecommendEpClick onRecommendEpClick) {
            super(view);
            tvEpisodeMainBannerName = (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Name);
            tvEpisodeMainBannerAuthor= (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Author);
            tvEpisodeMainBannerViews = (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Views);
            imgEpisodeMainBannerAvatar = (ShapeableImageView) view.findViewById(R.id.imb_Episode_Main_Banner_Avatar);
            rcvMainBanner = (RelativeLayout)  view.findViewById(R.id.rcv_Episode_Main_Banner);
            saiEpisodeBackgroundMainBanner = (ShapeableImageView)  view.findViewById(R.id.imgEpisodeBackgroundBanner);
            // Define click listener for the ViewHolder's View
            this.onRecommendEpClick = onRecommendEpClick;
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onRecommendEpClick.onRecommendEpClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_main_banner_layout, parent, false);
        return new ViewHolder(view, mOnRecommendEpClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpisodeOnMainBanner ep = reList.get(position);
        holder.tvEpisodeMainBannerName.setText(ep.getName());
        holder.tvEpisodeMainBannerAuthor.setText(ep.getAuthor());
        holder.tvEpisodeMainBannerViews.setText("Views: " + ep.getListens());
        Picasso.with(context).load(ep.getAuthorAVT()).into(holder.imgEpisodeMainBannerAvatar);
        Picasso.with(context).load(ep.getAvatar()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
               holder.saiEpisodeBackgroundMainBanner.setBackground(new BitmapDrawable(bitmap));
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

    public interface OnRecommendEpClick{
        void onRecommendEpClick(int position);
    }
}
