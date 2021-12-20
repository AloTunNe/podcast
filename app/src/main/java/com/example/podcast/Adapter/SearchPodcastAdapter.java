package com.example.podcast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Model.Episode;
import com.example.podcast.R;

import java.util.ArrayList;
import java.util.List;

public class SearchPodcastAdapter extends RecyclerView.Adapter<SearchPodcastAdapter.ViewHolder>{
    private List<Episode> reList;
    Context context;
    public SearchPodcastAdapter(Context context, List<Episode> reList) {
        this.context = context;
        this.reList = reList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEpisodeName;
        TextView tvEpisodeAuthorName;
        public ViewHolder(View view) {
            super(view);
            tvEpisodeName = (TextView) view.findViewById(R.id.tv_Episode_Name);
            tvEpisodeAuthorName = (TextView) view.findViewById(R.id.tv_Episode_Author);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_podcast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode ep = reList.get(position);
        holder.tvEpisodeName.setText(ep.getNameEpisode());
        holder.tvEpisodeAuthorName.setText(ep.getAuthorEpisode());
    }


    @Override
    public int getItemCount() {
        return (reList != null ? reList.size():0);
    }

}
