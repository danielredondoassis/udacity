package com.assis.redondo.daniel.popularmovies.view.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.MovieDetailActivity;
import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MovieHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textMovieName;
    private TextView textUnavaiablePoster;
    private MovieModel mMovieModel;
    private Context context;

    public MovieHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imagePoster);
        textMovieName = (TextView) itemView.findViewById(R.id.textMovieName);
        textUnavaiablePoster = (TextView) itemView.findViewById(R.id.textUnavaiablePoster);
    }

    public void setup(Context context,MovieModel movieModel) {
        mMovieModel = movieModel;
        this.context = context;
        imageView.setImageDrawable(null);
        if(movieModel.getPosterPath() != null) {
            textMovieName.setText("");
            textUnavaiablePoster.setVisibility(View.GONE);
            Picasso.with(context).load(context.getResources().getString(R.string.movie_db_image_endpoit) + movieModel.getPosterPath()).into(imageView);
        } else {
            textUnavaiablePoster.setVisibility(View.VISIBLE);

            String dateString = null;
            if(movieModel.getReleaseDate() != null) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movieModel.getReleaseDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    dateString = "\n(" + calendar.get(Calendar.YEAR) + ")";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            textMovieName.setText(movieModel.getOriginalTitle() + dateString );
        }

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_DETAIL,mMovieModel);
            context.startActivity(intent);
        });
    }

}