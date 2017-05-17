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