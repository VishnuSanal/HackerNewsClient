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

package com.vishnu.hackernewsclient.repository;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.Gson;
import com.vishnu.hackernewsclient.controller.AppController;
import com.vishnu.hackernewsclient.model.CommentItem;
import com.vishnu.hackernewsclient.model.NewsItem;
import com.vishnu.hackernewsclient.response.CommentsListAsyncResponse;
import com.vishnu.hackernewsclient.response.NewsListAsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class NewsRepository {

    private static int ITEM_COUNT = 10;

    public void getTopStories(final NewsListAsyncResponse callBack) {

        final String url = "https://hacker-news.firebaseio.com/v0/topstories.json";

        Thread thread =
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {

                                final JsonArrayRequest jsonArrayRequest =
                                        new JsonArrayRequest(
                                                Request.Method.GET,
                                                url,
                                                null,
                                                response -> {
                                                    for (int i = 0; i < ITEM_COUNT; i++) {
                                                        try {
                                                            long id = response.getLong(i);

                                                            String itemURL =
                                                                    "https://hacker-news.firebaseio.com/v0/item/"
                                                                            + id
                                                                            + ".json";

                                                            AppController.getInstance()
                                                                    .addToRequestQueue(
                                                                            new JsonObjectRequest(
                                                                                    Request.Method
                                                                                            .GET,
                                                                                    itemURL,
                                                                                    null,
                                                                                    item -> {
                                                                                        if (null
                                                                                                != callBack)
                                                                                            callBack
                                                                                                    .processFinished(
                                                                                                            new Gson()
                                                                                                                    .fromJson(
                                                                                                                            String
                                                                                                                                    .valueOf(
                                                                                                                                            item),
                                                                                                                            NewsItem
                                                                                                                                    .class));
                                                                                    },
                                                                                    Throwable
                                                                                            ::printStackTrace));

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                },
                                                Throwable::printStackTrace) {
                                            @Override
                                            protected Response<JSONArray> parseNetworkResponse(
                                                    NetworkResponse response) {
                                                try {
                                                    Cache.Entry cacheEntry =
                                                            HttpHeaderParser.parseCacheHeaders(
                                                                    response);
                                                    if (cacheEntry == null) {
                                                        cacheEntry = new Cache.Entry();
                                                    }
                                                    final long cacheHitButRefreshed = 1000;
                                                    final long cacheExpired = 12 * 60 * 60 * 1000;
                                                    long now = System.currentTimeMillis();
                                                    final long softExpire =
                                                            now + cacheHitButRefreshed;
                                                    final long ttl = now + cacheExpired;
                                                    cacheEntry.data = response.data;
                                                    cacheEntry.softTtl = softExpire;
                                                    cacheEntry.ttl = ttl;
                                                    String headerValue;
                                                    //noinspection ConstantConditions
                                                    headerValue = response.headers.get("Date");
                                                    if (headerValue != null) {
                                                        cacheEntry.serverDate =
                                                                HttpHeaderParser.parseDateAsEpoch(
                                                                        headerValue);
                                                    }
                                                    headerValue =
                                                            response.headers.get("Last-Modified");
                                                    if (headerValue != null) {
                                                        cacheEntry.lastModified =
                                                                HttpHeaderParser.parseDateAsEpoch(
                                                                        headerValue);
                                                    }
                                                    cacheEntry.responseHeaders = response.headers;
                                                    final String jsonString =
                                                            new String(
                                                                    response.data,
                                                                    HttpHeaderParser.parseCharset(
                                                                            response.headers));
                                                    return Response.success(
                                                            new JSONArray(jsonString), cacheEntry);
                                                } catch (UnsupportedEncodingException
                                                        | JSONException e) {
                                                    return Response.error(new ParseError(e));
                                                }
                                            }

                                            @Override
                                            protected void deliverResponse(JSONArray response) {
                                                super.deliverResponse(response);
                                            }

                                            @Override
                                            public void deliverError(VolleyError error) {
                                                super.deliverError(error);
                                            }

                                            @Override
                                            protected VolleyError parseNetworkError(
                                                    VolleyError volleyError) {
                                                return super.parseNetworkError(volleyError);
                                            }
                                        };
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            }
                        });

        thread.start();
    }

    public void getNewStories(final NewsListAsyncResponse callBack) {

        final String url = "https://hacker-news.firebaseio.com/v0/newstories.json";

        Thread thread =
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {

                                final JsonArrayRequest jsonArrayRequest =
                                        new JsonArrayRequest(
                                                Request.Method.GET,
                                                url,
                                                null,
                                                response -> {
                                                    for (int i = 0; i < ITEM_COUNT; i++) {
                                                        try {
                                                            long id = response.getLong(i);

                                                            String itemURL =
                                                                    "https://hacker-news.firebaseio.com/v0/item/"
                                                                            + id
                                                                            + ".json";

                                                            AppController.getInstance()
                                                                    .addToRequestQueue(
                                                                            new JsonObjectRequest(
                                                                                    Request.Method
                                                                                            .GET,
                                                                                    itemURL,
                                                                                    null,
                                                                                    item -> {
                                                                                        if (null
                                                                                                != callBack)
                                                                                            callBack
                                                                                                    .processFinished(
                                                                                                            new Gson()
                                                                                                                    .fromJson(
                                                                                                                            String
                                                                                                                                    .valueOf(
                                                                                                                                            item),
                                                                                                                            NewsItem
                                                                                                                                    .class));
                                                                                    },
                                                                                    Throwable
                                                                                            ::printStackTrace));

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                },
                                                Throwable::printStackTrace) {
                                            @Override
                                            protected Response<JSONArray> parseNetworkResponse(
                                                    NetworkResponse response) {
                                                try {
                                                    Cache.Entry cacheEntry =
                                                            HttpHeaderParser.parseCacheHeaders(
                                                                    response);
                                                    if (cacheEntry == null) {
                                                        cacheEntry = new Cache.Entry();
                                                    }
                                                    final long cacheHitButRefreshed = 1000;
                                                    final long cacheExpired = 12 * 60 * 60 * 1000;
                                                    long now = System.currentTimeMillis();
                                                    final long softExpire =
                                                            now + cacheHitButRefreshed;
                                                    final long ttl = now + cacheExpired;
                                                    cacheEntry.data = response.data;
                                                    cacheEntry.softTtl = softExpire;
                                                    cacheEntry.ttl = ttl;
                                                    String headerValue;
                                                    //noinspection ConstantConditions
                                                    headerValue = response.headers.get("Date");
                                                    if (headerValue != null) {
                                                        cacheEntry.serverDate =
                                                                HttpHeaderParser.parseDateAsEpoch(
                                                                        headerValue);
                                                    }
                                                    headerValue =
                                                            response.headers.get("Last-Modified");
                                                    if (headerValue != null) {
                                                        cacheEntry.lastModified =
                                                                HttpHeaderParser.parseDateAsEpoch(
                                                                        headerValue);
                                                    }
                                                    cacheEntry.responseHeaders = response.headers;
                                                    final String jsonString =
                                                            new String(
                                                                    response.data,
                                                                    HttpHeaderParser.parseCharset(
                                                                            response.headers));
                                                    return Response.success(
                                                            new JSONArray(jsonString), cacheEntry);
                                                } catch (UnsupportedEncodingException
                                                        | JSONException e) {
                                                    return Response.error(new ParseError(e));
                                                }
                                            }

                                            @Override
                                            protected void deliverResponse(JSONArray response) {
                                                super.deliverResponse(response);
                                            }

                                            @Override
                                            public void deliverError(VolleyError error) {
                                                super.deliverError(error);
                                            }

                                            @Override
                                            protected VolleyError parseNetworkError(
                                                    VolleyError volleyError) {
                                                return super.parseNetworkError(volleyError);
                                            }
                                        };
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            }
                        });

        thread.start();
    }

    public void getChildComments(NewsItem parent, final CommentsListAsyncResponse callBack) {

        for (Long kid : parent.getKids()) {

            String itemURL = "https://hacker-news.firebaseio.com/v0/item/" + kid + ".json";

            AppController.getInstance()
                    .addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    itemURL,
                                    null,
                                    item -> {
                                        if (null != callBack)
                                            callBack.processFinished(
                                                    new Gson()
                                                            .fromJson(
                                                                    String.valueOf(item),
                                                                    CommentItem.class));
                                    },
                                    Throwable::printStackTrace));
        }
    }
}
