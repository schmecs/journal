package com.schmecs.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

//import com.schmecs.journal.model.Journal;

import java.io.Serializable;
import java.util.Date;

import static com.schmecs.journal.R.menu.menu_main;

public class MainActivity extends AppCompatActivity implements Serializable {

    String mUserName;


    Date mDate = new Date();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //isUserLoggedIn = getApplicationContext().getSharedPreferences("loggedInUser",0);
        mUserName = "human";
//        if (isUserLoggedIn.contains("userName")) {
//            if (isUserLoggedIn.getString("userName",null) == null) {
//                mUserName = loginUser();
//            } else {
//                mUserName = isUserLoggedIn.getString("userName",null);
//            }
//        } else {
//            mUserName = loginUser();
//        }
        Log.d("Check mUserName","Value: " + mUserName);

        TextView textView = (TextView) findViewById(R.id.welcome_screen);
        // using postCount in welcome text just as a check for now
        String welcomeText = "Welcome, " + mUserName;
        textView.setText(welcomeText);

    }

//    public String loginUser() {
//        this.getUserName();
//        mUserName = isUserLoggedIn.getString("userName", null);
//        return mUserName;
//    }

    //TODO: pass in class to launch as a variable?
    public void launchEntry() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    public void launchReader() {
        Intent intent = new Intent(this, ReadActivity.class);
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
            case R.id.new_entry:
                this.launchEntry();
                return true;
            case R.id.read_journal:
                this.launchReader();
                return true;
//            case R.id.logout:
//                editor.clear();
//                editor.commit();
//                TextView textView = (TextView) findViewById(R.id.welcome_screen);
//                String welcomeText = "Welcome, stranger";
//                textView.setText(welcomeText);
            default:
                return false;
        }
    }

//    public void getUserName() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Enter username");
//
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                editor = isUserLoggedIn.edit();
//                String userInput = input.getText().toString();
//                editor.putString("userName",userInput);
//                editor.apply();
//            }
//        }
//        );
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//
//    }
}
