package com.example.aleemabdul.hackernews.story;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.base.BaseActivity;
import com.example.aleemabdul.hackernews.datahander.StoryDataHandler;
import com.example.aleemabdul.hackernews.network.Callback;
import com.example.aleemabdul.hackernews.network.StoryDataApi;
import com.example.aleemabdul.hackernews.network.TopStoriesAPI;

import java.util.List;


public class StoryActivity extends BaseActivity implements  Callback, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView storyRecycleView;
    private RecyclerView.LayoutManager storiesLayoutManager;
    private StoryCustomAdapter storyCustomAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int listLoaded = 0;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private  EndlessRecyclerOnScrollListener endlessScroller = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int current_page) {
            int listLoadedCount = listLoaded + 1;
            if(listLoadedCount < StoryDataHandler.getInstance().getSplitList().size()) {
                setRefreshing();
                listLoaded++;
                loadData();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        storyRecycleView = (RecyclerView) findViewById(R.id.story_list);

        setUp();
        setRefreshing();
        loadData();
    }

    private void setRefreshing(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void loadData() {
        List<Integer> smallerList = StoryDataHandler.getInstance().getSplitList().get(listLoaded);
        new StoryDataApi().execute(this, smallerList, this);
    }

    private void setUp() {

        storyRecycleView.setHasFixedSize(true);
        storyRecycleView.addItemDecoration(createItemDecoration(getResources()));

        storyRecycleView.setLayoutManager(linearLayoutManager);
        storyRecycleView.setOnScrollListener(endlessScroller);

        storyCustomAdapter = new StoryCustomAdapter(StoryDataHandler.getInstance().getStoryList(), getResources(), this);
        storyRecycleView.setAdapter(storyCustomAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorOrange, R.color.colorDarkOrange);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private RecyclerItemDecoration createItemDecoration(Resources resources) {
        int verticalItemSpacingInPx = resources.getDimensionPixelSize(R.dimen.feed_divider_height);
        int horizontalItemSpacingInPx = resources.getDimensionPixelSize(R.dimen.feed_padding_infra_spans);
        return new RecyclerItemDecoration(verticalItemSpacingInPx, horizontalItemSpacingInPx);
    }

    @Override
    public void onRefresh() {

        listLoaded = 0;
        StoryDataHandler.getInstance().clearStoryIdsList();
        StoryDataHandler.getInstance().clearStoryItemList();
        endlessScroller.previousTotal = 0;

        storyCustomAdapter.notifyDataSetChanged();
        new TopStoriesAPI().execute(this, new Callback() {
            @Override
            public void callback() {
                loadData();
            }
        });
    }

    @Override
    public void callback() {
        storyCustomAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

}
