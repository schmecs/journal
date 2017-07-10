package com.schmecs.journal.model;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Journal {
  private Map<String,Post> mPosts;
  private int mPostId;
  private int nextPostId;
  
  public Journal() {
    mPosts = new HashMap<String,Post>();
    //mPostId = 1;
    nextPostId = mPosts.size();
  }

  public void addPost(Post post) {
    mPosts.put(Integer.toString(nextPostId),post);
    nextPostId = mPosts.size();
  }

  public void loadPost(String postId, Post post) {
    mPosts.put(postId, post);
    nextPostId = mPosts.size();
  }

  public Post getPost(String postID) {
    return mPosts.get(postID);
  }
  
  public int getPostCount() {
    return mPosts.size();
  }

  public Set<String> postIds() {
    return mPosts.keySet();
  }

}