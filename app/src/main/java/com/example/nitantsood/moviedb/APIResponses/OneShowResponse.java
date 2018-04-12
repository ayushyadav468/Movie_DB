package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class OneShowResponse {
    String backdrop_path;
    ArrayList<Integer> episode_run_time;
    String first_air_date;
    ArrayList<Genres> genres;
    int id;
    String name;
    ArrayList<Networks> networks;
    int number_of_episodes;
    int number_of_seasons;
    String overview;
    String poster_path;
    ArrayList<Production> production_companies;
    ArrayList<Seasons> seasons;
    float vote_average;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(ArrayList<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
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

    public ArrayList<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<Networks> networks) {
        this.networks = networks;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
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

    public ArrayList<Production> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<Production> production_companies) {
        this.production_companies = production_companies;
    }

    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Seasons> seasons) {
        this.seasons = seasons;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public class Seasons{
        int episode_count;
        String air_date;
        int id;
        String poster_path;
        int season_number;

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }
    }
    public class Production{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public class Networks{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public class Genres{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
