package joke.model;

/**
 * Created by Subash on 8/5/16.
 */
public class JokeItem {
    private String title;
    private String joke;

    public JokeItem(String title, String joke) {
        this.title = title;
        this.joke = joke;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
