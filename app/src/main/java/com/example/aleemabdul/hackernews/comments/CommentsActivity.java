package com.example.aleemabdul.hackernews.comments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.datahander.CommentDataHandler;
import com.example.aleemabdul.hackernews.network.Callback;
import com.example.aleemabdul.hackernews.network.CommentsAPI;
import com.example.aleemabdul.hackernews.story.EndlessRecyclerOnScrollListener;
import com.example.aleemabdul.hackernews.story.RecyclerItemDecoration;

import java.util.List;

public class CommentsActivity  extends AppCompatActivity {

    private RecyclerView commentsRecycleView;
    private CommentsCustomAdapter commentsCustomAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private  int listLoaded = 0;
    private SwipeRefreshLayout swipeRefreshLayout;

    private EndlessRecyclerOnScrollListener endlessScroller = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int current_page) {
            int listLoadedCount = listLoaded + 1;
            if(listLoadedCount < CommentDataHandler.getInstance().getSplitList().size()) {
                setRefreshing();
                listLoaded++;
                loadData();
            }

        }
    };

    private void setRefreshing(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CommentDataHandler.getInstance().setCommentsIdList(extras.getStringArrayList("comments_ids"));
        }

        commentsRecycleView = (RecyclerView) findViewById(R.id.comments_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        setUp();
        setRefreshing();
        loadData();
    }

    private void setUp() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        commentsRecycleView.setHasFixedSize(true);
        commentsRecycleView.addItemDecoration(createItemDecoration(getResources()));

        commentsRecycleView.setLayoutManager(linearLayoutManager);
        commentsRecycleView.setOnScrollListener(endlessScroller);

        commentsCustomAdapter = new CommentsCustomAdapter(CommentDataHandler.getInstance().getCommentsList(), getResources());
        commentsRecycleView.setAdapter(commentsCustomAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorOrange, R.color.colorDarkOrange);
        swipeRefreshLayout.setEnabled(false);
    }

    private RecyclerItemDecoration createItemDecoration(Resources resources) {
        int verticalItemSpacingInPx = resources.getDimensionPixelSize(R.dimen.feed_divider_height);
        int horizontalItemSpacingInPx = resources.getDimensionPixelSize(R.dimen.feed_padding_infra_spans);
        return new RecyclerItemDecoration(verticalItemSpacingInPx, horizontalItemSpacingInPx);
    }

    private void loadData(){

        List<String> smallerList = CommentDataHandler.getInstance().getSplitList().get(listLoaded);

        new CommentsAPI().execute(this, smallerList, new Callback() {
            @Override
            public void callback() {
                commentsCustomAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            CommentDataHandler.getInstance().getCommentsList().clear();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
