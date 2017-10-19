package com.schmecs.journal;

import com.schmecs.journal.model.Journal;
import com.schmecs.journal.model.Journaldb;
import com.schmecs.journal.model.Post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.schmecs.journal.R.menu.menu_main;
import static com.schmecs.journal.R.menu.menu_read;

public class ReadActivity extends AppCompatActivity {

    private List<Post> postList;
    private RecyclerView mRecyclerView;
    private Journaldb mJournaldb;
    private JournalRVA adapter;
    private ProgressBar mProgressBar;

    String mUserId;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_journal);


        Toolbar readToolbar = (Toolbar) findViewById(R.id.read_toolbar);
        setSupportActionBar(readToolbar);
        readToolbar.inflateMenu(R.menu.menu_read);

        SessionManager session = new SessionManager(getApplicationContext());

        mUserId = session.getUserId();
        mJournaldb = new Journaldb();
        postList = mJournaldb.selectByAuthor(mUserId);
        Collections.sort(postList);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JournalRVA(this, postList);
        mRecyclerView.setAdapter(adapter);
        //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                //REPLACE THIS WITH AN EXPANDED VIEW OF THE SINGLE POST
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read, menu);
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
            case R.id.new_entry:
                this.launchEntry();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void launchHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchEntry() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

//    public Journal getJournal() {
//        return mJournal;
//    }



}
