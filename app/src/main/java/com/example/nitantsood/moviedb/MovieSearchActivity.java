package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nitantsood.moviedb.APIResponses.MoviesResponse;
import com.example.nitantsood.moviedb.APIResponses.results;
import com.example.nitantsood.moviedb.Adapter.CategoryListAdapter;
import com.example.nitantsood.moviedb.Adapter.MovieSearchAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieSearchActivity extends AppCompatActivity implements MovieSearchAdapter.setOnMovieSearchClickListener {
String searched;
    Client client = new Client();
    ArrayList<results> searchedMovies=new ArrayList<>();
    MovieSearchAdapter adapter1;
    RecyclerView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        Intent i=getIntent();
        searched=i.getStringExtra("text");

        listView = (RecyclerView) findViewById(R.id.search_movie_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter1 = new MovieSearchAdapter(this,searchedMovies,this);
        listView.setAdapter(adapter1);

        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MoviesResponse> searchrMoviesCall = apiInterface.getSearchedMovie(getString(R.string.API_KEY),searched);
        searchrMoviesCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();



                ArrayList<results> m = moviesResponse.getResults();
                searchedMovies.clear();
                searchedMovies.addAll(m);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMovieClicked(View v, int position) {
        Intent intent=new Intent(this,MovieDetailActivity.class);
        intent.putExtra("id",searchedMovies.get(position).getId());
        intent.putExtra("backdrop",searchedMovies.get(position).getBackdrop_path());
        intent.putExtra("title",searchedMovies.get(position).getTitle());
        startActivity(intent);
    }
}
