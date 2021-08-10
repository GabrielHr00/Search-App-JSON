package com.example.atacuniviehcimya2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class FavAlbums extends AppCompatActivity {
    public static final String FAV_ALB = "listWithFavourite";
    private RecyclerView aRecyclerView;
    private AlbumAdapter aAlbumAdapter;
    private ArrayList<AlbumItem> albumList;
    private RequestQueue aRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_albums);

        //get the inserted album objects in the list with favourites
        if(getIntent().hasExtra(FAV_ALB)){
            albumList = this.getIntent().getParcelableArrayListExtra(FAV_ALB);
        }

        aRequestQueue = Volley.newRequestQueue(this);

        aRecyclerView = findViewById(R.id.recycler_view);
        aRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        aAlbumAdapter = new AlbumAdapter(FavAlbums.this, albumList);
        aRecyclerView.setAdapter(aAlbumAdapter);
    }
}