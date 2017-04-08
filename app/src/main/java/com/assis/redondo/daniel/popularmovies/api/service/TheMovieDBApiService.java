package com.assis.redondo.daniel.popularmovies.api.service;

import android.content.Context;

import com.assis.redondo.daniel.popularmovies.PopularMoviesApplication;
import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.MoviesApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


public enum TheMovieDBApiService {

    INSTANCE();

    private static final String TAG = TheMovieDBApiService.class.getSimpleName();

    private MoviesApiInterface mMoviesAPI;

    private Retrofit mRetrofit;
    private Gson mGson;

    TheMovieDBApiService() {
        Context ctx = PopularMoviesApplication.getStaticContext();
        //Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress(ctx.getString(R.string.proxy), 8888));
        OkHttpClient client = new OkHttpClient.Builder()
                //.proxy(proxy)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ctx.getString(R.string.api_endpoint))
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(rxAdapter);

        if(ctx.getResources().getBoolean(R.bool.use_proxy)) {
            builder.client(client);
        }

        mRetrofit = builder.build();
    }

    public MoviesApiInterface getMoviesAPI() {
        if (mMoviesAPI == null) mMoviesAPI = mRetrofit.create(MoviesApiInterface.class);
        return mMoviesAPI;
    }

    private void nullifyAPIs() {
        mMoviesAPI = null;
    }

}
