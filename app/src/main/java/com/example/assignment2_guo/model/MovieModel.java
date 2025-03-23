package com.example.assignment2_guo.model;
public class MovieModel {
    private String title;
    private String year;
    private String type;
    private String poster;
    private String runtime;
    private String genre;
    private String plot;
    private String imdbRating;

    public MovieModel(String title, String year, String type, String poster, String runtime, String genre, String plot, String imdbRating) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
        this.runtime = runtime;
        this.genre = genre;
        this.plot = plot;
        this.imdbRating = imdbRating;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getRuntime() { return runtime; }
    public void setRuntime(String runtime) { this.runtime = runtime; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }

    public String getImdbRating() { return imdbRating; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }

    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", type='" + type + '\'' +
                ", poster='" + poster + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", plot='" + plot + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                '}';
    }
}
