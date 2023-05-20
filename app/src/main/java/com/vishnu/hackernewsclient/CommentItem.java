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
import java.util.Objects;

public class CommentItem implements Serializable {

    private long id;
    private String by;
    private long parent;
    private ArrayList<Long> kids;
    private long time;
    private String text;
    private String type;

    public CommentItem() {}

    public CommentItem(
            long id,
            String by,
            long parent,
            ArrayList<Long> kids,
            long time,
            String text,
            String type) {
        this.id = id;
        this.by = by;
        this.parent = parent;
        this.kids = kids;
        this.time = time;
        this.text = text;
        this.type = type;
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

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public ArrayList<Long> getKids() {
        return kids;
    }

    public void setKids(ArrayList<Long> kids) {
        this.kids = kids;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentItem that = (CommentItem) o;
        return id == that.id
                && parent == that.parent
                && time == that.time
                && Objects.equals(by, that.by)
                && Objects.equals(text, that.text)
                && Objects.equals(type, that.type);
    }

    @NonNull
    @Override
    public String toString() {
        return "CommentItem{"
                + "id="
                + id
                + ", by='"
                + by
                + '\''
                + ", parent="
                + parent
                + ", kids="
                + kids
                + ", time="
                + time
                + ", text='"
                + text
                + '\''
                + ", type='"
                + type
                + '\''
                + '}';
    }
}
