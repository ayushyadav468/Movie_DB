package com.example.nitantsood.moviedb.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.nitantsood.moviedb.APIResponses.CastResponse;
import com.example.nitantsood.moviedb.APIResponses.CelebCreditResponse;
import com.example.nitantsood.moviedb.APIResponses.CelebrityResponse;
import com.example.nitantsood.moviedb.APIResponses.GenresResponse;
import com.example.nitantsood.moviedb.APIResponses.ImageResponse;
import com.example.nitantsood.moviedb.APIResponses.MoviesResponse;
import com.example.nitantsood.moviedb.APIResponses.OneCelebResponse;
import com.example.nitantsood.moviedb.APIResponses.OneMovieResponse;
import com.example.nitantsood.moviedb.APIResponses.OneShowResponse;
import com.example.nitantsood.moviedb.APIResponses.ReviewResponse;
import com.example.nitantsood.moviedb.APIResponses.TvResponse;
import com.example.nitantsood.moviedb.APIResponses.TvShows;
import com.example.nitantsood.moviedb.APIResponses.VideoResponse;


/**
 * Created by NITANT SOOD on 25-07-2017.
 */

public interface ApiInterface{
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenresResponse> getGenre(@Query("api_key") String apiKey);

    @GET("genre/tv/list")
    Call<GenresResponse> getTvGenre(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvResponse> getPopularTvShows(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<TvResponse> getTopRatedTvShows(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TvResponse> getTvAiringToday(@Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<TvResponse> getTvAiring(@Query("api_key") String apiKey);

    @GET("person/popular")
    Call<CelebrityResponse> getPopularCelebrity(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<OneMovieResponse> getMovie(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/images")
    Call<ImageResponse> getImage(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCast(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideo(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<MoviesResponse> getSimilarMovie(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<OneShowResponse> getShow(@Path("tv_id")int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<VideoResponse> getShowVideo(@Path("tv_id")int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/credits")
    Call<CastResponse> getShowCast(@Path("tv_id")int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/similar")
    Call<TvResponse> getSimilarShow(@Path("tv_id")int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}")
    Call<OneCelebResponse> getCeleb(@Path("person_id")int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/images")
    Call<ImageResponse> getCelebImage(@Path("person_id")int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/combined_credits")
    Call<CelebCreditResponse> getCelebCredit(@Path("person_id")int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MoviesResponse> getSearchedMovie(@Query("api_key") String apiKey,@Query("query") String search);

    @GET("search/person")
    Call<CelebrityResponse> getSearchedPerson(@Query("api_key") String apiKey,@Query("query") String search);


}
