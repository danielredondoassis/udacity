package com.assis.redondo.daniel.popularmovies.api;

import com.assis.redondo.daniel.popularmovies.api.response.MovieReviewResponse;
import com.assis.redondo.daniel.popularmovies.api.response.MovieVideoResponse;
import com.assis.redondo.daniel.popularmovies.api.response.PopularMoviesResponse;
import com.assis.redondo.daniel.popularmovies.api.response.MovieDetailResponse;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rogerioshimizu on 10/10/16.
 */

public interface MoviesApiInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("discover/movie")
    Observable<PopularMoviesResponse> retrieveMovies(@Query("api_key") String apiKey, @Query("sort_by") String sort_option);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("popular")
    Observable<PopularMoviesResponse> retrievePopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("movie/{movie_id}/videos")
    Observable<MovieVideoResponse> retrieveMovieTrailer(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("movie/{movie_id}/reviews")
    Observable<MovieReviewResponse> retrieveMovieReviews(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("movie/{movie_id}")
    Observable<MovieDetailResponse> retrieveMovieDetailedInfo(@Path("movie_id") int movie_id,@Query("api_key") String apiKey,@Query("append_to_response") String field_append);
}

