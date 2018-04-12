package com.example.nitantsood.moviedb.APIResponses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 02-08-2017.
 */

public class Celebrity implements Serializable {

    int id;
    String name;
    String profile_path;
    ArrayList<KnownFor>  known_for;
    public class KnownFor{
        String title;
        String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            if(title==null)
            return name;
            else
                return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

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

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public ArrayList<KnownFor> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<KnownFor> known_for) {
        this.known_for = known_for;
    }
}
