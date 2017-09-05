package com.schmecs.journal;

import com.schmecs.journal.model.Journaldb;
import com.schmecs.journal.model.Post;
import com.schmecs.journal.model.Journal;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.schmecs.journal.R.menu.menu_entry;

public class EntryActivity extends AppCompatActivity {

    String mUserId;
    Map<String,Post> mJournal;
    Date mDate = new Date();
    private Journaldb mJournaldb;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Toolbar readToolbar = (Toolbar) findViewById(R.id.entry_toolbar);
        setSupportActionBar(readToolbar);
        readToolbar.inflateMenu(R.menu.menu_entry);

        SessionManager session = new SessionManager(getApplicationContext());

        mUserId = session.getUserId();
        Log.d("userId in OnCreate", mUserId);

        Button saveButton = (Button) findViewById(R.id.save_entry_button);
        final TextInputEditText entryTextInput = (TextInputEditText) findViewById(R.id.entry_text_input);

        saveButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick (View view) {
                Log.d("userID in OnClick", mUserId);
                String content = entryTextInput.getText().toString();
                Post post = new Post(mUserId,content,mDate);
                savePost(post);
                launchReader();
            }
        });

    }

    public void launchHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchReader() {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_entry, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePost(Post post) {
        Post mPost = post;
        String mAuthorId = post.getAuthor();
        Log.d("userId in save method", mAuthorId);

        Journaldb.createPostTable();
        mJournaldb = new Journaldb();
        Integer maxId = mJournaldb.maxId();
        int nextId;
        String nextAuthorId;

        if (maxId > 0) {
            mJournal = new HashMap<String,Post>();
            mJournal = mJournaldb.selectByAuthor(mAuthorId);
            Log.d("maxId in save method", Integer.toString(maxId));
            nextId = maxId + 1;
            String maxIdByAuthor = Collections.max(mJournal.keySet());
            nextAuthorId = Integer.toString(Integer.parseInt(maxIdByAuthor) + 1);
        } else {
            nextId = 1;
            nextAuthorId = "1";
        }

        mJournaldb.insert(nextId, nextAuthorId, mAuthorId, mPost.getDate(), mPost.getText());
        //TODO: add validation & toast that post successfully added
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.back:
                this.launchHome();
                return true;
            case R.id.read_journal:
                this.launchReader();
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
