package com.moodjournal;

import com.moodjournal.model.Post;
import com.moodjournal.model.Journal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

public class Journaldb {
     /**
     * Connect to a sample database
     */

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

    public static void createPostTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Journaldb.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS all_posts (\n"
                + " postId text PRIMARY KEY,\n"
                + " author text NOT NULL,\n"
                + " date text NOT NULL,\n"
                + " score text NOT NULL,\n"
                + " post_content text\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // public void connectJournal() {
    //     Connection mConn = this.connect();
    //     System.out.println("The connection has been established.");
    // }

    public void insert(String postId, String author, String date, String score, String postContent) {
        String sql = "INSERT INTO all_posts(postId,author,date,score,post_content) VALUES(?,?,?,?,?)";

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

    // public Map<String,Post> selectByAuthor(String author){
    //     String sql = "SELECT * FROM all_posts WHERE author = " + author;
        
    //     try (Connection conn = this.connect();
    //          Statement stmt  = conn.createStatement();
    //          ResultSet rs    = stmt.executeQuery(sql)){
            
    //         // loop through the result set
    //         Map<String,Post> journalContent = new HashMap<String,Post>();
    //         while (rs.next()) {
    //             //retrieve post Id

    //             //retrieve and combine rest of post into Post
    //             System.out.println(rs.getString("id") +  "\t" + 
    //                                rs.getString("name") + "\t" +
    //                                rs.getString("name") + "\t" +
    //                                rs.getString("capacity"));
    //         }
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    // }

    //TODO: Send Journal content to JournalEntry upon beginning of session
}