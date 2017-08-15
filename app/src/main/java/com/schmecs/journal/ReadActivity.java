package com.schmecs.journal;

import com.schmecs.journal.model.Journal;
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
import java.util.Set;

import static com.schmecs.journal.R.menu.menu_main;

public class ReadActivity extends AppCompatActivity {

    private List<Post> postList;
    private RecyclerView mRecyclerView;
    private JournalRVA adapter;
    private ProgressBar mProgressBar;
    private Context mContext;

    String mUserName;
    Journal mJournal;
    Set<String> mPostIds;


    //TODO: GET RID OF LOADING CIRCLE
    //TODO: FIGURE OUT WHAT'S GOING ON WITH PROGRESS BAR
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_journal);

        mUserName = "schmecs";
        mJournal = new Journal();
        mJournal.loadJournal(mUserName);
        postList = this.makeList(mJournal);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JournalRVA(mContext, postList);
        mRecyclerView.setAdapter(adapter);
        //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Button entryButton = (Button) findViewById(R.id.toEntryScreen);
        Button backButton = (Button) findViewById(R.id.backHome);

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

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                //REPLACE THIS WITH AN EXPANDED VIEW OF THE SINGLE POST
                Toast.makeText(ReadActivity.this, post.getDate(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private List<Post> makeList(Journal journal) {
            postList = new ArrayList<>();

            for (int i = 0; i < journal.getPostCount(); i++) {
                Post post = journal.getPost(Integer.toString(i));
                postList.add(post);
            }
            //Sort posts with newest first for recycler view
            Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return post2.getDateStamp().compareTo(post1.getDateStamp());
            }
        });
        return postList;
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
