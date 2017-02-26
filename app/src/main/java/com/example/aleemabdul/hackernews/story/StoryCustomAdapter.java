package com.example.aleemabdul.hackernews.story;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.comments.CommentsActivity;
import com.example.aleemabdul.hackernews.model.Story;
import com.example.aleemabdul.hackernews.utils.Utils;

import java.util.List;


public class StoryCustomAdapter extends RecyclerView.Adapter<StoryCustomAdapter.ViewHolder> {


    private List<Story> stories;
    private Resources resources;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, time, userName, score, comments;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            userName = (TextView) view.findViewById(R.id.user_name);
            score = (TextView) view.findViewById(R.id.score);
            comments = (TextView) view.findViewById(R.id.comments);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(stories.get(getLayoutPosition()).getListOfCommentsIds().size() > 0) {
                        Intent intent = new Intent(context, CommentsActivity.class);
                        intent.putStringArrayListExtra("comments_ids", stories.get(getLayoutPosition()).getListOfCommentsIds());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    public StoryCustomAdapter(List<Story> storiesList, Resources r, Context ctx) {
        this.stories = storiesList;
        this.resources = r;
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_story_view, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Story story = stories.get(position);

        holder.title.setText(story.getTitle());
        holder.time.setText(Utils.timeAgo(story.getTime(), this.resources));
        holder.userName.setText(holder.userName.getResources().getString(R.string.by_text, story.getUserName()));
        holder.score.setText(holder.score.getResources().getString(R.string.story_points, story.getScore()));
        holder.comments.setText(story.getComments());
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
