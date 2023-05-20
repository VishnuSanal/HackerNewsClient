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
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.Serializable;

public class CommentsFragment extends BottomSheetDialogFragment {

    private static final String NEWS_ITEM_KEY = "NEWS_ITEM";

    @Nullable private NewsItem newsItem = null;

    @Nullable private CommentItem commentItem = null;

    private RecyclerView recyclerView;
    private LinearProgressIndicator progressIndicator;
    private TextView commentTitleTV, commentLinkTV;

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

    public static CommentsFragment newInstance(CommentItem commentItem) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(NEWS_ITEM_KEY, commentItem);

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        if (getArguments() != null) {

            Serializable serializable = getArguments().getSerializable(NEWS_ITEM_KEY);

            if (serializable instanceof NewsItem) newsItem = (NewsItem) serializable;
            else if (serializable instanceof CommentItem) commentItem = (CommentItem) serializable;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        recyclerView = v.findViewById(R.id.commentRecyclerView);
        progressIndicator = v.findViewById(R.id.commentProgressIndicator);
        commentTitleTV = v.findViewById(R.id.commentTitleTV);
        commentLinkTV = v.findViewById(R.id.commentLinkTV);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setHasFixedSize(false);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));

        adapter = new CommentsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(
                clickItem -> {
                    if (clickItem.getKids() != null)
                        CommentsFragment.newInstance(clickItem)
                                .show(requireActivity().getSupportFragmentManager(), null);
                    else
                        Toast.makeText(
                                        requireContext(),
                                        "No more child comments!",
                                        Toast.LENGTH_SHORT)
                                .show();
                });

        viewModel = new ViewModelProvider(requireActivity()).get(CommentsViewModel.class);

        if (newsItem != null) {
            commentTitleTV.setText(newsItem.getTitle());
            commentLinkTV.setText(Html.fromHtml(newsItem.getUrl(), Html.FROM_HTML_MODE_COMPACT));

            viewModel
                    .getChildComments(newsItem)
                    .observe(
                            this,
                            list -> {
                                adapter.submitList(list, recyclerView::requestLayout);

                                adapter.notifyItemRangeChanged(0, list.size());

                                progressIndicator.setVisibility(View.GONE);
                            });

        } else if (commentItem != null) {

            commentTitleTV.setText(Html.fromHtml(commentItem.getText()));
            commentTitleTV.setSingleLine(true);
            commentTitleTV.setEllipsize(TextUtils.TruncateAt.END);

            commentLinkTV.setVisibility(View.GONE);

            viewModel
                    .getChildComments(new NewsItem(commentItem.getId(), commentItem.getKids()))
                    .observe(
                            this,
                            list -> {
                                adapter.submitList(list, recyclerView::requestLayout);

                                adapter.notifyItemRangeChanged(0, list.size());

                                progressIndicator.setVisibility(View.GONE);
                            });
        }
    }
}
