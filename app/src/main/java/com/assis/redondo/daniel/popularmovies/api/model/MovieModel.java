package com.assis.redondo.daniel.popularmovies.api.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieModel implements Serializable {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField()
    private String poster_path;

    @DatabaseField()
    private boolean adult;

    @DatabaseField()
    private String overview;

    @DatabaseField()
    private String release_date;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private int[] genre_ids;

    @DatabaseField()
    private String original_title;

    @DatabaseField()
    private String original_language;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String backdrop_path;

    @DatabaseField()
    private double popularity;

    @DatabaseField()
    private int vote_count;

    @DatabaseField()
    private boolean video;

    @DatabaseField()
    private double vote_average;

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public int[] getGenreIds() {
        return genre_ids;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public long getVoteCount() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public String getVoteAverage() {
        return vote_average + "/10";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass().equals(this.getClass())) {
            MovieModel object = (MovieModel) o;
            if(object.getId() == this.getId()) {
                return true;
            }
        }
        return false;
    }



}
