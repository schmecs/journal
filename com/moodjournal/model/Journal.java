package com.moodjournal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journal {
  private List<Post> mPosts;
  
  public Journal() {
    mPosts = new ArrayList<Post>();
  }

  public void addPost(Post post) {
    mPosts.add(post);
  }
  
  public int getPostCount() {
    return mPosts.size();
  }
  
  // FIXME:  This should be cached!
  private Map<String, List<Post>> byAuthor() {
    Map<String, List<Post>> byAuthor = new HashMap<>();
    for (Post post : mPosts) {
      List<Post> authorPosts = byAuthor.get(post.getAuthor());
      if (authorPosts == null) {
        authorPosts = new ArrayList<Post>();
        byAuthor.put(post.getAuthor(), authorPosts);
      }
      authorPosts.add(post);
    }
    return byAuthor;
  }