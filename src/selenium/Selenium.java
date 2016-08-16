package selenium;

import googletrend.GoogleParser;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import utils.PrintHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subash on 8/15/16.
 */
public class Selenium {
    public static ArrayList<String> parse(String search, String seleniumFilePath) {
        System.setProperty("webdriver.chrome.driver", seleniumFilePath);
        WebDriver driver = new ChromeDriver();
        String url = GoogleParser.getTrendsUrl(search);
        PrintHelper.print(url);
        driver.get(url);
        SeleniumUtils.bringToFront(driver);
        sleep(SeleniumUtils.WAIT_TIME_3);
        SeleniumUtils.scroll(driver, 250);
        sleep(SeleniumUtils.WAIT_TIME_3);
        SeleniumUtils.scroll(driver, 250);
        sleep(SeleniumUtils.WAIT_TIME_3);
        WebElement contentDiv = driver.findElement(By.className("content-wrap"));
        Actions actions = new Actions(driver);
        actions.moveToElement(contentDiv);
        WebElement divElement = driver.findElement(By.xpath("/html/body/div[2]/div[2]/md-content/div/div/div[4]/trends-widget/ng-include/widget/div/div/ng-include/div"));
        actions.moveToElement(divElement);
        List<WebElement> elements = divElement.findElements(By.tagName("span"));
        ArrayList<String> list = new ArrayList<>(50);
        for (WebElement element : elements) {
            String text = element.getText();
            if (text != null && !text.isEmpty()) {
                list.add(text);
            }
        }
        WebElement topElement = driver.findElement(By.xpath("//*[@id=\"select_option_40\"]"));
        PrintHelper.print(topElement.getText());
        actions.click(topElement);
        sleep(SeleniumUtils.WAIT_TIME_3 * 2);
        List<WebElement> topElements = divElement.findElements(By.tagName("span"));
        for (WebElement element : topElements) {
            String text = element.getText();
            if (text != null && !text.isEmpty()) {
                list.add(text);
            }
        }

        driver.quit();
        return list;
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}