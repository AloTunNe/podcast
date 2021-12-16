package com.example.podcast.Service;

import com.example.podcast.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("getinfor.php")
    Call<List<User>> getDataUser();
}
