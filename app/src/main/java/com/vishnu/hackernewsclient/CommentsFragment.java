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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class CommentsFragment extends BottomSheetDialogFragment {

    private static final String NEWS_ITEM_KEY = "NEWS_ITEM";

    private NewsItem newsItem = null;

    private RecyclerView recyclerView;
    private LinearProgressIndicator progressIndicator;

    private CommentsViewModel viewModel;
    private CommentsRecyclerViewAdapter adapter;

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

        recyclerView = v.findViewById(R.id.commentRecyclerView);
        progressIndicator = v.findViewById(R.id.commentProgressIndicator);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        adapter = new CommentsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);

        viewModel
                .getChildComments(newsItem)
                .observe(
                        this,
                        list -> {
                            adapter.submitList(list, recyclerView::requestLayout);

                            adapter.notifyItemRangeChanged(0, list.size());

                            progressIndicator.setVisibility(View.GONE);
                        });
    }
}
