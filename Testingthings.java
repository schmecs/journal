import com.moodjournal.model.Post;

import java.util.Date;

public class Testingthings {
  
  public static void main(String[] args) {
        Post post = new Post(3, "test", new Date());
        System.out.printf("%s%n",post.getScore());
        System.out.printf("%s%n",post.getText());
        System.out.printf("%s%n",post.getDate());
	}
}