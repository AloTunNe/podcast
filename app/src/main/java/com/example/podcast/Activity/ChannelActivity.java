package com.example.podcast.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.podcast.Model.Chanel;
import com.example.podcast.Model.Playlist;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelActivity extends Activity {

    ImageView imgIconBack;

    Chanel chanel;
    ArrayList<Chanel> chanelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chanel);

        Init();
        SetUi();
      // SearchChanelById(chanel.getChanelId( ));
       // Log.d("bbb: ", chanel.getChanelName());

    }

    private void SetUi() {

    }

    private void Init() {
        chanel = (Chanel) getIntent().getParcelableExtra("Chanel");

    }
    private void SearchChanelById(String keyword) {
        DataService dataService = APIService.getService();
        Call<List<Chanel>> callback = dataService.SearchChanelDataById(keyword);
        callback.enqueue(new Callback<List<Chanel>>() {
            @Override
            public void onResponse(Call<List<Chanel>> call, Response<List<Chanel>> response) {
                chanelArrayList = (ArrayList<Chanel>) response.body();
                Log.d("bbb: ", chanelArrayList.get(0).getChanelName());
            }

            @Override
            public void onFailure(Call<List<Chanel>> call, Throwable t) {

            }
        });
    }
}
