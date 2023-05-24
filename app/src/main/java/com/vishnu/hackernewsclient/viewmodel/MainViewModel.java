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

package com.vishnu.hackernewsclient.viewmodel;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vishnu.hackernewsclient.model.NewsItem;
import com.vishnu.hackernewsclient.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends ViewModel {

    private final ArrayList<NewsItem> topStoriesList = new ArrayList<>();
    private final ArrayList<NewsItem> newStoriesList = new ArrayList<>();

    private MutableLiveData<List<NewsItem>> topStoriesLiveData = null;
    private MutableLiveData<List<NewsItem>> newStoriesLiveData = null;

    public MainViewModel() {
        super();
    }

    public MutableLiveData<List<NewsItem>> getTopStories() {

        if (topStoriesList == null || topStoriesList.isEmpty()) loadTopStories();

        return topStoriesLiveData;
    }

    private void loadTopStories() {

        topStoriesLiveData = new MutableLiveData<>();

        new NewsRepository()
                .getTopStories(
                        newsItem -> {
                            if (topStoriesList.contains(newsItem)) return;

                            int i = 0;
                            while (i < topStoriesList.size()
                                    && newsItem.getScore() <= topStoriesList.get(i).getScore()) i++;

                            newsItem.setColor(getRandomColor());

                            topStoriesList.add(i, newsItem);
                            topStoriesLiveData.setValue(topStoriesList);
                        });
    }

    public MutableLiveData<List<NewsItem>> getNewStories() {

        if (newStoriesList == null || newStoriesList.isEmpty()) loadNewStories();

        return newStoriesLiveData;
    }

    private void loadNewStories() {

        newStoriesLiveData = new MutableLiveData<>();

        new NewsRepository()
                .getNewStories(
                        newsItem -> {
                            if (newStoriesList.contains(newsItem)) return;

                            int i = 0;
                            while (i < newStoriesList.size()
                                    && newsItem.getTime() <= newStoriesList.get(i).getTime()) i++;

                            newsItem.setColor(getRandomColor());

                            newStoriesList.add(i, newsItem);
                            newStoriesLiveData.setValue(newStoriesList);
                        });
    }

    private int getRandomColor() {

        String[] colorArray =
                new String[] {
                    "#e57373", "#f06292", "#ba68c8", "#9575cd", "#7986cb", "#64b5f6", "#4fc3f7",
                    "#4dd0e1", "#4db6ac", "#81c784", "#ff8a65", "#a1887f"
                };

        return Color.parseColor(colorArray[new Random().nextInt(colorArray.length - 1)]);
    }
}
