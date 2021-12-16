package com.example.podcast.Service;

public class APIService {
    private static String base_url = "https://podcastnhom12.000webhostapp.com/Server/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
