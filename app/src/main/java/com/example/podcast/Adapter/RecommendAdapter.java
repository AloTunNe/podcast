package com.example.podcast.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Model.EpisodeOnMainBanner;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{
    private List<EpisodeOnMainBanner> reList;
    private Context context;
    public RecommendAdapter(Context context, List<EpisodeOnMainBanner> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tvEpisodeMainBannerName;
         TextView tvEpisodeMainBannerAuthor;
         TextView tvEpisodeMainBannerViews;
         ImageButton imgEpisodeMainBannerAvatar;
         RelativeLayout rcvMainBanner;
        public ViewHolder(View view) {
            super(view);
            tvEpisodeMainBannerName = (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Name);
            tvEpisodeMainBannerAuthor= (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Author);
            tvEpisodeMainBannerViews = (TextView) view.findViewById(R.id.tv_Episode_Main_Banner_Views);
            imgEpisodeMainBannerAvatar = (ImageButton)view.findViewById(R.id.imb_Episode_Main_Banner_Avatar);
            rcvMainBanner = (RelativeLayout)  view.findViewById(R.id.rcv_Episode_Main_Banner);
            // Define click listener for the ViewHolder's View

        }


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_main_banner_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpisodeOnMainBanner ep = reList.get(position);
        holder.tvEpisodeMainBannerName.setText(ep.getName());
        holder.tvEpisodeMainBannerAuthor.setText(ep.getAuthor());
        Picasso.with(context).load(ep.getAvatar()).into(holder.imgEpisodeMainBannerAvatar);
    }


    @Override
    public int getItemCount() {
        return (reList != null ? reList.size():0);
    }
}
