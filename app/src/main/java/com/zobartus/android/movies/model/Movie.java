package com.zobartus.android.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String overview;
    private String releaseDate;
    private Double review;
    private String imagePath;

    final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

    public Movie() {
    }

    public Movie(String title, String overview, String releaseDate, Double review, String imagePath) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.review = review;
        this.imagePath = imagePath;
    }

    public Movie(Parcel parcel) {
        title = parcel.readString();
        overview = parcel.readString();
        releaseDate = parcel.readString();
        review = parcel.readDouble();
        imagePath = parcel.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public String getImagePath() {
        return POSTER_BASE_URL + imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeDouble(review);
        dest.writeString(imagePath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}