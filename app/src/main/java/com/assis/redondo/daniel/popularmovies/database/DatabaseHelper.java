package com.assis.redondo.daniel.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by DT on 5/28/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "PopularMovies.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getDatabaseName(String token) {
        return token + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource source) {
        createTables(source);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource source, int oldVersion, int newVersion) {
        //dropTables(source, oldVersion, newVersion);
        createNewTables(source);
    }

    @Override
    public void close() {
        super.close();
    }

    private void createNewTables(ConnectionSource source) {
        try {
            TableUtils.createTableIfNotExists(source, MovieModel.class);
            TableUtils.createTableIfNotExists(source, MovieReviewModel.class);
            TableUtils.createTableIfNotExists(source, MovieVideoModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables(ConnectionSource source) {
        try {
            TableUtils.createTable(source, MovieModel.class);
            TableUtils.createTable(source, MovieReviewModel.class);
            TableUtils.createTable(source, MovieVideoModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTables(ConnectionSource source, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(source, MovieModel.class, true);
            TableUtils.dropTable(source, MovieReviewModel.class, true);
            TableUtils.dropTable(source, MovieVideoModel.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


