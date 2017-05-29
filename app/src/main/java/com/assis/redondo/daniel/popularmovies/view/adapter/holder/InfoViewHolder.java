package com.assis.redondo.daniel.popularmovies.view.adapter.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.assis.redondo.daniel.popularmovies.R;
import com.assis.redondo.daniel.popularmovies.api.model.MovieReviewModel;
import com.assis.redondo.daniel.popularmovies.api.model.MovieVideoModel;

/**
 * Created by DT on 5/16/17.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder {


    public final TextView mTextInfo1;
    public final TextView mTextInfo2;
    public final TextView mTextInfo3;
    public final CardView holderLayout;


    public InfoViewHolder(View itemView) {
        super(itemView);

        mTextInfo1 = (TextView) itemView.findViewById(R.id.textInfo1);
        mTextInfo2 = (TextView) itemView.findViewById(R.id.textInfo2);
        mTextInfo3 = (TextView) itemView.findViewById(R.id.textInfo3);
        holderLayout = (CardView) itemView.findViewById(R.id.holderLayout);
    }


    public void setup(Context context, MovieVideoModel trailerModel) {
        mTextInfo1.setText(trailerModel.getName());
        mTextInfo2.setText(trailerModel.getSite());
        mTextInfo3.setText(trailerModel.getType());
    }

    public void setup(Context context, MovieReviewModel reviewModel) {
        mTextInfo1.setText(reviewModel.getAuthor());
        mTextInfo2.setText(reviewModel.getContent());
        mTextInfo3.setText(reviewModel.getUrl());
    }

}
