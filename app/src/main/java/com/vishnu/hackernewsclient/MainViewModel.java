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

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final ArrayList<NewsItem> itemArrayList = new ArrayList<>();

    private MutableLiveData<List<NewsItem>> mutableLiveData = null;

    public MainViewModel() {
        super();
    }

    public MutableLiveData<List<NewsItem>> getTopStories() {

        if (itemArrayList == null || itemArrayList.isEmpty()) loadTopStories();

        return mutableLiveData;
    }

    private void loadTopStories() {

        mutableLiveData = new MutableLiveData<>();

        new NewsRepository()
                .getTopStories(
                        newsItem -> {
                            if (itemArrayList.contains(newsItem)) return;

                            int i = 0;
                            while (i < itemArrayList.size()
                                    && newsItem.getScore() <= itemArrayList.get(i).getScore()) i++;

                            itemArrayList.add(i, newsItem);
                            mutableLiveData.setValue(itemArrayList);
                        });
    }
}
