package selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created by Subash on 8/15/16.
 */
public class SeleniumUtils {
    public static final int WAIT_TIME_3 = 3;

    public static void bringToFront(WebDriver webDriver) {
        String currentWindowHandle = webDriver.getWindowHandle();
        //run your javascript and alert code
        ((JavascriptExecutor) webDriver).executeScript("alert('Test')");
        webDriver.switchTo().alert().accept();
        //Switch back to to the window using the handle saved earlier
        webDriver.switchTo().window(currentWindowHandle);
    }

    public static void scroll(WebDriver driver, int value) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0," + value + ")", "");
    }
}
