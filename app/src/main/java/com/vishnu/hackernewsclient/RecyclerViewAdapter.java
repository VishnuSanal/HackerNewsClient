/*
 * Copyright (C) 2023 Vishnu Sanal T
 *
 * This file is part of Hacker News Client.
 *
 * Hacker News Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        super(
                new DiffUtil.ItemCallback<NewsItem>() {
                    @Override
                    public boolean areItemsTheSame(
                            @NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
                        return oldItem.getId() == newItem.getId();
                    }

                    @Override
                    public boolean areContentsTheSame(
                            @NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
                        return oldItem.equals(newItem);
                    }
                });
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
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
        holder.scoresTV.setText(String.valueOf(item.getScore()));

        holder.colorView.setBackground(new ColorDrawable(getCardBGColor()));
    }

    private int getCardBGColor() {

        String[] colorArray =
                new String[] {
                    "#e57373", "#f06292", "#ba68c8", "#9575cd", "#7986cb", "#64b5f6", "#4fc3f7",
                    "#4dd0e1", "#4db6ac", "#81c784", "#ff8a65", "#a1887f"
                };

        return Color.parseColor(colorArray[new Random().nextInt(colorArray.length - 1)]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV, linkTV, scoresTV;
        public final View colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            linkTV = itemView.findViewById(R.id.linkTV);
            colorView = itemView.findViewById(R.id.sampleColorView);
            scoresTV = itemView.findViewById(R.id.scoresTV);
        }
    }
}
