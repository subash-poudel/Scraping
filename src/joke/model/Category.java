package joke.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subash on 8/5/16.
 */
public class Category {
    private String webSiteName;
    private List<CategoryItem> items;

    public Category(String webSiteName) {
        this.webSiteName = webSiteName;
        this.items = new ArrayList<>();
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public List<CategoryItem> getItems() {
        return items;
    }

    public void setItems(List<CategoryItem> items) {
        this.items = items;
    }

    public void addItem(CategoryItem item) {
        items.add(item);
    }
}
