package com.schmecs.journal;

import com.schmecs.journal.model.Journaldb;
import com.schmecs.journal.model.Post;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

//    private ProgressBar mProgressBar;

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
        Journaldb journaldb = new Journaldb();

        List<Post> postList = journaldb.selectByAuthor(mUserId);
        Collections.sort(postList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        JournalRVA adapter = new JournalRVA(this, postList);
        recyclerView.setAdapter(adapter);
        //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(Post post) {
//                //REPLACE THIS WITH AN EXPANDED VIEW OF THE SINGLE POST
//            }
//        });
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
        new EntryFragment().show(getFragmentManager(), "EntryDialogFragment");
    }

}
