package com.schmecs.journal;

import com.schmecs.journal.model.Journal;

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
import android.widget.TextView;

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

        Button entryButton = (Button) findViewById(R.id.toEntryScreen);
        Button backButton = (Button) findViewById(R.id.backHome);

        mUserName = "schmecs";
        mJournal = new Journal();
        mJournal.loadJournal(mUserName);
        String postDate;
        String postText;
        Log.d("postCount",Integer.toString(mJournal.getPostCount()));
        if (mJournal.getPostCount() == 0) {
            postDate = "No posts!";
            postText = "You haven't written anything yet.";
        } else {
            mPostIds = mJournal.postIds();
            String lastPost = Collections.max(mPostIds);
            postDate = mJournal.getPost(lastPost).getDate();
            postText = mJournal.getPost(lastPost).getText();
        }

        TextView dateText = (TextView) findViewById(R.id.entryDate);
        TextView contentText = (TextView) findViewById(R.id.entryContent);
        dateText.setText(postDate);
        contentText.setText(postText);

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHome();
            }
        });

        entryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEntry();
            }
        });
    }

    public void launchHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchEntry() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
}
