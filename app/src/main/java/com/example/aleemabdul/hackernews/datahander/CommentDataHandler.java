package com.example.aleemabdul.hackernews.datahander;

import com.example.aleemabdul.hackernews.Constants;
import com.example.aleemabdul.hackernews.model.Comment;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class CommentDataHandler {

    private static CommentDataHandler INSTANCE = null ;

    public static CommentDataHandler getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new CommentDataHandler();
        }
        return INSTANCE;
    }

    private List<String> commentsIdList;
    private List<List<String>> splitedCommentIdList;
    private List<Comment> commentsList;

    private CommentDataHandler() {
        commentsIdList = new ArrayList<>();
        commentsList = new ArrayList<>();
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public List<String> getCommentsIdList() {
        return commentsIdList;
    }

    public void setCommentsIdList(List<String> commentsIdList) {
        this.commentsIdList = commentsIdList;
        splitList();
    }

    public void addComments(Comment c) {
        commentsList.add(c);
    }

    public void splitList(){
        splitedCommentIdList = Lists.partition(commentsIdList, Constants.SPLIT_LIST_SIZE);
    }

    public List<List<String>> getSplitList(){
        return splitedCommentIdList;
    }
}
