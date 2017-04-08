package com.assis.redondo.daniel.popularmovies.api.response;

import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class PopularMoviesResponse implements Serializable {

    private int page;
    private MovieModel[] results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public MovieModel[] getResults() {
        return results;
    }

    public int getTotalResults() {
        return total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }
}
