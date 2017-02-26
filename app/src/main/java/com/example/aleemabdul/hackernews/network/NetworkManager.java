package com.example.aleemabdul.hackernews.network;

import android.content.Context;

import com.example.aleemabdul.hackernews.Constants;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class NetworkManager {

    private static NetworkManager INSTANCE = new NetworkManager();

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    public void executeRequest(Context context, String url, final IResponseProtocol delegate){

        Ion.with(context)
                .load(url)
                .setTimeout(Constants.TIME_OUT)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response result) {
                        if(result != null){
                            respondTo(delegate, result);
                        }else {
                            respondTo(delegate, e);
                        }
                    }
                });
    }

    private void respondTo(IResponseProtocol delegate, Exception error) {
        if (null != delegate) {
            delegate.responseWithError(error);
        }
    }

    private void respondTo(IResponseProtocol delegate, Response<String> data) {
        if (null != delegate) {
            delegate.successWithData(data);
        }
    }
}
