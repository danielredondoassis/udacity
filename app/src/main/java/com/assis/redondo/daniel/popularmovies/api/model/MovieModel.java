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

}
