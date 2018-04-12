package com.example.nitantsood.moviedb.APIResponses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 26-07-2017.
 */

public class results implements Serializable {
    int id;
    float vote_average;
    String title;
    String release_date;
    String overview;
    String poster_path;
    String backdrop_path;
    ArrayList<Integer> genre_ids=new ArrayList<>();

    public results(int id, float vote_average, String title, String poster_path, ArrayList<Integer> genre_ids) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }
    public String generateDate(){
        String raw_date=getRelease_date();
        String processed_date=raw_date.substring(8,10);
        if(raw_date.substring(5,6).equals("0")){
            if(raw_date.substring(6,7).equals("1")){
                processed_date=processed_date+" Jan";
            }else if(raw_date.substring(6,7).equals("2")){
                processed_date=processed_date+" Feb";
            }else if(raw_date.substring(6,7).equals("3")){
                processed_date=processed_date+" Mar";
            }else if(raw_date.substring(6,7).equals("4")){
                processed_date=processed_date+" Apr";
            }else if(raw_date.substring(6,7).equals("5")){
                processed_date=processed_date+" May";
            }else if(raw_date.substring(6,7).equals("6")){
                processed_date=processed_date+" Jun";
            }else if(raw_date.substring(6,7).equals("7")){
                processed_date=processed_date+" Jul";
            }else if(raw_date.substring(6,7).equals("8")){
                processed_date=processed_date+" Aug";
            }else if(raw_date.substring(6,7).equals("9")){
                processed_date=processed_date+" Sep";
            }
        }
        else{
            if(raw_date.substring(6,7).equals("0")){
                processed_date=processed_date+" Oct";
            }else if(raw_date.substring(6,7).equals("1")){
                processed_date=processed_date+" Nov";
            }
            else if(raw_date.substring(6,7).equals("2")){
                processed_date=processed_date+" Dec";
            }
        }
        return processed_date+" "+raw_date.substring(2,4);
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }
}
