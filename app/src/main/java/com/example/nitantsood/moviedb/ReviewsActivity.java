package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.ReviewResponse;
import com.example.nitantsood.moviedb.Adapter.ReviewsAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewsActivity extends AppCompatActivity {
    ReviewsAdapter reviewsAdapter;
    ArrayList<ReviewResponse.Review> reviewArrayList=new ArrayList<>();
    RecyclerView ReviewViewer;
    TextView noReviews;
    Client client = new Client();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent intent=getIntent();
        int ID=intent.getIntExtra("movieId",0);
        name=intent.getStringExtra("movieTitle");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(name);
        noReviews=(TextView) findViewById(R.id.no_review_text);
        ReviewViewer=(RecyclerView) findViewById(R.id.outer_reviews_recycler);
        ReviewViewer.setLayoutManager(new LinearLayoutManager(this));
        ReviewViewer.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        reviewsAdapter=new ReviewsAdapter(this,reviewArrayList);
        ReviewViewer.setAdapter(reviewsAdapter);
        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ReviewResponse> ReviewCall = apiInterface.getReviews(ID,getString(R.string.API_KEY));
        ReviewCall.enqueue(new retrofit2.Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                ArrayList<ReviewResponse.Review> m=reviewResponse.getResults();
                reviewArrayList.clear();
                reviewArrayList.addAll(m);
                reviewsAdapter.notifyDataSetChanged();
                if(m.size()!=0){
                    noReviews.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }
}
