package com.assis.redondo.daniel.popularmovies.api.response;

import com.assis.redondo.daniel.popularmovies.api.model.MovieGenreModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieProductionCompaniesModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieSpokenLanguagesModel;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieDetailResponse implements Serializable {

    private long id;
    private boolean adult;
    private String backdrop_path;
    private Object belongs_to_collection;
    private long budget; //63000000
    private MovieGenreModel[] genres;
    private String homepage;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private MovieProductionCompaniesModel[] production_companies;
    private String release_date;
    private int revenue;
    private int runtime;
    private MovieSpokenLanguagesModel[] spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    public long getId() {
        return id;
    }

    public boolean isRatedRMovie() {
        return adult;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public Object getBelongsToCollection() {
        return belongs_to_collection;
    }

    public long getBudget() {
        return budget;
    }

    public MovieGenreModel[] getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdb_id;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public MovieProductionCompaniesModel[] getProductionCompanies() {
        return production_companies;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public MovieSpokenLanguagesModel[] getSpokenLanguages() {
        return spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public int getVoteCount() {
        return vote_count;
    }
}
