package com.schmecs.journal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Serializable;

import static com.schmecs.journal.R.menu.menu_main;

public class MainActivity extends AppCompatActivity implements Serializable {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SessionManager session = new SessionManager(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                launchEntry();
            }
        });

        Log.d("Check mUserName","Value: " + session.getUsername());

        TextView textView = (TextView) findViewById(R.id.welcome_screen);

        String welcomeText = String.format("Welcome, %s", session.getUsername());
        textView.setText(welcomeText);

    }

    //TODO: what's the right way not to duplicate this method?
    public void launchEntry() {
        new EntryFragment().show(getFragmentManager(), "EntryDialogFragment");
    }

    public void launchReader() {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.read_journal:
                this.launchReader();
                return true;
            case R.id.logout:
                SessionManager session = new SessionManager(getApplicationContext());
                session.logoutUser();
                finish();
                return true;
            default:
                return false;
        }
    }
}
