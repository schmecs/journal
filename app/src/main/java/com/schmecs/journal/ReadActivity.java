package com.schmecs.journal;

import com.schmecs.journal.model.Journal;
import com.schmecs.journal.model.Journaldb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;

import java.util.Collections;
import java.util.Set;

import static com.schmecs.journal.R.menu.menu_main;

public class ReadActivity extends AppCompatActivity {
    String mUserName;
    Journal mJournal;
    Set<String> mPostIds;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_journal);

        mUserName = "schmecs";
        mJournal = new Journal();
        mJournal.loadJournal(mUserName);
        mPostIds = mJournal.postIds();
        String lastPost = Collections.max(mPostIds);

        String postDate = mJournal.getPost(lastPost).getDate();
        String postText = mJournal.getPost(lastPost).getText();
        Log.d("postDate",postDate);
        Log.d("postText",postText);

    }
}
