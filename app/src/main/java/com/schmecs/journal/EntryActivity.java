package com.schmecs.journal;

import com.schmecs.journal.model.Journaldb;
import com.schmecs.journal.model.Post;
import com.schmecs.journal.model.Journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.schmecs.journal.R.menu.menu_entry;
import static com.schmecs.journal.R.menu.menu_main;

public class EntryActivity extends AppCompatActivity {

    String mUserId;
    Journal mJournal;
    Date mDate = new Date();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        SessionManager session = new SessionManager(getApplicationContext());

        mUserId = session.getUserId();
        mJournal = new Journal();
        mJournal.loadJournal(mUserId);
        String postCount = Integer.toString(mJournal.getPostCount());
        Log.d("postCount", postCount);
        Log.d("current user ID", mUserId);

        Button saveButton = (Button) findViewById(R.id.save_entry_button);
        final TextInputEditText entryTextInput = (TextInputEditText) findViewById(R.id.entry_text_input);

        saveButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick (View view) {
                String content = entryTextInput.getText().toString();
                Post post = new Post(mUserId,content,mDate);
                mJournal.addPost(post);
                mJournal.saveJournal();
                String postCount = Integer.toString(mJournal.getPostCount());
                Log.d("postCount", postCount);
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
