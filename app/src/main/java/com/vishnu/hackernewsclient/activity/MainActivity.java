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

package com.vishnu.hackernewsclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.vishnu.hackernewsclient.R;
import com.vishnu.hackernewsclient.adapter.RecyclerViewAdapter;
import com.vishnu.hackernewsclient.fragment.CommentsFragment;
import com.vishnu.hackernewsclient.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private MainViewModel viewModel;

    private LinearProgressIndicator progressIndicator;
    private TextView titleTV, dateTV;
    private ImageView sortIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressIndicator = findViewById(R.id.progressIndicator);
        titleTV = findViewById(R.id.homeTitleTV);
        dateTV = findViewById(R.id.homeDateTV);
        sortIV = findViewById(R.id.homeSortIV);

        dateTV.setText(
                new SimpleDateFormat("E, dd MMMM yyyy", Locale.getDefault()).format(new Date()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //        recyclerView.addOnScrollListener(
        //                new RecyclerView.OnScrollListener() {
        //                    @Override
        //                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int
        // dy) {
        //                        super.onScrolled(recyclerView, dx, dy);
        //
        //                        if (recyclerView.canScrollVertically(-1)) {
        //                            titleTV.setVisibility(View.GONE);
        //                            dateTV.setVisibility(View.GONE);
        //                            sortIV.setVisibility(View.GONE);
        //                        } else {
        //                            titleTV.setVisibility(View.VISIBLE);
        //                            dateTV.setVisibility(View.VISIBLE);
        //                            sortIV.setVisibility(View.VISIBLE);
        //                        }
        //                    }
        //                });

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(
                newsItem -> {
                    if (newsItem.getType().equals("story"))
                        CommentsFragment.newInstance(newsItem)
                                .show(getSupportFragmentManager(), null);
                    else
                        startActivity(
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                                "https://news.ycombinator.com/item?id="
                                                        + newsItem.getId())));
                });

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel
                .getTopStories()
                .observe(
                        this,
                        list -> {
                            adapter.submitList(list);

                            adapter.notifyItemRangeChanged(0, list.size());

                            progressIndicator.setVisibility(View.GONE);
                        });
    }
}
