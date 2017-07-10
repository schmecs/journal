package com.schmecs.journal;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.moodjournal.model.Post;
import com.moodjournal.model.Journal;

import java.sql.Connection;
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
        String url = "jdbc:sqlite:Journaldb.db";
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
        String url = "jdbc:sqlite:Journaldb.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS all_posts (\n"
                + " postId text NOT NULL,\n"
                + " author text NOT NULL,\n"
                + " date text NOT NULL,\n"
                + " score text NOT NULL,\n"
                + " postContent text,\n"
                + " PRIMARY KEY(postId, author));";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //temporary for testing purposes
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void dropPostTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Journaldb.db";
        
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
    public void insert(String postId, String author, String date, String score, String postContent) {
        String sql = "INSERT INTO all_posts(postId,author,date,score,postContent) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, postId);
            pstmt.setString(2, author);
            pstmt.setString(3, date);
            pstmt.setString(4, score);
            pstmt.setString(5, postContent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Journal selectByAuthor(String author){
        Journal mJournal = new Journal();
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
                String thisScoreStr = rs.getString("score");
                int thisScore = Integer.parseInt(thisScoreStr);
                String thisPostContent = rs.getString("postContent");

                Post post = new Post(author,thisScore,thisPostContent,thisDate);

                //load post to Journal
                mJournal.loadPost(postId,post);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException p) {
            System.out.println(p.getMessage());
        }
        return mJournal;
    }

}