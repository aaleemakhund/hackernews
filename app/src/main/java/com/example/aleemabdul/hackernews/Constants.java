package com.example.aleemabdul.hackernews;

public class Constants {

    //SPLASH_DELAY
    public static final int SPLASH_DELAY = 3000;

    //network call timeout
    public static final int TIME_OUT = 8000;

    private static String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    public static String TOP_STORIES =  BASE_URL + "topstories.json";
    public static String ITEM_DETAILS = BASE_URL + "item/";

    public static int SPLIT_LIST_SIZE = 20;
}