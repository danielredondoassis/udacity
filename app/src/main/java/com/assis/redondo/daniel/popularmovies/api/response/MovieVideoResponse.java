package com.assis.redondo.daniel.popularmovies.api.response;

import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieVideoResponse implements Serializable {

    private int id;
    private MovieVideoModel[] results;

    public long getId() {
        return id;
    }

    public MovieVideoModel[] getResults() {
        return results;
    }
}
