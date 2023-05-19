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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentsFragment extends BottomSheetDialogFragment {

    private static final String NEWS_ITEM_KEY = "NEWS_ITEM";

    private NewsItem newsItem = null;

    public CommentsFragment() {}

    public static CommentsFragment newInstance(NewsItem newsItem) {

        Bundle bundle = new Bundle();

        bundle.putSerializable(NEWS_ITEM_KEY, newsItem);

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        if (getArguments() != null)
            newsItem = (NewsItem) getArguments().getSerializable(NEWS_ITEM_KEY);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        startActivity(
                new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://news.ycombinator.com/item?id=" + newsItem.getId())));

        return v;
    }
}
