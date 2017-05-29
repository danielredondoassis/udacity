package com.assis.redondo.daniel.popularmovies.api.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieReviewModel implements Serializable {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField()
    private String author;

    @DatabaseField()
    private String content;

    @DatabaseField()
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
