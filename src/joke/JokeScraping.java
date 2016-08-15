package joke;

import com.google.gson.Gson;
import joke.model.Category;
import joke.model.CategoryItem;
import joke.model.Joke;
import joke.model.JokeItem;
import org.jsoup.nodes.Element;
import utils.FileUtils;
import utils.PrintHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subash on 8/5/16.
 */
public class JokeScraping {
    public static final String BASE_URL = "http://www.rd.com/jokes/";
    public static final String CATEGORY_FILE_PATH = FileUtils.DESKTOP_PATH + "joke/" + "category.json";

    public static void getJokeCategories() {
        Document doc;
        try {
            doc = Utility.getDefaultConnection(BASE_URL)
                    .get();
            Elements mainDiv = doc.getElementsByClass("tag-cloud");
            Element firstDiv = mainDiv.first();
            Elements anchorElements = firstDiv.children();
            Category category = new Category(BASE_URL);
            for (Element element : anchorElements) {
                String name = element.text();
                String url = element.attr("href");
                CategoryItem item = new CategoryItem(name, url);
                category.addItem(item);
            }
            String json = Utility.getString(category, true);
            FileUtils.writeToFile(CATEGORY_FILE_PATH, json);
            PrintHelper.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getJokeFromCategory(CategoryItem item) {
        Document doc;
        try {
            doc = Utility.getDefaultConnection(item.getUrl())
                    .get();
            String xpathSelector = "[@id=\"main\"]/div/div[1]/div";
            System.out.println(item.toString());
            String jsoupSelector = Utility.xpathToJsoupSelector(xpathSelector);
            Elements main = doc.select(jsoupSelector);
            Element first = main.first();
            Elements anchor = first.select("article");
            Joke joke = new Joke(item.getName());
            for (Element element : anchor) {
                String title = element.select("header").first().text();
                String jokeText = element.select("div").first().text();
                joke.addItem(new JokeItem(title, jokeText));
            }
            String json = Utility.getString(joke, true);
            String path = FileUtils.DESKTOP_PATH + "joke/" + joke.getCategory() + ".json";
            FileUtils.writeToFile(path, json);
            PrintHelper.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getJokeFromCategory() {
        String json = FileUtils.readFromFile(CATEGORY_FILE_PATH);
        Category category = Utility.getObject(json, Category.class);
        for (CategoryItem item : category.getItems()) {
            getJokeFromCategory(item);
        }
    }
}
