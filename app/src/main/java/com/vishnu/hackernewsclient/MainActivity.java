package com.vishnu.hackernewsclient;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

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


        adapter.submitList(getDummyList());
    }

    private List<NewsItem> getDummyList() {
        ArrayList<NewsItem> arrayList = new ArrayList<>();

        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));
        arrayList.add(new NewsItem(0, "", 0, null, 0, 0, "Hello", "story", "https://archlinux.org"));

        return arrayList;
    }
}