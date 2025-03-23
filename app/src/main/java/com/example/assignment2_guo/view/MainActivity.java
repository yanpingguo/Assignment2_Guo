package com.example.assignment2_guo.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2_guo.adapter.MovieAdapter;
import com.example.assignment2_guo.databinding.ActivityMainBinding;
import com.example.assignment2_guo.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    MovieViewModel viewModel;
    ActivityMainBinding binding;
    private MovieAdapter movieAdapter;
    private final String API_KEY = "3b64e9ba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapter = new MovieAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(movieAdapter);
        //enter movie detail page
        movieAdapter.setOnMovieClickListener(model ->
                MovieDetailActivity.enter(this, model));

        binding.searchButton.setOnClickListener(view -> {
            String query = binding.searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.searchMovies(query, API_KEY);
            } else {
                Toast.makeText(this, "Please enter the movie name", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getMovieDataList().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                movieAdapter.setMovieList(movies);
            } else {
                Toast.makeText(this, "did not find this movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
