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

package com.vishnu.hackernewsclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vishnu.hackernewsclient.R;
import com.vishnu.hackernewsclient.model.NewsItem;

public class RecyclerViewAdapter extends ListAdapter<NewsItem, RecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public RecyclerViewAdapter() {
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
        holder.authorTV.setText(item.getBy());
        holder.colorView.setBackgroundColor(item.getColor());

        if (item.getKids() != null) {
            String s = String.valueOf(item.getKids().size());

            holder.commentCountTV.setText(s.length() >= 2 ? s : s + " "); // hack!
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV, linkTV, scoresTV, commentCountTV, authorTV;
        public final View colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            linkTV = itemView.findViewById(R.id.linkTV);
            colorView = itemView.findViewById(R.id.sampleColorView);
            scoresTV = itemView.findViewById(R.id.scoresTV);
            commentCountTV = itemView.findViewById(R.id.commentCountTV);
            authorTV = itemView.findViewById(R.id.authorTV);

            itemView.setOnClickListener(
                    v -> {
                        if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                            listener.onItemClick(getItem(getAdapterPosition()));
                    });
        }
    }
}
