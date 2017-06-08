package com.assis.redondo.daniel.popularmovies.view.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.database.MoviesCPContract;
import com.assis.redondo.daniel.popularmovies.view.adapter.MoviesAdapter;
import com.jakewharton.rxbinding.internal.MainThreadSubscription;

import java.util.ArrayList;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void setupMoviesAdapter( ArrayList<MovieModel> favoriteMovies) {
        mLoadingLayout.setVisibility(View.GONE);

        Cursor cursor =  getContentResolver().query(MoviesCPContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                MoviesCPContract.MovieEntry.COLUMN_TITLE);

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
        checkFavorites();
    }

    private void checkFavorites() {
        new AsyncTask<Void,Void,String>() {

            @Override
            protected String doInBackground(Void... params) {

                try {
                    Cursor cursor =  getContentResolver().query(MoviesCPContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MoviesCPContract.MovieEntry.COLUMN_TITLE);

                    ArrayList<MovieModel> favoriteMovies;
                    favoriteMovies = convertCursorToMovies(cursor);
                    runOnUiThread(new MainThreadSubscription() {
                        @Override
                        protected void onUnsubscribe() {
                            setupMoviesAdapter(favoriteMovies);
                        }
                    });

                } catch (Exception e) {
                    Log.e("DERP", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }

                return null;
            }
        }.execute();
    }

    private ArrayList<MovieModel> convertCursorToMovies(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry._ID);
        int originalLanguageIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE);
        int titleIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_TITLE);
        int originalTitleIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        int adultIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_ADULT);
        int popularityIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_POPULARITY);
        int posterPathIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_POSTER_PATH);
        int overviewIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_OVERVIEW);
        int releaseDateIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_RELEASE_DATE);
        int voteAverageIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        int voteCountIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_VOTE_COUNT);
        int videoIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_VIDEO);

        ArrayList<MovieModel> favoriteMovies = new ArrayList<MovieModel>();

        while(cursor.moveToNext()) {

            MovieModel movieModel = new MovieModel();
            movieModel.setId(cursor.getInt(idIndex));
            movieModel.setOriginal_language(cursor.getString(originalLanguageIndex));
            movieModel.setOriginal_title(cursor.getString(originalTitleIndex));
            movieModel.setTitle(cursor.getString(titleIndex));
            movieModel.setAdult(cursor.getInt(adultIndex) > 0);
            movieModel.setPopularity(cursor.getDouble(popularityIndex));
            movieModel.setPoster_path(cursor.getString(posterPathIndex));
            movieModel.setOverview(cursor.getString(overviewIndex));
            movieModel.setRelease_date(cursor.getString(releaseDateIndex));
            movieModel.setVote_average(cursor.getDouble(voteAverageIndex));
            movieModel.setVote_count(cursor.getInt(voteCountIndex));
            movieModel.setVideo(cursor.getInt(videoIndex) > 0);

            if(!favoriteMovies.contains(movieModel)) favoriteMovies.add(movieModel);
        }
        cursor.close();
        return favoriteMovies;
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
