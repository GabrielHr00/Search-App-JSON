package com.example.atacuniviehcimya2app;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumItem implements Parcelable{
    private String title;
    private String year;
    private String imageUrl;
    private String genre;
    private String desc;

    public AlbumItem(Parcel in){
        this.title = in.readString();
        this.year = in.readString();
        this.imageUrl = in.readString();
        this.genre = in.readString();
        this.desc = in.readString();
    }

    public AlbumItem(String title, String year, String imageUrl, String genre, String desc) {
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
        this.genre = genre;
        this.desc = desc;
    }

    public String getTitle() {return title;}

    public String getYear() {return year;}

    public String getImageUrl() {return imageUrl;}

    public String getGenre() {return genre;}

    public String getDesc() {return desc;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.year);
        dest.writeString(this.imageUrl);
        dest.writeString(this.genre);
        dest.writeString(this.desc);
    }

    public static final Parcelable.Creator<AlbumItem> CREATOR = new Parcelable.Creator<AlbumItem>() {
        public AlbumItem createFromParcel(Parcel in) {
            return new AlbumItem(in);
        }

        public AlbumItem[] newArray(int size) {
            return new AlbumItem[size];
        }
    };
}
