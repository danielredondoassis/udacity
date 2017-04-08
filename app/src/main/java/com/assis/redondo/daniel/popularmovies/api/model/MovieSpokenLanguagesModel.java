package com.assis.redondo.daniel.popularmovies.api.model;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieSpokenLanguagesModel  implements Serializable {

    private String iso_639_1;
    private String name;

    public String getISO639_1() {
        return iso_639_1;
    }

    public String getName() {
        return name;
    }
}
