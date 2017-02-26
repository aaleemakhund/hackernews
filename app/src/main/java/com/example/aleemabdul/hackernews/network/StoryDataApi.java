package com.example.aleemabdul.hackernews.network;

import android.content.Context;

import com.example.aleemabdul.hackernews.Constants;
import com.example.aleemabdul.hackernews.model.Story;
import com.example.aleemabdul.hackernews.datahander.StoryDataHandler;
import com.koushikdutta.ion.Response;

import java.util.List;

import static com.example.aleemabdul.hackernews.utils.Utils.parseStoryData;

public class StoryDataApi implements IResponseProtocol{

    int itemCount = 0;
    Callback callback;
    int listSize = 0;

    public void execute(Context ctx, List<Integer> storyList, Callback cb){

        callback = cb;
        listSize = storyList.size();

        for(int i=0; i<storyList.size();i++){

            StringBuilder sb = new StringBuilder();
            sb.append(Constants.ITEM_DETAILS);
            sb.append(storyList.get(i));
            sb.append(".json");

            NetworkManager.getInstance().executeRequest(ctx, sb.toString(), this);
        }
    }

    @Override
    public void responseWithError(Exception error) {
        itemCount ++;
        checkIfItemCountIsUpdated();
    }

    @Override
    public void successWithData(Response<String> data) {
        itemCount++;

        if(data.getResult() !=null){

            Story story = parseStoryData(data.getResult());
            if(story != null){
                StoryDataHandler.getInstance().addStoryItem(story);
            }

            checkIfItemCountIsUpdated();
        }
    }

    private void checkIfItemCountIsUpdated(){

        if(itemCount >= listSize) {
            itemCount=0;
            callback.callback();
        }
    }
}
