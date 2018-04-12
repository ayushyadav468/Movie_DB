package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class VideoResponse {
 ArrayList<Video> results;

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }

    public class Video{
        String name;
        String key;
        String type;
        String site;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }
    }
}
