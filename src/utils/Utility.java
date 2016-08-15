package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import joke.model.Category;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * Created by Subash on 8/2/16.
 */
public class Utility {
    public static final String REFERER_GOOGLE = "http://www.google.com";
    public static final String USERAGENT_MOZILLA = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
    public static final int DEFAULT_TIME_OUT = 10 * 1000;

    public static Connection getDefaultConnection(String url) {
        Connection connection = Jsoup.connect(url);
        connection.userAgent(USERAGENT_MOZILLA);
        connection.referrer(REFERER_GOOGLE);
        connection.timeout(DEFAULT_TIME_OUT);
        return connection;
    }



    public static Connection getDefaultConnection(String url, String username, String password) {
        String login = username + ":" + password;
        String base64login = Base64.getEncoder().encodeToString(login.getBytes(StandardCharsets.UTF_8));
        Connection connection = Jsoup.connect(url);
        connection.header("Authorization", "Basic " + base64login);
        connection.userAgent(USERAGENT_MOZILLA);
        connection.referrer(REFERER_GOOGLE);
        connection.timeout(DEFAULT_TIME_OUT);
        return connection;
    }
    public static String listToString(List<String> list, String separator) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s);
            if (separator != null) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }

    public static String getString(Object object, boolean prettyPrint) {
        if (object == null) throw new NullPointerException("Cannot pass null to Utility.getString() method");
        Gson gson;
        if (prettyPrint) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        } else {
            gson = new Gson();
        }
        return gson.toJson(object);
    }

    public static <T> T getObject(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    public static String xpathToJsoupSelector(String xpath) {
        //*[@id="main"]/div/div[1]/div
        // #main > div > div:eq(0) > div
        String[] components = xpath.split("/");
        String selector = "";
        for (int i = 0; i < components.length; i++) {
            String component = components[i];
            if (component == null || component.isEmpty()){
                continue;
            }
            System.out.println(component);
            if (component.contains("[@id=")) {// id is present
                int start = component.indexOf("\"") + 1;// neglect the quotation
                int end = component.indexOf("\"", start);
                String id = component.substring(start, end);
                PrintHelper.print(id);
                selector += "#" + id;
            } else if (component.contains("div")) {
                selector += "div";
                if (component.contains("[")) {
                    int start = component.indexOf("[") + 1;// neglect the quotation
                    int end = component.indexOf("]", start);
                    String id = component.substring(start, end);
                    int divNumber = Integer.parseInt(id) - 1;
                    PrintHelper.print(divNumber);
                    selector += ":eq(" + divNumber + ")";
                }
            }
            if (i < components.length - 1) {
                selector = appendNextElement(selector);
            }
        }
        return selector;
    }

    private static String appendNextElement(String path) {
        return path += " > ";
    }

    public static void printXpathJsoup(String xpath, String jsoup) {
        System.out.println(String.format("xpath=%s jsoup=%s", xpath, jsoup));
    }
}
