package com.example.nitantsood.moviedb.APIResponses;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class CastResponse {
    ArrayList<Cast> cast;

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public class Cast{
        int id;
        String character;
        String name;
        String profile_path;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
