package com.moodjournal.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

	private String mScore;
	private String mText;
	private Date mDate;
	private Map mPostInfo;

	public Post(int score, String text, Date date) {
		mScore = Integer.toString(score);
		mText = text;
		mDate = date;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		String mDateFormat = DATE_FORMAT.format(mDate);
		mPostInfo = new HashMap<String,String>();
		mPostInfo.put("score",mScore);
		mPostInfo.put("text",mText);
		mPostInfo.put("date",mDateFormat);
	}

	public String getScore() {
		//return mScore;
		return (String) mPostInfo.get("score");
	}

	public String getText() {
		//return mText;
		return (String) mPostInfo.get("text");
	}

	public String getDate() {
		//return mDate;
		return (String) mPostInfo.get("date");
	}


	// @Override
	// public String toString() {
	// 	// TODO
	// }


}