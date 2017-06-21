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
        JournalEntry journalEntry = new JournalEntry();
        //Journaldb journalDb = new Journaldb();
        //journalDb.dropPostTable();
        journalEntry.openJournal();
        // //journalDb.selectByAuthor("rebecca");
	}

}