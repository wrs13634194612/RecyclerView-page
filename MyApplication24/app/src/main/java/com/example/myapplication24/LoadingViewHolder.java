package com.example.myapplication24;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progress_bar;


    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progress_bar = itemView.findViewById(R.id.progress_bar);
    }

}