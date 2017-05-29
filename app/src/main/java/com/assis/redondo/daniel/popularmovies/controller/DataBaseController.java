package com.assis.redondo.daniel.popularmovies.controller;

import android.widget.Toast;

import com.assis.redondo.daniel.popularmovies.PopularMoviesApplication;
import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;
import com.assis.redondo.daniel.popularmovies.database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by DT on 5/28/17.
 */

public enum  DataBaseController {

    INSTANCE;

    private DatabaseHelper mHelper;
    private Dao<MovieModel, Integer> mMoviesDao;
    private Dao<MovieReviewModel, Integer> mMoviesReviewDao;
    private Dao<MovieVideoModel, Integer> mMoviesVideos;

    DataBaseController() {
        openDatabase();
    }

    private void openDatabase() {
        mHelper = new DatabaseHelper(PopularMoviesApplication.getStaticContext());
        try {
            mMoviesDao = mHelper.getDao(MovieModel.class);
            mMoviesReviewDao = mHelper.getDao(MovieReviewModel.class);
            mMoviesVideos = mHelper.getDao(MovieVideoModel.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveFavoriteMovie(MovieModel movieModel) {
        try {
            mMoviesDao.createOrUpdate(movieModel);
            Toast.makeText(PopularMoviesApplication.getStaticContext(), R.string.movie_added_to_favorites,Toast.LENGTH_SHORT);
        } catch (SQLException e) {
            Timber.e(e);
            Toast.makeText(PopularMoviesApplication.getStaticContext(), R.string.movie_added_to_favorites_failed,Toast.LENGTH_SHORT);
        }
    }

    public ArrayList<MovieModel> getFavoriteMovies() {
        try {
            ArrayList<MovieModel> favoriteMovies = (ArrayList<MovieModel>) mMoviesDao.queryForAll();
            return favoriteMovies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean isFavorite(MovieModel mMovieModel) {

        try {
            MovieModel favoriteMovie = (MovieModel) mMoviesDao.queryForId(mMovieModel.getId());
            return favoriteMovie != null ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteFavoriteMovie(MovieModel movieModel) {

        try {
            mMoviesDao.delete(movieModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public MovieReviewModel getReviewModel(MovieModel movieModel) {
        DBChatMessageModel dbMessage = new DBChatMessageModel(message);
        try {
            mMoviesReviewDao.createOrUpdate(dbMessage);
            return dbMessage.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int saveMovieVideos(MessageModel message) {
        DBChatMessageModel dbMessage = new DBChatMessageModel(message);
        try {
            mMessagesDAO.createOrUpdate(dbMessage);
            return dbMessage.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteMessage(String wistl_ref, String id) {
        DeleteBuilder deleteBuilder = mMessagesDAO.deleteBuilder();
        try {
            deleteBuilder.where().eq(DBChatMessageModel.WISTL_REF, wistl_ref).and().eq(DBChatMessageModel.ID, String.valueOf(id));
        }
    }
    */
}