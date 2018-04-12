package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NITANT SOOD on 25-07-2017.
 */

public class MoviesResponse {
    @SerializedName("results")
    private ArrayList<com.example.nitantsood.moviedb.APIResponses.results> results;

    public ArrayList<com.example.nitantsood.moviedb.APIResponses.results> getResults() {
        return results;
    }

    public void setResults(ArrayList<com.example.nitantsood.moviedb.APIResponses.results> results) {
        this.results = results;
    }
}
