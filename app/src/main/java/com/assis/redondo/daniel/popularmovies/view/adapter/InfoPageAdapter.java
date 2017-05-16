package com.assis.redondo.daniel.popularmovies.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;
import com.assis.redondo.daniel.popularmovies.view.MovieDetailActivity;
import com.assis.redondo.daniel.popularmovies.view.adapter.holder.InfoViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DT on 5/16/17.
 */

public class InfoPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MovieVideoModel> mTrailerSource;
    private ArrayList<MovieReviewModel> mReviewSource;
    private MovieDetailActivity.TabInfo mTab;

    public InfoPageAdapter(Context context, MovieReviewModel[] results) {
        this.context = context;
        mTab = MovieDetailActivity.TabInfo.REVIEWS;
        this.mReviewSource = new ArrayList<>(Arrays.asList(results));
    }

    public InfoPageAdapter(Context context, MovieVideoModel[] results) {
        this.context = context;
        mTab = MovieDetailActivity.TabInfo.TRAILERS;
        this.mTrailerSource = new ArrayList<>(Arrays.asList(results));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_card, viewGroup, false);

        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InfoViewHolder infoHolder = (InfoViewHolder) holder;

        if(mTab == MovieDetailActivity.TabInfo.REVIEWS){

            MovieReviewModel reviewModel = mReviewSource.get(position);
            infoHolder.setup(context, reviewModel);

        } else if(mTab == MovieDetailActivity.TabInfo.TRAILERS){

            MovieVideoModel trailerModel = mTrailerSource.get(position);
            infoHolder.setup(context, trailerModel);

        }
    }

    @Override
    public int getItemCount() {
        return mTab == MovieDetailActivity.TabInfo.REVIEWS ? mReviewSource.size() : mTrailerSource.size();
    }
}
