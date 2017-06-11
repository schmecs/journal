import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.moodjournal.model.Post;
import com.moodjournal.model.Journal;
import com.moodjournal.JournalEntry;
import com.moodjournal.Journaldb;

import java.util.Date;

public class Testingthings {
  
  public static void main(String[] args) {
        // JournalEntry journalEntry = new JournalEntry();
        // journalEntry.openJournal();
        Journaldb journalDb = new Journaldb();
        journalDb.createPostTable();
        journalDb.insert("1","rebecca","06-10-2017","4","yay");
        //journalDb.selectByAuthor("rebecca");
	}

}