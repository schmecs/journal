import com.moodjournal.model.Post;
import com.moodjournal.model.PostId;
import com.moodjournal.model.Journal;
import com.moodjournal.JournalEntry;

import java.util.Date;

public class Testingthings {
  
  public static void main(String[] args) {
        Post post = new Post("Rebecca", 3, "test", new Date());
        Journal journal = new Journal();
        JournalEntry journalEntry = new JournalEntry(journal);
        journal.addPost(post);
        journalEntry.openJournal();
	}
}