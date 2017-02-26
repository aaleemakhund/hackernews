package com.example.aleemabdul.hackernews.network;

import com.koushikdutta.ion.Response;

public interface IResponseProtocol {
    void responseWithError(Exception error);
    void successWithData(Response<String> data);
}