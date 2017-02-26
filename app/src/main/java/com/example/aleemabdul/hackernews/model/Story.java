package com.example.aleemabdul.hackernews.model;

import java.util.ArrayList;

public class Story {

    private String title;
    private long time;
    private String userName;
    private int score;
    private String comments;
    private ArrayList<String> listOfCommentsIds;

    public Story(String title, long time, String userName, int score, String comments, ArrayList<String> listOfCommentsIds) {
        this.title = title;
        this.time = time;
        this.userName = userName;
        this.score = score;
        this.comments = comments;
        this.listOfCommentsIds =  listOfCommentsIds;
    }

    public ArrayList<String> getListOfCommentsIds() {
        return listOfCommentsIds;
    }

    public void setListOfCommentsIds(ArrayList<String> listOfCommentsIds) {
        this.listOfCommentsIds = listOfCommentsIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
