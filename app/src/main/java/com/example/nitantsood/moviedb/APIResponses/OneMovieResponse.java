package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 02-08-2017.
 */

public class OneMovieResponse {
    String backdrop_path;
    ArrayList<movieGenre> genres;
    public class movieGenre{
        int id;
        String name;

        public movieGenre(int id, String name) {
            this.id = id;
            this.name = name;
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
    }
    int id;
    String original_title;
    String overview;
    String poster_path;
    ArrayList<ProductionCompanies> production_companies;
    public class ProductionCompanies{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    String release_date;
    String tagline;
    int runtime;
    Float vote_average;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<movieGenre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<movieGenre> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<ProductionCompanies> production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }
}
