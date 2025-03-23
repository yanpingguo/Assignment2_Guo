package com.example.assignment2_guo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_guo.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    TextView title, year,type;
    ImageView poster;

    MovieViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_title);
        year = itemView.findViewById(R.id.movie_year);
        type = itemView.findViewById(R.id.movie_type);
        poster = itemView.findViewById(R.id.movie_poster);
    }
}
