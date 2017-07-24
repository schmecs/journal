package com.schmecs.journal.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Journal implements Serializable {
    private Map<String,Post> mPosts;
    private int mPostId;
    private int nextPostId;
    private Journaldb mJournaldb;
    private String mAuthor;
    private int mLatest;
  
  public Journal() {
      mPosts = new HashMap<String,Post>();
      mJournaldb = new Journaldb();
      //mPostId = 1;
      nextPostId = mPosts.size();
  }

  public void addPost(Post post) {
    mPosts.put(Integer.toString(nextPostId),post);
    nextPostId = mPosts.size();
  }

  public void loadPost(String postId, Post post) {
    mPosts.put(postId, post);
    //nextPostId = mPosts.size();
  }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadJournal(String userName) {
        mJournaldb.createPostTable();
        //try {
            mAuthor = userName.toLowerCase();
        //} catch (IOException ioe) {
        //    ioe.printStackTrace();
        //}
        Map<String, Post> oldPosts = mJournaldb.selectByAuthor(mAuthor);
        for (Map.Entry<String, Post> entry : oldPosts.entrySet()) {
            this.addPost(entry.getValue());
        }
        mLatest = this.getPostCount() - 1; //this is janky and perhaps journal format needs some work, or i need a better way of tracking last id / date
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