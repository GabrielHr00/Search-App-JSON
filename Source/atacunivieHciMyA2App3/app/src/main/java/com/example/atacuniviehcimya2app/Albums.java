package com.example.atacuniviehcimya2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//implements AlbumAdapter.OnItemClickListener
public class Albums extends AppCompatActivity {
    private RecyclerView aRecyclerView;
    private AlbumAdapter aAlbumAdapter;
    private ArrayList<AlbumItem> albumList;
    private ArrayList<AlbumItem> favAlbums;
    private RequestQueue aRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //get the Musician ID from the main activity
        Intent intent = getIntent();
        String idM = intent.getStringExtra("idArtist");
        // initialise the list for the albums
        albumList = new ArrayList<>();
        // initialise the list, where the favourite artist will be saved
        favAlbums = new ArrayList<>();

        //Check Musician ID - optional
        Toast.makeText(this, "ID Musician in the albums: " + idM, Toast.LENGTH_LONG).show();

        //where comes the answer to
        aRequestQueue = Volley.newRequestQueue(this);

        //ALBUM - initialise the adapter and set it to the RecyclerView
        aRecyclerView = findViewById(R.id.recycler_view);
        //ensure that height and width won't change
        aRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // parse all of the albums from the JSON Database for a selected artist, the musician id helps with it
        parseJSON(idM);
    }


    private void parseJSON(String idM){

        // url, which we use in our following request to parse the json objects from
        String url = "https://theaudiodb.com/api/v1/json/1/album.php?i=" + idM;

        //request to get the json object and copy them in a array list
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("album");

                    for(int i = 0; i < jsonArray.length(); i++){
                        //get the needed json object from json database (parsing)
                        JSONObject album = jsonArray.getJSONObject(i);

                        //take the strings from the json Object and check if they actually exist,
                        // if not, we don't insert the album object in our list
                        String title = "";
                        if(album.has("strAlbum")){
                            title = album.getString("strAlbum");
                        }

                        String year = "";
                        if(album.has("intYearReleased")){
                            year = album.getString("intYearReleased");
                        }

                        String imageUrl = "";
                        if(album.has("strAlbumThumbBack")){
                            imageUrl = album.getString("strAlbumThumbBack");
                        }
                        else{
                            imageUrl = album.getString("strAlbumCDart");
                        }

                        String genre = "";
                        if(album.has("strStyle")){
                            genre = album.getString("strStyle");
                        }

                        String desc = "";
                        if(album.has("strDescriptionEN")) {
                            desc = album.getString("strDescriptionEN");
                        }


                        //add the object details in the array array list as Album objects, but only if all its components exist
                        if(title != "" && year != "" && imageUrl != "" && genre != "" && desc != "") {
                            albumList.add(new AlbumItem(title, year, imageUrl, genre, desc));
                        }
                    }

                    //AT ALL: pass the json objects in the list, then the list in the adapter and set the adapter on the recycler view
                    aAlbumAdapter = new AlbumAdapter(Albums.this, albumList);
                    aRecyclerView.setAdapter(aAlbumAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        });

        //add the request in the request queue
        aRequestQueue.add(request);
    }
}

























