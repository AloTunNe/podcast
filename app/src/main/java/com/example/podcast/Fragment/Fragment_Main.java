package com.example.podcast.Fragment;

import static com.example.podcast.R.layout.activity_main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.podcast.R;


public class Fragment_Main extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(activity_main, container, false);
        return view;
    }
}
