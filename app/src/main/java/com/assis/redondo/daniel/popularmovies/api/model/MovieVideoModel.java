package com.assis.redondo.daniel.popularmovies.api.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieVideoModel implements Serializable {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField()
    private String iso_639_1;

    @DatabaseField()
    private String iso_3166_1;

    @DatabaseField()
    private String key;

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String site;

    @DatabaseField()
    private String size;

    @DatabaseField()
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
