package com.assis.redondo.daniel.popularmovies;

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

import com.assis.redondo.daniel.popularmovies.api.response.PopularMoviesResponse;
import com.assis.redondo.daniel.popularmovies.api.service.TheMovieDBApiService;
import com.assis.redondo.daniel.popularmovies.view.adapter.MoviesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MoviesActivity extends AppCompatActivity {

    private static final String SORT_OPTION = "SORT_OPTION";
    private static final String SORT_TITLE = "SORT_TITLE";
    private static final String MOVIE_RESPONSE = "MOVIE_RESPONSE";


    @BindView(R.id.loadingLayout)
    LinearLayout mLoadingLayout;
    @BindView(R.id.errorLayout)
    LinearLayout mErrorLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Subscription mSub;
    private MoviesAdapter mAdapter;
    private String mCurrentSort;
    private PopularMoviesResponse mMoviesResponse;
    private CharSequence mSortTitle;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SORT_TITLE,mSortTitle.toString());
        outState.putString(SORT_OPTION,mCurrentSort);
        outState.putSerializable(MOVIE_RESPONSE,mMoviesResponse);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            mSortTitle = savedInstanceState.getString(SORT_TITLE);
            mCurrentSort = savedInstanceState.getString(SORT_OPTION);
            mMoviesResponse = (PopularMoviesResponse) savedInstanceState.getSerializable(MOVIE_RESPONSE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            mSortTitle = savedInstanceState.getString(SORT_TITLE);
            mCurrentSort = savedInstanceState.getString(SORT_OPTION);
            mMoviesResponse = (PopularMoviesResponse) savedInstanceState.getSerializable(MOVIE_RESPONSE);
        }

        this.setSupportActionBar(mToolbar);

        if(mMoviesResponse != null) {
            getSupportActionBar().setTitle(mSortTitle);
            setupMoviesAdapter(mMoviesResponse);
        } else {
            setupLoadingUI();
            mSortTitle = "Popularidade ASC";
            requestMovies(getString(R.string.popularity_asc));
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
        requestMovies(mCurrentSort);
    }

    private void setupErrorUI() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    public void setupMoviesAdapter(PopularMoviesResponse moviesResponse) {
        mLoadingLayout.setVisibility(View.GONE);

        mAdapter = new MoviesAdapter
                .Builder()
                .with(this)
                .setSource(moviesResponse)
                .build();


        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mLoadingLayout.setVisibility(View.GONE);
    }

    private void requestMovies(String sortOption) {
        setupLoadingUI();
        mCurrentSort = sortOption;
        getSupportActionBar().setTitle(mSortTitle);

        Observable<PopularMoviesResponse> call = TheMovieDBApiService.INSTANCE.getMoviesAPI()
                .retrieveMovies(
                        getString(R.string.api_key),
                        sortOption);


        if (mSub != null) {
            mSub.unsubscribe();
            mSub = null;
        }

        mSub = call.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::moviesLoaded, this::movieError);
    }

    protected void moviesLoaded(PopularMoviesResponse moviesResponse) {
        Timber.v("movies loaded");

        mMoviesResponse = moviesResponse;

        // TODO view when API return 0 results;
        setupMoviesAdapter(moviesResponse);
    }

    protected void movieError(Throwable throwable) {
        Timber.e(throwable, "movies load error");
        setupErrorUI();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (mSub != null) {
            mSub.unsubscribe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mSortTitle = item.getTitle();
        switch (id) {
            case R.id.popularity_asc:
                requestMovies(getString(R.string.popularity_asc));
                return true;
            case R.id.popularity_desc:
                requestMovies(getString(R.string.popularity_desc));
                return true;
            case R.id.release_date_asc:
                requestMovies(getString(R.string.release_date_asc));
                return true;
            case R.id.release_date_desc:
                requestMovies(getString(R.string.release_date_desc));
                return true;
            case R.id.original_title_asc:
                requestMovies(getString(R.string.original_title_asc));
                return true;
            case R.id.original_title_desc:
                requestMovies(getString(R.string.original_title_desc));
                return true;
            case R.id.vote_average_asc:
                requestMovies(getString(R.string.vote_average_asc));
                return true;
            case R.id.vote_average_dessc:
                requestMovies(getString(R.string.vote_average_desc));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
