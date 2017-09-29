package com.schmecs.journal.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Post implements Comparable<Post>, Parcelable {

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a", Locale.ENGLISH);

	private Map<String,String> mPostInfo;

	public Post(String authorId, String text, Date date) {

		mPostInfo = new HashMap<>();
		mPostInfo.put("text",text);
		mPostInfo.put("date",DATE_FORMAT.format(date));
		mPostInfo.put("authorId",authorId);
	}

	public String getText() {
		return mPostInfo.get("text");
	}

	public String getDate() {
		return mPostInfo.get("date");
	}

	public String getAuthor() {
		return mPostInfo.get("authorId");
	}

	public Date getDateStamp() {
		try {
			return DATE_FORMAT.parse(mPostInfo.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int compareTo(@NonNull Post post) {
		return post.getDateStamp().compareTo(this.getDateStamp());
	}

	//Not used yet
	@Override
	public int describeContents() {
		return 0;
	}

	// Not used yet
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(mPostInfo.get("date"));
		parcel.writeString(mPostInfo.get("authorId"));
		parcel.writeString(mPostInfo.get("text"));
	}

	// Not used yet
	public static final Parcelable.Creator<Post> CREATOR
			= new Parcelable.Creator<Post>() {
		public Post createFromParcel(Parcel in) {
			return new Post(in);
		}

		public Post[] newArray(int size) {
			return new Post[size];
		}
	};

	private Post(Parcel in) {
		mPostInfo = new HashMap<>();
		mPostInfo.put("date", in.readString());
		mPostInfo.put("authorId", in.readString());
		mPostInfo.put("text", in.readString());
	}

}