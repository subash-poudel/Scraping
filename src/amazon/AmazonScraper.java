package amazon;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PrintHelper;
import utils.Utility;

import java.io.IOException;

/**
 * Created by Subash on 8/8/16.
 */
public class AmazonScraper {
    public static final String BASE_URL = "https://www.amazon.com/s/ref=nb_sb_ss_c_3_5?url=search-alias%3Dfashion-mens-clothing&field-keywords=shirts";

    public static String generateUrl(String search, String category) {
        return "https://www.amazon.com/s/ref=nb_sb_ss_c_3_5?url=search-alias%3D" + category + "&field-keywords=" + search;
    }

    public static void parse(){
        String url = generateUrl("shirt", "fashion-mens-clothing");
        Connection connection = Utility.getDefaultConnection(url);
        try {
            Document doc = connection.get();
            String xPath = "//*[@id=\"result_0\"]";
            String jsoupPath = Utility.xpathToJsoupSelector(xPath);
            Element element = doc.select(jsoupPath).first();
            PrintHelper.print(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
