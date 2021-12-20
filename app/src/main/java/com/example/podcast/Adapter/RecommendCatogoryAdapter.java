package com.example.podcast.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Model.CatogoryOnMainBanner;
import com.example.podcast.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class RecommendCatogoryAdapter extends RecyclerView.Adapter<RecommendCatogoryAdapter.ViewHolder>{
    private List<CatogoryOnMainBanner> reList;
    private Context context;
    private OnCategoryClick mOnCategoryClick;
    public RecommendCatogoryAdapter(Context context, List<CatogoryOnMainBanner> reList, OnCategoryClick onCategoryClick) {
        this.context = context;
        this.reList = reList;
        this.mOnCategoryClick = onCategoryClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvCatogoryMainBanner;
        RelativeLayout rcvCatogoryMainBanner;
        ShapeableImageView saiCatogoryMainBannerBackground;
        OnCategoryClick onCategoryClick;
        public ViewHolder(View view, OnCategoryClick onCategoryClick) {
            super(view);
            tvCatogoryMainBanner = (TextView) view.findViewById(R.id.tv_Catogory_Main_Banner_Name);
            rcvCatogoryMainBanner = (RelativeLayout) view.findViewById(R.id.rcv_Catogory_Main_Banner);
            saiCatogoryMainBannerBackground = (ShapeableImageView) view.findViewById(R.id.imgCatogoryBackgroundBanner);
            // Define click listener for the ViewHolder's View
            this.onCategoryClick = onCategoryClick;
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onCategoryClick.onCategoryClick(getAdapterPosition());

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_main_banner_layout, parent, false);
        return new ViewHolder(view, mOnCategoryClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatogoryOnMainBanner catogoryOnMainBanner = reList.get(position);
        holder.tvCatogoryMainBanner.setText(catogoryOnMainBanner.getName());
        Picasso.with(context).load(catogoryOnMainBanner.getPicture()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.saiCatogoryMainBannerBackground.setBackground(new BitmapDrawable(bitmap));
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

    public interface OnCategoryClick{
        void onCategoryClick(int position);
    }
}