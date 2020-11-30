package com.zobartus.android.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zobartus.android.movies.model.Movie;
import com.zobartus.android.movies.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private final int NUM_OF_COLUMNS = 2;
    private final String POPULAR_QUERY = "popular";
    private final String TOP_RATED_QUERY = "top_rated";
    Movie[] movies;
    ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        // Using a Grid Layout Manager
        mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // SPINNER METHODS
        Spinner sortSpinner = findViewById(R.id.sort_spinner);
        final int currentSelection = sortSpinner.getSelectedItemPosition();

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentSelection == i) {
                    // If most popular was selected
                    new FetchDataAsyncTask().execute(POPULAR_QUERY);
                } else {
                    // If top rated was selected
                    new FetchDataAsyncTask().execute(TOP_RATED_QUERY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    /***
     * METHOD TO MAKE STRING OF MOVIE DATA TO AN ARRAY OF MOVIE OBJECTS
     ***/

    public Movie[] makeMoviesDataToArray(String moviesJsonResults) throws JSONException {
        // JSON filters
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTER_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        // Get results as an array
        JSONObject moviesJson = new JSONObject(moviesJsonResults);
        JSONArray resultsArray = moviesJson.getJSONArray(RESULTS);

        // Create array of Movie objects that stores data from the JSON string
        movies = new Movie[resultsArray.length()];

        // Go through movies one by one and get data
        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            movies[i] = new Movie();

            // Object contains all tags we're looking for
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            // Store data in movie object
            movies[i].setTitle(movieInfo.getString(ORIGINAL_TITLE));
            movies[i].setImagePath(movieInfo.getString(POSTER_PATH));
            movies[i].setOverview(movieInfo.getString(OVERVIEW));
            movies[i].setReview(movieInfo.getDouble(VOTER_AVERAGE));
            movies[i].setReleaseDate(movieInfo.getString(RELEASE_DATE));
        }
        return movies;
    }

    /***
     * FETCH MOVIE DATA ASYNC TASK
     ***/

    public class FetchDataAsyncTask extends AsyncTask<String, Void, Movie[]> {
        public FetchDataAsyncTask() {
            super();
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            // Holds data returned from the API
            String movieSearchResults = null;

            try {
                URL url = JsonUtils.buildUrl(params);
                movieSearchResults = JsonUtils.getResponseFromHttpUrl(url);

                if (movieSearchResults == null) {
                    return null;
                }
            } catch (IOException e) {
                return null;
            }

            try {
                return makeMoviesDataToArray(movieSearchResults);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Movie[] movies) {
            mImageAdapter = new ImageAdapter(getApplicationContext(), movies);
            mRecyclerView.setAdapter(mImageAdapter);
        }
    }
}
