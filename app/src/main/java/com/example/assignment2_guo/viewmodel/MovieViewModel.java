package com.example.assignment2_guo.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2_guo.model.MovieModel;
import com.example.assignment2_guo.utils.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<List<MovieModel>> movieDataList = new MutableLiveData<>();
    private final MutableLiveData<MovieModel> movieDetailData = new MutableLiveData<>();
    public LiveData<List<MovieModel>> getMovieDataList() {
        return movieDataList;
    }

    public LiveData<MovieModel> getMovieData() {
        return movieDetailData;
    }

    /**
     * button search
     * @param query
     * @param apiKey
     */
    public void searchMovies(String query, String apiKey) {
        String urlString = "https://www.omdbapi.com/?apikey=" + apiKey + "&s=" + query;

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                movieDataList.postValue(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() == null) return;

                String responseData = response.body().string();
                List<MovieModel> movieList = new ArrayList<>();
                try {
                    JSONObject json = new JSONObject(responseData);
                    if (json.has("Search")) {
                        JSONArray movieArray = json.getJSONArray("Search");
                        for (int i = 0; i < movieArray.length(); i++) {
                            JSONObject movieJson = movieArray.getJSONObject(i);
                            MovieModel movie = returnMovieModel(movieJson);
                            movieList.add(movie);
                        }
                    }
                    movieDataList.postValue(movieList);
                } catch (JSONException e) {
                    movieDataList.postValue(null);
                }
            }
        });
    }


    /**
     * get the movie detail
     * @param title
     * @param apiKey
     */
    public void getMovieDetail(String title, String apiKey) {
        String urlString = "https://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title;

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                movieDetailData.postValue(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() == null) {
                    movieDetailData.postValue(null);
                    return;
                }

                String responseData = response.body().string();
                try {
                    JSONObject json = new JSONObject(responseData);
                    MovieModel movie = MovieViewModel.returnMovieModel(json);
                    movieDetailData.postValue(movie);
                } catch (JSONException e) {
                    movieDetailData.postValue(null);
                }
            }
        });
    }

public static MovieModel returnMovieModel(JSONObject movieJson){
    return new MovieModel(
            movieJson.optString("Title"),
            movieJson.optString("Year"),
            movieJson.optString("Type"),
            movieJson.optString("Poster"),
            movieJson.optString("Runtime").equals("N/A") ? " " : movieJson.optString("Runtime"),
            movieJson.optString("Genre").equals("N/A") ? " " : movieJson.optString("Genre"),
            movieJson.optString("Plot"),
            movieJson.optString("imdbRating").equals("N/A") ? " " : movieJson.optString("imdbRating")
    );
}
}
