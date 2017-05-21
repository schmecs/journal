package com.moodjournal;

import com.moodjournal.model.Post;
import com.moodjournal.model.Journal;

import java.io.*;

import java.text.SimpleDateFormat;

import java.util.*;

public class JournalEntry {
	private Journal mJournal;
	private BufferedReader mReader;
	private Map<String, String> mMenu;
	private Boolean mEmpty;

	public JournalEntry(Journal journal) {
		mJournal = journal;
		mReader = new BufferedReader(new InputStreamReader(System.in));
		mMenu = new HashMap<String, String>();
		mMenu.put("create","Create a new post");
		mMenu.put("edit","Edit an existing post");
		mMenu.put("read","Read old posts");
		mMenu.put("quit","Close the journal");
	}

	private String promptAction() throws IOException {
		Date mDate = new Date();
		mEmpty = (mJournal.getPost(mDate) == null);
		String mResult = "";
		if (mEmpty) {
			mResult = "How are you feeling today?"; //temporary for test
			} else {
			mResult = "This is a test."; //temporary for test
			}
		System.out.printf("%s %n",mResult);
		return mResult;
	}

	public void openJournal() {
		try { promptAction();
		} catch(IOException ioe) {
			System.out.println("Problem");
			ioe.printStackTrace();
		}
	}
}

