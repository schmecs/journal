package com.schmecs.journal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

class SessionManager {
    // Shared Preferences
    private SharedPreferences mPref;

    // Editor for Shared preferences
    private Editor mEditor;

    // Context
    private Context mContext;

    // Sharedpref file name
    private static final String PREF_NAME = "JournalPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    private static final String GIVEN_NAME = "name";

    // User ID (make variable public to access from outside)
    private static final String USER_ID = "id";

    // Constructor
    SessionManager(Context context){
        this.mContext = context;
        int PRIVATE_MODE = 0;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }



    /**
     * Create login session
     * */
    void createLoginSession(String name, String id){
        // Storing login value as TRUE
        mEditor.putBoolean(IS_LOGIN, true);

        // Storing name in mPref
        mEditor.putString(GIVEN_NAME, name);

        // Storing id in mPref
        mEditor.putString(USER_ID, id);

        // commit changes
        mEditor.commit();
    }

    /**
     * Get stored session data
     * */
    String getUsername(){

        // return user
        return mPref.getString(GIVEN_NAME, null);
    }

    /**
     * Get stored session data
     * */
    String getUserId(){

        // return user
        return mPref.getString(USER_ID, null);
    }

    /**
     * Clear session details
     * */
    void logoutUser(){
        // Clearing all data from Shared Preferences

        CharSequence text = "Logging out " + this.getUsername();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(mContext, text, duration);
        toast.show();

        mEditor.clear();
        mEditor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start Login Activity
        mContext.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    private boolean isLoggedIn(){
        return mPref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }

    }
}
