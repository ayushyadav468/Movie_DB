package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitantsood.moviedb.APIResponses.CelebCreditResponse;
import com.example.nitantsood.moviedb.APIResponses.Celebrity;
import com.example.nitantsood.moviedb.APIResponses.ImageResponse;
import com.example.nitantsood.moviedb.APIResponses.OneCelebResponse;
import com.example.nitantsood.moviedb.APIResponses.OneShowResponse;
import com.example.nitantsood.moviedb.APIResponses.TvShows;
import com.example.nitantsood.moviedb.Adapter.CelebCreditAdapter;
import com.example.nitantsood.moviedb.Adapter.MovieDetailImageAdapter;
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

import static com.example.nitantsood.moviedb.PlaceholderFragment.CLICKED_CELEB;
import static com.example.nitantsood.moviedb.PlaceholderFragment.CLICKED_SHOW;

public class CelebDetailActivity extends AppCompatActivity implements MovieDetailImageAdapter.setOnImageClickListener, CelebCreditAdapter.setOnItemClickListener {
    Bitmap bitmap;
    Client client = new Client();
    TextView name;
    TextView placeOfBirth;
    TextView birthday;
    TextView biography;
    Palette p;
    RecyclerView imagesViewer;
    MovieDetailImageAdapter imageAdapter;
    ArrayList<ImageResponse.Image> PhotosList=new ArrayList<>();
    CelebCreditAdapter celebCreditAdapter;
    RecyclerView creditView;
    ArrayList<CelebCreditResponse.Cast> creditList=new ArrayList<>();
    OneCelebResponse currentCeleb;
    final int mScreenWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celeb_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.celeb_detail_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent=getIntent();
        final int currentCelebid=(int) intent.getIntExtra(CLICKED_CELEB,0);
        final String currentCelebName=(String) intent.getStringExtra(CLICKED_CELEB+"name") ;

        name=(TextView) findViewById(R.id.celeb_name);
        placeOfBirth=(TextView) findViewById(R.id.celeb_birth_place);
        birthday=(TextView) findViewById(R.id.celeb_birhday);
        biography=(TextView) findViewById(R.id.celeb_biography);

        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.celeb_poster);
        final ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(currentCelebName);
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_backdrop);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        final ImageView backdrop=(ImageView) findViewById(R.id.celeb_backdrop);

        Call<OneCelebResponse> ShowCall = apiInterface.getCeleb(currentCelebid,getString(R.string.API_KEY));
        ShowCall.enqueue(new retrofit2.Callback<OneCelebResponse>() {
            @Override
            public void onResponse(Call<OneCelebResponse> call, Response<OneCelebResponse> response) {
                currentCeleb= response.body();

                name.setText(currentCeleb.getName());
                birthday.setText(currentCeleb.getBirthday());
                placeOfBirth.setText(currentCeleb.getPlace_of_birth());
                biography.setText(currentCeleb.getBiography());
                Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + currentCeleb.getProfile_path()).resize(mScreenWidth,0).error(android.R.drawable.stat_notify_error).into(backdrop,new Callback.EmptyCallback() {
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
            }
            @Override
            public void onFailure(Call<OneCelebResponse> call, Throwable t) {

            }
        });

        imagesViewer=(RecyclerView) findViewById(R.id.celeb_detail_image_viewer);
        imagesViewer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageAdapter=new MovieDetailImageAdapter(this,PhotosList,this);
        imagesViewer.setAdapter(imageAdapter);
        Call<ImageResponse> ImageCall = apiInterface.getCelebImage(currentCelebid,getString(R.string.API_KEY));
        ImageCall.enqueue(new retrofit2.Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse imageResponse = response.body();
                ArrayList<ImageResponse.Image> m=imageResponse.getProfiles();
                PhotosList.clear();
                PhotosList.addAll(m);
                imageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });

        creditView=(RecyclerView) findViewById(R.id.credit_recycler_view);
        creditView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        celebCreditAdapter=new CelebCreditAdapter(this,creditList,this);
        creditView.setAdapter(celebCreditAdapter);
        Call<CelebCreditResponse> celebCreditCall = apiInterface.getCelebCredit(currentCelebid,getString(R.string.API_KEY));
        celebCreditCall.enqueue(new retrofit2.Callback<CelebCreditResponse>() {
            @Override
            public void onResponse(Call<CelebCreditResponse> call, Response<CelebCreditResponse> response) {
                CelebCreditResponse creditResponse= response.body();
                ArrayList<CelebCreditResponse.Cast> m=creditResponse.getCast();
                creditList.clear();
                creditList.addAll(m);
                celebCreditAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<CelebCreditResponse> call, Throwable t) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                File image = new File(sdCardDirectory,currentCeleb.getName()+"_backdrop.png");
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
                    Toast.makeText(getApplicationContext(),currentCeleb.getName()+" image successfully saved",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error during image saving", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onImageClicked(View view) {
        //call intent to zoom image
        String imageUrl=(String) view.getTag();
        Intent i=new Intent(this,ImageIndependentViewer.class);
        i.putExtra("url",imageUrl);
        startActivity(i);
    }

    @Override
    public void onItemClicked(View v, int position) {
        //credit is clicked
        if(creditList.get(position).getMedia_type().equals("movie")){
            Intent intent=new Intent(getApplicationContext(),MovieDetailActivity.class);
            intent.putExtra("id",creditList.get(position).getId());
            intent.putExtra("backdrop",creditList.get(position).getBackdrop_path());
            intent.putExtra("title",creditList.get(position).getName());
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(getApplicationContext(),ShowDetailActiivity.class);
            intent.putExtra("title",creditList.get(position).getName());
            intent.putExtra("backdrop",creditList.get(position).getBackdrop_path());
            intent.putExtra("id",creditList.get(position).getId());
            startActivity(intent);
        }
    }
}

