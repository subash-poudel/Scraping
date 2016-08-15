package joke.model;

/**
 * Created by Subash on 8/5/16.
 */
public class CategoryItem {
    private String name;
    private String url;

    public CategoryItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("name=%s url=%s", name, url);
    }
}
