package com.vishnu.hackernewsclient;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class RecyclerViewAdapter extends ListAdapter<NewsItem, RecyclerViewAdapter.ViewHolder> {

    protected RecyclerViewAdapter() {
        super(new DiffUtil.ItemCallback<NewsItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        NewsItem item = getItem(position);

        holder.titleTV.setText(item.getTitle());
        holder.linkTV.setText(item.getUrl());
        holder.colorView.setBackground(new ColorDrawable(getCardBGColor()));
    }

    private int getCardBGColor() {

        String[] colorArray =
                new String[]{
                        "#e57373", "#f06292", "#ba68c8", "#9575cd", "#7986cb", "#64b5f6", "#4fc3f7",
                        "#4dd0e1", "#4db6ac", "#81c784", "#ff8a65", "#a1887f"
                };

        return Color.parseColor(colorArray[new Random().nextInt(colorArray.length - 1)]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV, linkTV;
        public final View colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            linkTV = itemView.findViewById(R.id.linkTV);
            colorView = itemView.findViewById(R.id.sampleColorView);
        }
    }
}
