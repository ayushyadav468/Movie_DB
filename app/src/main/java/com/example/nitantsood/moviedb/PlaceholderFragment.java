package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.nitantsood.moviedb.APIResponses.Celebrity;
import com.example.nitantsood.moviedb.APIResponses.CelebrityResponse;
import com.example.nitantsood.moviedb.APIResponses.GenresResponse;
import com.example.nitantsood.moviedb.APIResponses.MoviesResponse;
import com.example.nitantsood.moviedb.APIResponses.TvResponse;
import com.example.nitantsood.moviedb.APIResponses.TvShows;
import com.example.nitantsood.moviedb.APIResponses.results;
import com.example.nitantsood.moviedb.Adapter.CategoryListAdapter;
import com.example.nitantsood.moviedb.Adapter.CelebrityListAdapter;
import com.example.nitantsood.moviedb.Retrofit.ApiInterface;
import com.example.nitantsood.moviedb.Retrofit.Client;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by NITANT SOOD on 26-07-2017.
 */

public  class PlaceholderFragment extends Fragment implements CategoryListAdapter.itemClickListener, CelebrityListAdapter.celebItemClickListener, BaseSliderView.OnSliderClickListener {
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;


    public static final String TAB_NUMBER = "tab_number";
    public static final int MOVIES=0;
    public static final int TV_SHOWS=1;
    public static final int CELEBS=2;

    public static final int POPULAR_MOVIES=10;
    public static final int TOP_RATED_MOVIES=20;
    public static final int UPCOMING_MOVIES=30;

    public static final int POPULAR_TV_SHOWS=40;
    public static final int TOP_RATED_TV_SHOWS=50;
    public static final int AIRING_TODAY_TV_SHOWS=60;

    ArrayList<GenresResponse.genres> genres=new ArrayList<>();
    ArrayList<GenresResponse.genres> TvGenres=new ArrayList<>();

    ArrayList<results> popularMovies=new ArrayList<>();
    ArrayList<results> topRatedMovies=new ArrayList<>();
    ArrayList<results> upcomingMovies=new ArrayList<>();
    ArrayList<results> nowPlayingMovies=new ArrayList<>();

    ArrayList<TvShows> popularTvShows=new ArrayList<>();
    ArrayList<TvShows> topRatedTvShows=new ArrayList<>();
    ArrayList<TvShows> airingTodayTvShows=new ArrayList<>();
    ArrayList<TvShows> onAirTvShows=new ArrayList<>();

    ArrayList<Celebrity> popularCelebrities=new ArrayList<>();

    Client client = new Client();

    CategoryListAdapter adapter1;
    CategoryListAdapter adapter2;
    CategoryListAdapter adapter3;

    CategoryListAdapter adapter4;
    CategoryListAdapter adapter5;
    CategoryListAdapter adapter6;

    CelebrityListAdapter celebrityListAdapter;

    RecyclerView categoryView1;
    RecyclerView categoryView2;
    RecyclerView categoryView3;

    RecyclerView categoryView4;
    RecyclerView categoryView5;
    RecyclerView categoryView6;

    RecyclerView celebRecyclerView;

    final static String CLICKED_MOVIE="ClickedMovie";
    final static String CLICKED_SHOW="ClickedShow";
    final static String CLICKED_CELEB="ClickedCeleb";

    static HashMap<Integer,String> genre=new HashMap<>();
    HashMap<Integer,String> TvGenre=new HashMap<>();
    HashMap<Integer,String> MoviesBackdropPath=new HashMap<>();
    HashMap<Integer,String> MoviesName=new HashMap<>();
    HashMap<Integer,String> TvShowBackdropPath=new HashMap<>();
    HashMap<Integer,String> TvShowName=new HashMap<>();

    TextView listType1;
    TextView listType2;
    TextView listType3;

    TextView listType4;
    TextView listType5;
    TextView listType6;

    SliderLayout movieSlider;
    SliderLayout tvShowSlider;

    public PlaceholderFragment() {

    }
    public void onGenreDownload(){
        for(int i=0;i<genres.size();i++){
            int id=genres.get(i).getId();
            String name=genres.get(i).getName();
            genre.put(id,name);
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }
    public void onTvGenreDownload(){
        for(int i=0;i<TvGenres.size();i++){
            int id=TvGenres.get(i).getId();
            String name=TvGenres.get(i).getName();
            TvGenre.put(id,name);
        }
        adapter4.notifyDataSetChanged();
        adapter5.notifyDataSetChanged();
        adapter6.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int tab=getArguments().getInt(TAB_NUMBER);
        Retrofit retrofit = client.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(tab==MOVIES) {
            View rootView = inflater.inflate(R.layout.fragment_master,container, false);
            movieSlider=(SliderLayout) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.slider);
            movieSlider.setMinimumHeight(50);
            final Call<MoviesResponse>  nowPlayingMoviesCall=apiInterface.getNowPlayingMovies(
                    getString(R.string.API_KEY));
            nowPlayingMoviesCall.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse moviesResponse = response.body();
                    ArrayList<results> m = moviesResponse.getResults();
                    nowPlayingMovies.clear();
                    nowPlayingMovies.addAll(m);
                    for(int i=0;i<nowPlayingMovies.size();i++){
                        MoviesBackdropPath.put(nowPlayingMovies.get(i).getId(),getString(R.string.image_getter)+nowPlayingMovies.get(i).getBackdrop_path());
                        MoviesName.put(nowPlayingMovies.get(i).getId(),nowPlayingMovies.get(i).getTitle());
                    }
                    for(int currentMovie:MoviesBackdropPath.keySet()){
                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(MoviesName.get(currentMovie))
                                .image(MoviesBackdropPath.get(currentMovie))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                               .setOnSliderClickListener(PlaceholderFragment.this);

                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putInt("extra",currentMovie);
                        textSliderView.getBundle()
                                .putInt("type",MOVIES);

                        movieSlider.addSlider(textSliderView);
                    }
                    movieSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    movieSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    movieSlider.setCustomAnimation(new DescriptionAnimation());
                    movieSlider.setDuration(4000);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {

                }
            });
            {
                listType1 = (TextView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_1).findViewById(R.id.list_type);
                listType1.setText("Popular Movies");
                listType1.setTextColor(Color.WHITE);
                categoryView1 = (RecyclerView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_1).findViewById(R.id.category_list);
                categoryView1.setMinimumHeight(200);
                categoryView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter1 = new CategoryListAdapter(PlaceholderFragment.this, getContext(),MOVIES,POPULAR_MOVIES,popularMovies,null,genre);
                categoryView1.setAdapter(adapter1);

                Call<MoviesResponse> popularMoviesCall = apiInterface.getPopularMovies(getString(R.string.API_KEY));
                popularMoviesCall.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        MoviesResponse moviesResponse = response.body();



                        ArrayList<results> m = moviesResponse.getResults();
                       popularMovies.clear();
                       popularMovies.addAll(m);
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
            }
            {
                listType2 = (TextView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_2).findViewById(R.id.list_type);
                listType2.setText("Top Rated Movies");
                listType2.setTextColor(Color.WHITE);
                categoryView2 = (RecyclerView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_2).findViewById(R.id.category_list);
                categoryView2.setMinimumHeight(200);
                categoryView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter2 = new CategoryListAdapter(PlaceholderFragment.this, getContext(),MOVIES,TOP_RATED_MOVIES,topRatedMovies,null,genre);
                categoryView2.setAdapter(adapter2);
                Call<MoviesResponse> topRatedMoviesCall = apiInterface.getTopRatedMovies(getString(R.string.API_KEY));
                topRatedMoviesCall.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        Toast.makeText(getContext(),"connected",Toast.LENGTH_SHORT).show();
                        MoviesResponse moviesResponse = response.body();
                        ArrayList<results> m = moviesResponse.getResults();
                        topRatedMovies.clear();
                        topRatedMovies.addAll(m);
                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Toast.makeText(getContext(),"error in connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            {
                listType3 = (TextView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_3).findViewById(R.id.list_type);
                listType3.setText("Upcoming Movies");
                listType3.setTextColor(Color.WHITE);
                categoryView3 = (RecyclerView) rootView.findViewById(R.id.fragmentMain).findViewById(R.id.list_3).findViewById(R.id.category_list);
                categoryView3.setMinimumHeight(200);
                categoryView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter3= new CategoryListAdapter(PlaceholderFragment.this, getContext(),MOVIES,UPCOMING_MOVIES,upcomingMovies,null,genre);
                categoryView3.setAdapter(adapter3);
                Call<MoviesResponse> upcomingMoviesCall = apiInterface.getUpcomingMovies(getString(R.string.API_KEY));

                upcomingMoviesCall.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        MoviesResponse moviesResponse = response.body();

                        ArrayList<results> m = moviesResponse.getResults();
                        upcomingMovies.clear();
                        upcomingMovies.addAll(m);
                        adapter3.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
               }


            Call<GenresResponse> genresResponseCall=apiInterface.getGenre(getString(R.string.API_KEY));
            genresResponseCall.enqueue(new Callback<GenresResponse>() {
                @Override
                public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                    GenresResponse genresResponse=response.body();
                    ArrayList<GenresResponse.genres> g=genresResponse.getGenres();
                    genres.clear();
                    genres.addAll(g);
                    onGenreDownload();
                }
                @Override
                public void onFailure(Call<GenresResponse> call, Throwable t) {

                }
            });
        return rootView;
        }
        else if(tab==TV_SHOWS){
            View rootView2 = inflater.inflate(R.layout.fragment_master, container, false);
            tvShowSlider=(SliderLayout) rootView2.findViewById(R.id.fragmentMain).findViewById(R.id.slider);
            tvShowSlider.setMinimumHeight(50);
            Call<TvResponse> onAirTvShowsCall = apiInterface.getTvAiring(getString(R.string.API_KEY));
            onAirTvShowsCall.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    ArrayList<TvShows> m = tvResponse.getResults();
                    onAirTvShows.clear();
                    onAirTvShows.addAll(m);
                    for(int i=0;i<onAirTvShows.size();i++){
                        TvShowBackdropPath.put(onAirTvShows.get(i).getId(),getString(R.string.image_getter)+onAirTvShows.get(i).getBackdrop_path());
                        TvShowName.put(onAirTvShows.get(i).getId(),onAirTvShows.get(i).getTitle());
                    }
                    for(int currentShow:TvShowBackdropPath.keySet()){
                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout
                        textSliderView
                                .description(TvShowName.get(currentShow))
                                .image(TvShowBackdropPath.get(currentShow))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(PlaceholderFragment.this);

                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putInt("extra",currentShow);
                        textSliderView.getBundle()
                                .putInt("type",TV_SHOWS);


                        tvShowSlider.addSlider(textSliderView);
                    }
                    tvShowSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    tvShowSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    tvShowSlider.setCustomAnimation(new DescriptionAnimation());
                    tvShowSlider.setDuration(4000);

                }

                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
            {
                listType4=(TextView) rootView2.findViewById(R.id.fragmentMain).findViewById(R.id.list_1).findViewById(R.id.list_type);
                listType4.setText("Popular Shows");
                listType4.setTextColor(Color.WHITE);
                categoryView4= (RecyclerView) rootView2.findViewById(R.id.list_1).findViewById(R.id.category_list);
                categoryView4.setMinimumHeight(200);
                categoryView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter4 = new CategoryListAdapter(PlaceholderFragment.this, getContext(),TV_SHOWS,POPULAR_TV_SHOWS,null,popularTvShows,TvGenre);
                categoryView4.setAdapter(adapter4);

                Call<TvResponse> popularTvShowsCall = apiInterface.getPopularTvShows(getString(R.string.API_KEY));
                popularTvShowsCall.enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        TvResponse tvResponse = response.body();
                        ArrayList<TvShows> m = tvResponse.getResults();
                        popularTvShows.clear();
                        popularTvShows.addAll(m);
                        adapter4.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {

                    }
                });
            }
            {
                listType5=(TextView) rootView2.findViewById(R.id.fragmentMain).findViewById(R.id.list_2).findViewById(R.id.list_type);
                listType5.setText("Top Rated Shows");
                listType5.setTextColor(Color.WHITE);
                categoryView5= (RecyclerView) rootView2.findViewById(R.id.list_2).findViewById(R.id.category_list);
                categoryView5.setMinimumHeight(200);
                categoryView5.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter5= new CategoryListAdapter(PlaceholderFragment.this, getContext(),TV_SHOWS,POPULAR_TV_SHOWS,null,topRatedTvShows,TvGenre);
                categoryView5.setAdapter(adapter5);

                Call<TvResponse> topRatedTvShowsCall = apiInterface.getTopRatedTvShows(getString(R.string.API_KEY));
                topRatedTvShowsCall.enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        TvResponse tvResponse = response.body();
                        ArrayList<TvShows> m = tvResponse.getResults();
                        topRatedTvShows.clear();
                        topRatedTvShows.addAll(m);
                        adapter5.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {

                    }
                });
            }
            {
                listType6=(TextView) rootView2.findViewById(R.id.fragmentMain).findViewById(R.id.list_3).findViewById(R.id.list_type);
                listType6.setText("Airing Today..");
                listType6.setTextColor(Color.WHITE);
                categoryView6= (RecyclerView) rootView2.findViewById(R.id.list_3).findViewById(R.id.category_list);
                categoryView6.setMinimumHeight(200);
                categoryView6.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter6= new CategoryListAdapter(PlaceholderFragment.this, getContext(),TV_SHOWS,POPULAR_TV_SHOWS,null,airingTodayTvShows,TvGenre);
                categoryView6.setAdapter(adapter6);

                Call<TvResponse> onAirTodayTvShowsCall = apiInterface.getTvAiringToday(getString(R.string.API_KEY));
                onAirTodayTvShowsCall.enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        TvResponse tvResponse = response.body();
                        ArrayList<TvShows> m = tvResponse.getResults();
                        airingTodayTvShows.clear();
                        airingTodayTvShows.addAll(m);
                        adapter6.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {

                    }
                });
            }

            Call<GenresResponse> tvGenresResponseCall=apiInterface.getTvGenre(getString(R.string.API_KEY));
            tvGenresResponseCall.enqueue(new Callback<GenresResponse>() {
                @Override
                public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                    GenresResponse genresResponse=response.body();
                    ArrayList<GenresResponse.genres> g=genresResponse.getGenres();
                    TvGenres.clear();
                    TvGenres.addAll(g);
                    onTvGenreDownload();
                }
                @Override
                public void onFailure(Call<GenresResponse> call, Throwable t) {

                }
            });
//
            return rootView2;
        }else if(tab==CELEBS){
            View rootView3 = inflater.inflate(R.layout.popular_celeb_homepage, container, false);
            celebRecyclerView=(RecyclerView) rootView3.findViewById(R.id.popular_celeb_list_homepage);
            celebRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            celebRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            celebrityListAdapter=new CelebrityListAdapter(getContext(),popularCelebrities,PlaceholderFragment.this);
            celebRecyclerView.setAdapter(celebrityListAdapter);
            Call<CelebrityResponse> popularCelebrityCall = apiInterface.getPopularCelebrity(getString(R.string.API_KEY));
            popularCelebrityCall.enqueue(new Callback<CelebrityResponse>() {
                @Override
                public void onResponse(Call<CelebrityResponse> call, Response<CelebrityResponse> response) {
                    CelebrityResponse celebrityResponse = response.body();
                    ArrayList<Celebrity> m = celebrityResponse.getResults();
                    popularCelebrities.clear();
                    popularCelebrities.addAll(m);
                    celebrityListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<CelebrityResponse> call, Throwable t) {

                }
            });
            return rootView3;
        }
            return null;
    }

    @Override
    public void celebItemCLicked(View view, int position) {
//        Toast.makeText(getContext(),popularCelebrities.get(position).getName()+" Clicked",Toast.LENGTH_SHORT).show();
        int id=popularCelebrities.get(position).getId();
        Intent intent=new Intent(getContext(),CelebDetailActivity.class);
        intent.putExtra(CLICKED_CELEB,id);
        intent.putExtra(CLICKED_CELEB+"name",popularCelebrities.get(position).getName());
        startActivity(intent);
    }

    @Override
    public void movieItemCLicked(View view, int position) {
        results clickedMovie=(results) view.getTag();
        Intent intent=new Intent(getContext(),MovieDetailActivity.class);
        intent.putExtra("id",clickedMovie.getId());
        intent.putExtra("backdrop",clickedMovie.getBackdrop_path());
        intent.putExtra("title",clickedMovie.getTitle());
        startActivity(intent);
    }

    @Override
    public void showItemCLicked(View view, int position) {
        TvShows clickedShow=(TvShows) view.getTag();
        Intent intent=new Intent(getContext(),ShowDetailActiivity.class);
        intent.putExtra("title",clickedShow.getTitle());
        intent.putExtra("backdrop",clickedShow.getBackdrop_path());
        intent.putExtra("id",clickedShow.getId());
        startActivity(intent);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        if(slider.getBundle().getInt("type")==MOVIES) {
            int id;
            results currentMovie = null;
            for (int i = 0; i < nowPlayingMovies.size(); i++) {
                id = nowPlayingMovies.get(i).getId();
                if (id == slider.getBundle().getInt("extra")) {
                    currentMovie = nowPlayingMovies.get(i);
                    break;
                }
            }
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("id",currentMovie.getId());
            intent.putExtra("backdrop",currentMovie.getBackdrop_path());
            intent.putExtra("title",currentMovie.getTitle());
            startActivity(intent);
        }
        else if(slider.getBundle().getInt("type")==TV_SHOWS){
            int id;
            TvShows currentShow = null;
            for (int i = 0; i < onAirTvShows.size(); i++) {
                id = onAirTvShows.get(i).getId();
                if (id == slider.getBundle().getInt("extra")) {
                    currentShow = onAirTvShows.get(i);
                    break;
                }
            }
            Intent intent = new Intent(getContext(), ShowDetailActiivity.class);
            intent.putExtra("title",currentShow.getTitle());
            intent.putExtra("backdrop",currentShow.getBackdrop_path());
            intent.putExtra("id",currentShow.getId());
            startActivity(intent);
        }
    }
}