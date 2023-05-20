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

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vishnu.hackernewsclient.model.CommentItem;
import com.vishnu.hackernewsclient.model.NewsItem;
import com.vishnu.hackernewsclient.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

public class CommentsViewModel extends ViewModel {

    private final ArrayList<CommentItem> itemArrayList = new ArrayList<>();

    private MutableLiveData<List<CommentItem>> mutableLiveData = null;

    public CommentsViewModel() {
        super();
    }

    public MutableLiveData<List<CommentItem>> getChildComments(NewsItem parent) {

        itemArrayList.clear();

        loadTopStories(parent);

        return mutableLiveData;
    }

    private void loadTopStories(NewsItem parent) {

        mutableLiveData = new MutableLiveData<>();

        new NewsRepository()
                .getChildComments(
                        parent,
                        newsItem -> {
                            if (itemArrayList.contains(newsItem)) return;

                            //                            int i = 0;
                            //                            while (i < itemArrayList.size()
                            //                                    && newsItem.getTime() >=
                            // itemArrayList.get(i).getTime()) i++;

                            itemArrayList.add(/*i,*/ newsItem);
                            mutableLiveData.setValue(itemArrayList);
                        });
    }
}
