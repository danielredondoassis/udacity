package com.assis.redondo.daniel.popularmovies;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by DT on 6/7/16.
 */
public class PopularMoviesApplication extends Application {

    private static final String TAG = PopularMoviesApplication.class.getSimpleName();
    private RefWatcher refWatcher;
    private static int stateCounter;

    private static Context mContext;

    public static Context getStaticContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        refWatcher = LeakCanary.install(this);
    }


    public static RefWatcher getRefWatcher(Context context) {
        PopularMoviesApplication application = (PopularMoviesApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}