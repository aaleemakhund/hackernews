package com.example.aleemabdul.hackernews.splash;

import android.os.Bundle;

import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.base.BaseActivity;
import com.example.aleemabdul.hackernews.network.Callback;
import com.example.aleemabdul.hackernews.network.IResponseProtocol;
import com.example.aleemabdul.hackernews.network.TopStoriesAPI;
import com.example.aleemabdul.hackernews.story.StoryActivity;
import com.example.aleemabdul.hackernews.utils.Utils;
import com.koushikdutta.ion.Response;

public class SplashActivity extends BaseActivity implements Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new TopStoriesAPI().execute(this, this);
    }

    @Override
    public void callback() {
        gotoActivity(StoryActivity.class);
        finish();
    }
}
