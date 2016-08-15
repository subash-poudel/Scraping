package joke.model;

import java.util.ArrayList;

/**
 * Created by Subash on 8/5/16.
 */
public class Joke {
    private String category;
    private ArrayList<JokeItem> items;

    public Joke(String category) {
        this.category = category;
        items = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<JokeItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<JokeItem> items) {
        this.items = items;
    }

    public void addItem(JokeItem item) {
        items.add(item);
    }
}
