package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder>{
    private List<Episode> reList;
    private Context context;
    public RecommendAdapter(Context context, List<Episode> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthorName;
        private final TextView tvTitle;
        private final TextView tvDate;
        private final TextView tvTime;
        private final ImageView ivAuthorAvt;
        private final ImageView ivTime;
        private final RelativeLayout rlRecommendEpisode;
        private final ImageButton ibPlay;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvAuthorName = (TextView) view.findViewById(R.id.tv_author_name);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDate = view.findViewById(R.id.tv_date);
            tvTime = view.findViewById(R.id.tv_time);
            ivTime = view.findViewById(R.id.iv_time);
            ivAuthorAvt = view.findViewById(R.id.iv_author_avt);
            rlRecommendEpisode = view.findViewById(R.id.rl_recommend_episode);
            ibPlay = view.findViewById(R.id.ib_play);
        }


    }
    @NonNull
    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommend_epis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode ep = reList.get(position);
        holder.tvAuthorName.setText(ep.getAuthorName());
        holder.tvTime.setText(ep.getTime());
        holder.tvDate.setText(ep.getDate());
        holder.tvTitle.setText(ep.getTitle());
        holder.rlRecommendEpisode.setBackgroundResource(ep.getImgEpis());
        holder.ivAuthorAvt.setImageResource(ep.getImgAuthor());

    }


    @Override
    public int getItemCount() {
        return (reList != null ? reList.size():0);
    }
}
