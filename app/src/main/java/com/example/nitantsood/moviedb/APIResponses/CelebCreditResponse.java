package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 04-08-2017.
 */

public class CelebCreditResponse {
ArrayList<Cast>  cast;

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public class Cast{
        String name;
        String title;

        public void setTitle(String title) {
            this.title = title;
        }

        String media_type;
        String backdrop_path;
        int id;
        String character;

        public String getName() {
            if(name==null){
                return title;
            }else
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }
    }
}
