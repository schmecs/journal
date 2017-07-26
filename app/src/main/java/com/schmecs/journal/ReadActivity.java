package com.schmecs.journal;

import com.schmecs.journal.model.Journal;
import com.schmecs.journal.model.Journaldb;

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

import static com.schmecs.journal.R.menu.menu_main;

public class ReadActivity extends AppCompatActivity {
    String mUserName;
    Journal mJournal;
    Journaldb mJournaldb;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_journal);
    }
}
