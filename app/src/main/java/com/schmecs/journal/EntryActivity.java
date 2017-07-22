package com.schmecs.journal;

import com.schmecs.journal.model.Post;
import com.schmecs.journal.model.Journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.EditText;

import java.util.Date;

import static com.schmecs.journal.R.menu.menu_main;

public class EntryActivity extends AppCompatActivity {

    SharedPreferences settings;
    String mUserName;
    JournalEntry mJournalEntry = new JournalEntry();
    Journal mJournal;
    Date mDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        settings = getApplicationContext().getSharedPreferences("appSettings",0);
        mUserName = settings.getString("userName",null);

        Intent i = getIntent();
        mJournal = (Journal) i.getSerializableExtra("Journal");

        Button saveButton = (Button) findViewById(R.id.save_entry_button);
        final EditText entryText = (EditText) findViewById(R.id.entry_text);

        saveButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick (View view) {
                String content = entryText.getText().toString();
                Post post = new Post(mUserName,content,mDate);
                mJournal.addPost(post);
                mJournalEntry.saveJournal();
                String postCount = Integer.toString(mJournal.getPostCount());
                Log.d("postcount", postCount);
                launchHome();
            }
        });

    }

    public void launchHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                this.launchHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
