package com.example.nitantsood.moviedb.APIResponses;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class ReviewResponse {
    ArrayList<Review> results;

    public ReviewResponse(ArrayList<Review> results) {
        this.results = results;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    public class Review{
        String  author;
        String content;

        public Review(String author, String content) {
            this.author = author;
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
