package com.example.aleemabdul.hackernews.datahander;

import com.example.aleemabdul.hackernews.Constants;
import com.example.aleemabdul.hackernews.model.Story;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class StoryDataHandler {

    private static StoryDataHandler INSTANCE = null ;

    public static StoryDataHandler getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new StoryDataHandler();
        }
        return INSTANCE;
    }

    private List<Story> storyList;
    private List<Integer> storyIdList;
    List<List<Integer>> smallerLists;

    private StoryDataHandler() {
        storyList = new ArrayList<>();
        storyIdList = new ArrayList<>();
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void addStoryItem(Story story) {
        storyList.add(story);
    }

    public void clearStoryItemList(){
        storyList.clear();
    }

    public List<Integer> getStoryIdList() {
        return storyIdList;
    }

    public void setStoryIdList(List<Integer> storyIdList) {
        this.storyIdList = storyIdList;
    }

    public void addStoryId(int id){
        storyIdList.add(id);
    }

    public void clearStoryIdsList(){
        storyIdList.clear();
    }

    public void splitList(){
         smallerLists = Lists.partition(storyIdList, Constants.SPLIT_LIST_SIZE);
    }

    public List<List<Integer>> getSplitList(){
        return smallerLists;
    }
}
