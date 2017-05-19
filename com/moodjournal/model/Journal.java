package com.moodjournal.model;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journal {
  private Map<Date,Post> mPosts;
  
  public Journal() {
    mPosts = new HashMap<Date,Post>();
  }

  public void addPost(Post post) {
    mPosts.put(post.getDate(),post);
  }

  public Post getPost(Date date) {
    return mPosts.get(date);
  }
  
  public int getPostCount() {
    return mPosts.size();
  }
  
  // FIXME:  This should be cached!
  private Map<String, List<Post>> byAuthor() {
    Map<String, List<Post>> byAuthor = new HashMap<>();
    for (Post post : mPosts.values()) {
      List<Post> authorPosts = byAuthor.get(post.getAuthor());
      if (authorPosts == null) {
        authorPosts = new ArrayList<Post>();
        byAuthor.put(post.getAuthor(), authorPosts);
      }
      authorPosts.add(post);
    }
    return byAuthor;
  }

}