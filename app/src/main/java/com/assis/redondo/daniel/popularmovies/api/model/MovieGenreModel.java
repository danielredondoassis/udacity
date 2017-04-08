package com.assis.redondo.daniel.popularmovies.api.model;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieGenreModel implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
