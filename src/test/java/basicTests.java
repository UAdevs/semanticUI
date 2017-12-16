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
    private static ConsoleLogger logger = new ConsoleLogger();

    private boolean subCaseResult(WebElement element,String tagValue){
        String text = element.getText();
        boolean subResult = false;
        if(text.equals(tagValue)){
            logger.log("Pass");
            subResult = true;
        }
        else if (!text.equals(tagValue)){
            logger.log("Fail");
        }
        return subResult;
    }

    private boolean containsWarning(WebElement element){
        List<WebElement> tdList = element.findElements(By.tagName("td"));
        boolean result = false;
        for (WebElement td:tdList ) {
            List<WebElement> iList = td.findElements(By.cssSelector("i[class='attention icon']"));
            if(iList.size()>0){result = true;}
        }
        if(result){logger.log("Pass");}
        if(!result){logger.log("Fail");}
        return result;
    }
    @BeforeClass
    public static void setup() {
        //-------------setting up Browser configuration ------------------------
        // System.setProperty("webdriver.chrome.driver", "Install/Dev/chromedriver_win32");
        driver = new WebDriverManager().getWebDriver(SupportedBrowsers.DEFAULT);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
    }

    @Test
    public void tableTest(){
        currentUrl = urlBuilder.getUrl(currentEnv,Page.TABLE);
        driver.get(currentUrl);
        String currentPageTitle = driver.getTitle();
        logger.log("--------------Test: table test-------------------");
        if(currentPageTitle.equals("Table | Semantic UI")) {
            logger.log("page '" + currentPageTitle + "'  was opened");
        }
        List<WebElement> trListPositiveNegative =  driver.findElements(By.xpath("//h4[text()='Positive / Negative']/../table[@class='ui celled table']/tbody/tr"));
        boolean case1 = false;
        boolean case2_1 = false;
        boolean case2_2 = false;
        boolean case3_1 = false;
        boolean case3_2 = false;
        boolean result = false;
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jimmy")) {
                logger.log("Case: Jimmy - Approved");
                case1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Approved");
            }
        }
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("No Name Specified")) {
                logger.log("Case: No Name Specified Status - Unknown");
                case2_1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Unknown");
                logger.log("Case: No Name Specified Notes - None");
                case2_2 = subCaseResult(tr.findElement(By.xpath("td[3]")), "None");
            }
        }
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jill")) {
                logger.log("Case: Jill Status - Unknown");
                case2_1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Unknown");
                logger.log("Case: Jill Notes - None");
                case2_2 = subCaseResult(tr.findElement(By.xpath("td[3]")), "None");
            }
        }

        List<WebElement> trListWarning =  driver.findElements(By.xpath("//h4[text()='Warning']/../table[@class='ui celled table']/tbody/tr"));

        for (WebElement tr : trListWarning) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jimmy")) {
                logger.log("Case: Jimmy contains Warning");
                case3_1 = containsWarning(tr);
            }
            if (cell.getText().equals("Jamie")) {
                logger.log("Case: Jamie contains Warning");
                case3_2 = containsWarning(tr);
            }
        }
        if(case1 & case2_1 & case2_2 & case3_1 & case3_2) {result = true;}
        Assert.assertEquals(true,result);
    }
@Test public void dropdownTest(){
    currentUrl = urlBuilder.getUrl(currentEnv,Page.DROPDOWN);
    driver.get(currentUrl);

    Assert.assertEquals(true,true);
}

}
