package com.example.aleemabdul.hackernews.network;

import android.content.Context;

import com.example.aleemabdul.hackernews.Constants;
import com.example.aleemabdul.hackernews.utils.Utils;
import com.koushikdutta.ion.Response;

public class TopStoriesAPI implements IResponseProtocol{

    private Callback callback;
    public void execute(Context ctx, Callback cb){
        callback = cb;
        NetworkManager.getInstance().executeRequest(ctx, Constants.TOP_STORIES, this);
    }

    @Override
    public void responseWithError(Exception error) {
        callback.callback();
    }

    @Override
    public void successWithData(Response<String> data) {

        Utils.parseStoriesIdData(data.getResult());

        callback.callback();
    }
}
