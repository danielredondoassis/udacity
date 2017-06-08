package com.assis.redondo.daniel.popularmovies.view.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.response.MovieDetailResponse;
import com.assis.redondo.daniel.popularmovies.api.service.TheMovieDBApiService;
import com.assis.redondo.daniel.popularmovies.controller.DataBaseController;
import com.assis.redondo.daniel.popularmovies.database.MoviesCPContract;
import com.assis.redondo.daniel.popularmovies.database.MoviesContentProvider;
import com.assis.redondo.daniel.popularmovies.view.fragment.MovieTabFragment;
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

public class MovieDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private int mMaxScrollSize;

    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";

    @BindView(R.id.imageMoviePoster)
    ImageView mImageMoviePoster;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.textMovieName)
    TextView mTextMovieName;
    @BindView(R.id.textMovieYear)
    TextView mTextMovieYear;
    @BindView(R.id.textMovieLenght)
    TextView mTextMovieLenght;
    @BindView(R.id.textMovieRate)
    TextView mTextMovieRate;
    @BindView(R.id.textMovieDesc)
    TextView mTextMovieDesc;
    @BindView(R.id.title_container)
    LinearLayout mTitleContainer;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.btnFavoriteMovie)
    FloatingActionButton mBtnFavoriteMovie;

    private MovieDetailResponse mMovieDetailResponse;
    private Subscription mSub;
    private MovieDetailActivity context;
    private MovieModel mMovieModel;


    public enum TabInfo {
        REVIEWS,
        TRAILERS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        this.context = this;

        setupActionBar();

        if (getIntent() == null) finish();

        Bundle bundle = getIntent().getExtras();

        mMovieModel = (MovieModel) bundle.getSerializable(MOVIE_DETAIL);

        int movieID = mMovieModel.getId();

        setupBasicMovieDetailUI(mMovieModel);
        requestMovieDetailedInfo(movieID);

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        mAppbar.addOnOffsetChangedListener(this);
        mMaxScrollSize = mAppbar.getTotalScrollRange();

        mViewpager.setAdapter(new TabsAdapter(context,getSupportFragmentManager(),mMovieModel));
        mTabs.setupWithViewPager(mViewpager);
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAppbar.setExpanded(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mAppbar.setExpanded(false);
            }
        });

    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        private MovieModel mMovieModel;
        private Context context;

        TabsAdapter(Context context, FragmentManager fm, MovieModel movieModel) {
            super(fm);
            this.context = context;
            this.mMovieModel = movieModel;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return MovieTabFragment.newInstance(position == 0 ? TabInfo.REVIEWS : TabInfo.TRAILERS, mMovieModel);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? context.getResources().getString(R.string.tab_reviews) : context.getResources().getString(R.string.tab_trailers);
        }
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;
    }

    private void setupActionBar() {
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

    }

    public void setupBasicMovieDetailUI(MovieModel movieModel) {


        if(movieModel.getPosterPath() != null){
            Picasso.with(context).load(context.getResources().getString(R.string.movie_db_image_endpoit_big) + movieModel.getPosterPath()).into(mImageMoviePoster);
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
        mTextMovieLenght.setText("...");
        mTextMovieYear.setText(Integer.toString(year));
        mTextMovieDesc.setText(movieModel.getOverview());
        mTextMovieRate.setText(movieModel.getVoteAverage());




        mBtnFavoriteMovie.setSelected(isFavorite(mMovieModel) ? true : false);

        mBtnFavoriteMovie.setOnClickListener(v -> {

            if(isFavorite(mMovieModel)){
                removeMovieFromContentProvider(movieModel);
            } else {
                addMovieToContentProvider(movieModel);
            }
        });
    }

    public void setupMovieRemainingUI(MovieDetailResponse movieDetail) {
        if(movieDetail != null)
            mTextMovieLenght.setText(movieDetail != null ? Integer.toString(movieDetail.getRuntime()) + context.getResources().getString(R.string.movie_runtime) :
                    context.getResources().getString(R.string.detail_not_avaiable));
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

    private void addMovieToContentProvider(MovieModel movieModel) {

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesCPContract.MovieEntry._ID, movieModel.getId());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movieModel.getOriginalLanguage());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_TITLE, movieModel.getTitle());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieModel.getOriginalTitle());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ADULT, movieModel.isAdult());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_POPULARITY, movieModel.getPopularity());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_POSTER_PATH, movieModel.getPosterPath());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_OVERVIEW, movieModel.getOverview());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_RELEASE_DATE, movieModel.getReleaseDate());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieModel.getVoteAverage());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VOTE_COUNT, movieModel.getVoteCount());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VIDEO, movieModel.isVideo());
            Uri uri = getContentResolver().insert(MoviesCPContract.MovieEntry.CONTENT_URI, contentValues);
            mBtnFavoriteMovie.setSelected(true);
            Log.w("URI: ", uri.toString());
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
    }

    private boolean isFavorite(MovieModel movieModel) {
        Uri singleUri = ContentUris.withAppendedId(MoviesCPContract.MovieEntry.CONTENT_URI,movieModel.getId());
        String[] titleColumn = {MoviesCPContract.MovieEntry.COLUMN_TITLE};
        Cursor coverCursor = getContentResolver().query(singleUri,titleColumn , null, null, null);

        boolean isFavorite = coverCursor.getCount() > 0 ? true : false;
        coverCursor.close();
        return isFavorite;
    }

    private void removeMovieFromContentProvider(MovieModel movieModel) {
        try {
            int id = movieModel.getId();
            String stringId = Integer.toString(id);
            Uri uri = MoviesCPContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();
            getContentResolver().delete(uri, null, null);
            mBtnFavoriteMovie.setSelected(false);
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
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
