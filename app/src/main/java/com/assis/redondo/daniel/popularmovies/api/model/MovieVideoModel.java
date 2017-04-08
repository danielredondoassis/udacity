package com.assis.redondo.daniel.popularmovies.api.model;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieVideoModel implements Serializable {

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public String getId() {
        return id;
    }

    public String getISO639_1() {
        return iso_639_1;
    }

    public String getISO3166_1() {
        return iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
