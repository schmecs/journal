package com.moodjournal;

import com.teamtreehouse.model.Post;
import com.teamtreehouse.model.Journal;

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
		mDate = DATE_FORMAT.format(new Date());
		mEmpty = (mJournal.get(mDate) == null);
		if (mEmpty) {
			System.out.println("How are you feeling today?");
			}

		}
	}
}