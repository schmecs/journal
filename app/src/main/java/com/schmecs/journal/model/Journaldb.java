package com.schmecs.journal.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Journaldb {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

    private Connection connect() {
        // SQLite connection string
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Failed to register SQLDroidDriver");
        }
        String url = "jdbc:sqldroid:/data/data/com.schmecs.journal/Journaldb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createPostTable() {
        // SQLite connection string
        String url = "jdbc:sqldroid:/data/data/com.schmecs.journal/Journaldb.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS all_posts (\n"
                + " postId text NOT NULL,\n"
                + " author text NOT NULL,\n"
                + " date text NOT NULL,\n"
                + " postContent text,\n"
                + " PRIMARY KEY(postId, author));";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
    }

    //temporary for testing purposes
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void dropPostTable() {
        // SQLite connection string
        String url = "jdbc:sqldroid:/data/data/com.schmecs.journal/Journaldb.db";
        
        String sql = "DROP TABLE all_posts;";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void insert(String postId, String author, String date, String postContent) {
        String sql = "INSERT INTO all_posts(postId,author,date,postContent) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, postId);
            pstmt.setString(2, author);
            pstmt.setString(3, date);
            pstmt.setString(4, postContent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Map<String, Post> selectByAuthor(String author){
        Map<String, Post> oldPosts = new HashMap<String, Post>();
        String sql = "SELECT * FROM all_posts WHERE author = ?"; // TODO: fix parameterized query with author string
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
             pstmt.setString(1, author);
             ResultSet rs    = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                //retrieve post Id
                String postId = rs.getString("postId");

                //retrieve and combine rest of post into Post
                //String thisAuthor = rs.getString("author");
                String thisDateStr = rs.getString("date");
                Date thisDate = DATE_FORMAT.parse(thisDateStr);
                String thisPostContent = rs.getString("postContent");

                Post post = new Post(author,thisPostContent,thisDate);

                //load post to Journal
                oldPosts.put(postId, post);
            }
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        } catch (ParseException p) {
            Log.d("error",p.getMessage());
        }
        return oldPosts;
    }

}