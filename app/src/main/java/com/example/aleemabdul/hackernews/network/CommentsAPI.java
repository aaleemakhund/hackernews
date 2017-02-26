package com.example.aleemabdul.hackernews.network;

import android.content.Context;

import com.example.aleemabdul.hackernews.Constants;
import com.example.aleemabdul.hackernews.datahander.CommentDataHandler;
import com.example.aleemabdul.hackernews.model.Comment;
import com.koushikdutta.ion.Response;

import java.util.List;

import static com.example.aleemabdul.hackernews.utils.Utils.parseCommentsData;


public class CommentsAPI implements IResponseProtocol{

    private int itemCount = 0;
    private Callback callback;
    private int listSize = 0;

    public void execute(Context ctx, List<String> commentsIdsList, Callback cb){

        callback = cb;
        listSize = commentsIdsList.size();

        for(int i=0; i<commentsIdsList.size();i++){

            StringBuilder sb = new StringBuilder();
            sb.append(Constants.ITEM_DETAILS);
            sb.append(commentsIdsList.get(i));
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

            Comment comment = parseCommentsData(data.getResult());

            if(comment != null){
                CommentDataHandler.getInstance().addComments(comment);
            }

            checkIfItemCountIsUpdated();
        }
    }

    private void checkIfItemCountIsUpdated(){
        if(itemCount == listSize) {
            itemCount=0;
            callback.callback();
        }
    }
}
