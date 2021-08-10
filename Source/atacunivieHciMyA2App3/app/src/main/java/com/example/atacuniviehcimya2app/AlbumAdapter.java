package com.example.atacuniviehcimya2app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// make an adapter class which we can adapt the lists with Album objects with
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.aViewHolder> {
        private Context context;
        private ArrayList<AlbumItem> albumItems;
        private ArrayList<AlbumItem> listFav;

        //constructor for the adapter
        public AlbumAdapter(Context aContext, ArrayList<AlbumItem> albumList) {
            context = aContext;
            albumItems = albumList;
        }

        // make sure the image from the JSON Database will be printed in the album activity
        @NonNull
        @Override
        public aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            return new aViewHolder(v);
        }

        //use the position to get the item of our array list
        @Override
        public void onBindViewHolder(@NonNull aViewHolder holder, int position) {
            //get the album on position "position"
            AlbumItem currentItem = albumItems.get(position);

            //get all the album items
            String titleText = currentItem.getTitle();
            String yearText = currentItem.getYear();
            String imageUrl = currentItem.getImageUrl();
            String genreText = currentItem.getGenre();
            String descText = currentItem.getDesc();

            //set the views of the card to those values
            holder.TextViewTitle.setText(titleText);
            holder.TextViewYear.setText(yearText);

            //set a image url
            Picasso.with(context).load(imageUrl).fit().centerInside().into(holder.aImageView);

            holder.TextViewGenre.setText("Genre: " + genreText);
            holder.TextViewDesc.setText( descText);

            // set the favourite buttin on click Listener
            holder.ImageViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Album added in favourite albums!", Toast.LENGTH_LONG).show();

                    listFav.add(currentItem);

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putParcelableArrayListExtra("listWithFavourite", listFav);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return albumItems.size();
        }

        // make a class with saves the components of a album object
        public class aViewHolder extends RecyclerView.ViewHolder {
            // components of the view
            public ImageView aImageView;
            public TextView TextViewTitle;
            public TextView TextViewYear;
            public TextView TextViewGenre;
            public TextView TextViewDesc;
            public ImageButton ImageViewButton;

            public aViewHolder(@NonNull View itemView) {
                super(itemView);
                //find the views
                aImageView = itemView.findViewById(R.id.image_view);
                TextViewTitle = itemView.findViewById(R.id.text_view_title);
                TextViewYear = itemView.findViewById(R.id.text_view_year);
                TextViewGenre = itemView.findViewById(R.id.text_view_genre);
                TextViewDesc = itemView.findViewById(R.id.text_view_desc);
                ImageViewButton = itemView.findViewById(R.id.fav_album_button);
            }
        }
}
