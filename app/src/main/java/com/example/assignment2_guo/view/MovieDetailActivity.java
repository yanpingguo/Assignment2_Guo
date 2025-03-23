package com.example.assignment2_guo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

    private MovieViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        viewModel.getMovieDetail(getIntent().getStringExtra("Title"), API_KEY);
        viewModel.getMovieData().observe(this, movie -> {
            if (movie != null) {
                binding.movieDetailTitle.setText(movie.getTitle());
                binding.movieDetailRating.setText(movie.getImdbRating());
                binding.movieDetailInfo.setText(movie.getYear() + "  " + movie.getRuntime() + "  " + movie.getGenre());
                binding.movieDetailDescription.setText(movie.getPlot());

                // Use Glide to load the poster image
                Glide.with(getApplicationContext())
                        .load(movie.getPoster())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(binding.moviePoster);
            } else {
                Toast.makeText(this, "Failed to load movie details", Toast.LENGTH_SHORT).show();
            }
        });
        binding.goBackButton.setOnClickListener(view -> finish());
    }
}
