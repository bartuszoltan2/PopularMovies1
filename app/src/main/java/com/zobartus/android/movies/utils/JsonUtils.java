package com.zobartus.android.movies.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;


public class JsonUtils {
    final static String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String API_KEY = "[YOUR_API_KEY]";

    public static final String TITLE = "title",
            OVERVIEW = "overview",
            RELEASE_DATE = "release_date",
            RATING = "vote_average",
            POSTER = "poster_path";

    public static URL buildUrl(String[] query) throws MalformedURLException {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(query[0])
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return reader.lines().collect(Collectors.joining("\\A"));
        } finally {
            urlConnection.disconnect();
        }
    }
}

