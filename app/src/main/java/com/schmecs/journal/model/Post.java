package com.schmecs.journal.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

	public class Post {

	private Map<String,String> mPostInfo;
	private String mPostString;
	private String mDate;

	public Post(String author, String text, Date date) {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		mDate = DATE_FORMAT.format(date);
		mPostInfo = new HashMap<String,String>();
		mPostInfo.put("text",text);
		mPostInfo.put("date",mDate);
		mPostInfo.put("author",author);
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