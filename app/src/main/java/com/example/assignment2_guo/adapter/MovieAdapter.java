package com.example.assignment2_guo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2_guo.R;
import com.example.assignment2_guo.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<MovieModel> movieList = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieHolder, int position) {
        MovieModel movie = movieList.get(position);
        movieHolder.title.setText(movie.getTitle());
        movieHolder.year.setText(movie.getYear() );
        movieHolder.type.setText(movie.getType() );

        Glide.with(movieHolder.itemView.getContext())
                .load(movie.getPoster())
                .placeholder(R.mipmap.ic_launcher)  // Placeholder image for loading
                .into(movieHolder.poster);

        movieHolder.itemView.setOnClickListener(view -> {
            if (onMovieClickListener != null) {

                onMovieClickListener.onGoBackClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface OnMovieClickListener {
        void onGoBackClicked(MovieModel model);
    }
}
