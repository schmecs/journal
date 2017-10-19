package com.schmecs.journal.model;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journaldb {

    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

    public Journaldb () {
        createPostTable();
    }

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
        
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                Statement stmt = conn.createStatement();
                try {
                    // create a new table
                    stmt.execute(sql);
                    Log.d("table created", "true");
                } finally {
                    stmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
    }


    public static void dropPostTable() {
        // SQLite connection string
        String url = "jdbc:sqldroid:data/data/com.schmecs.journal/Journaldb.db";
        
        String sql = "DROP TABLE all_posts;";
        
        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                Statement stmt = conn.createStatement();
                try {
                    // create a new table
                    stmt.execute(sql);
                } finally {
                    stmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public int getNextUserPostId(String authorId) {
        String lastUserPostId = "0";
        String sql = "SELECT COUNT(*) postId FROM all_posts WHERE authorId = ?";
        try {
            Connection conn = this.connect();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    pstmt.setString(1, authorId);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        lastUserPostId = rs.getString(1);
                    }
                    Log.d("max User Post Id", lastUserPostId);
                } finally {
                    pstmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
                Log.d("error",e.getMessage());
            }
            return Integer.parseInt(lastUserPostId) + 1;
        }


    public boolean insert(Post post) {
        int tableId = nextId();
        String authorId = post.getAuthor();
        int userPostId = getNextUserPostId(authorId);
        String date = post.getDate();
        String postContent = post.getText();

        String sql = "INSERT INTO all_posts(id,postId,authorId,date,postContent) VALUES(?,?,?,?,?)";

        try {
            Connection conn = this.connect();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    pstmt.setInt(1, tableId);
                    pstmt.setString(2, Integer.toString(userPostId));
                    pstmt.setString(3, authorId);
                    pstmt.setString(4, date);
                    pstmt.setString(5, postContent);
                    pstmt.executeUpdate();
                    Log.d("tableId", Integer.toString(tableId));
                    Log.d("userPostId", Integer.toString(userPostId));
                    Log.d("authorId", authorId);
                } finally {
                    pstmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }

        return userPostId + 1 == (getNextUserPostId(authorId)); // check that new user ID has incremented
    }

    public List<Post> selectByAuthor(String authorId){
        List<Post> oldPosts = new ArrayList<>();
        String sql = "SELECT * FROM all_posts WHERE authorId = ?";
        Connection conn = this.connect();
        PreparedStatement pstmt = null;
        try {
            pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, authorId);
             ResultSet rs    = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {

                //retrieve and combine contents into Post
                //String thisAuthor = rs.getString("author");
                String thisDateStr = rs.getString("date");
                Date thisDate = DATE_FORMAT.parse(thisDateStr);
                String thisPostContent = rs.getString("postContent");

                Post post = new Post(authorId,thisPostContent,thisDate);

                //load post to Journal
                oldPosts.add(post);
            }
        } catch (SQLException|ParseException e) {
            Log.d("error", e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    Log.d("error", e.getMessage());
                }
            }
        }
        return oldPosts;
    }

    public int nextId() {
        String sql = "SELECT max(id) id FROM all_posts";
        Integer maxId = 0;
        try {
            Connection conn = this.connect();
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                try {
                    ResultSet rs = pstmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    Log.d("column count", Integer.toString(rsmd.getColumnCount()));
                    while (rs.next()) {
                        maxId = rs.getInt(1);
                    }
                    Log.d("maxId in db", Integer.toString(maxId));
                } finally {
                    pstmt.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            Log.d("error",e.getMessage());
        }
        return maxId + 1;
    }

}