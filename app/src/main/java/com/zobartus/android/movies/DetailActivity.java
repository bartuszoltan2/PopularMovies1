package com.zobartus.android.movies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.zobartus.android.movies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);

        TextView originalTitleTV = (TextView) findViewById (R.id.titleTextView);
        TextView ratingTV = (TextView) findViewById (R.id.reviewTextView);
        TextView releaseDateTV = (TextView) findViewById (R.id.releaseDateTextView);
        TextView overviewTV = (TextView) findViewById (R.id.overviewTextView);
        ImageView posterIV = (ImageView) findViewById (R.id.posterImageView);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Movie movie = (Movie) intent.getParcelableExtra("movie");

        originalTitleTV.setText(movie.getTitle());
        ratingTV.setText (String.valueOf(movie.getReview()) + " / 10");

        Picasso.get()
                .load(movie.getImagePath())
                .into(posterIV);

        overviewTV.setText (movie.getOverview ());
        releaseDateTV.setText (movie.getReleaseDate());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Unexpected failure. Application stopped.", Toast.LENGTH_SHORT).show();
    }
}