package com.example.assignment2_guo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.assignment2_guo.R;
import com.example.assignment2_guo.adapter.MovieAdapter;
import com.example.assignment2_guo.databinding.ActivityMovieDetailBinding;
import com.example.assignment2_guo.model.MovieModel;
import com.example.assignment2_guo.utils.ApiClient;
import com.example.assignment2_guo.viewmodel.MovieViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private final String API_KEY = "3b64e9ba";
    ActivityMovieDetailBinding binding;

    static public void enter(Activity activity, MovieModel model) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra("Title", model.getTitle());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getMovieDetail(getIntent().getStringExtra("Title"),API_KEY);
        binding.goBackButton.setOnClickListener(view -> finish());
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
                Toast.makeText(MovieDetailActivity.this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() == null) return;

                String responseData = response.body().string();
                try {
                    JSONObject json = new JSONObject(responseData);
                    MovieModel movie = MovieViewModel.returnMovieModel(json);

                    runOnUiThread(() -> {
                        binding.movieDetailTitle.setText(movie.getTitle());
                        binding.movieDetailRating.setText(movie.getImdbRating());
                        binding.movieDetailInfo.setText(movie.getYear() + "  " + movie.getRuntime() + "  " + movie.getGenre());
                        binding.movieDetailDescription.setText(movie.getPlot());

                        // Use Glide to load the poster image
                        Glide.with(getApplicationContext())
                                .load(movie.getPoster())
                                .placeholder(R.mipmap.ic_launcher)
                                .into(binding.moviePoster);
                    });
                } catch (JSONException e) {
                    runOnUiThread(() -> {
                        // Handle the error if JSON parsing fails
                        Toast.makeText(MovieDetailActivity.this, "Failed to parse movie data", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

}
