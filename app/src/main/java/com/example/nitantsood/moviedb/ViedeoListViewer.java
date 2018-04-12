package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.nitantsood.moviedb.APIResponses.OneMovieResponse;
import com.example.nitantsood.moviedb.APIResponses.VideoResponse;
import com.example.nitantsood.moviedb.Adapter.MovieDetailImageAdapter;
import com.example.nitantsood.moviedb.Adapter.VideoAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViedeoListViewer extends AppCompatActivity implements VideoAdapter.setOnVideoClickListener {
String title;
    int id,caller;
    Client client = new Client();
    ArrayList<VideoResponse.Video> videoList=new ArrayList<>();
    RecyclerView videoRecycleViewer;
    VideoAdapter videoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viedeo_list_viewer);
        ActionBar actionBar = getSupportActionBar();
        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        videoRecycleViewer = (RecyclerView) findViewById(R.id.video_list);
        videoRecycleViewer.setLayoutManager(new LinearLayoutManager(this));
        videoRecycleViewer.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        videoAdapter = new VideoAdapter(this, videoList, this);
        videoRecycleViewer.setAdapter(videoAdapter);

        Intent i = getIntent();
        caller = i.getIntExtra("caller", 0);
        if (caller == PlaceholderFragment.MOVIES) {
            id = i.getIntExtra("movieId", 0);
            title = i.getStringExtra("movieTitle");
            actionBar.setTitle(title);
            Call<VideoResponse> VideoCall = apiInterface.getVideo(id, getString(R.string.API_KEY));
            VideoCall.enqueue(new retrofit2.Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    VideoResponse videoResponse = response.body();
                    ArrayList<VideoResponse.Video> v = videoResponse.getResults();
                    videoList.clear();
                    videoList.addAll(v);
                    videoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {

                }
            });
        }
        else if(caller==PlaceholderFragment.TV_SHOWS){
            id = i.getIntExtra("showId", 0);
            title = i.getStringExtra("showTitle");
            actionBar.setTitle(title);
            Call<VideoResponse> VideoCall = apiInterface.getShowVideo(id, getString(R.string.API_KEY));
            VideoCall.enqueue(new retrofit2.Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    VideoResponse videoResponse = response.body();
                    ArrayList<VideoResponse.Video> v = videoResponse.getResults();
                    videoList.clear();
                    videoList.addAll(v);
                    videoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onVideoClicked(View v) {
        Intent i=new Intent(this,VideoPlayer.class);
        i.putExtra("key",(String)v.getTag());
        startActivity(i);
    }
}
