package com.schmecs.journal.model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post implements Comparator<Post> {

    private Map<String,String> mPostInfo;
    private String mPostString;
    private String mDate;

	public Post(String author, String text, Date date) {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
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

	public Date getDateStamp() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		try {
			return DATE_FORMAT.parse(mPostInfo.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	    //sorts in ascending date order but i'm not currently using this...
		@Override
		public int compare(Post post1, Post post2) {
			return post1.getDateStamp().compareTo(post2.getDateStamp());
		}

		//public String getAuthor() {
	//	return (String) mPostInfo.get("author");
	//}


	// @Override
	// public String toString() {
	// 	// TODO
	// }


}