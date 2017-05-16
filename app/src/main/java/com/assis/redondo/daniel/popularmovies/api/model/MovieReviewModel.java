package com.assis.redondo.daniel.popularmovies.api.model;

import java.io.Serializable;

/**
 * Created by DT on 3/30/17.
 */

public class MovieReviewModel implements Serializable {

    private String id;
    private String author;
    private String content;
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
