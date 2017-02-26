package com.example.aleemabdul.hackernews.utils;

import android.content.res.Resources;

import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.datahander.StoryDataHandler;
import com.example.aleemabdul.hackernews.model.Comment;
import com.example.aleemabdul.hackernews.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utils {

    public static String timeAgo(final long secs, Resources resources) {
        return time(System.currentTimeMillis() - (secs*1000), resources);
    }

    public static String time(Long distanceMillis, Resources resources) {

        final double seconds = distanceMillis / 1000;
        final double minutes = seconds / 60;
        final double hours = minutes / 60;
        final double days = hours / 24;
        final double years = days / 365;

        final String time;
        if (seconds < 45) {
            time = resources.getString(R.string.time_seconds);
        } else if (seconds < 90 || minutes < 45) {
            time = resources.getQuantityString(R.plurals.time_minute, minutes < 2 ? 1 : 2, Math.round(minutes));
        } else if (minutes < 90 || hours < 24) {
            time = resources.getQuantityString(R.plurals.time_hour, hours < 2 ? 1 : 2, Math.round(hours));
        } else if (hours < 48 || days < 30) {
            time = resources.getQuantityString(R.plurals.time_day, days < 2 ? 1 : 2, Math.round(days));
        } else if (days < 60 || days < 365) {
            time = resources.getQuantityString(R.plurals.time_month, (days / 30) < 2 ? 1 : 2, Math.round(days / 30));
        } else {
            time = resources.getQuantityString(R.plurals.time_year, years < 2 ? 1 : 2, Math.round(years));
        }

        return time + " " + resources.getString(R.string.time_ago);

    }

    public static void parseStoriesIdData(String data){

        StoryDataHandler.getInstance().clearStoryIdsList();

        try {
            if (data !=null){

                JSONArray arr = new JSONArray(data);

                for(int i=0; i<arr.length();i++) {
                    StoryDataHandler.getInstance().addStoryId(arr.getInt(i));
                }

                StoryDataHandler.getInstance().splitList();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Story parseStoryData(String data){

        try {
            JSONObject jsonObject = new JSONObject(data);

            int commentCount = 0;
            ArrayList<String> list = new ArrayList<>();
            if(jsonObject.has("kids")) {
                commentCount = jsonObject.getJSONArray("kids").length();

                for (int i=0; i<jsonObject.getJSONArray("kids").length(); i++) {
                    list.add(jsonObject.getJSONArray("kids").getInt(i)+"" );
                }
            }
            return new Story(jsonObject.getString("title"), jsonObject.getLong("time"), jsonObject.getString("by"),
                    jsonObject.getInt("score"), commentCount + " comment(s)", list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Comment parseCommentsData(String data){

        try {
            JSONObject jsonObject = new JSONObject(data);

            return new Comment(jsonObject.getString("by"), jsonObject.getLong("time"), jsonObject.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
