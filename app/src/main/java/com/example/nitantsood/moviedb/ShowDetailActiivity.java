package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitantsood.moviedb.APIResponses.CastResponse;
import com.example.nitantsood.moviedb.APIResponses.Celebrity;
import com.example.nitantsood.moviedb.APIResponses.MoviesResponse;
import com.example.nitantsood.moviedb.APIResponses.OneMovieResponse;
import com.example.nitantsood.moviedb.APIResponses.OneShowResponse;
import com.example.nitantsood.moviedb.APIResponses.TvResponse;
import com.example.nitantsood.moviedb.APIResponses.TvShows;
import com.example.nitantsood.moviedb.APIResponses.results;
import com.example.nitantsood.moviedb.Adapter.CategoryListAdapter;
import com.example.nitantsood.moviedb.Adapter.SeasonsAdapter;
import com.example.nitantsood.moviedb.Adapter.castAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.nitantsood.moviedb.PlaceholderFragment.CLICKED_MOVIE;
import static com.example.nitantsood.moviedb.PlaceholderFragment.CLICKED_SHOW;

public class ShowDetailActiivity extends AppCompatActivity implements SeasonsAdapter.setOnSeasonClickListener, com.example.nitantsood.moviedb.Adapter.castAdapter.setOnCastClickedListener, CategoryListAdapter.itemClickListener {
    Client client = new Client();
    OneShowResponse showResponse;
    final int mScreenWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
    TextView name,network,first_air_date,Producers,Storyline,genre,episodes,seasons;
    RatingBar ratingBar;
    ImageView poster;
    Bitmap bitmap;
    RecyclerView imagesViewer;
    ArrayList<OneShowResponse.Seasons> seasonsArrayList=new ArrayList<>();
    SeasonsAdapter seasonsAdapter;
    Palette p;
    Button video;
    RecyclerView castViewer;
    castAdapter castAdapter;
    RecyclerView suggestMovieViewer;
    TextView listType1;
    CategoryListAdapter categoryListAdapter;
    ArrayList<TvShows> similarShows=new ArrayList<>();
    ArrayList<CastResponse.Cast> CastList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_actiivity);

        final Intent intent=getIntent();
//        final TvShows currentShow=(TvShows) intent.getSerializableExtra(CLICKED_SHOW);
        final int currentShowid=(int) intent.getIntExtra("id",0);
        final  String currentShowTitle=intent.getStringExtra("title");
        final String currentShowBackdrop=intent.getStringExtra("backdrop");


        Toolbar toolbar = (Toolbar) findViewById(R.id.show_detail_toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.show_poster);
        final ActionBar actionBar=getSupportActionBar();
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_backdrop);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        actionBar.setTitle(currentShowTitle);
        final ImageView backdrop=(ImageView) findViewById(R.id.show_backdrop);

        name=(TextView) findViewById(R.id.show_detail_title);
        network=(TextView) findViewById(R.id.show_detail_network);
        Storyline=(TextView) findViewById(R.id.movie_detail_storyline_matter);
        first_air_date=(TextView) findViewById(R.id.show_detail_first_air_date);
        ratingBar=(RatingBar) findViewById(R.id.show_detail_ratingBar);
        Producers=(TextView) findViewById(R.id.show_detail_producers);
        genre=(TextView) findViewById(R.id.movie_detail_genre_matter);
        poster=(ImageView) findViewById(R.id.show_detail_poster);
        episodes=(TextView) findViewById(R.id.show_episodes);
        seasons=(TextView) findViewById(R.id.show_season);

        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + currentShowBackdrop).resize(mScreenWidth,0).error(android.R.drawable.stat_notify_error).into(backdrop,new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                bitmap = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
                p= Palette.from(bitmap).generate();
                collapsingToolbarLayout.setContentScrimColor(p.getVibrantColor(getResources().getColor(R.color.colorPrimary)));
                Window window=getWindow();
                window.setStatusBarColor(p.getDarkVibrantColor(getResources().getColor(R.color.colorPrimaryDark)));
//                nestedScrollView.setBackgroundColor(p.getDarkVibrantColor(getResources().getColor(R.color.colorPrimaryDark)));
                fab.setBackgroundTintList(ColorStateList.valueOf(p.getDarkVibrantColor(getResources().getColor(R.color.colorPrimaryDark))));
            }
        });



        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<OneShowResponse> ShowCall = apiInterface.getShow(currentShowid,getString(R.string.API_KEY));
        ShowCall.enqueue(new retrofit2.Callback<OneShowResponse>() {
            @Override
            public void onResponse(Call<OneShowResponse> call, Response<OneShowResponse> response) {
                showResponse = response.body();
                showDownloadComplete(showResponse);
            }
            @Override
            public void onFailure(Call<OneShowResponse> call, Throwable t) {

            }
        });

        imagesViewer=(RecyclerView) findViewById(R.id.show_detail_image_viewer);
        imagesViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seasonsAdapter=new SeasonsAdapter(this,seasonsArrayList,this);
        imagesViewer.setAdapter(seasonsAdapter);
        seasonsAdapter.notifyDataSetChanged();

        video=(Button) findViewById(R.id.video_fetch_button);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),ViedeoListViewer.class);
                intent1.putExtra("caller",PlaceholderFragment.TV_SHOWS);
                intent1.putExtra("showId",currentShowid);
                intent1.putExtra("showTitle",currentShowTitle);
                startActivity(intent1);
            }
        });

        castViewer=(RecyclerView) findViewById(R.id.cast_recycler);
        castViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castAdapter=new castAdapter(this,CastList,this);
        castViewer.setAdapter(castAdapter);
        Call<CastResponse> CastCall = apiInterface.getShowCast(currentShowid,getString(R.string.API_KEY));
        CastCall.enqueue(new retrofit2.Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse castResponse = response.body();
                ArrayList<CastResponse.Cast> m=castResponse.getCast();
                CastList.clear();
                CastList.addAll(m);
                castAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });

        listType1= (TextView) findViewById(R.id.list_type);
        listType1.setText("Similar Shows");
        listType1.setTextColor(Color.WHITE);
        suggestMovieViewer = (RecyclerView) findViewById(R.id.category_list);
        suggestMovieViewer.setMinimumHeight(200);
        suggestMovieViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryListAdapter = new CategoryListAdapter(this,this,PlaceholderFragment.TV_SHOWS,PlaceholderFragment.POPULAR_TV_SHOWS,null,similarShows,PlaceholderFragment.genre);
        suggestMovieViewer.setAdapter(categoryListAdapter);
        Call<TvResponse> similarShowCall = apiInterface.getSimilarShow(currentShowid,getString(R.string.API_KEY));
        similarShowCall.enqueue(new retrofit2.Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse tvResponse = response.body();
                ArrayList<TvShows> m = tvResponse.getResults();
                similarShows.clear();
                similarShows.addAll(m);
                categoryListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                File image = new File(sdCardDirectory,currentShowid+"_backdrop.png");
                boolean success = false;
                FileOutputStream outStream;
                try {

                    outStream = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    success = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (success) {
                    Toast.makeText(getApplicationContext(),currentShowTitle+" image successfully saved",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error during image saving", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showDownloadComplete(OneShowResponse showResponse){
        ArrayList<OneShowResponse.Seasons> m=showResponse.getSeasons();
        seasonsArrayList.clear();
        seasonsArrayList.addAll(m);
        seasonsAdapter.notifyDataSetChanged();
        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + showResponse.getPoster_path()).resize(mScreenWidth/3,0).error(android.R.drawable.stat_notify_error).into(poster);
        name.setText(showResponse.getName());
        episodes.setText("Episodes: "+showResponse.getNumber_of_episodes());
        seasons.setText("Seasons: "+showResponse.getNumber_of_seasons());
        network.setText(showResponse.getNetworks().get(0).getName());
        first_air_date.setText(showResponse.getFirst_air_date());
        String producers="Produced by";
        for(int i=0;i<showResponse.getProduction_companies().size();i++){
            producers=producers+", "+showResponse.getProduction_companies().get(i).getName();
        }
        Producers.setText(producers);
        Storyline.setText(showResponse.getOverview());
        String Genre="";
        for(int i=0;i<showResponse.getGenres().size();i++){
            Genre=Genre+"/"+showResponse.getGenres().get(i).getName();
        }
        genre.setText(Genre);
        ratingBar.setRating(showResponse.getVote_average()/2);
    }

    @Override
    public void onSeasonClicked(View v) {
        //intent to open full page image
        String imageUrl=(String) v.getTag();
        Intent i=new Intent(this,ImageIndependentViewer.class);
        i.putExtra("url",imageUrl);
        startActivity(i);
    }

    @Override
    public void onCastClicked(View v,int position) {
        int id=CastList.get(position).getId();
        Intent intent=new Intent(this,CelebDetailActivity.class);
        intent.putExtra(PlaceholderFragment.CLICKED_CELEB,id);
        intent.putExtra(PlaceholderFragment.CLICKED_CELEB+"name",CastList.get(position).getName());
        startActivity(intent);
    }

    @Override
    public void movieItemCLicked(View view, int position) {
        //do nothing
    }

    @Override
    public void showItemCLicked(View view, int position) {
        TvShows clickedShow=(TvShows) view.getTag();
        Intent intent=new Intent(this,ShowDetailActiivity.class);
//        intent.putExtra(CLICKED_SHOW,clickedShow);
        intent.putExtra("title",clickedShow.getTitle());
        intent.putExtra("backdrop",clickedShow.getBackdrop_path());
        intent.putExtra("id",clickedShow.getId());
        startActivity(intent);
    }
}
