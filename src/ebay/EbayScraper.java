package ebay;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.FileUtils;
import utils.PrintHelper;
import utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Subash on 8/14/16.
 */
public class EbayScraper {
    private String outputFileDirectory;

    public String parse(ArrayList<String> searchQuery, String outputFileDirectory) {
        this.outputFileDirectory = outputFileDirectory;
        ArrayList<Result> results = new ArrayList<>(searchQuery.size());
        StringBuilder builder = new StringBuilder();
        for (String query : searchQuery) {
            Result result = parseQuery(query);
            if (result != null) {
                results.add(result);
                builder.append(result.getString());
                builder.append(Data.NEW_LINE);
            }
        }
        String outputFile = outputFileDirectory + File.separator + "ebaysearchresultsubash.txt";
        FileUtils.writeToFile(outputFile, builder.toString());
        PrintHelper.print(outputFile);
        return outputFile;
    }

    private static Result parseQuery(String query) {
        String url = getUrl(query);
        PrintHelper.print(url);
        Connection connection = Utility.getDefaultConnection(url);
        try {
            Document document = connection.get();
            String xpath = "//*[@id=\"LeftNavContainer\"]";
            String jsoupPath = Utility.xpathToJsoupSelector(xpath);
            Elements elements = document.select(jsoupPath);
            Element divElement = elements.first();
            ArrayList<Data> datas = new ArrayList<>();
            if (!(divElement.children().size() < 2)) {
                Element requiredElement = divElement.children().get(1);
                Elements divElements = requiredElement.children();
                for (Element element : divElements) {
                    Elements nameElements = element.select("span[class=pnl-h]");
                    Element nameElement = nameElements.first();
                    if (nameElement == null) {
                        continue;
                    }
                    String name = nameElement.text();
                    Data data = new Data(name);
                    Elements itemElements = element.select("span[class=cbx]");
                    PrintHelper.print(itemElements);
                    for (Element e : itemElements) {
                        String item = e.text();
                        if (item != null) {
                            data.add(item);
                        }
                    }
                    datas.add(data);
                }
            }
            Result result = new Result(query, datas);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUrl(String searchItem) {
        String url = "http://www.ebay.com/sch/i.html?_from=R40&_trksid=p2050601.m570.l1313.TR0.TRC0.H0.X" + searchItem + ".TRS0&_nkw=" + searchItem + "&_sacat=0";
        return url.replaceAll(" ", "%20");
    }

}
