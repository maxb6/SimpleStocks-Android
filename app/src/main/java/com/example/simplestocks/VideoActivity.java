package com.example.simplestocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.simplestocks.Model.Article;
import com.example.simplestocks.Model.ArticleAdapter;
import com.example.simplestocks.Model.Video;
import com.example.simplestocks.Model.VideoAdapter;
import com.example.simplestocks.Model.YoutubeConfig;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends YouTubeBaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "VideoActivity";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    YouTubePlayerView youTubePlayerView;
    Button playButton;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Context context;

    private List<Video> videoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //drawer layout menu system
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_video);


        Log.d(TAG, "OnCreate: Starting.");

        //intialize youtube components

        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        playButton = findViewById(R.id.playButton);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                List<String> ytList = new ArrayList<>();

                ytList.add("sCqox8Mfm58");
                ytList.add("aVTffZmlKVY");
                ytList.add("YANlHHaBp8k");
                ytList.add("IGcq8FiIpOk");

                youTubePlayer.loadVideos(ytList);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
            }
        });

        //recycler view and article adapter
        mRecyclerView = findViewById(R.id.videoRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        Video video1 = new Video("What is Dollar Cost Averaging?");
        videoList.add(video1);

        Video video2 = new Video("Stock Market for Beginners");
        videoList.add(video2);

        Video video3 = new Video("Warren Buffett: Proper Stock Picking");
        videoList.add(video3);

        Video video4 = new Video("Reading a Candlestick Chart");
        videoList.add(video4);

        mAdapter = new VideoAdapter(videoList);
        Log.i(TAG,"Video LIST: "+videoList.toString());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();

    }

    // Functions for menu system
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                goToMainActivity();
                break ;
            case R.id.nav_article:
                goToArticleActivity();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToArticleActivity(){
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }

}