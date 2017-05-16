package com.assis.redondo.daniel.popularmovies.api.response;

import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieReviewResponse implements Serializable {

    private int id;
    private MovieReviewModel[] results;

    public int getId() {
        return id;
    }

    public MovieReviewModel[] getResults() {
        return results;
    }
}
