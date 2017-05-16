/*
 * Copyright (C) 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.assis.redondo.daniel.popularmovies.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;
import com.assis.redondo.daniel.popularmovies.api.response.MovieReviewResponse;
import com.assis.redondo.daniel.popularmovies.api.response.MovieVideoResponse;
import com.assis.redondo.daniel.popularmovies.api.service.TheMovieDBApiService;
import com.assis.redondo.daniel.popularmovies.view.adapter.InfoPageAdapter;
import com.assis.redondo.daniel.popularmovies.view.adapter.holder.InfoViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MovieTabFragment extends Fragment {


    @BindView(R.id.errorLayout)
    LinearLayout mErrorLayout;
    @BindView(R.id.loadingLayout)
    LinearLayout mLoadingLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private Subscription mSubMovies;
    private Subscription mSubMovieReviews;
    private static final String TAB_INFO = "TAB_INFO";
    private static final String MOVIE_MODEL = "MOVIE_MODEL";
    private MovieDetailActivity.TabInfo mTab;
    private MovieModel mMovieModel;

    public static Fragment newInstance(MovieDetailActivity.TabInfo tabInfo, MovieModel movieModel) {
        Bundle args = new Bundle();
        args.putSerializable(TAB_INFO, tabInfo);
        args.putSerializable(MOVIE_MODEL, movieModel);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_tab, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setNestedScrollingEnabled(false);
        if (getArguments() != null) {
            mTab = (MovieDetailActivity.TabInfo) getArguments().getSerializable(TAB_INFO);
            mMovieModel = (MovieModel) getArguments().getSerializable(MOVIE_MODEL);
        }

        loadInfo(mTab, mMovieModel.getId());
        return view;
    }

    private void loadInfo(MovieDetailActivity.TabInfo tab, int movieId) {
        setupLoadingUI();
        if (tab == MovieDetailActivity.TabInfo.REVIEWS) {
            requestMovieReviews(movieId);
        } else if (tab == MovieDetailActivity.TabInfo.TRAILERS) {
            requestMovieTrailerInfo(movieId);
        }
    }

    private void setupLoadingUI() {
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.errorLayout})
    protected void reload() {
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        if (mTab == MovieDetailActivity.TabInfo.REVIEWS) {
            requestMovieReviews(mMovieModel.getId());
        } else if (mTab == MovieDetailActivity.TabInfo.TRAILERS) {
            requestMovieTrailerInfo(mMovieModel.getId());
        }
    }

    private void setupErrorUI() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    private void requestMovieTrailerInfo(int movieId) {

        Observable<MovieVideoResponse> call = TheMovieDBApiService.INSTANCE.getMoviesAPI()
                .retrieveMovieTrailer(movieId, getActivity().getResources().getString(R.string.api_key));

        if (mSubMovies != null) {
            mSubMovies.unsubscribe();
            mSubMovies = null;
        }

        mSubMovies = call.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::movieTrailersLoaded, this::movieTrailersError);
    }

    private void requestMovieReviews(int movieId) {

        Observable<MovieReviewResponse> call = TheMovieDBApiService.INSTANCE.getMoviesAPI()
                .retrieveMovieReviews(movieId, getActivity().getResources().getString(R.string.api_key));

        if (mSubMovieReviews != null) {
            mSubMovieReviews.unsubscribe();
            mSubMovieReviews = null;
        }

        mSubMovieReviews = call.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::movieReviewsLoaded, this::movieReviewsError);
    }

    protected void movieReviewsLoaded(MovieReviewResponse movieReviewResponse) {
        Timber.v("movie review loaded");
        mLoadingLayout.setVisibility(View.GONE);
        if(movieReviewResponse.getResults() != null) {
            mRecyclerView.setAdapter(new InfoPageAdapter(getActivity(),movieReviewResponse.getResults()));
        }
    }

    protected void movieReviewsError(Throwable throwable) {
        Timber.e(throwable, "movie review load error");
        setupErrorUI();
    }

    protected void movieTrailersLoaded(MovieVideoResponse movieVideoResponse) {
        Timber.v("movie trailer loaded");
        mLoadingLayout.setVisibility(View.GONE);
        if(movieVideoResponse.getResults() != null) {
            mRecyclerView.setAdapter(new InfoPageAdapter(getActivity(),movieVideoResponse.getResults()));
        }
    }

    protected void movieTrailersError(Throwable throwable) {
        Timber.e(throwable, "movie trailer load error");
        setupErrorUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (mSubMovies != null) {
            mSubMovies.unsubscribe();
            mSubMovies = null;
        }

        if (mSubMovieReviews != null) {
            mSubMovieReviews.unsubscribe();
            mSubMovieReviews = null;
        }
    }
}
