package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcast.Adapter.SearchChanelAdapter;
import com.example.podcast.Adapter.SearchPlaylistAdapter;
import com.example.podcast.Adapter.SearchPodcastAdapter;
import com.example.podcast.Model.Chanel;
import com.example.podcast.Model.ChanelOnMainBanner;
import com.example.podcast.Model.Episode;
import com.example.podcast.Model.Playlist;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Browse_Podcast extends AppCompatActivity implements SearchPodcastAdapter.OnPodcastSearchClick, SearchPlaylistAdapter.OnPlaylistSearchClick, SearchChanelAdapter.OnChannelSearchClick{

    Context context;

    EditText edtSearch;

    ImageView imgIconsearch;
    ImageView imgIconChanel;
    ImageView imgIconPlaylist;
    ImageView imgIconPodcast;
    ImageView imgOption;






    TextView tvPodcastNumber;

    SearchPodcastAdapter searchPodcastAdapter;
    SearchChanelAdapter searchChanelAdapter;
    SearchPlaylistAdapter searchPlaylistAdapter;

    ArrayList<Episode> episodeArrayList;
    ArrayList<Chanel> chanelArrayList;
    ArrayList<Playlist> playlistArrayList;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_podcasts);

        Init();
        SetUi();

        imgIconsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSearch.getText().toString().compareTo("") != 0) {
                    getDataEpisodeSearch(edtSearch.getText().toString());
                    searchPodcastAdapter = new SearchPodcastAdapter(Browse_Podcast.this, episodeArrayList, Browse_Podcast.this);
                    searchPodcastAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Podcast.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(searchPodcastAdapter);
                    imgIconChanel.setImageResource(R.drawable.ic_channeloff);
                    imgIconPlaylist.setImageResource(R.drawable.ic_poscastoff);
                    imgIconPodcast.setImageResource(R.drawable.ic_pod_on);
                }
                else Toast.makeText(context, "Please write Keyword to Search!", Toast.LENGTH_LONG).show();
            }
        });

        imgIconChanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSearch.getText().toString().compareTo("") != 0) {
                    SearchDataChanel(edtSearch.getText().toString());
                    searchChanelAdapter = new SearchChanelAdapter(Browse_Podcast.this, chanelArrayList, Browse_Podcast.this);
                    searchChanelAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Podcast.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(searchChanelAdapter);
                    imgIconPodcast.setImageResource(R.drawable.ic_poscastoff);
                    imgIconPlaylist.setImageResource(R.drawable.ic_playlistoff);
                    imgIconChanel.setImageResource(R.drawable.ic_channelon);
                }
                else Toast.makeText(context, "Please write Keyword to Search!", Toast.LENGTH_LONG).show();
            }
        });

        imgIconPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSearch.getText().toString().compareTo("") != 0) {
                    SearchDataPlaylist(edtSearch.getText().toString());
                    searchPlaylistAdapter = new SearchPlaylistAdapter(Browse_Podcast.this, playlistArrayList, Browse_Podcast.this);
                    searchPlaylistAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Podcast.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(searchPlaylistAdapter);
                    imgIconChanel.setImageResource(R.drawable.ic_channeloff);
                    imgIconPodcast.setImageResource(R.drawable.ic_poscastoff);
                    imgIconPlaylist.setImageResource(R.drawable.ic_playliston);
                }
                else Toast.makeText(context, "Please write Keyword to Search!", Toast.LENGTH_LONG).show();
            }
        });

        imgIconPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSearch.getText().toString().compareTo("") != 0) {
                    getDataEpisodeSearch(edtSearch.getText().toString());
                    searchPodcastAdapter = new SearchPodcastAdapter(Browse_Podcast.this, episodeArrayList, Browse_Podcast.this);
                    searchPodcastAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Browse_Podcast.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(searchPodcastAdapter);
                    imgIconChanel.setImageResource(R.drawable.ic_channeloff);
                    imgIconPlaylist.setImageResource(R.drawable.ic_poscastoff);
                    imgIconPodcast.setImageResource(R.drawable.ic_pod_on);
                }
                else Toast.makeText(context, "Please write Keyword to Search!", Toast.LENGTH_LONG).show();
            }
        });

        imgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });



    }
    private void Init() {
        context = this;

        edtSearch = (EditText) findViewById(R.id.edt_Search);

        imgIconsearch = (ImageView) findViewById(R.id.img_icon_search);

        imgIconChanel = (ImageView) findViewById(R.id.img_icon_chanel);
        imgIconPlaylist = (ImageView) findViewById(R.id.img_icon_playlist);
        imgIconPodcast = (ImageView) findViewById(R.id.img_icon_pocast);
        imgOption = (ImageView) findViewById(R.id.option);


        tvPodcastNumber = (TextView) findViewById(R.id.tv_podcast_number);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_banner_search);

    }

    private void SetUi() {
        /*searchPodcastAdapter = new SearchPodcastAdapter(context, episodeArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchPodcastAdapter);
        searchPodcastAdapter.notifyDataSetChanged();*/
    }
    private void getDataEpisodeSearch(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Episode>> callback = dataService.SearchEpisode(keyword);
        callback.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
               episodeArrayList = (ArrayList<Episode>) response.body();
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });
    }
    private void SearchDataChanel(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Chanel>> callback = dataService.SearchChanelData(keyword);
        callback.enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {
                chanelArrayList = (ArrayList<Chanel>) response.body();
                for(int i = 0; i < chanelArrayList.size(); i++) {
                }
            }

            @Override
            public void onFailure(Call<List<Chanel>> call, Throwable t) {

            }
        });
    }
    private void SearchDataPlaylist(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.SearchPlaylistData(keyword);
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlistArrayList = (ArrayList<Playlist>) response.body();
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onPodcastSearchClick(int position) {
        Log.d(TAG, "onPodcastSearchClick: click on item " + position);
        Intent iNewActivity = new Intent(this, PlayEpisode.class);
        iNewActivity.putExtra("Episode", episodeArrayList.get(position));
        startActivity(iNewActivity);
    }

    @Override
    public void onPlaylistSearchClick(int position) {
        Log.d(TAG, "onPlaylistSearchClick: click on item " + position);
    }

    @Override
    public void onChannelSearchClick(int position) {
        Chanel chanel = chanelArrayList.get(position);
        Intent iNewActivity = new Intent(Browse_Podcast.this, ChannelActivity.class);
        iNewActivity.putExtra("Chanel", chanel);
        startActivity(iNewActivity);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
