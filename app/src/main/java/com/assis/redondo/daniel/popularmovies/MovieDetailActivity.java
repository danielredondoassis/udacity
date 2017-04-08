package com.assis.redondo.daniel.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.response.MovieDetailResponse;
import com.assis.redondo.daniel.popularmovies.api.response.MovieVideoResponse;
import com.assis.redondo.daniel.popularmovies.api.response.PopularMoviesResponse;
import com.assis.redondo.daniel.popularmovies.api.service.TheMovieDBApiService;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MovieDetailActivity extends AppCompatActivity {


    @BindView(R.id.imageMoviePoster)
    ImageView mImageMoviePoster;
    @BindView(R.id.textMovieName)
    TextView mTextMovieName;
    @BindView(R.id.movieHeaderLayout)
    RelativeLayout movieHeaderLayout;
    @BindView(R.id.textMovieYear)
    TextView mTextMovieYear;
    @BindView(R.id.textMovieLenght)
    TextView mTextMovieLength;
    @BindView(R.id.textMovieRate)
    TextView mTextMovieRate;
    @BindView(R.id.btnFavoriteMovie)
    Button btnFavoriteMovie;
    @BindView(R.id.movideDescriptionLayout)
    LinearLayout movideDescriptionLayout;
    @BindView(R.id.textMovieDesc)
    TextView mTextMovieDesc;
    @BindView(R.id.movieDetailsLayout)
    LinearLayout movieDetailsLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";
    private MovieDetailResponse mMovieDetailResponse;
    private Subscription mSub;
    private Subscription mSubMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setupActionBar();


        if(getIntent() == null) finish();

        Bundle bundle = getIntent().getExtras();

        MovieModel movieModel = (MovieModel) bundle.getSerializable(MOVIE_DETAIL);

        setupBasicMovieDetailUI(movieModel);

        int movieID = movieModel.getId();

        /*
            Stage 2
            requestMovieTrailerInfo(movieID);
         */
        requestMovieDetailedInfo(movieModel.getId());
    }

    private void setupActionBar() {
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_movide_detail));

    }

    public void setupBasicMovieDetailUI(MovieModel movieModel) {


        if(movieModel.getPosterPath() != null){
            Picasso.with(this).load(getString(R.string.movie_db_image_endpoit_big) + movieModel.getPosterPath()).into(mImageMoviePoster);
        }

        int year = 0;
        if(movieModel.getReleaseDate() != null) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movieModel.getReleaseDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                year = calendar.get(Calendar.YEAR);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mTextMovieName.setText(movieModel.getTitle());
        mTextMovieLength.setText("...");
        mTextMovieYear.setText(Integer.toString(year));
        mTextMovieDesc.setText(movieModel.getOverview());
        mTextMovieRate.setText(movieModel.getVoteAverage());
    }

    public void setupMovieRemainingUI(MovieDetailResponse movieDetail) {
        mTextMovieLength.setText(movieDetail != null ? Integer.toString(movieDetail.getRuntime()) + getString(R.string.movie_runtime) :
                                                        getString(R.string.detail_not_avaiable));
    }

    private void requestMovieDetailedInfo(int movieId) {

        Observable<MovieDetailResponse> call = TheMovieDBApiService.INSTANCE.getMoviesAPI()
                .retrieveMovieDetailedInfo(
                        movieId,
                        getString(R.string.api_key),
                        "videos");


        if (mSub != null) {
            mSub.unsubscribe();
            mSub = null;
        }

        mSub = call.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::movieDetailLoaded, this::movieDetailError);
    }

    protected void movieDetailLoaded(MovieDetailResponse movieDetailResponse) {
        Timber.v("movies loaded");
        mMovieDetailResponse = movieDetailResponse;
        setupMovieRemainingUI(movieDetailResponse);
    }

    protected void movieDetailError(Throwable throwable) {
        Timber.e(throwable, "movies load error");
        //setupErrorUI();
        setupMovieRemainingUI(null);
    }


    private void requestMovieTrailerInfo(int movieId) {

        Observable<MovieVideoResponse> call = TheMovieDBApiService.INSTANCE.getMoviesAPI()
                .retrieveMovieTrailer(movieId, getString(R.string.api_key));

        if (mSubMovies != null) {
            mSubMovies.unsubscribe();
            mSubMovies = null;
        }

        mSubMovies = call.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::movieTrailersLoaded, this::movieTrailersError);
    }

    protected void movieTrailersLoaded(MovieVideoResponse movieVideoResponse) {
        Timber.v("movie trailer loaded");
        //setupTrailerUI(movieVideoResponse);
    }

    protected void movieTrailersError(Throwable throwable) {
        Timber.e(throwable, "movie trailer load error");
        // TODO create no trailer avaiable UI
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
        if (mSubMovies != null) {
            mSubMovies.unsubscribe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
