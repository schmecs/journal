package com.moodjournal.model;

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
  
  public Journal() {
    mPosts = new HashMap<String,Post>();
    mPostId = 1;
  }

  public void addPost(Post post) {
    mPosts.put(Integer.toString(mPostId),post);
    mPostId++;
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