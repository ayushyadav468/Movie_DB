package com.example.nitantsood.moviedb.APIResponses;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 27-07-2017.
 */

public class GenresResponse {
    @SerializedName("genres")
    public ArrayList<genres> genres;
    public class  genres{
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public ArrayList<genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<genres> genres) {
        this.genres = genres;
    }
}
