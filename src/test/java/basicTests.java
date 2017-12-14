/**
 * Created by Dev on 14.12.2017.
 */
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import  conf.*;
import java.util.*;

import org.openqa.selenium.support.ui.WebDriverWait;

public class basicTests {
    private static WebDriver driver;
    private static String currentUrl;
    private static  UrlBuilder urlBuilder = new UrlBuilder();
    private static Env currentEnv = Env.STAGE;
    private static Page currentPage;
    private static ConsoleLogger logger = new ConsoleLogger();


    @BeforeClass
    public static void setup() {
        //-------------setting up Browser configuration ------------------------
        // System.setProperty("webdriver.chrome.driver", "Install/Dev/chromedriver_win32");
        driver = new WebDriverManager().getWebDriver(SupportedBrowsers.DEFAULT);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void tableTest(){
        boolean result = false;
        currentPage = Page.TABLE;
        currentUrl = urlBuilder.getUrl(currentEnv,currentPage);
        driver.get(currentUrl);
        String currentPageTitle = driver.getTitle();
        logger.log("--------------Test: table test-------------------");
        if(currentPageTitle.equals("Table | Semantic UI")) {
            logger.log("page '" + currentPageTitle + "'  was opened");
            result = true;
        }
        WebElement userTable = driver.findElement(By.cssSelector("table[class='ui celled table']"))
                .findElement(By.cssSelector("tbody"));
        List<WebElement> userTableTrItems = userTable.findElements(By.tagName("tr"));
        System.out.println(userTableTrItems.size());
        Assert.assertEquals(true,result);
    }

}
