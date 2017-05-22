package com.moodjournal.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

	private Map mPostInfo;

	public Post(String author, int score, String text, Date date) {

		//SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		mPostInfo = new HashMap<String,String>();
		mPostInfo.put("score",Integer.toString(score));
		mPostInfo.put("text",text);
		mPostInfo.put("date",date);
		mPostInfo.put("author",author);
	}

	public String getScore() {
		return (String) mPostInfo.get("score");
	}

	public String getText() {
		return (String) mPostInfo.get("text");
	}

	public Date getDate() {
		return (Date) mPostInfo.get("date");
	}

	public String getAuthor() {
		return (String) mPostInfo.get("author");
	}


	// @Override
	// public String toString() {
	// 	// TODO
	// }


}