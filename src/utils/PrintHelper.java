package utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;

/**
 * Created by Subash on 8/2/16.
 */
public class PrintHelper {

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(String[] items) {
        for (String s : items){
            System.out.println(s);
        }
    }

    public static void print(WebElement element){
        System.out.println(element.toString());
    }

    public static void print(int message) {
        System.out.println(message);
    }

    public static void print(Element element) {
        print(element.toString());
    }

    public static void print(Elements elements) {
        print("Element's size = " + elements.size());
        int i = 0;
        for (Element element : elements) {
            print(i);
            print(element.toString());
            i++;
        }
    }

    public static void print(Document document){
        print(document.toString());
    }
}
