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

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsItem implements Serializable {

    private long id;
    private String by;
    private int descendants;
    private ArrayList<Long> kids;
    private int score;
    private long time;
    private String title;
    private String type;
    private String url;

    public NewsItem() {}

    public NewsItem(
            long id,
            String by,
            int descendants,
            ArrayList<Long> kids,
            int score,
            long time,
            String title,
            String type,
            String url) {
        this.id = id;
        this.by = by;
        this.descendants = descendants;
        this.kids = kids;
        this.score = score;
        this.time = time;
        this.title = title;
        this.type = type;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public ArrayList<Long> getKids() {
        return kids;
    }

    public void setKids(ArrayList<Long> kids) {
        this.kids = kids;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem newsItem = (NewsItem) o;
        return id == newsItem.id
                && descendants == newsItem.descendants
                && score == newsItem.score
                && time == newsItem.time
                && by.equals(newsItem.by)
                && title.equals(newsItem.title)
                && type.equals(newsItem.type)
                && url.equals(newsItem.url);
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsItem{"
                + "id="
                + id
                + ", by='"
                + by
                + '\''
                + ", descendants="
                + descendants
                + ", kids="
                + kids
                + ", score="
                + score
                + ", time="
                + time
                + ", title='"
                + title
                + '\''
                + ", type='"
                + type
                + '\''
                + ", url='"
                + url
                + '\''
                + '}';
    }
}
