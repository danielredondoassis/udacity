package com.assis.redondo.daniel.popularmovies.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.response.PopularMoviesResponse;
import com.assis.redondo.daniel.popularmovies.view.adapter.holder.MovieHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class MoviesAdapter extends BaseAdapter {

    private static final String TAG = MoviesAdapter.class.getSimpleName();

    private ArrayList<MovieModel> mSource;
    private static final int HOLDER_POSTER = 0;


    private Context context;

    private MoviesAdapter(Builder builder) {
        this.mSource = builder.mSource;
        this.context = builder.context;
    }

    public static class Builder {
        private Context context;
        private ArrayList<MovieModel> mSource;

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder setSource(Object... objects) {
            this.mSource = objects[0] instanceof PopularMoviesResponse ?
                    ((PopularMoviesResponse) objects[0]).getResults() != null ?
                            new ArrayList<>(Arrays.asList(((PopularMoviesResponse) objects[0]).getResults()))
                            : null : null;
            return this;
        }

        public Builder setFavoriteSource(ArrayList<MovieModel> favoriteMovies) {
            this.mSource = new ArrayList<>(favoriteMovies);
            return this;
        }

        public MoviesAdapter build() {
            return new MoviesAdapter(this);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = null;
        MovieHolder holder = null;

        v = inflater.inflate(R.layout.holder_movie_poster, parent, false);
        holder = new MovieHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieHolder movieHolder = (MovieHolder) holder;
        MovieModel movieModel = mSource.get(position);
        movieHolder.setup(context, movieModel);
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        return mSource != null ? mSource.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return HOLDER_POSTER;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
