package com.example.atacuniviehcimya2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String ID_MUS = "idArtist";
    public static final String FAV_ALB = "fav_albums";
    private ArrayList<AlbumItem> albumList;
    private AlbumItem item;
    private RequestQueue mRequestQueue;
    public static String idMusician;
    Button searchB;
    EditText typeN;
    Button favouriteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialiase the array list for albums
        albumList = new ArrayList<>();

        //MUSICIAN buttons
        searchB = findViewById(R.id.searchName);
        typeN = findViewById(R.id.typeName);
        favouriteButton = findViewById(R.id.favoriteButton);

        //set the 2 buttons on click listener
        mRequestQueue = Volley.newRequestQueue(this);
        searchB.setOnClickListener(MainActivity.this);
        favouriteButton.setOnClickListener(MainActivity.this);
    }


    //make difference between the two main buttons in this Activity
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // if the search button is clicked, take the musician id from the URL and send it to the Album Activity
            case R.id.searchName:
                String url = "https://www.theaudiodb.com/api/v1/json/1/search.php?s=" + typeN.getText().toString();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("artists");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject items = jsonArray.getJSONObject(i);
                                idMusician = items.getString("idArtist");
                            }
                            Log.d("idArtist", idMusician);

                            //give the parsed id to the next activity "Albums"
                            Intent id = new Intent(MainActivity.this, Albums.class);
                            id.putExtra(ID_MUS, idMusician);
                            startActivity(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                    }
                });
                mRequestQueue.add(request);
                break;

                // if the button with favourite artists is clicked, redirect to new Activity to show the saved favourite artists
            case R.id.favoriteButton:
                //get the list of favourite albums from the album activity
                if(getIntent().hasExtra("listWithFavourite")){
                    albumList = this.getIntent().getParcelableArrayListExtra("listWithFavourite");
                }
                else{
                        Toast.makeText(this, "The list of favourite albums is EMPTY!        Go and find your fovourite hits! ", Toast.LENGTH_LONG).show();
                }

                //make intent and send it direct to the FavAlbums activity
                Intent favourites = new Intent(MainActivity.this, FavAlbums.class);
                favourites.putParcelableArrayListExtra("listWithFavourite", albumList);
                startActivity(favourites);
        }
    }


}




























