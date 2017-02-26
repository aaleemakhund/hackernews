package com.example.aleemabdul.hackernews.comments;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleemabdul.hackernews.R;
import com.example.aleemabdul.hackernews.model.Comment;
import com.example.aleemabdul.hackernews.utils.Utils;

import java.util.List;


public class CommentsCustomAdapter extends RecyclerView.Adapter<CommentsCustomAdapter.ViewHolder>  {

    private List<Comment> commentsList;
    private Resources resources;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView by, time, comments;

        public ViewHolder(View view) {
            super(view);

            by = (TextView) view.findViewById(R.id.by);
            time = (TextView) view.findViewById(R.id.time);
            comments = (TextView) view.findViewById(R.id.comment);
        }
    }

    public CommentsCustomAdapter(List<Comment> storiesList, Resources r) {
        this.commentsList = storiesList;
        this.resources = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comments_view, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Comment comment = commentsList.get(position);

        holder.by.setText(holder.by.getResources().getString(R.string.by_text, comment.getBy()));
        holder.time.setText(Utils.timeAgo(comment.getTime(), this.resources));
        holder.comments.setText(Html.fromHtml(comment.getComment()));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

}
