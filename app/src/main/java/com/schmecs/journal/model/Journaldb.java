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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Journaldb {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

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
                + " id bigint NOT NULL,"
                + " postId text NOT NULL,\n"
                + " authorId text NOT NULL,\n"
                + " date text NOT NULL,\n"
                + " postContent text,\n"
                + " PRIMARY KEY(id));";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            Log.d("table created", "true");
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
    }

    //temporary for testing purposes
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void dropPostTable() {
        // SQLite connection string
        String url = "jdbc:sqldroid:data/data/com.schmecs.journal/Journaldb.db";
        
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
    public void insert(int tableId, String postId, String authorId, String date, String postContent) {
        String sql = "INSERT INTO all_posts(id,postId,authorId,date,postContent) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableId);
            pstmt.setString(2, postId);
            pstmt.setString(3, authorId);
            pstmt.setString(4, date);
            pstmt.setString(5, postContent);
            pstmt.executeUpdate();
            Log.d("postId saved",postId);
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Map<String, Post> selectByAuthor(String authorId){
        Map<String, Post> oldPosts = new HashMap<String, Post>();
        String sql = "SELECT * FROM all_posts WHERE authorId = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
             pstmt.setString(1, authorId);
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

                Post post = new Post(authorId,thisPostContent,thisDate);

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int maxId() {
        String sql = "SELECT max(id) id FROM all_posts";
        Integer maxId = 0;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs    = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.d("column count", Integer.toString(rsmd.getColumnCount()));
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
            Log.d("maxId in db", Integer.toString(maxId));
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
        return maxId;
    }

}