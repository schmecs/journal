package com.moodjournal.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

	public class Post {

	private Map<String,String> mPostInfo;
	private PostId mPostID;
	private String mPostString;
	private String mDate;

	public Post(String author, int score, String text, Date date) {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		mDate = DATE_FORMAT.format(date);
		mPostID = new PostId();
		mPostString = mPostID.toString();
		mPostInfo = new HashMap<String,String>();
		mPostInfo.put("id",mPostString);
		mPostInfo.put("score",Integer.toString(score));
		mPostInfo.put("text",text);
		mPostInfo.put("date",mDate);
		mPostInfo.put("author",author);
	}

	public String getID() {
		return (String) mPostInfo.get("id");
	}

	public String getScore() {
		return (String) mPostInfo.get("score");
	}

	public String getText() {
		return (String) mPostInfo.get("text");
	}

	public String getDate() {
		return (String) mPostInfo.get("date");
	}

	public String getAuthor() {
		return (String) mPostInfo.get("author");
	}


	// @Override
	// public String toString() {
	// 	// TODO
	// }


}