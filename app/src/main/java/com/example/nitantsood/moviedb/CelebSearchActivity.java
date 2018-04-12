package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nitantsood.moviedb.APIResponses.Celebrity;
import com.example.nitantsood.moviedb.APIResponses.CelebrityResponse;
import com.example.nitantsood.moviedb.Adapter.CelebrityListAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CelebSearchActivity extends AppCompatActivity implements CelebrityListAdapter.celebItemClickListener {
    Client client = new Client();
    RecyclerView listView;
    String searched;
    CelebrityListAdapter celebrityListAdapter;
    ArrayList<Celebrity> searchedList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celeb_search);

        Intent i=getIntent();
        searched=i.getStringExtra("text");

        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        listView=(RecyclerView) findViewById(R.id.searched_celeb_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        celebrityListAdapter=new CelebrityListAdapter(this,searchedList,this);
        listView.setAdapter(celebrityListAdapter);
        Call<CelebrityResponse> searchedCelebrityCall = apiInterface.getSearchedPerson(getString(R.string.API_KEY),searched);
        searchedCelebrityCall.enqueue(new Callback<CelebrityResponse>() {
            @Override
            public void onResponse(Call<CelebrityResponse> call, Response<CelebrityResponse> response) {
                CelebrityResponse celebrityResponse = response.body();
                ArrayList<Celebrity> m = celebrityResponse.getResults();
                searchedList.clear();
                searchedList.addAll(m);
                celebrityListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CelebrityResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void celebItemCLicked(View view, int position) {
        int id=searchedList.get(position).getId();
        Intent intent=new Intent(this,CelebDetailActivity.class);
        intent.putExtra(PlaceholderFragment.CLICKED_CELEB,id);
        intent.putExtra(PlaceholderFragment.CLICKED_CELEB+"name",searchedList.get(position).getName());
        startActivity(intent);
    }
}
