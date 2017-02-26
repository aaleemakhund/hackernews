package com.example.aleemabdul.hackernews.model;


public class Comment {

    private String by;
    private long time;
    private String comment;

    public Comment(String by, long time, String comment) {
        this.by = by;
        this.time = time;
        this.comment = comment;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
