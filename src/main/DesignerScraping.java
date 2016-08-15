package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Subash on 8/2/16.
 */
public class DesignerScraping {
    public static final String BASE_URL = "http://biid.org.uk/find-a-designer/designers-a-z";

    public static void getListOfDesigners() {
        Document doc = null;
        try {
            doc = Jsoup.connect(BASE_URL).get();
            String title = doc.title();
            System.out.println("title is: " + title);
            String xPath = "//*[@id=\"page_container\"]/section/div/div/section/div";
            // document.select("#docs > div:eq(1) > h4 > a").attr("href");
            Elements elements = doc.select("#page_container div:eq(0)");
            Element requiredElement = elements.get(8);
            Elements anchorElements = requiredElement.select("a");
//            utils.PrintHelper.printElements(anchorElements);
            ArrayList<String> designerUrl = new ArrayList<>(anchorElements.size());
            for (Element element : anchorElements) {
                String url = element.attr("href");
                if (url != null) {
                    System.out.println(url);
                    designerUrl.add(url);
                }
            }
            if (!designerUrl.isEmpty()){
                File f = FileUtils.createFile(FileUtils.DESKTOP_PATH +"designer.txt");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
