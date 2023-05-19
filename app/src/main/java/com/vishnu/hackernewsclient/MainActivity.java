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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel
                .getTopStories()
                .observe(
                        this,
                        list -> {
                            adapter.submitList(list);

                            adapter.notifyDataSetChanged();

                            // progressIndicator.setVisibility(View.GONE);
                        });
    }

    private List<NewsItem> getDummyList() {
        ArrayList<NewsItem> arrayList = new ArrayList<>();

        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(
                new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));

        return arrayList;
    }
}
