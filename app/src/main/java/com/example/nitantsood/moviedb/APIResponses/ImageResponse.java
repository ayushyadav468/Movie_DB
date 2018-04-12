package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 02-08-2017.
 */

public class ImageResponse {
    ArrayList<Image> backdrops;
    ArrayList<Image> posters;
    ArrayList<Image>  profiles;

    public ArrayList<Image> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Image> profiles) {
        this.profiles = profiles;
    }

    public class Image{
        String file_path;

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

    public ArrayList<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ArrayList<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public ArrayList<Image> getPosters() {
        return posters;
    }

    public void setPosters(ArrayList<Image> posters) {
        this.posters = posters;
    }
}
