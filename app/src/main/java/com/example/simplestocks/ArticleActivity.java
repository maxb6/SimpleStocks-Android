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

import com.example.simplestocks.Model.Article;
import com.example.simplestocks.Model.ArticleAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ArticleActivity";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Context context;

    private List<Article> articleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //drawer layout menu system
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Articles");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_article);

        //recycler view and article adapter
        mRecyclerView = findViewById(R.id.articleRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        Article article1 = new Article("7 steps to DCA"
                , "Educators Financial Group",getString(R.string.article1), BitmapFactory.decodeResource(getResources(),R.drawable.dca));
        articleList.add(article1);

        Article article2 = new Article("How to pick a stock"
                , "Investopedia",getString(R.string.article2), BitmapFactory.decodeResource(getResources(),R.drawable.stock_pick));
        articleList.add(article2);

        Article article3 = new Article("How to read a stock chart"
                , "Money Under 30",getString(R.string.article3), BitmapFactory.decodeResource(getResources(),R.drawable.stock_chart));
        articleList.add(article3);

        Article article4 = new Article("What is an index fund"
                , "Nerd Wallet",getString(R.string.article4), BitmapFactory.decodeResource(getResources(),R.drawable.index_fund));
        articleList.add(article4);

        mAdapter = new ArticleAdapter(articleList);
        Log.i(TAG,"Article LIST: "+articleList.toString());
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
                break;
            case R.id.nav_video:
                goToVideoActivity();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToVideoActivity(){
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }


}