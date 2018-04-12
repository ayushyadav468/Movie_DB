package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
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
import com.example.nitantsood.moviedb.APIResponses.ImageResponse;
import com.example.nitantsood.moviedb.APIResponses.MoviesResponse;
import com.example.nitantsood.moviedb.APIResponses.OneMovieResponse;
import com.example.nitantsood.moviedb.APIResponses.ReviewResponse;
import com.example.nitantsood.moviedb.APIResponses.results;
import com.example.nitantsood.moviedb.Adapter.CategoryListAdapter;
import com.example.nitantsood.moviedb.Adapter.MovieDetailImageAdapter;
import com.example.nitantsood.moviedb.Adapter.ReviewsAdapter;
import com.example.nitantsood.moviedb.Adapter.castAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.nitantsood.moviedb.PlaceholderFragment.CLICKED_MOVIE;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailImageAdapter.setOnImageClickListener, com.example.nitantsood.moviedb.Adapter.castAdapter.setOnCastClickedListener, CategoryListAdapter.itemClickListener {
    final int mScreenWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
    Palette p;
    OneMovieResponse movieResponse;
    Bitmap bitmap;
    Client client = new Client();
    TextView title,tagline,release_date,Producers,Storyline,genre,listType1;
    RatingBar ratingBar;
    ImageView poster;
    RecyclerView imagesViewer,castViewer;
    MovieDetailImageAdapter imageAdapter;
    castAdapter castAdapter;
    Button reviews,video;
    CategoryListAdapter categoryListAdapter;
    RecyclerView suggestMovieViewer;
    ArrayList<results> suggestedMoviesList=new ArrayList<>();
    ArrayList<ImageResponse.Image> PhotosList=new ArrayList<>();
    ArrayList<CastResponse.Cast>  CastList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent=getIntent();
//        final results currentMovie=(results) intent.getSerializableExtra(CLICKED_MOVIE);
        final int currentMovieid=intent.getIntExtra("id",0);
        final String currentMovieTitle=intent.getStringExtra("title");
        final String currentMovieBackdrop=intent.getStringExtra("backdrop");

        title=(TextView) findViewById(R.id.movie_detail_title);
        tagline=(TextView) findViewById(R.id.movie_detail_tagline);
        Storyline=(TextView) findViewById(R.id.movie_detail_storyline_matter);
        release_date=(TextView) findViewById(R.id.movie_detail_release_date);
        ratingBar=(RatingBar) findViewById(R.id.movie_detail_ratingBar);
        Producers=(TextView) findViewById(R.id.movie_detail_producers);
        genre=(TextView) findViewById(R.id.movie_detail_genre_matter);
        poster=(ImageView) findViewById(R.id.movie_detail_poster);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.movie_poster);
        final NestedScrollView nestedScrollView=(NestedScrollView) findViewById(R.id.movie_detail_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.movie_detail_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar=getSupportActionBar();
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_backdrop);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        actionBar.setTitle(currentMovieTitle);
        final ImageView backdrop=(ImageView) findViewById(R.id.movie_backdrop);

        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + currentMovieBackdrop).resize(mScreenWidth,0).error(android.R.drawable.stat_notify_error).into(backdrop,new Callback.EmptyCallback() {
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
        Call<OneMovieResponse> MovieCall = apiInterface.getMovie(currentMovieid,getString(R.string.API_KEY));
        MovieCall.enqueue(new retrofit2.Callback<OneMovieResponse>() {
            @Override
            public void onResponse(Call<OneMovieResponse> call, Response<OneMovieResponse> response) {
                movieResponse = response.body();
                movieDownloadComplete(movieResponse);
            }
            @Override
            public void onFailure(Call<OneMovieResponse> call, Throwable t) {

            }
        });

        reviews=(Button) findViewById(R.id.review_button);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),ReviewsActivity.class);
                intent1.putExtra("movieId",currentMovieid);
                intent1.putExtra("movieTitle",currentMovieTitle);
                startActivity(intent1);
            }
        });
        video=(Button) findViewById(R.id.video_fetch_button);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),ViedeoListViewer.class);
                intent1.putExtra("movieId",currentMovieid);
                intent1.putExtra("movieTitle",currentMovieTitle);
                startActivity(intent1);
            }
        });

        imagesViewer=(RecyclerView) findViewById(R.id.movie_detail_image_viewer);
        imagesViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageAdapter=new MovieDetailImageAdapter(this,PhotosList,this);
        imagesViewer.setAdapter(imageAdapter);
        Call<ImageResponse> ImageCall = apiInterface.getImage(currentMovieid,getString(R.string.API_KEY));
        ImageCall.enqueue(new retrofit2.Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse imageResponse = response.body();
                ArrayList<ImageResponse.Image> m=imageResponse.getBackdrops();
                ArrayList<ImageResponse.Image> n=imageResponse.getPosters();
                PhotosList.clear();
                PhotosList.addAll(m);
                PhotosList.addAll(n);
                imageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });

        castViewer=(RecyclerView) findViewById(R.id.cast_recycler);
        castViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castAdapter=new castAdapter(this,CastList,this);
        castViewer.setAdapter(castAdapter);
        Call<CastResponse> CastCall = apiInterface.getCast(currentMovieid,getString(R.string.API_KEY));
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



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                File image = new File(sdCardDirectory,currentMovieTitle+"_backdrop.png");
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
                    Toast.makeText(getApplicationContext(),currentMovieTitle+" image successfully saved",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error during image saving", Toast.LENGTH_LONG).show();
                }
            }
        });


        listType1= (TextView) findViewById(R.id.list_type);
        listType1.setText("Suggested Movies");
        listType1.setTextColor(Color.WHITE);
        suggestMovieViewer = (RecyclerView) findViewById(R.id.category_list);
        suggestMovieViewer.setMinimumHeight(200);
        suggestMovieViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryListAdapter = new CategoryListAdapter(this,this,PlaceholderFragment.MOVIES,PlaceholderFragment.POPULAR_MOVIES,suggestedMoviesList,null,PlaceholderFragment.genre);
        suggestMovieViewer.setAdapter(categoryListAdapter);
        Call<MoviesResponse> similarMoviesCall = apiInterface.getSimilarMovie(currentMovieid,getString(R.string.API_KEY));
        similarMoviesCall.enqueue(new retrofit2.Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                ArrayList<results> m = moviesResponse.getResults();
                suggestedMoviesList.clear();
                suggestedMoviesList.addAll(m);
                categoryListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });


    }


    public void movieDownloadComplete(OneMovieResponse movieResponse){
        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + movieResponse.getPoster_path()).resize(mScreenWidth/3,0).error(android.R.drawable.stat_notify_error).into(poster);
        title.setText(movieResponse.getOriginal_title());
        tagline.setText(movieResponse.getTagline());
        release_date.setText(movieResponse.getRelease_date());
        String producers="Produced by";
        for(int i=0;i<movieResponse.getProduction_companies().size();i++){
            producers=producers+", "+movieResponse.getProduction_companies().get(i).getName();
        }
        Producers.setText(producers);
        Storyline.setText(movieResponse.getOverview());
        String Genre="";
        for(int i=0;i<movieResponse.getGenres().size();i++){
            Genre=Genre+"/"+movieResponse.getGenres().get(i).getName();
        }
        genre.setText(Genre);
        ratingBar.setRating(movieResponse.getVote_average()/2);
    }

    @Override
    public void onImageClicked(View v) {
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
        //call  intent for detail movie again
        results clickedMovie=(results) view.getTag();
        Intent intent = new Intent(this, MovieDetailActivity.class);
//        intent.putExtra(CLICKED_MOVIE, clickedMovie);
        intent.putExtra("id",clickedMovie.getId());
        intent.putExtra("backdrop",clickedMovie.getBackdrop_path());
        intent.putExtra("title",clickedMovie.getTitle());
        startActivity(intent);
    }

    @Override
    public void showItemCLicked(View view, int position) {
        //do nothing
    }
}
