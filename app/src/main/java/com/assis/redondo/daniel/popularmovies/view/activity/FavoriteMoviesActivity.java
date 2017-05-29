package com.assis.redondo.daniel.popularmovies.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.response.PopularMoviesResponse;
import com.assis.redondo.daniel.popularmovies.api.service.TheMovieDBApiService;
import com.assis.redondo.daniel.popularmovies.controller.DataBaseController;
import com.assis.redondo.daniel.popularmovies.view.adapter.MoviesAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class FavoriteMoviesActivity extends AppCompatActivity {

    @BindView(R.id.loadingLayout)
    LinearLayout mLoadingLayout;
    @BindView(R.id.errorLayout)
    LinearLayout mErrorLayout;
    @BindView(R.id.textError)
    TextView mTextError;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        ButterKnife.bind(this);

        this.setSupportActionBar(mToolbar);
        setupUI();
        getSupportActionBar().setTitle(getString(R.string.favorite_movies));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUI() {
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    public void setupMoviesAdapter() {
        ArrayList<MovieModel> favoriteMovies = new ArrayList<MovieModel>(DataBaseController.INSTANCE.getFavoriteMovies());

        if(favoriteMovies.size() > 0){

        mLoadingLayout.setVisibility(View.GONE);

        mAdapter = new MoviesAdapter
                .Builder()
                .with(this)
                .setFavoriteSource(favoriteMovies)
                .build();


        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        } else {
            mErrorLayout.setVisibility(View.VISIBLE);
            mTextError.setText(getString(R.string.no_favorite_movies));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupMoviesAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
