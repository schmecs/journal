package com.schmecs.journal;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.schmecs.journal.model.Post;
import com.schmecs.journal.model.Journal;

import java.io.*;

import java.text.SimpleDateFormat;

import java.util.*;

public class JournalEntry implements Serializable {
	//private Journaldb mJournaldb;
	private Journal mJournal;
	private String mAuthor;
	private int mLatest;
	private BufferedReader mReader;
	private Scanner	mScanner;
	private Map<String, String> mMenu;
	private Boolean mEmpty;
	public static Date mDate;

	public JournalEntry() {
		//mJournaldb = new Journaldb();
		mJournal = new Journal();
		mReader = new BufferedReader(new InputStreamReader(System.in));
		mMenu = new HashMap<String, String>();
		mDate = new Date();
		mScanner = new Scanner(System.in);
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		String mDateFormat = DATE_FORMAT.format(mDate);
		mMenu.put("create","Create a new post");
		//mMenu.put("edit","Edit an existing post");
		mMenu.put("read","Read old posts");
		mMenu.put("quit","Close the journal");
	}

	//TODO: Replace with login screen of some kind
	private String getUser(String userName) throws IOException {
		String mUser;
		//System.out.printf("Enter your name: %n");
		mUser = userName;
		return mUser;
	}

	//TODO: Replace with user input via menu selection
	private String promptAction() throws IOException {
		System.out.printf("What would you like to do? %n");
		for (Map.Entry<String,String> option : mMenu.entrySet()) {
		System.out.printf("%s - %s %n", 
			option.getKey(), 
			option.getValue());
		}
		String mResult = mReader.readLine();
		return mResult;
	}

	//TODO: New layout with text entry field
	private void createPost() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		String mDateFormat = DATE_FORMAT.format(mDate);
		System.out.printf("%nNew post on %s: How are you feeling? %n", mDateFormat);
		String mText = "";
		System.out.println("Type today's journal entry: ");
		try {
			mText = mReader.readLine();
		} catch (IOException ioe) {
				System.out.println("Problem: ");
				ioe.printStackTrace();
		}
		Post mPost = new Post(mAuthor, mText, mDate);
		mJournal.addPost(mPost);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public Journal loadJournal(String userName) {
		mJournaldb.createPostTable();
		try {
			mAuthor = this.getUser(userName).toLowerCase();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		mJournal = mJournaldb.selectByAuthor(mAuthor);
		mLatest = mJournal.getPostCount() - 1; //this is janky and perhaps journal format needs some work, or i need a better way of tracking last id / date
		return mJournal;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void saveJournal() {
		for (String id : mJournal.postIds()) {
			if (Integer.parseInt(id) > mLatest) {
				Post post = mJournal.getPost(id);
        		mJournaldb.insert(id, mAuthor, post.getDate(), post.getText());
			}
    	}

		//TODO: deal with posts that were edited -- need to overwrite data
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void openJournal(String userName) {
		this.loadJournal(userName);
		String choice = "";
		do {
			try { 
			choice = promptAction();
			while (!mMenu.keySet().contains(choice)) {
				System.out.println("Please enter a valid choice.");
				choice = promptAction();
			} 
			switch(choice) {
				case "create" : 
					createPost();
					break;
				case "read" :
					readJournal();
					break;
				case "quit" :
					this.saveJournal();
					System.out.printf("Saving updates . . . %n");
					System.out.printf("Bye for now. %n");
					break;
				default :
					System.out.printf("Please enter a valid choice. %n"); //placeholder
				}
			} catch(IOException ioe) {
				System.out.println("Problem");
				ioe.printStackTrace();
			}
		} while (!choice.equals("quit"));
	}

	public void readJournal() {
		
		for (String id : mJournal.postIds()) {
        Post post = mJournal.getPost(id);
        System.out.printf("%n%s: %n%s %n%n",
                          post.getDate(),
                          post.getText());
    	}
	} 
}

